package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.DBConnection;

/**
 * 
 * 数据库连接dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月12日    caowei    新建
 * </pre>
 */
public interface DBConnectionMapper {

	/**
	 *
	 * 查询数据库连接.
	 *
	 * @param condition
	 * 			条件
	 * @return
	 * 			记录列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月12日    caowei    新建
	 * </pre>
	 */
	List<DBConnection> selectDBConnectionListByConditions(DBConnection condition);

	/**
	 * 根据条件分页查询连接表记录
	 * @param page
	 * @return
	 * 2016年7月21日 下午4:07:30   caowei-ds1
	 */
	List<DBConnection> selectDBConnectionListByPage(Page<DBConnection> page);

	/**
	 * 根据条件查询连接表记录数
	 * @param page
	 * @return
	 * 2016年7月21日 下午4:07:30   caowei-ds1
	 */
	int selectTotalResultByConditions(DBConnection conditions);

	/**
	 * 查询所有的数据库类型
	 * @return
	 * 2016年7月25日 下午3:19:47   caowei-ds1
	 */
	List<DBConnection> selectAllDbTypes();

}
