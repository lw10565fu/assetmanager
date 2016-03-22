package com.fujitsu.assetmanager.http.httputils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fujitsu.assetmanager.MainApplication;

public class HttpUtils {
	/**网络连接错误*/
	public static int NET_ERR = 99999;
	/**网络连接超时*/
	public static int NET_TIME_OUT = 88888;
	
	  /**检测状态*/
	  public static boolean isNetOK()
	    {
	        ConnectivityManager cm =
	            (ConnectivityManager)MainApplication.getIns().getSystemService(Context.CONNECTIVITY_SERVICE);
	        if (null != cm)
	        {
	            NetworkInfo info = cm.getActiveNetworkInfo();
	            if (null != info)
	            {
	                if (info.isConnected())
	                {
	                    return true;
	                }
	                else
	                {
	                    return false;
	                }
	            }
	        }
	        return false;
	    }

}
