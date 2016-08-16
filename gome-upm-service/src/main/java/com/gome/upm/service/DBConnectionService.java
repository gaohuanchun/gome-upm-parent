package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.DBConnection;

/**
 * 数据库连接service
 * @author caowei-ds1
 *
 */
public interface DBConnectionService {
	
	/**
	 *
	 * 根据条件查询实例列表,不分页.
	 *
	 * @param condition
	 * 				查询条件
	 * @return
	 * 				连接列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月12日    caowei-ds1    新建
	 * </pre>
	 */
	List<DBConnection> findDBConnectionListByCondition(DBConnection condition);

	/**
	 * 分页查询连接数表记录
	 * @param p
	 * @return
	 * 2016年7月21日 下午4:02:22   caowei-ds1
	 */
	Page<DBConnection> findDBConnectionListByPage(Page<DBConnection> page);
	
	/**
	 * 查询所有的数据库类型
	 * @param p
	 * @return
	 * 2016年7月21日 下午4:02:22   caowei-ds1
	 */
	List<DBConnection> findAllDbTypes();

}
