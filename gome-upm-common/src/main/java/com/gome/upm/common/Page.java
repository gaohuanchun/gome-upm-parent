package com.gome.upm.common;

import java.util.List;

public class Page<T> {
	private Integer pageSize;//每页显示多少条记录
	private Integer pageNo;//当前页码
	private Integer totalPage;//总页数
	private Integer totalResult;//总记录数
	private List<T> data;//分页数据
	private T conditions;
	private Integer start;
	
	//oracle分页，rownum从1开始
	private Integer startIndex;
	private Integer endIndex;
	
	public Page(){
		
	}
	public Page(int pageNo, int pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		//第三页---（21-30）对应数据库中的索引号是：（20-29）
		this.start = (pageNo-1)*pageSize+1-1;
		this.startIndex = (pageNo-1)*pageSize+1;
		this.endIndex = (pageNo-1)*pageSize+1+pageSize-1;
	}
	public Page(int pageNo, int pageSize, int totalResult, List<T> data, T conditions){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalResult = totalResult;
		this.data = data;
		this.conditions = conditions;
		this.totalPage = (totalResult + pageSize - 1)/pageSize;//计算总页数
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(Integer totalResult) {
		this.totalResult = totalResult;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public T getConditions() {
		return conditions;
	}
	public void setConditions(T conditions) {
		this.conditions = conditions;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

}
