package com.lzkj.chain.filter;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.TypeReference;
import com.lzkj.chain.entity.Span;
import com.lzkj.chain.utils.IdUtils;
import com.lzkj.chain.utils.JsonUtils;

/**
 * 连锁拦截
 * 
 * @auth zone
 * @date 2018-01-26
 */
public class ChainFilter implements Filter {
	private final static String TRACE_ID = "TRACE_ID";
	private final static String SPAN_ID = "SPAN_ID";
	private final static String SPAN_JSON = "SPAN_JSON";

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		RpcContext rpcContext = RpcContext.getContext();
		Result result = null;
		if (rpcContext.isConsumerSide()) {
			result = consumer(invoker, invocation, rpcContext);
		}
		if (rpcContext.isProviderSide()) {
			result = provider(invoker, invocation, rpcContext);
		}
		return result;
	}

	private Result consumer(Invoker<?> invoker, Invocation invocation, RpcContext rpcContext) {
		String traceId = IdUtils.generalId();
		String spanId = IdUtils.generalId();
		String interfaceName = invoker.getInterface().getName();
		rpcContext.setAttachment(TRACE_ID, traceId);
		rpcContext.setAttachment(SPAN_ID, spanId);

		Span span = new Span();
		span.setTraceId(traceId);
		span.setSpanId(spanId);
		span.setInterfaceName(interfaceName);
		span.setMethodName(invocation.getMethodName());
		span.setParmas(invocation.getArguments());
		span.setStartTime(System.nanoTime());

		String spanJson = JsonUtils.toJSONString(span);
		rpcContext.setAttachment(SPAN_JSON, spanJson);

		// System.out.println("\t\tConsumer " + SPAN_JSON + " is " + spanJson);
		return invoker.invoke(invocation);
	}

	private Result provider(Invoker<?> invoker, Invocation invocation, RpcContext rpcContext) {
		String methodName = invocation.getMethodName();
		String spanId = rpcContext.getAttachment(SPAN_ID);
		String spanJson = rpcContext.getAttachment(SPAN_JSON);

		String interfaceName = invoker.getInterface().getName();
		Span span = JsonUtils.parseObject(spanJson, Span.class);

		if (!interfaceName.equals(span.getInterfaceName())) {
			span.setInterfaceName(interfaceName);
		}
		if (!methodName.equals(span.getMethodName())) {
			span.setMethodName(methodName);
		}

		Result result = invoker.invoke(invocation);

		span.setEndTime(System.nanoTime());

		spanId = IdUtils.generalId();
		span.setNextSpanId(spanId);
		spanJson = JsonUtils.toJSONString(span);
		System.out.println("\t\tsave Provider " + SPAN_JSON + " is " + spanJson);
		// TODO 保存

		// 开始新的span
		if (StringUtils.isNotBlank(spanId)) {
			rpcContext.setAttachment(SPAN_ID, spanId);
			span.setSpanId(spanId);
			span.setStartTime(System.nanoTime());
			spanJson = JsonUtils.toJSONString(span);
			rpcContext.setAttachment(SPAN_JSON, spanJson);
		}
		return result;
	}
}
