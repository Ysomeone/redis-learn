package com.yuan.redis.service;

import com.yuan.redis.entity.Stock;

import java.util.List;

/**
 * SERVICE - Stock(库存)
 *
 * @author
 * @version 2.0
 */
public interface StockService   extends GenericService<Stock, Long> {
    List<Stock> findListByPage(Object parameter);

    List<Stock> findListNewByPage(Object parameter);

    Long deletes(Object parameter);
}
