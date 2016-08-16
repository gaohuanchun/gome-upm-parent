package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ServerInfo;

/*
 * system监控service接口
 */
public interface SystemProcessService {

	
	/**
	 *
	 * 分页查询系统进程监控点列表. and 
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月21日    qiaowentao    新建
	 * </pre>
	 */
	Page<ServerInfo> findSystemProcessByPage(Page<ServerInfo> page);
	
	/**
	 * 根据条件查询监控点记录数.
	 * @param serverInfo
	 * @return
	 * 
	 */
	int findTotalResultByConditions(ServerInfo serverInfo);
	
	/**
	 *
	 * 分页查询系统进程监控点列表. and 
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月21日    qiaowentao    新建
	 * </pre>
	 */
	Page<ServerInfo> findSystemProcessByPageNotN(Page<ServerInfo> page);
	
	/**
	 * 根据条件查询监控点记录数.
	 * @param serverInfo
	 * @return
	 * 
	 */
	int findTotalResultByConditionsNotN(ServerInfo serverInfo);
	
	/**
	 *
	 * 分页查询系统进程监控点列表. like 
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月21日    qiaowentao    新建
	 * </pre>
	 */
	Page<ServerInfo> findSystemProcessByPageLike(Page<ServerInfo> page);
	
	/**
	 * 根据条件查询监控点记录数.
	 * @param serverInfo
	 * @return
	 * 
	 */
	int findTotalResultByConditionsLike(ServerInfo serverInfo);
	
	
	/**
	 * 获取部门列表信息
	 * @return
	 */
	List<String> selectSszList();
	
	/**
	 * 获取应用名列表信息
	 * @return
	 */
	List<String> selectXmmList(String ssz);
	
	/**
	 * 根据xmm、ssz：项目名和部门查询信息
	 * @param xmm
	 * @return
	 */
	List<ServerInfo> selectServerInfosByXmmAndSsz(ServerInfo serverIf);
	
	/**
	 * 查询累计编译次数
	 * @param xmm
	 * @return
	 */
	Integer selectLjbycs(String xmm);
	
	/**
	 * 查询累计发布次数
	 * @param xmm
	 * @return
	 */
	Integer selectLjfbcs(String xmm);
	
	/**
	 * 查询vm 集合
	 * @param ipdzList
	 * @return
	 */
	List<String> getVmCount(List<String> ipdzList);
	
	/**
	 * 根据ssz 查询出对应的唯一sszid
	 * @param ssz
	 * @return
	 */
	Integer selectSszidBySsz(String ssz);
	
	/**
	 * 查询异常信息
	 * @param bjzt
	 * @return
	 */
	List<ServerInfo> getInfomationWithBjzt();
}
