package com.fujitsu.assetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.fujitsu.assetmanager.util.AppManager;

/**
 * Created by lw on 2016/3/15.
 * Activity父类
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);

    }

    /**
     *tipString
     * @param tipStr
     */
    protected void tip(String tipStr)
    {
        if (!TextUtils.isEmpty(tipStr))
        {
            Toast.makeText(this, tipStr, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * tipID
     * @param resId
     */
    protected void tip(int resId)
    {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 无参数启�?
     * @param mContext
     * @param clazz
     */
    protected void startWithActivity(Context mContext, Class<?> clazz)
    {
        this.startWithActivity(mContext, clazz, null);
    }

    /**
     * 有参数启�?
     * @param mContext
     * @param clazz
     * @param bundle
     */
    protected void startWithActivity(Context mContext, Class<?> clazz, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.setClass(mContext, clazz);
        if (null != bundle)
        {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }
}
