package com.fujitsu.assetmanager.http;

import org.apache.http.Header;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.fujitsu.assetmanager.http.httputils.HttpUtils;
import com.loopj.android.http.TextHttpResponseHandler;

public abstract class BaseTextResponsHandler extends TextHttpResponseHandler{
	/**网络连接超时*/
    public static final int NET_TIME_OUT = 99999;
    
    /**网络连接错误*/
    public static final int NET_ERR = 88888;
    
    private Handler handler;
    
    private Context mContext;
    
    public BaseTextResponsHandler(Handler handler, Context mContext)
    {
        super();
        this.mContext = mContext;
        this.handler = handler;
    }
    
    public BaseTextResponsHandler(Handler handler)
    {
        super();
        this.handler = handler;
    }

	@Override
	public void onFailure(int statusCode, Header[] arg1, String responseString, Throwable throwable) {
      if (!HttpUtils.isNetOK())
      {
          handler.sendMessage(handler.obtainMessage(NET_ERR));
          return;
      }
      else
      {
          if(null == responseString)
          {
              handler.sendMessage(handler.obtainMessage(NET_TIME_OUT));
              return;
          }
          onResponseFailed(statusCode, responseString, throwable);
      }
		
	}

	@Override
	public void onSuccess(int statusCode, Header[] arg1, String responseString) {
		Log.d("lwlw", responseString);
		onResponseSuccess(responseString);
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
	 * @param <T>
	   * @param t
	   */
	    public abstract void onResponseSuccess(String t);
}
