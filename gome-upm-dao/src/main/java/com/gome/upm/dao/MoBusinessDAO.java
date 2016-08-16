package com.gome.upm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.upm.domain.MapCoordinate;
import com.gome.upm.domain.MoBusiness;
@Repository("moBusinessDAO")
public interface MoBusinessDAO {
	/**
	 * 查询种类排行
	 */
	public List<MoBusiness> searchGoodKindSort(MoBusiness moBusiness);
	/**
	 * 查询品牌排行
	 */
	public List<MoBusiness> searchGoodBrandSort(MoBusiness moBusiness);
	/**
	 * 查询商品属性分类
	 */
	public List<MoBusiness> searchGoodProperty(MoBusiness moBusiness);
	/**
	 * 充值订单
	 */
	public Integer getOrderAmountForPay(MoBusiness moBusiness);
	/**
	 * 非充值订单
	 */
	public Integer getOrderAmountForNotPay(MoBusiness moBusiness);
	/**
	 * 支付成功订单量		ALREADY
	 */
	public Integer getOrderAmountForAlreadyPay(MoBusiness moBusiness);
	/**
	 * 妥投成功订单
	 */
	public Integer getOrderAmountForDelivery(MoBusiness moBusiness);
	/**
	 * Web端
	 */
	public Integer getOrderAmountForWeb(MoBusiness moBusiness);
	/**
	 * Wap
	 */
	public Integer getOrderAmountForWap(MoBusiness moBusiness);
	/**
	 * App端
	 */
	public Integer getOrderAmountForApp(MoBusiness moBusiness);
	/**
	 * 查询订单量
	 */
	public void searchOrderCount();
	
	
	/**
	 * 查询所有订单数量
	 */
	public List<MoBusiness> searchAllOrderCountHistory(MoBusiness moBusiness);
	/**
	 * 查询成功在线支付的订单数量
	 */
	public List<MoBusiness> searchOneLineOrderCountHistory(MoBusiness moBusiness);
	
	/**
	 * 获取订单最多的前5个城市
	 * @param moBusiness
	 * @return
	 */
	public List<MoBusiness> getCityBySales(MoBusiness moBusiness);
	/**
	 * 获取城市坐标
	 * @return
	 */
	public List<MapCoordinate> getCityCoordinateList();
	
	
	/**
	 * OMS-DRG正向订单状态差异
	 * @param moBusiness
	 * @return
	 */
	public Integer getOms_Drg_forward_state_error(MoBusiness moBusiness); 
	/**
	 * OMS-POP正向订单状态差异
	 * @param moBusiness
	 * @return
	 */
	public Integer getOms_Pop_forward_state_error(MoBusiness moBusiness); 
	
	/**
	 * OMS-DRG逆向订单状态差异
	 * @param moBusiness
	 * @return
	 */
	public Integer getOms_Drg_state_error(MoBusiness moBusiness); 
	/**
	 * OMS-POP逆向订单状态差异
	 * @param moBusiness
	 * @return
	 */
	public Integer getOms_Pop_state_error(MoBusiness moBusiness); 
	/**
	 * 正向单停在CO的订单--总数
	 * @param moBusiness
	 * @return
	 */
	public Integer getCoCount(MoBusiness moBusiness); 
	/**
	 * 正向单停在CO的订单--G3PP返回状态不正确
	 * @param moBusiness
	 * @return
	 */
	public Integer getCoG3ppErrorState(MoBusiness moBusiness); 
	
	
	/**
	 * 正向单停在CO的订单--未发SO至DRG
	 * @param moBusiness
	 * @return
	 */
	public Integer getCO_Not_So_OrderForDrg(MoBusiness moBusiness);
	/**
	 * 正向单停在CO的订单--未发SO至POP
	 * @param moBusiness
	 * @return
	 */
	public Integer getCO_Not_So_OrderForPop(MoBusiness moBusiness);
	/**
	 * 正向单停在CO的订单--已发送SO至DRG
	 * @param moBusiness
	 * @return
	 */
	public Integer getCO_So_OrderForDrg(MoBusiness moBusiness);
	/**
	 * 正向单停在CO的订单--已发送SO至POP
	 * @param moBusiness
	 * @return
	 */
	public Integer getCO_So_OrderForPop(MoBusiness moBusiness);
	/**
	 * 正向单停在CO的订单--待客服处理
	 * @param moBusiness
	 * @return
	 */
	public Integer getCOOrderForPending(MoBusiness moBusiness);
	
	/**
	 * DRAGON 逆向单停在OD的订单
	 * @param moBusiness
	 * @return
	 */
	public Integer getDragonReverseODOrder(MoBusiness moBusiness);
	
	/**
	 * DRAGON 正向单停在OD的订单 ok
	 * @param moBusiness
	 * @return
	 */
	public Integer getDragonForwardODOrder(MoBusiness moBusiness);
	
	/**
	 * 查询CPS数据
	 * @param monitoVO
	 * @return
	 */
	Integer searchCpsCount(MoBusiness monitoVO);
}
