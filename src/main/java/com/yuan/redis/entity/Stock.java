package com.yuan.redis.entity;


import com.yuan.redis.service.GenericEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 库存
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "库存")
public class Stock implements GenericEntity {

    private static final long serialVersionUID = 5081846432919091193L;

    /**
     *
     */
    @ApiModelProperty(value = "", required = true)
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存", required = true)
    private Integer count;

    /**
     * 已售
     */
    @ApiModelProperty(value = "已售", required = true)
    private Integer sale;

    /**
     * 乐观锁，版本号
     */
    @ApiModelProperty(value = "乐观锁，版本号", required = true)
    private Integer version;

}
