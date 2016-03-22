package com.fujitsu.assetmanager.http;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fujitsu.assetmanager.home.bean.BaseBean;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class BaseDefault extends DefaultHandler {
	/**标记*/
	private String TAG = "BaseDefault";
	/**回调成功*/
	public final static int JSON_SUCCESS = 0x1234 ;
	/**界面回调*/
	public Handler mHandler;
	/**根节点*/
	private String name ="";
	/**数据对象*/
	BaseBean baseBean = new BaseBean();
	/**根标签*/
	private final static String DEFAUL_STR = "string";
	/**构造方法*/
	public  BaseDefault(Handler handler) {
		this.mHandler = handler;
	}
	 
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.name = qName;
		Log.d(TAG, "uri=" + uri + "localName=" + localName + "qName="+ qName);
				
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		Log.d(TAG, "uri=" + uri + "localName=" + localName + "qName="+ qName);				
	}
    /**
     * 获取数据标签下的数据
     */
	@Override
	public void characters(char[] ch, int start, int length)throws SAXException {
		 String data = new String(ch, start, length);
		 if(this.name.equals(DEFAUL_STR)){
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(data);
				baseBean.setJsonValue(jsonObj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Message mes = new Message();
			mes.obj = baseBean;
			mes.what = JSON_SUCCESS;
			mHandler.sendMessage(mes);
		 }
	}

}