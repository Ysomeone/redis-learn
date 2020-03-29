package com.yuan.redis.service;

import com.yuan.redis.entity.Stock;

import java.util.List;

/**
 * SERVICE - Stock(库存)
 *
 * @author
 * @version 2.0
 */
public interface StockService extends GenericService<Stock, Long> {
    List<Stock> findListByPage(Object parameter);

    List<Stock> findListNewByPage(Object parameter);

    Long deletes(Object parameter);

    /**
     * 创建订单
     *
     * @param sid 库存ID
     * @return 订单ID
     */
    Long createWrongOrder(Long sid)throws Exception;


    /**
     * 创建订单 乐观锁
     *
     * @param sid
     * @return
     * @throws Exception
     */
    Long createOptimisticOrder(Long sid)throws Exception;

    /**
     * 创建订单 悲观锁 for update
     *
     * @param sid
     * @return
     * @throws Exception
     */
    Long createPessimisticOrder(Long sid) throws Exception;
}
