package com.lzkj.chain.utils;

import com.google.gson.Gson;

public final class JsonUtils {
	private static Gson gson = new Gson();

	/**
	 * 把对象转为字符串
	 */
	public static String toJSONString(Object object) {
		if (null == object)
			return null;
		return gson.toJson(object);
	}

	/**
	 * 把JSON文本parse为JavaBean
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		if (json == null)
			return null;
		return gson.fromJson(json, clazz);
	}
}
