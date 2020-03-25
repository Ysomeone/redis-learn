package com.yuan.redis.service.impl;

import com.yuan.redis.dao.StockDao;
import com.yuan.redis.dao.StockOrderDao;
import com.yuan.redis.entity.Stock;
import com.yuan.redis.entity.StockOrder;
import com.yuan.redis.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * SERVICE - Stock(库存)
 *
 * @author
 * @version 2.0
 * @Transactional
 */
@Service
public class StockServiceImpl extends GenericServiceImpl<Stock, Long> implements StockService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockOrderDao stockOrderDao;

    @Autowired
    public void setGenericDao() {
        super.setGenericDao(stockDao);
    }
    @Override
    public List<Stock> findListByPage(Object parameter) {
        return stockDao.findListByPage(parameter);
    }

    @Override
    public List<Stock> findListNewByPage(Object parameter) {
        return stockDao.findListNewByPage(parameter);
    }

    @Override
    public Long deletes(Object parameter) {
        return stockDao.deletes(parameter);
    }

    private Long createOrder(Stock stock) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setSid(stock.getId());
        stockOrder.setName(stock.getName());
        Long id = stockOrderDao.insert(stockOrder);
        return id;
    }



    /**
     * 更新库存
     *
     * @param stock
     */
    private void updateOptimistic(Stock stock) {
        Long update = stockDao.updateByOptimistic(stock);
    }


}
