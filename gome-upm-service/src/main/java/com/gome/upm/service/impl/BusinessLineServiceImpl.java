package com.gome.upm.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.BusinessLineDao;
import com.gome.upm.domain.BusinessLine;
import com.gome.upm.service.BusinessLineService;

@Service("businessLineService")
public class BusinessLineServiceImpl implements BusinessLineService{
	
	@Resource
	private BusinessLineDao businessLineDao;

	@Override
	public Page<BusinessLine> loadBusinessLine(Page<BusinessLine> page) throws SQLException {
		List<BusinessLine> businessLineList = businessLineDao.selectBusinessLineListByPage(page);
		int total = businessLineDao.selectTotalResultByConditions(page.getConditions());
		return new Page<BusinessLine>(page.getPageNo(), page.getPageSize(), total, businessLineList, page.getConditions());
	}

	@Override
	public BusinessLine findBusinessLineById(String code) {
		return businessLineDao.selectBusinessLineByCode(code);
	}

	@Override
	public List<BusinessLine> loadSelectData() {
		String sql = "select bcode, bname from business_line";
		List<BusinessLine> blist = businessLineDao.getSelectData(sql);
		return blist;
	}

	@Override
	public boolean checkBusinessLineIsExist(String code) {
		int count = businessLineDao.selectCountByCode(code);
		if(count > 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int addBusinessLine(BusinessLine businessLine) {
		return businessLineDao.insertBusinessLine(businessLine);
	}
	
	@Override
	public int batchDeleteBusinessLineByIds(String[] ids) {
		return businessLineDao.batchDeleteBusinessLineByIds(ids);
	}
}
