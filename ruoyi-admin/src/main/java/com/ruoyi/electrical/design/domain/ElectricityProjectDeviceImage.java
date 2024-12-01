package com.ruoyi.electrical.design.domain;

import java.util.List;

import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 设备照片集对象 electricity_project_device_image
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ElectricityProjectDeviceImage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 项目ID */
	private Long projectId;

	/** 项目设备id */
	private Long deviceId;

	/** 照片集名称 */
	private String imageName;

	/** 照片集类型 */
	private String imageType;

	/** 照片/视频 */
	private List<DeviceImages> images;

	@Data
	public static class DeviceImages {

		private Long id = 1L;

		/**
		 * 名称
		 */
		private String name;

		/**
		 * 图片/视频列表
		 */
		private List<String> images;
	}

}
