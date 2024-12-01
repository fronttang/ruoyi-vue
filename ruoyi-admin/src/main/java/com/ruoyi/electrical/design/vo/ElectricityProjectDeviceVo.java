package com.ruoyi.electrical.design.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 电力设计项目设备对象 electricity_project_device
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Data
public class ElectricityProjectDeviceVo {

	/** ID */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/** 项目ID */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long projectId;

	/** 设备名称 */
	private String deviceName;

	/** 设备位置 */
	private String deviceLocation;

	/** 设备类型 */
	private String deviceType;

	/**
	 * 图片数量
	 */
	private Long images;

}
