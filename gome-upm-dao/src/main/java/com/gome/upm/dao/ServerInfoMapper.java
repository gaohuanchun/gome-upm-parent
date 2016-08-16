package com.gome.upm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ServerInfo;

public interface ServerInfoMapper {

    /**
     * 根据搜索条件查询正常数据
     * @param page
     * @return
     */
    List<ServerInfo> selectServerInfoListByPage(Page<ServerInfo> page);
    
    /**
     * 根据搜索条件查询正常总记录数.
     * 
     * @param serverInfo
     * @return
     * 
     */
    Integer selectTotalResultByConditions(ServerInfo serverInfo); 
    
    /**
     * 根据搜索条件查询非正常数据
     * @param page
     * @return
     */
    List<ServerInfo> selectServerInfoListByPageNotN(Page<ServerInfo> page);
    
    /**
     * 根据搜索条件查询非正常总记录数.
     * 
     * @param serverInfo
     * @return
     * 
     */
    Integer selectTotalResultByConditionsNotN(ServerInfo serverInfo); 
    
    List<ServerInfo> selectServerInfoListByPageLike(Page<ServerInfo> page);
    
    /**
     * 根据搜索条件查询总记录数.
     * 
     * @param serverInfo
     * @return
     * 
     */
    Integer selectTotalResultByConditionsLike(ServerInfo serverInfo); 
    
    /**
	 * 获取部门列表信息
	 * @return
	 */
	List<String> selectSszList();
	
	/**
	 * 获取应用名列表信息
	 * @return
	 */
	List<String> selectXmmList(@Param("sszid")Integer sszid);
	
	/**
	 * 根据xmm、ssz：项目名和部门 查询具体信息
	 * @param xmm
	 * @return
	 */
	List<ServerInfo> selectServerInfosByXmmAndSsz(ServerInfo serverIf);
	
	/**
	 * 查询排除的ida
	 * @return
	 */
	List<String> selectEliminateIdaList();
	
	/**
	 * 查询排除的ip
	 * @return
	 */
	List<String> selectEliminateIpdzList();
	
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
	 * 获取ssz对应的唯一标识
	 * @return
	 */
	List<ServerInfo> selectSszAndZidList();
	
	/**
	 * 查询异常信息
	 * @param bjzt
	 * @return
	 */
	List<ServerInfo> getInfomationWithBjzt();

}