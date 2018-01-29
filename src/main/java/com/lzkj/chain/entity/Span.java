package com.lzkj.chain.entity;

/**
 *
 * @auth zone
 * @date 2018-01-29
 */
public class Span {
	private String traceId;
	private String spanId;
	private String nextSpanId;
	private Long startTime;
	private Long endTime;
	private String interfaceName;
	private String methodName;
	private Object[] parmas;
	
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getSpanId() {
		return spanId;
	}
	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}
	public String getNextSpanId() {
		return nextSpanId;
	}
	public void setNextSpanId(String nextSpanId) {
		this.nextSpanId = nextSpanId;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object[] getParmas() {
		return parmas;
	}
	public void setParmas(Object[] parmas) {
		this.parmas = parmas;
	}
	
}