package com.yuan.redis.dao;


import com.yuan.redis.entity.StockOrder;

import java.util.List;

/**
 * DAO - StockOrder(库存订单)
 *
 */
public interface StockOrderDao extends GenericDao<StockOrder, Long> {
    List<StockOrder> findListByPage(Object parameter);

    List<StockOrder> findListNewByPage(Object parameter);

    Long deletes(Object parameter);

    Long insert(Object parameter);

}
