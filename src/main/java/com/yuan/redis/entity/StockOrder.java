package com.yuan.redis.entity;


import com.yuan.redis.service.GenericEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 订单
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单")
public class StockOrder implements GenericEntity {

    private static final long serialVersionUID = 5081846432919091193L;

    /**
     *
     */
    @ApiModelProperty(value = "", required = true)
    private Integer id;

    /**
     * 库存ID
     */
    @ApiModelProperty(value = "库存ID", required = true)
    private Integer sid;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", required = true)
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private String createTime;

}
