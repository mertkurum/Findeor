package common;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class WebServiceHelper {

    private static final String BASE_URL = "http://findeor.com/api/";
    private static AsyncHttpClient client;
    private static Context context;
    private static WebServiceHelper ourInstance ;
    public static Gson gson ;
    public static WebServiceHelper getInstance() {
        return ourInstance;
    }

    public static void CreateInstance(Context context)
    {
        gson = new Gson();
        ourInstance = new WebServiceHelper(context);
    }

    private WebServiceHelper(Context context) {
        client = new AsyncHttpClient();
        this.context = context;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url,Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(context,getAbsoluteUrl(url),headers, params, responseHandler);
    }

    public static void post(String url,Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(context,getAbsoluteUrl(url),headers,params,"application/json",responseHandler);
    }

    public static <T> void post(String url,Header[] headers,T entity, AsyncHttpResponseHandler responseHandler) {
        String jsonObject = gson.toJson(entity);
        HttpEntity postEntity = getRequestEntity(jsonObject);
        client.post(context,getAbsoluteUrl(url),headers,postEntity,"application/json",responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    public static HttpEntity getRequestEntity(String jsonString) {
        try {
            StringEntity entity = new StringEntity(jsonString);
            entity.setContentType("application/json");
            return entity;
        } catch (UnsupportedEncodingException e) {
            Log.e("yok", "cannot create String entity", e);
        }

        return null;
    }

}
