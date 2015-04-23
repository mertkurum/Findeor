package ServiceCalls;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import Classes.RegisterRequestModel;
import common.WebServiceHelper;

/**
 * Created by mertocan on 21/04/2015.
 */
public class LoginCall extends CallServicesBase  {

    public LoginCall(String endPoint) {
        super(endPoint);
    }

    public void registerAccessToken(RegisterRequestModel bodyObject, AsyncHttpResponseHandler responseHandler)
    {
        post(EndPoint+"/RegisterExternalToken",bodyObject,responseHandler);
    }
}
