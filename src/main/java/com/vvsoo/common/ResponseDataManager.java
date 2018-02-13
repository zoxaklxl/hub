package com.vvsoo.common;

import org.json.simple.JSONObject;

public class ResponseDataManager {
	
	public static String getUpdateResponseJSON(int errorcode,String newVersion,String url) {
		JSONObject object = new JSONObject();
		object.put("errorCode", errorcode);
		object.put("newVersion", newVersion);
		object.put("url", url);
		return object.toJSONString();
	}
}
