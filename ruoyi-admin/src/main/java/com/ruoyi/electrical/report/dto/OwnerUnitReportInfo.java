package com.ruoyi.electrical.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerUnitReportInfo {

	/**
	 * 报告ID
	 */
	private Long id;

	/**
	 * 检测时间
	 */
	private String detectData;

	/**
	 * 检测员
	 */
	private String inspector;

	/**
	 * 制式WORD报告版本号
	 */
	private Integer wordFileVersion;
}
