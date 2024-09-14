package com.ruoyi.electrical.report.dto;

import java.util.List;

import com.ruoyi.electrical.role.domain.DetectUnit;

import lombok.Data;

@Data
public class DetectForm {

	/**
	 * 检测单位信息
	 */
	private DetectUnit detect;

	/**
	 * 业主单元信息
	 */
	private OwnerUnitInfo unit;

	/**
	 * 检测表名称
	 */
	private String name;

	/**
	 * 检测表内容
	 */
	private List<DetectFormData> data;
}
