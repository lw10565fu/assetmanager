package com.fujitsu.assetmanager.http;


import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;

import com.loopj.android.http.JsonHttpResponseHandler;


public abstract class BaseJsonResponseHandler<T> extends JsonHttpResponseHandler {

    private static final String tag = BaseJsonResponseHandler.class.getSimpleName();
    
    /**
     * 网络连接超时
     */
    public static final int NET_TIME_OUT = 99999;
    
    /**
     * 网络错误
     */
    public static final int NET_ERR = 88888;
    
    /**
     * 界面回调
     */
    protected Handler handler;
    
    private Context mContext;
    
    
    
    public BaseJsonResponseHandler(Handler handler, Context mContext)
    {
        super();
        this.mContext = mContext;
        this.handler = handler;
    }
    
    public BaseJsonResponseHandler(Handler handler)
    {
        super();
        this.handler = handler;
    }
    
    @Override
    public void onStart()
    {
        super.onStart();
        if (null == mContext)
        {
            return;
        }
     
    }
    
    @Override
    public void onFinish()
    {
        super.onFinish();
    }
    
    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
    {
        super.onFailure(statusCode, headers, responseString, throwable);
//        log.d(tag, "responseString ="+responseString);
//        if (!HttpUtils.isNetOK())
//        {
//            handler.sendMessage(handler.obtainMessage(NET_ERR));
//            return;
//        }
//        else
//        {
//            if(null == responseString)
//            {
//                handler.sendMessage(handler.obtainMessage(NET_TIME_OUT));
//                return;
//            }
//            onResponseFailed(statusCode, responseString, throwable);
//        }
    }
    
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse)
    {
        super.onFailure(statusCode, headers, throwable, errorResponse);
//        MyLog.d(tag, "errorResponse ="+errorResponse);
//        if (!HttpUtils.isNetOK())
//        {
//            handler.sendMessage(handler.obtainMessage(NET_ERR));
//            return;
//        }
//        else
//        {
//            if(null == errorResponse)
//            {
//                handler.sendMessage(handler.obtainMessage(NET_TIME_OUT));
//                return;
//            }
//            onResponseFailed(statusCode, null, throwable);
//        }
    }
    
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
    {
        super.onFailure(statusCode, headers, throwable, errorResponse);
//        MyLog.d(tag, "errorResponse ="+errorResponse);
//        if (!HttpUtils.isNetOK())
//        {
//            handler.sendMessage(handler.obtainMessage(NET_ERR));
//            return;
//        }
//        else
//        {
//            if(null == errorResponse)
//            {
//                handler.sendMessage(handler.obtainMessage(NET_TIME_OUT));
//                return;
//            }
//            onResponseFailed(statusCode, null, throwable);
//        }
    }
    
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response)
    {
        super.onSuccess(statusCode, headers, response);
//        MyLog.d(tag, "response ="+response);
        onResponseSuccess((T)response);
    }
    
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response)
    {
        super.onSuccess(statusCode, headers, response);
//        MyLog.d(tag, "response ="+response);
        onResponseSuccess((T)response);
    }
    
    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString)
    {
        super.onSuccess(statusCode, headers, responseString);
//        MyLog.d(tag, "responseString ="+responseString);
        onResponseSuccess((T)responseString);
    }
    
   /**
    * 返回失败
    * @param statusCode
    * @param responseString
    * @param errThrowable
    */
   
    public abstract void onResponseFailed(int statusCode, String responseString, Throwable errThrowable);
    
  /**
   * 返回成功
   * @param t
   */
    public abstract void onResponseSuccess(T t);
    

	
}
