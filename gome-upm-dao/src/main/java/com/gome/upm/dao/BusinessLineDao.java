package com.gome.upm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gome.upm.common.Page;
import com.gome.upm.domain.BusinessLine;

public interface BusinessLineDao {
	/**
	 * 查询记录
	 * @param page
	 * @return
	 */
	List<BusinessLine> selectBusinessLineListByPage(Page<BusinessLine> page);
	
	/**
	 * 查询总记录数
	 * @param applicationInfo
	 * @return
	 */
	Integer selectTotalResultByConditions(BusinessLine businessLine);
	
	/**
	 * 根据主键查询
	 * @param code
	 * @return
	 */
	BusinessLine selectBusinessLineByCode(String id);
	
	/**
	 * 是否存在
	 * @param code
	 * @return
	 */
	Integer selectCountByCode(String code);
	
	/**
	 * 新增
	 * @param urlMonitor
	 * @return
	 */
	int insertBusinessLine(BusinessLine businessLine);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int batchDeleteBusinessLineByIds(String[] ids);
	
	/**
	 * 查询下拉框
	 * @return
	 */
	List<BusinessLine> getSelectData(@Param("paramSQL")String sql);
}
