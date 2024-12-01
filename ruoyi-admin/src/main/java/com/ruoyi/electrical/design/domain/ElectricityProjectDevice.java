package com.ruoyi.electrical.design.domain;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ElectricityProjectDevice extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 项目ID */
	private Long projectId;

	/** 设备名称 */
	private String deviceName;

	/** 设备位置 */
	private String deviceLocation;

	/** 设备类型 */
	private String deviceType;

}
