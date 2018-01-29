package com.lzkj.chain.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.RpcContext;

/**
 * RpcContext工具类
 * @auth zone
 * @date 2017-10-14
 */
public class RpcContextUtils {

	public static String getParameter(String name) {
		RpcContext rpcContext = RpcContext.getContext();
		URL url = rpcContext.getUrl();
		return url.getParameter(name);
	}

	public static int getPort() {
		RpcContext rpcContext = RpcContext.getContext();
		URL url = rpcContext.getUrl();
		return url.getPort();
	}

	public static String getHost() {
		RpcContext rpcContext = RpcContext.getContext();
		URL url = rpcContext.getUrl();
		return url.getHost();
	}
	
	public static boolean isConsumer() {
		RpcContext rpcContext = RpcContext.getContext();
		return rpcContext.isConsumerSide();
	}
	
	public static boolean isProvider() {
		RpcContext rpcContext = RpcContext.getContext();
		return rpcContext.isProviderSide();
	}
	
	public static void put(String key, Object value) {
		RpcContext rpcContext = RpcContext.getContext();
		rpcContext.set(key, value);
	}
	
	public static Object get(String key) {
		RpcContext rpcContext = RpcContext.getContext();
		return rpcContext.get(key);
	}
	
	/**
	 * 判断在rpcContext和attachment里有没有这个值
	 * @param key
	 * @param attachment
	 * @return
	 */
	public static boolean isContains(String key, Map<String, String> attachment) {
		String attValue = attachment.get(key);
		String rpcValue = get(key) + "";
		return StringUtils.isNotBlank(attValue) && !"null".equalsIgnoreCase(attValue) && StringUtils.isNotBlank(rpcValue) && !"null".equalsIgnoreCase(rpcValue);
	}

	public static void putAttachement(String key, Object value, Map<String, String> attachment) {
		put(key, value);
		attachment.put(key, value + "");
	}
}
