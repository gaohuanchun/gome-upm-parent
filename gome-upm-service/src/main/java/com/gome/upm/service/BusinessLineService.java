package com.gome.upm.service;

import java.sql.SQLException;
import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.BusinessLine;

public interface BusinessLineService {
	Page<BusinessLine> loadBusinessLine(Page<BusinessLine> page) throws SQLException;
	
	BusinessLine findBusinessLineById(String code);
	
	boolean checkBusinessLineIsExist(String code);
	
	int addBusinessLine(BusinessLine businessLine);
	
	int batchDeleteBusinessLineByIds(String[] ids);
	
	List<BusinessLine> loadSelectData();
}
