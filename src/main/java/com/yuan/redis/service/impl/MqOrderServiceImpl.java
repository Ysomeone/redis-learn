package com.yuan.redis.service.impl;

import com.yuan.redis.dao.MqOrderDao;
import com.yuan.redis.entity.MqOrder;
import com.yuan.redis.rabbitmq.publisher.DeadOrderPublisher;
import com.yuan.redis.service.MqOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * SERVICE - MqOrder(订单)
 *
 * @author wmj
 * @version 2.0
 * @Transactional ***对表进行修改，添加时，记得加这个事务处理，尤其是下订单的业务，多表的业务处理***例子看GenericServiceImpl
 */
@Service
public class MqOrderServiceImpl extends GenericServiceImpl<MqOrder, Long> implements MqOrderService {

    @Autowired
    private MqOrderDao mqOrderDao;

    @Autowired
    private DeadOrderPublisher deadOrderPublisher;

    @Autowired
    public void setGenericDao() {
        super.setGenericDao(mqOrderDao);
    }

	@Override
    public List<MqOrder> findListByPage(Object parameter) {
        return mqOrderDao.findListByPage(parameter);
    }

	@Override
	public List<MqOrder> findListNewByPage(Object parameter) {
        return mqOrderDao.findListNewByPage(parameter);
    }


	@Override
    public Long deletes(Object parameter) {
        return mqOrderDao.deletes(parameter);
    }

    @Override
    public Long saveOrder(Long userId) {
        MqOrder mqOrder = new MqOrder();
        mqOrder.setOrderNum("");
        mqOrder.setStatus(1);
        mqOrder.setUserId(userId);
        mqOrder.setCreateTime(new Date());
        mqOrder.setUpdateTime(new Date());
        mqOrderDao.insert(mqOrder);
        deadOrderPublisher.sendMsg(mqOrder.getId());
        return 1L;
    }


}
