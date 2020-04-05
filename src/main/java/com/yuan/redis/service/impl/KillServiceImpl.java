package com.yuan.redis.service.impl;

import com.yuan.redis.controller.api.common.ApiConstants;
import com.yuan.redis.controller.api.common.Result;
import com.yuan.redis.controller.api.exception.ApiException;
import com.yuan.redis.dao.ActivityGoodsDao;
import com.yuan.redis.dao.KillOrderDao;
import com.yuan.redis.entity.ActivityGoods;
import com.yuan.redis.entity.KillOrder;
import com.yuan.redis.entity.Paramap;
import com.yuan.redis.service.KillService;
import com.yuan.redis.toolkit.RedissLockUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class KillServiceImpl implements KillService {

    @Autowired
    private KillOrderDao killOrderDao;

    @Autowired
    private ActivityGoodsDao activityGoodsDao;

    @Override
    public Result<String> robGoodsByAopLock(Long userId, Long killActivityId) throws Exception {
        ActivityGoods activityGoods = activityGoodsDao.find(killActivityId);
        if (activityGoods == null) {
            return Result.jsonStringError(ApiConstants.ERROR100100, "该活动不存在");
        }
        List<KillOrder> orderList = killOrderDao.findByParams(Paramap.create().put("userId", userId).put("activityId", killActivityId));
        if (!CollectionUtils.isEmpty(orderList)) {
            return Result.jsonStringError(ApiConstants.ERROR100200, "您已经抢到该商品");
        }
        Integer goodsNum = activityGoods.getGoodsNum();
        if (goodsNum > 0) {
            saveOrderAndUpdateNum(userId, killActivityId, activityGoods);
        } else {
            return Result.jsonStringError(ApiConstants.ERROR100300, "该商品已经抢完");
        }
        return Result.jsonStringOk();
    }

    @Override
    public Result<String> robGoodsByRedissonLock(Long userId, Long killActivityId) {
        boolean res = false;
        try {
            res = RedissLockUtil.tryLock(killActivityId + "", TimeUnit.SECONDS, 1, 1);
            if (res) {
                ActivityGoods activityGoods = activityGoodsDao.find(killActivityId);
                if (activityGoods == null) {
                    return Result.jsonStringError(ApiConstants.ERROR100100, "该活动不存在");
                }
                List<KillOrder> orderList = killOrderDao.findByParams(Paramap.create().put("userId", userId).put("activityId", killActivityId));
                if (!CollectionUtils.isEmpty(orderList)) {
                    return Result.jsonStringError(ApiConstants.ERROR100200, "您已经抢到该商品");
                }
                Integer goodsNum = activityGoods.getGoodsNum();
                if (goodsNum > 0) {
                    saveOrderAndUpdateNum(userId, killActivityId, activityGoods);
                } else {
                    return Result.jsonStringError(ApiConstants.ERROR100300, "该商品已经抢完");
                }
            } else {
                return Result.jsonStringError(ApiConstants.ERROR100400, "人数过多，请再次尝试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (res) {
                RedissLockUtil.unlock(killActivityId + "");
            }
        }
        return Result.jsonStringOk();
    }

    @Override
    public Long robGoodsByRedisPush(Long userId, Long killActivityId) {
        return null;
    }

    @Override
    public Result<String> robGoodsByOptimismLock(Long userId, Long killActivityId) throws ApiException {
        ActivityGoods activityGoods = activityGoodsDao.find(killActivityId);
        if (activityGoods == null) {
            return Result.jsonStringError(ApiConstants.ERROR100100, "该活动不存在");
        }
        List<KillOrder> orderList = killOrderDao.findByParams(Paramap.create().put("userId", userId).put("activityId", killActivityId));
        if (!CollectionUtils.isEmpty(orderList)) {
            return Result.jsonStringError(ApiConstants.ERROR100200, "您已经抢到该商品");
        }
        Integer goodsNum = activityGoods.getGoodsNum();
        if (goodsNum > 0) {
            saveOrderAndUpdateNumByOptimismLock(userId, killActivityId, activityGoods);
        } else {
            return Result.jsonStringError(ApiConstants.ERROR100300, "该商品已经抢完");
        }
        return Result.jsonStringOk();
    }

    @Override
    @Transactional
    public Result<String> robGoodsByForUpdate(Long userId, Long killActivityId) throws ApiException {
        ActivityGoods activityGoods = activityGoodsDao.selectForUpdate(killActivityId);
        if (activityGoods == null) {
            return Result.jsonStringError(ApiConstants.ERROR100100, "该活动不存在");
        }
        List<KillOrder> orderList = killOrderDao.findByParams(Paramap.create().put("userId", userId).put("activityId", killActivityId));
        if (!CollectionUtils.isEmpty(orderList)) {
            return Result.jsonStringError(ApiConstants.ERROR100200, "您已经抢到该商品");
        }
        Integer goodsNum = activityGoods.getGoodsNum();
        if (goodsNum > 0) {
            saveOrderAndUpdateNumByForUpdate(userId, killActivityId, activityGoods);
        } else {
            return Result.jsonStringError(ApiConstants.ERROR100300, "该商品已经抢完");
        }
        return Result.jsonStringOk();
    }

    /**
     * 修改库存数保存订单
     *
     * @param userId
     * @param killActivityId
     * @param activityGoods
     */
    private void saveOrderAndUpdateNum(Long userId, Long killActivityId, ActivityGoods activityGoods) {
        activityGoods.setGoodsNum(activityGoods.getGoodsNum() - 1);
        activityGoodsDao.update(activityGoods);
        KillOrder killOrder = new KillOrder();
        killOrder.setActivityId(killActivityId);
        killOrder.setUserId(userId);
        killOrder.setCreateTime(new Timestamp(System.currentTimeMillis()).toString());
        killOrder.setStatus(1);
        killOrderDao.insert(killOrder);
    }

    /**
     * 修改库存数保存订单（乐观锁实现）
     *
     * @param userId
     * @param killActivityId
     * @param activityGoods
     */
    private void saveOrderAndUpdateNumByOptimismLock(Long userId, Long killActivityId, ActivityGoods activityGoods) throws ApiException {
        activityGoods.setGoodsNum(activityGoods.getGoodsNum() - 1);
        Long num = activityGoodsDao.updateByOptimismLock(activityGoods);
        if (num > 0) {
            KillOrder killOrder = new KillOrder();
            killOrder.setActivityId(killActivityId);
            killOrder.setUserId(userId);
            killOrder.setCreateTime(new Timestamp(System.currentTimeMillis()).toString());
            killOrder.setStatus(1);
            killOrderDao.insert(killOrder);
        } else {
            throw new ApiException(ApiConstants.ERROR100700, "人数过多，请再次尝试！");
        }
    }

    /**
     * 修改库存数保存订单（悲观锁实现）
     *
     * @param userId
     * @param killActivityId
     * @param activityGoods
     * @throws ApiException
     */
    public void saveOrderAndUpdateNumByForUpdate(Long userId, Long killActivityId, ActivityGoods activityGoods) throws ApiException {
        activityGoods.setGoodsNum(activityGoods.getGoodsNum() - 1);
        Long num = activityGoodsDao.update(activityGoods);
        if (num > 0) {
            KillOrder killOrder = new KillOrder();
            killOrder.setActivityId(killActivityId);
            killOrder.setUserId(userId);
            killOrder.setCreateTime(new Timestamp(System.currentTimeMillis()).toString());
            killOrder.setStatus(1);
            killOrderDao.insert(killOrder);
        } else {
            throw new ApiException(ApiConstants.ERROR100700, "人数过多，请再次尝试！");
        }
    }

}
