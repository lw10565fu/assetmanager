package com.fujitsu.assetmanager.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by lw on 2016/3/15.
 * 应用程序Activity管理类：用于Activity管理和应用程序�?�?
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;
    private static Activity mLastActivity = null;

    private  AppManager(){

    }
    /**
     * 单一实例
     */
    public static AppManager getAppManager(){
        if (null == instance) {
            instance = new AppManager();
        }
        return instance;
    }
    /**
     * 添加Activity到堆�?
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    /**
     * 设置�?��显示的acvitity
     */
    public void setLastActivity(Activity activity) {
        mLastActivity = activity;
    }

    /**
     * 获取�?��显示的acvitity
     */
    public Activity getLastActivity() {
        return mLastActivity;
    }
    /**
     * 获取当前Activity（堆栈中�?���?��压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }
    /**
     * 结束当前Activity（堆栈中�?���?��压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束�?��Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                //Logger.e("AppManager finishAllActivity:", activityStack.get(i).getLocalClassName());
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * �?��应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
        } catch (Exception e) {
        }
    }

    public void AppExitCurrent(Context context) {
        try {
            if (mLastActivity != null) {
                mLastActivity.finish();
            }
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

}
