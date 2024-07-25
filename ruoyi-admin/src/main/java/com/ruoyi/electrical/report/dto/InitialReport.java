package com.ruoyi.electrical.report.dto;

import java.util.List;
import java.util.Map;

import com.ruoyi.electrical.project.domain.Project;

import lombok.Data;

/**
 * 原始记录
 */
@Data
public class InitialReport {

	/**
	 * 编制日期
	 */
	private String createDate;

	/**
	 * 项目信息
	 */
	private Project project;

	/**
	 * 检测单位信息
	 */
	private DetectUnitInfo detect;

	/**
	 * 业主单元信息
	 */
	private OwnerUnitInfo unit;

	/**
	 * 报告信息
	 */
	private OwnerUnitReportInfo report;

	/**
	 * 设备列表
	 */
	List<DetectDeviceInfo> device;

	/**
	 * 检测表内容
	 */
	private List<DetectFormData> data;

	/**
	 * B表数据
	 */
	private Map<String, List<Object>> formb;

}
