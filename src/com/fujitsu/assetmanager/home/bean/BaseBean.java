package com.fujitsu.assetmanager.home.bean;

import java.io.Serializable;

import org.json.JSONObject;

public class BaseBean implements Serializable{
	private JSONObject JsonValue;

	public JSONObject getJsonValue() {
		return JsonValue;
	}

	public void setJsonValue(JSONObject jsonValue) {
		JsonValue = jsonValue;
	}


	

}
