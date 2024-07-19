package com.ruoyi.electrical.report.dto;

import lombok.Data;

/**
 * 检测仪器对象 detect_device
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@Data
public class DetectDeviceInfo {

	/** ID */
	private String id;

	/** 检测单位 */
	private Long detectId;

	/** 检测单位名称 */
	private String detectName;

	/** 仪器编号 */
	private String deviceId;

	/** 类型 */
	private String type;

	/** 仪器名称 */
	private String name;

	/** 校准日期 */
	private String calibrationDate;

	/** 是否过期1过期，0未过期 */
	private String isExpired;

}
