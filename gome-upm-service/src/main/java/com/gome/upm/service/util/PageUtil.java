package com.gome.upm.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gome.upm.common.Page;

public class PageUtil {
	
	/**
	 * list分页
	 * @param pageNo
	 * @param pageSize
	 * @param list
	 * @return
	 */
	public static <T> Page<T> getPage(int pageNo, int pageSize, List<T> list){
		if(pageNo < 0){
			pageNo = 1;
		}
		if(pageSize < 1){
			pageNo = 1;
		}
		Page<T> page = new Page<T>(pageNo, pageSize);
		if(list == null || list.size() == 0){
			page.setData(new ArrayList<T>());
			page.setTotalResult(0);
			return page;
		}
		
		page.setTotalResult(list.size());
		int total = list.size();
		int totalPage =  (total + pageSize - 1)/pageSize;
		
		int startResult = (pageNo-1)*pageSize >= total ? (totalPage-1)*pageSize : (pageNo-1)*pageSize;//开始索引
		int endResult = startResult+pageSize > total ? total : startResult+pageSize;
		//System.out.println("startResult:"+startResult+"|endResult:"+endResult+"|total:"+total+"|totalPage:"+totalPage);
		List<T> data = list.subList(startResult, endResult);
		page.setData(new ArrayList<T>(data));
		page.setTotalPage(totalPage);
		return page;
	}
	
	
	public static void main(String[] args) {
		String[] arr = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		Page<String> page = PageUtil.getPage(3, 5, Arrays.asList(arr));
		System.out.println(page);
		System.exit(0);
	}

}
