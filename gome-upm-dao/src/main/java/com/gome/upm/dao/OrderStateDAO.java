package com.gome.upm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.upm.domain.OrderState;
@Repository("orderStateDAO")
public interface OrderStateDAO {
	List<OrderState> searchOrderStateListByType(OrderState orderState);
}
