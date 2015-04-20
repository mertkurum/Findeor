/***
 7  Copyright (c) 2013 CommonsWare, LLC

 Licensed under the Apache License, Version 2.0 (the "License"); you may
 not use this file except in compliance with the License. You may obtain
 a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.findeor.android.findeor;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraUtils;
import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;

public class DisplayCameraFragment extends CameraFragment implements
        OnSeekBarChangeListener {
    private static final String KEY_USE_FFC=
            "com.commonsware.cwac.camera.demo.USE_FFC";
    private MenuItem singleShotItem=null;
    private MenuItem autoFocusItem=null;
    private MenuItem takePictureItem=null;
    private MenuItem flashItem=null;
    private MenuItem recordItem=null;
    private MenuItem stopRecordItem=null;
    private MenuItem mirrorFFC=null;
    private boolean singleShotProcessing=false;
    private long lastFaceToast=0L;
    String flashMode=null;
    public static DisplayCameraFragment displayCamera;
    static DisplayCameraFragment newInstance(boolean useFFC) {
        displayCamera =new DisplayCameraFragment();
        Bundle args=new Bundle();

        args.putBoolean(KEY_USE_FFC, useFFC);
        displayCamera.setArguments(args);

        return(displayCamera);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        setHasOptionsMenu(true);

        SimpleCameraHost.Builder builder=
                new SimpleCameraHost.Builder(new DemoCameraHost(getActivity()));

        setHost(builder.useFullBleedPreview(true).build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View cameraView=
                super.onCreateView(inflater, container, savedInstanceState);
        View results=inflater.inflate(R.layout.camera, container, false);

        ((ViewGroup)results.findViewById(R.id.camera)).addView(cameraView);


        Button takePictureBut = (Button)results.findViewById(R.id.takePictureButton);

        takePictureBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                takePicture();
                displayCamera.mirrorFFC.setChecked(false);
                displayCamera.flashItem.setChecked(true);
            }
        });

        return(results);
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().invalidateOptionsMenu();
    }


    boolean isSingleShotProcessing() {
        return(singleShotProcessing);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if (fromUser) {

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // ignore
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // ignore
    }


    Contract getContract() {
        return((Contract)getActivity());
    }

    void takeSimplePicture() {
        if (singleShotItem!=null && singleShotItem.isChecked()) {
            singleShotProcessing=true;
            takePictureItem.setEnabled(false);
        }

        PictureTransaction xact=new PictureTransaction(getHost());

        if (flashItem!=null && flashItem.isChecked()) {
            xact.flashMode(flashMode);
        }

        takePicture(xact);
    }

    interface Contract {
        boolean isSingleShotMode();

        void setSingleShotMode(boolean mode);
    }

    class DemoCameraHost extends SimpleCameraHost implements
            Camera.FaceDetectionListener {
        boolean supportsFaces=false;

        public DemoCameraHost(Context _ctxt) {
            super(_ctxt);
        }

        @Override
        public boolean useFrontFacingCamera() {
            if (getArguments() == null) {
                return(false);
            }

            return(getArguments().getBoolean(KEY_USE_FFC));
        }

        @Override
        public boolean useSingleShotMode() {
            if (singleShotItem == null) {
                return(false);
            }

            return(singleShotItem.isChecked());
        }

        @Override
        public void saveImage(PictureTransaction xact, byte[] image) {
            if (useSingleShotMode()) {
                singleShotProcessing=false;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        takePictureItem.setEnabled(true);
                    }
                });

                DisplayActivity.imageToShow=image;
                startActivity(new Intent(getActivity(), DisplayActivity.class));
            }
            else {
                super.saveImage(xact, image);
            }
        }

        @Override
        public void autoFocusAvailable() {
            if (autoFocusItem != null) {
                autoFocusItem.setEnabled(true);

                if (supportsFaces)
                    startFaceDetection();
            }
        }

        @Override
        public void autoFocusUnavailable() {
            if (autoFocusItem != null) {
                stopFaceDetection();

                if (supportsFaces)
                    autoFocusItem.setEnabled(false);
            }
        }

        @Override
        public void onCameraFail(CameraHost.FailureReason reason) {
            super.onCameraFail(reason);

            Toast.makeText(getActivity(),
                    "Sorry, but you cannot use the camera now!",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public Parameters adjustPreviewParameters(Parameters parameters) {
            flashMode=
                    CameraUtils.findBestFlashModeMatch(parameters,
                            Parameters.FLASH_MODE_RED_EYE,
                            Parameters.FLASH_MODE_AUTO,
                            Parameters.FLASH_MODE_ON);


            if (parameters.getMaxNumDetectedFaces() > 0) {
                supportsFaces=true;
            }
            else {
                Toast.makeText(getActivity(),
                        "Face detection not available for this camera",
                        Toast.LENGTH_LONG).show();
            }

            return(super.adjustPreviewParameters(parameters));
        }

        @Override
        public void onFaceDetection(Face[] faces, Camera camera) {
            if (faces.length > 0) {
                long now=SystemClock.elapsedRealtime();

                if (now > lastFaceToast + 10000) {
                    Toast.makeText(getActivity(), "I see your face!",
                            Toast.LENGTH_LONG).show();
                    lastFaceToast=now;
                }
            }
        }

        @Override
        @TargetApi(16)
        public void onAutoFocus(boolean success, Camera camera) {
            super.onAutoFocus(success, camera);

            takePictureItem.setEnabled(true);
        }

        @Override
        public boolean mirrorFFC() {
            return(mirrorFFC.isChecked());
        }
    }
}