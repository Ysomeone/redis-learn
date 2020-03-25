package com.yuan.redis.dao;


import com.yuan.redis.entity.Stock;
import com.yuan.redis.entity.StockOrder;

import java.util.List;

/**
 * DAO - Stock(库存)
 */
public interface StockDao extends GenericDao<Stock, Long> {
    List<Stock> findListByPage(Object parameter);

    List<Stock> findListNewByPage(Object parameter);

    Long deletes(Object parameter);

    Long update(Object parameter);

    Long updateByOptimistic(Object parameter);


}
