package com.fujitsu.assetmanager;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;


import android.app.Application;

public class MainApplication extends Application {
	private static MainApplication instance; 
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static MainApplication getIns() {
		return instance;
	}
}
