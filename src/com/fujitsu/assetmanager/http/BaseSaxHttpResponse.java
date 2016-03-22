package com.fujitsu.assetmanager.http;

import org.apache.http.Header;

import android.os.Handler;
import android.util.Log;

import com.fujitsu.assetmanager.http.httputils.HttpUtils;
import com.loopj.android.http.SaxAsyncHttpResponseHandler;
import com.sun.org.apache.xml.internal.security.encryption.AgreementMethod;

/**
 * 基本解析XML类
 * 
 * @author lw
 *
 */
public  class BaseSaxHttpResponse extends SaxAsyncHttpResponseHandler<BaseDefault> {
	
    
	public BaseSaxHttpResponse(BaseDefault t) {
		super(t);
	}

	@Override
	public void onFailure(int statusCode, Header[] header, BaseDefault baseDefault) {
      if (!HttpUtils.isNetOK())
      {
    	  baseDefault.mHandler.sendEmptyMessage(HttpUtils.NET_ERR);
          return;
      }
      else
      {
    	  baseDefault.mHandler.sendEmptyMessage(HttpUtils.NET_TIME_OUT);
    	  return;
      }
	}

	@Override
	public void onSuccess(int statusCode, Header[] header, BaseDefault baseDefault) {
		if(statusCode==0){
		   baseDefault.mHandler.sendEmptyMessage(HttpUtils.NET_ERR);
		   return;
		}
		Log.d("lwlw", "success");
	}

	

}


