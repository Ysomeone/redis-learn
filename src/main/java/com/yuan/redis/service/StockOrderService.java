package com.yuan.redis.service;

import com.yuan.redis.dao.GenericDao;
import com.yuan.redis.entity.Stock;
import com.yuan.redis.entity.StockOrder;

import java.util.List;

/**
 * SERVICE - StockOrder(库存订单)
 *
 * @author
 * @version 2.0
 */
public interface StockOrderService extends GenericService<StockOrder, Long>{
    List<StockOrder> findListByPage(Object parameter);

    List<StockOrder> findListNewByPage(Object parameter);

    Long deletes(Object parameter);
}
