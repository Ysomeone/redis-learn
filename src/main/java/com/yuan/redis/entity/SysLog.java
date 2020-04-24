package com.yuan.redis.entity;


import com.yuan.redis.service.GenericEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity - 日志记录表
 * 
 * @author wmj
 * @version 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "日志记录表")
public class SysLog implements GenericEntity {

	private static final long serialVersionUID = 5081846432919091193L;

	/**  */
	@ApiModelProperty(value = "", required = true)
	private Long id;
	
	/** 用户id */
	@ApiModelProperty(value = "用户id", required = true)
	private Long userId;
	
	/** 所属操作模块 */
	@ApiModelProperty(value = "所属操作模块", required = true)
	private String module;
	
	/** 操作数据 */
	@ApiModelProperty(value = "操作数据", required = true)
	private String data;
	
	/** 备注 */
	@ApiModelProperty(value = "备注", required = true)
	private String memo;
	
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间", required = true)
	private java.util.Date createTime;
	
}
