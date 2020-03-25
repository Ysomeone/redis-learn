package com.yuan.redis.service.impl;

import com.yuan.redis.dao.StockDao;
import com.yuan.redis.dao.StockOrderDao;
import com.yuan.redis.entity.Stock;
import com.yuan.redis.entity.StockOrder;
import com.yuan.redis.service.StockOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * SERVICE - StockOrder(库存订单)
 *
 * @author
 * @version 2.0
 * @Transactional ***对表进行修改，添加时，记得加这个事务处理，尤其是下订单的业务，多表的业务处理***例子看GenericServiceImpl
 */
@Service
public class StockOrderServiceImpl extends GenericServiceImpl<StockOrder, Long> implements StockOrderService {

    @Autowired
    private StockOrderDao stockOrderDao;

    @Autowired
    private StockDao stockDao;


    @Autowired
    public void setGenericDao() {
        super.setGenericDao(stockOrderDao);
    }

    @Override
    public List<StockOrder> findListByPage(Object parameter) {
        return stockOrderDao.findListByPage(parameter);
    }

    @Override
    public List<StockOrder> findListNewByPage(Object parameter) {
        return stockOrderDao.findListNewByPage(parameter);
    }

    @Override
    public Long deletes(Object parameter) {
        return stockOrderDao.deletes(parameter);
    }


}
