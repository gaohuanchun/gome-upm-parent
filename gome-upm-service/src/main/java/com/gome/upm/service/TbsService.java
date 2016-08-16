package com.gome.upm.service;

import java.util.List;

import com.gome.upm.domain.Tbs;

/**
 * 表空间service
 * @author caowei-ds1
 *
 */
public interface TbsService {
	
	/**
	 *
	 * 根据条件查询表空间列表,不分页.
	 *
	 * @param condition
	 * 				查询条件
	 * @return
	 * 				表空间列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月18日    caowei-ds1    新建
	 * </pre>
	 */
	List<Tbs> findTbsListByCondition(Tbs condition);

}
