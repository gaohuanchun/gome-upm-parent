package com.gome.upm.common.util;

import java.util.List;

import com.gome.upm.constants.Constants;


public class Page<T> {

	private List<T> list;//对象记录结果集
	private int rowTotal;// 总记录数
	private int pageSize = Constants.WEB_PAGE_SIZE;// 默认每页记录数10条
	private int currentPage;// 当前页码
	private int beginIndex;//起始记录下标 
	private int totalPage;//总页数
	public Page(Integer rowTotal, Integer pageSize, Integer currentPage) {
		this.pageSize = pageSize;
		this.rowTotal = rowTotal;
		if(rowTotal>0){
			if(currentPage ==null || currentPage<1){
				this.currentPage =1;
			}else{
				this.currentPage = currentPage;
			}
			this.beginIndex = (this.currentPage - 1) * pageSize ; 
			this.totalPage = (rowTotal-1)/pageSize+1;
		}else{
			this.totalPage =1 ;
			this.currentPage =1;
		}
	}

	public int getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
