package com.yuan.redis.service.impl;

import com.yuan.redis.dao.StockDao;
import com.yuan.redis.dao.StockOrderDao;
import com.yuan.redis.entity.Paramap;
import com.yuan.redis.entity.Stock;
import com.yuan.redis.entity.StockOrder;
import com.yuan.redis.service.StockService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
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
@Log4j
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

    @Override
    public Long createWrongOrder(Long sid) {
        /**
         * 校验库存
         */
        Stock stock = checkStock(sid);
        /**
         * 扣库存
         */
        saleStock(stock);
        /**
         * 创建订单
         */
        Long id = createOrder(stock);
        return id;
    }

    @Override
    public Long createOptimisticOrder(Long sid) {
        /**
         * 校验库存
         */
        Stock stock = checkStock(sid);
        /**
         * 乐观锁更新库存
         */
        saleStockOptimistic(stock);
        /**
         * 创建订单
         */
        long id = createOrder(stock);
        return (long)(stock.getCount() - (stock.getSale() + 1));
    }


    @Override
    public Long createPessimisticOrder(Long sid) {
        /**
         * 校验库存（悲观锁 for update）
         */
        Stock stock = checkStockForUpdate(sid);
        /**
         * 更新库存
         */
        saleStock(stock);
        /**
         * 创建订单
         */
        long id = createOrder(stock);
        return (long)stock.getCount() - (stock.getSale());

    }


    public Stock checkStockForUpdate(Long sid) {
        Stock stock = stockDao.selectByPrimaryKeyForUpdate(sid);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }


    /**
     * 检查库存 ForUpdate
     *
     * @param sid
     * @return
     * @throws Exception
     */
    public Stock checkStock(Long sid) {
        Stock stock = stockDao.find(sid);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    /**
     * 更新库存
     *
     * @param stock
     */
    public void saleStock(Stock stock) {
        stock.setSale(stock.getSale() + 1);
        stockDao.update(stock);
    }

    /**
     * 创建订单
     *
     * @param stock
     * @return
     */
    private Long createOrder(Stock stock) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setSid(stock.getId());
        stockOrder.setName(stock.getName());
        Long insert = stockOrderDao.insert(stockOrder);
        return insert;
    }

    /**
     * 更新库存 乐观锁
     *
     * @param stock
     */
    private void saleStockOptimistic(Stock stock) {
        /**
         * 查询数据库，尝试更新库存
         */
        long count = stockDao.updateByOptimistic(stock);
        if (count == 0) {
            throw new RuntimeException("乐观锁并发更新库存失败");
        }
    }


}
