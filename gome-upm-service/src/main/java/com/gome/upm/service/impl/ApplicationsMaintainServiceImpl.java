package com.gome.upm.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.gome.upm.common.Page;
import com.gome.upm.dao.ApplicationsDao;
import com.gome.upm.domain.Application;
import com.gome.upm.domain.ApplicationInfo;
import com.gome.upm.service.ApplicationsMaintainService;

@Service("applicationService")
public class ApplicationsMaintainServiceImpl implements ApplicationsMaintainService{

	@Resource
	private ApplicationsDao applicationsDao;
	
	@Override
	public int insertApplication(Application application) throws SQLException {
		return applicationsDao.insertApplication(application);
	}

	@Override
	public Page<ApplicationInfo> loadApplication(Page<ApplicationInfo> page) throws SQLException {
		List<ApplicationInfo> applicationInfoList = applicationsDao.selectApplicationListByPage(page);
		int totalResult = applicationsDao.selectTotalResultByConditions(page.getConditions());
		return new Page<ApplicationInfo>(page.getPageNo(), page.getPageSize(), totalResult, applicationInfoList, page.getConditions());
	}
	
	@Override
	public boolean checkApplicationExist(String code) {
		int count = applicationsDao.selectCountByCode(code);
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int batchDeleteAppByIds(Long[] ids) {
		return applicationsDao.batchDeleteAppByIds(ids);
	}

	@Override
	public int deleteAppById(int id) throws SQLException {
		return applicationsDao.deleteById(id);
	}
}
