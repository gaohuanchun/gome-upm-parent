package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.DBConnectionMapper;
import com.gome.upm.domain.DBConnection;
import com.gome.upm.service.DBConnectionService;
import com.gome.upm.service.util.DBContextHolder;
@Service
public class DBConnectionServiceImpl implements DBConnectionService {
	
	@Resource
	private DBConnectionMapper dbConnMapper;

	@Override
	public List<DBConnection> findDBConnectionListByCondition(DBConnection condition) {
		DBContextHolder.setDataSource("dataSourceFour");
		return dbConnMapper.selectDBConnectionListByConditions(condition);
	}

	@Override
	public Page<DBConnection> findDBConnectionListByPage(Page<DBConnection> page) {
		DBContextHolder.setDataSource("dataSourceFour");
		List<DBConnection> connList = dbConnMapper.selectDBConnectionListByPage(page);
		int totalResult = dbConnMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<DBConnection>(page.getPageNo(), page.getPageSize(), totalResult, connList, page.getConditions());
	}

	@Override
	public List<DBConnection> findAllDbTypes() {
		DBContextHolder.setDataSource("dataSourceFour");
		return dbConnMapper.selectAllDbTypes();
	}

}
