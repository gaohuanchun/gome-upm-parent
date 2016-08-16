package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.dao.AsmMapper;
import com.gome.upm.domain.Asm;
import com.gome.upm.service.AsmService;
import com.gome.upm.service.util.DBContextHolder;
@Service
public class AsmServiceImpl implements AsmService {
	
	@Resource
	private AsmMapper asmMapper;

	@Override
	public List<Asm> findAsmListByCondition(Asm condition) {
		DBContextHolder.setDataSource("dataSourceFour");
		return asmMapper.selectAsmListByConditions(condition);
	}

}
