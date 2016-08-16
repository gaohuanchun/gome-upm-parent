package com.gome.upm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.ServerInfoMapper;
import com.gome.upm.domain.ServerInfo;
import com.gome.upm.service.SystemProcessService;
import com.gome.upm.service.util.DBContextHolder;
import com.gome.upm.service.util.SystemProcessConvertUtils;

/**
 * 系统进程监控实现类
 * @author qiaowentao
 * * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月21日    qiaowentao    新建
 * </pre>
 */

@Service
public class SystemProcessServiceImpl implements SystemProcessService {

	@Resource
	private ServerInfoMapper serverInfoMapper;
	
	@Override
	public Page<ServerInfo> findSystemProcessByPage(Page<ServerInfo> page) {
		DBContextHolder.setDataSource("dataSourceFive");
		List<ServerInfo> serverInfoList = serverInfoMapper.selectServerInfoListByPage(page);
		int totalResult = serverInfoMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<ServerInfo>(page.getPageNo(),page.getPageSize(),totalResult,serverInfoList,page.getConditions());
	}

	@Override
	public int findTotalResultByConditions(ServerInfo serverInfo) {
		DBContextHolder.setDataSource("dataSourceFive");
		return serverInfoMapper.selectTotalResultByConditions(serverInfo);
	}
	
	@Override
	public Page<ServerInfo> findSystemProcessByPageNotN(Page<ServerInfo> page) {
		DBContextHolder.setDataSource("dataSourceFive");
		List<ServerInfo> serverInfoList = serverInfoMapper.selectServerInfoListByPageNotN(page);
		int totalResult = serverInfoMapper.selectTotalResultByConditionsNotN(page.getConditions());
		return new Page<ServerInfo>(page.getPageNo(),page.getPageSize(),totalResult,serverInfoList,page.getConditions());
	}
	
	@Override
	public int findTotalResultByConditionsNotN(ServerInfo serverInfo) {
		DBContextHolder.setDataSource("dataSourceFive");
		return serverInfoMapper.selectTotalResultByConditionsNotN(serverInfo);
	}
	
	@Override
	public Page<ServerInfo> findSystemProcessByPageLike(Page<ServerInfo> page) {
		DBContextHolder.setDataSource("dataSourceFive");
		List<ServerInfo> serverInfoList = serverInfoMapper.selectServerInfoListByPageLike(page);
		int totalResult = serverInfoMapper.selectTotalResultByConditionsLike(page.getConditions());
		return new Page<ServerInfo>(page.getPageNo(),page.getPageSize(),totalResult,serverInfoList,page.getConditions());
	}
	
	@Override
	public int findTotalResultByConditionsLike(ServerInfo serverInfo) {
		DBContextHolder.setDataSource("dataSourceFive");
		return serverInfoMapper.selectTotalResultByConditionsLike(serverInfo);
	}

	@Override
	public List<String> selectSszList() {
		DBContextHolder.setDataSource("dataSourceFive");
		return serverInfoMapper.selectSszList();
	}

	@Override
	public List<String> selectXmmList(String ssz) {
		//ssz = utf8Togb2312(ssz);
		DBContextHolder.setDataSource("dataSourceFive");
		//获取ssz 对应的唯一标示
		List<ServerInfo> sszAndZidList = serverInfoMapper.selectSszAndZidList();
		List<String> xmmList = null;
		if(sszAndZidList.size() > 0){
			Integer sszid = null;
			for (ServerInfo serverInfo : sszAndZidList) {
				if(ssz.equals(serverInfo.getSsz())){
					sszid = serverInfo.getSszid();
					break;
				}
			}
			xmmList = serverInfoMapper.selectXmmList(sszid);
		}
		return xmmList;
	}

	@Override
	public List<ServerInfo> selectServerInfosByXmmAndSsz(ServerInfo serverIf) {
		DBContextHolder.setDataSource("dataSourceFive");
		List<ServerInfo> serverInfoList = serverInfoMapper.selectServerInfosByXmmAndSsz(serverIf);
		List<String> idaList = serverInfoMapper.selectEliminateIdaList();
		List<String> ipdzList = serverInfoMapper.selectEliminateIpdzList();
		List<ServerInfo> infos = new ArrayList<ServerInfo>();
		if(serverInfoList != null && serverInfoList.size() > 0){
			
			for (ServerInfo serverInfo : serverInfoList) {
				 String ida = serverInfo.getIda();
				 String xmip = serverInfo.getXmip();
				 if(idaList.size() > 0 && idaList.contains(ida) ){
					 infos.add(serverInfo);
				 }
				 if(ipdzList.size() > 0 && ipdzList.contains(xmip)){
					 infos.add(serverInfo);
				 }
			}
		}
		
		serverInfoList.removeAll(infos);
		return serverInfoList;
	}

	@Override
	public Integer selectLjbycs(String xmm) {
		DBContextHolder.setDataSource("dataSourceFive");
		return serverInfoMapper.selectLjbycs(xmm);
	}

	@Override
	public Integer selectLjfbcs(String xmm) {
		DBContextHolder.setDataSource("dataSourceFive");
		return serverInfoMapper.selectLjfbcs(xmm);
	}

	@Override
	public List<String> getVmCount(List<String> ipdzList) {
		DBContextHolder.setDataSource("dataSourceFive");
		return serverInfoMapper.getVmCount(ipdzList);
	}

	@Override
	public Integer selectSszidBySsz(String ssz) {
		DBContextHolder.setDataSource("dataSourceFive");
		//获取ssz 对应的唯一标示
		List<ServerInfo> sszAndZidList = serverInfoMapper.selectSszAndZidList();
		Integer sszid = null;
		if(sszAndZidList.size() > 0){
			
			for (ServerInfo serverInfo : sszAndZidList) {
				if(ssz.equals(serverInfo.getSsz())){
					sszid = serverInfo.getSszid();
					break;
				}
			}
		}
		return sszid;
	}

	@Override
	public List<ServerInfo> getInfomationWithBjzt() {
		DBContextHolder.setDataSource("dataSourceFive");
		return serverInfoMapper.getInfomationWithBjzt();
	}
	
	
}
