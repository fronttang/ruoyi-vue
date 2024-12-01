package com.ruoyi.electrical.design.vo;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.electrical.design.domain.ElectricityProjectDeviceImage.DeviceImages;

import lombok.Data;

/**
 * 设备照片集对象 electricity_project_device_image
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Data
public class ElectricityProjectDeviceImageVo {

	/** ID */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/** 项目ID */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long projectId;

	/** 项目设备id */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deviceId;

	/** 照片集名称 */
	private String imageName;

	/** 照片集类型 */
	private String imageType;

	/** 照片/视频 */
	private List<DeviceImages> images;

	/**
	 * 照片数量
	 */
	private Long count;

}
