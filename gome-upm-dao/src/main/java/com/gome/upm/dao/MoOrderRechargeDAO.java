package com.gome.upm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.upm.domain.MoOrderRechargeBO;

/**
 * @author fangjinwei
 */
@Repository("moOrderRechargeDAO")
public interface MoOrderRechargeDAO{
	/**
	 * @param omsOrder  开始时间  结束时间
	 * @return
	 */
	List<MoOrderRechargeBO> searchMoOrderRechargeList(MoOrderRechargeBO omsOrder);
	/**
	 * @param omsOrder  开始时间  结束时间
	 * @return
	 */
	Integer searchCountByTime(MoOrderRechargeBO omsOrder);
	/**
	 * 保存
	 * @param omsOrder
	 */
	void saveMoOrderRecharge(MoOrderRechargeBO omsOrder);
}