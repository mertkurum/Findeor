package ServiceCalls;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import common.WebServiceHelper;

/**
 * Created by mertocan on 23/04/2015.
 */
public class AuthCallServicesBase extends CallServicesBase{

    public AuthCallServicesBase(String endPoint)
    {
        super(endPoint);
    }

    public void get(String url,Header[] headers,String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        headers[headers.length] = new BasicHeader("Authorization",token);
        super.get(url, headers, params, responseHandler);
    }
    public void post(String url,Header[] headers,String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        headers[headers.length] = new BasicHeader("Authorization",token);
        super.post(url, headers, params, responseHandler);
    }
    public <T> void post(String url,Header[] headers,String token,T entity, AsyncHttpResponseHandler responseHandler) {
        headers[headers.length] = new BasicHeader("Authorization",token);
        super.post(url,headers,entity,responseHandler);
    }

    public void get(Header[] headers,String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        get(EndPoint, headers,token, params, responseHandler);
    }
    public void post(Header[] headers,String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(EndPoint, headers,token, params, responseHandler);
    }
    public <T> void post(Header[] headers,String token,T entity, AsyncHttpResponseHandler responseHandler) {
        post(EndPoint, headers,token, entity, responseHandler);
    }

    public void get(String url,String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Header[] headers = new Header[]{};
        get(url, headers,token, params, responseHandler);
    }
    public void post(String url,String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Header[] headers = new Header[]{};
        post(url, headers,token, params, responseHandler);
    }
    public <T> void post(String url,String token,T entity, AsyncHttpResponseHandler responseHandler) {
        Header[] headers = new Header[]{};
        post(url,headers,token,entity,responseHandler);
    }

    public void get(String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        get(EndPoint, token, params, responseHandler);
    }
    public void post(String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(EndPoint, token, params, responseHandler);
    }
    public <T> void post(String token,T entity, AsyncHttpResponseHandler responseHandler) {
        post(EndPoint, token, entity, responseHandler);
    }

}
