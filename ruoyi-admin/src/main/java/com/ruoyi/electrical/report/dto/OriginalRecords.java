package com.ruoyi.electrical.report.dto;

import java.util.List;
import java.util.Map;

import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.role.domain.DetectUnit;

import lombok.Data;

/**
 * 原始记录
 */
@Data
public class OriginalRecords {

	/**
	 * 项目信息
	 */
	private Project project;

	/**
	 * 检测单位信息
	 */
	private DetectUnit detect;

	/**
	 * 业主单元信息
	 */
	private OwnerUnitInfo unit;

	/**
	 * 
	 */
	private OwnerUnitReportInfo report;

	/**
	 * B表数据
	 */
	private Map<String, List<Object>> formb;

}
