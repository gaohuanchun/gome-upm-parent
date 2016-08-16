package com.gome.upm.dao;

import org.springframework.stereotype.Repository;

import com.gome.upm.domain.MoOrderNotRechargeBO;
@Repository("moNotPayDAO")
public interface MoNotPayDAO {
	Integer searchMoNotPayCountByTime(MoOrderNotRechargeBO monitoVO);
}
