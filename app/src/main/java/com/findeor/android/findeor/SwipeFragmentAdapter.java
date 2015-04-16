package com.findeor.android.findeor;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;

class SwipeFragmentAdapter extends FragmentPagerAdapter implements
        CameraHostProvider {
    protected static final String[] CONTENT = new String[] { "This", "Is", "A", "Test", };
    private DisplayCameraFragment current=null;
    private Context me;
    private int mCount = CONTENT.length;

    public SwipeFragmentAdapter(FragmentManager fm, Context currentContext) {
        super(fm);
        me = currentContext;
    }

    @Override
    public CameraHost getCameraHost() {
        return(new SimpleCameraHost(me));
    }

    @Override
    public Fragment getItem(int position) {
        if(position==1) return current.newInstance(true);
        return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return SwipeFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}