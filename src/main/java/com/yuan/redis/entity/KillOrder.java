package com.yuan.redis.entity;


import com.yuan.redis.service.GenericEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 秒杀订单
 * 
 * @author wmj
 * @version 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "秒杀订单")
public class KillOrder implements GenericEntity {

	private static final long serialVersionUID = 5081846432919091193L;

	/**  */
	@ApiModelProperty(value = "", required = true)
	private Long id;
	
	/** 活动id */
	@ApiModelProperty(value = "活动id", required = true)
	private Long activityId;
	
	/** 用户id */
	@ApiModelProperty(value = "用户id", required = true)
	private Long userId;
	
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间", required = true)
	private String createTime;
	
	/** 状态 1待支付 2代发货 */
	@ApiModelProperty(value = "状态 1待支付 2代发货", required = true)
	private Integer status;
	
}
