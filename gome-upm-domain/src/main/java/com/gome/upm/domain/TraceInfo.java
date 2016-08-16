package com.gome.upm.domain;

public class TraceInfo {
	/**
	 * 检索命中总记录数
	 */
	private long totalHits;

	private String traceId;

	/**
	 * 当前调用链的上级描述<br/>
	 * 如当前序号为：0.1.0时，parentLevel=0.1
	 */
	private String parentLevel;
	/**
	 * 当前调用链的本机描述<br/>
	 * 如当前序号为：0.1.0时，levelId=0
	 */
	private int levelId = 0;
	/**
	 * 调用链中单个节点的入口描述<br/>
	 * 如：java方法名，调用的RPC地址等等
	 */
	private String viewPointId = "";
	/**
	 * 节点调用开始时间
	 */
	private long startDate = System.currentTimeMillis();
	/**
	 * 节点调用花费时间
	 */
	private long cost = 0L;
	/**
	 * 节点调用的发生机器描述<br/>
	 * 包含机器名 + IP地址
	 */
	private String address = "";
	/**
	 * 节点调用的状态<br/>
	 * 0：成功<br/>
	 * 1：异常<br/>
	 * 异常判断原则：代码产生exception，并且此exception不在忽略列表中
	 */
	private byte statusCode = 0;
	/**
	 * 节点调用的错误堆栈<br/>
	 * 堆栈以JAVA的exception为主要判断依据
	 */
	private String exceptionStack;
	/**
	 * 节点类型描述<br/>
	 * 已字符串的形式描述<br/>
	 * 如：java,dubbo等
	 */
	private String spanType = "";
	/**
	 * 节点调用类型描述<br/>
	 * 
	 * @see gome.gtrace.protocol.CallType
	 */
	private String callType = "";
	/**
	 * 节点分布式类型<br/>
	 * 服务端/客户端
	 */
	private boolean isReceiver = false;
	/**
	 * 节点调用过程中的业务字段<br/>
	 * 如：业务系统设置的订单号，SQL语句等
	 */
	private String businessKey = "";
	/**
	 * 节点调用的所在进程号
	 */
	private String processNo = "";
	/**
	 * 节点调用所在的系统逻辑名称<br/>
	 * 由授权文件指定
	 */
	private String applicationId = "";
	/**
	 * 反序列化时，存储序列化前的字符串原文
	 */
	private String originData = "";
	/**
	 * 用户id<br/>
	 * 由授权文件指定
	 */
	private String userId;

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getViewPointId() {
		return viewPointId;
	}

	public void setViewPointId(String viewPointId) {
		this.viewPointId = viewPointId;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(byte statusCode) {
		this.statusCode = statusCode;
	}

	public String getExceptionStack() {
		return exceptionStack;
	}

	public void setExceptionStack(String exceptionStack) {
		this.exceptionStack = exceptionStack;
	}

	public String getSpanType() {
		return spanType;
	}

	public void setSpanType(String spanType) {
		this.spanType = spanType;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public boolean isReceiver() {
		return isReceiver;
	}

	public void setReceiver(boolean isReceiver) {
		this.isReceiver = isReceiver;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getOriginData() {
		return originData;
	}

	public void setOriginData(String originData) {
		this.originData = originData;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(long totalHits) {
		this.totalHits = totalHits;
	}
}
