package cn.backcar.util;

import com.google.gson.Gson;






public class JsonUtil {

	private static JsonUtil jsonUtil;

	public static JsonUtil getInstance() {

		if (jsonUtil == null) {
			jsonUtil = new JsonUtil();
		}
		return jsonUtil;
	}

	private Gson gson;

	private JsonUtil() {
		gson = new Gson();
	}

	public String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public <T> T fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

}
