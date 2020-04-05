package com.yuan.redis.dao;


import com.yuan.redis.entity.ActivityGoods;

import java.util.List;


/**
 * DAO - ActivityGoods(秒杀活动商品)
 *
 * @author
 */
public interface ActivityGoodsDao extends GenericDao<ActivityGoods, Long> {
    List<ActivityGoods> findListByPage(Object parameter);

    List<ActivityGoods> findListNewByPage(Object parameter);

    Long deletes(Object parameter);

    Long updateByOptimismLock(Object parameter);

    ActivityGoods selectForUpdate(Long id);
}
