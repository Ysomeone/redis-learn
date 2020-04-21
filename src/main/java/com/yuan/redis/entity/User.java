package com.yuan.redis.entity;


import com.yuan.redis.service.GenericEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 用户
 * 
 * @author wmj
 * @version 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户")
public class User implements GenericEntity {

	private static final long serialVersionUID = 5081846432919091193L;

	/** ID */
	@ApiModelProperty(value = "ID", required = true)
	private Long id;
	
	/** 用户名称 */
	@ApiModelProperty(value = "用户名称", required = true)
	private String username;
	
	/** 用户密码 */
	@ApiModelProperty(value = "用户密码", required = true)
	private String password;
	
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间", required = true)
	private java.util.Date createTime;
	
	/** 更新时间 */
	@ApiModelProperty(value = "更新时间", required = true)
	private java.util.Date updateTime;
	
}
