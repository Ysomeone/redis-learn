package com.yuan.redis.entity;


import com.yuan.redis.service.GenericEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * Entity - 秒杀活动商品
 * 
 * @author wmj
 * @version 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "秒杀活动商品")
@Entity
@Table(name = "kill_activity_goods")
public class ActivityGoods implements GenericEntity{

	private static final long serialVersionUID = 5081846432919091193L;

	/** ID */
	@ApiModelProperty(value = "ID", required = true)
	private Long id;
	
	/** 商品名称 */
	@ApiModelProperty(value = "商品名称", required = true)
	private String goodsName;
	
	/** 商品总数量 */
	@ApiModelProperty(value = "商品总数量", required = true)
	private Integer goodsNum;
	
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间", required = true)
	private String createTime;
	

	
}
