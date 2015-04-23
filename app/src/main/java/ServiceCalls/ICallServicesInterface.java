package ServiceCalls;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * Created by mertocan on 23/04/2015.
 */
public interface ICallServicesInterface {

    void get(String url,Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler);
    void post(String url,Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler);
    <T> void  post(String url,Header[] headers,T entity, AsyncHttpResponseHandler responseHandler);
    void get(Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler);
    void post(Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler);
    <T> void post(Header[] headers,T entity, AsyncHttpResponseHandler responseHandler);

}
