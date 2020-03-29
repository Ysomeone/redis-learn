package com.yuan.redis.dao;


import com.yuan.redis.entity.KillOrder;

import java.util.List;

/**
 * DAO - KillOrder(秒杀订单)
 */
public interface KillOrderDao extends GenericDao<KillOrder, Long> {

    List<KillOrder> findListByPage(Object parameter);

    List<KillOrder> findListNewByPage(Object parameter);

    Long deletes(Object parameter);
}
