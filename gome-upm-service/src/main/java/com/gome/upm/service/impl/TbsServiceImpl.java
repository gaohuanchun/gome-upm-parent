package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.dao.TbsMapper;
import com.gome.upm.domain.Tbs;
import com.gome.upm.service.TbsService;
import com.gome.upm.service.util.DBContextHolder;
@Service
public class TbsServiceImpl implements TbsService {
	
	@Resource
	private TbsMapper tbsMapper;

	@Override
	public List<Tbs> findTbsListByCondition(Tbs condition) {
		DBContextHolder.setDataSource("dataSourceFour");
		return tbsMapper.selectTbsListByConditions(condition);
	}

}
