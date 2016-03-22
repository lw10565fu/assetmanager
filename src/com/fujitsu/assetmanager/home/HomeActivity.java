package com.fujitsu.assetmanager.home;


import org.apache.http.Header;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.TextView;

import com.fujitsu.assetmanager.BaseActivity;
import com.fujitsu.assetmanager.R;
import com.fujitsu.assetmanager.home.bean.BaseBean;
import com.fujitsu.assetmanager.http.BaseDefault;
import com.fujitsu.assetmanager.http.BaseSaxHttpResponse;
import com.fujitsu.assetmanager.http.BaseTextResponsHandler;
import com.fujitsu.assetmanager.http.CommonClient;
import com.loopj.android.http.SaxAsyncHttpResponseHandler;

/**
 * 应用主页面
 * @author lw
 *
 */
public class HomeActivity extends BaseActivity{
	/**当前时间*/
    private TextView curTime;
    /**用户*/
    private  TextView userName;
    /**主要功能*/
    private GridView mGridView;
    private String [] itemName = {"资产入库","资产出库","资产查询","资产报废","资产盘点","扫一扫"};
    private String drs;
    
    
    private Handler mHandler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case BaseTextResponsHandler.NET_TIME_OUT:
				tip("服务器连接超时");
				break;
			case BaseDefault.JSON_SUCCESS:
				BaseBean baseBean= (BaseBean) msg.obj;
				tip(baseBean.getJsonValue().toString());
				break;

			default:
				break;
			}
    	}
    };
    
    private SaxAsyncHttpResponseHandler<BaseDefault> sax = new SaxAsyncHttpResponseHandler<BaseDefault>(new BaseDefault(mHandler)) {
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, BaseDefault arg2) {
			
		}
		
		@Override
		public void onFailure(int arg0, Header[] arg1, BaseDefault arg2) {
			
			
		}
	};
	
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_home);
    	initView();
    	initOk();
    	
//    	initData();
//    	initOk();
    }

    private void initView(){
         curTime = (TextView) findViewById(R.id.tv_cur_time);
         userName = (TextView) findViewById(R.id.tv_user_name);
     }
    
    private void initOk(){
    	String url = "http://192.168.4.70:8092/Service1.asmx/DownLoadCheckPlanList?_nPlanid=4";
    	CommonClient.get(url, new BaseSaxHttpResponse(new BaseDefault(mHandler)));
    	
    }
    
}
