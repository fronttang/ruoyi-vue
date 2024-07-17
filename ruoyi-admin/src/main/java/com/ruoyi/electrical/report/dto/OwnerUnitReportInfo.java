package com.ruoyi.electrical.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerUnitReportInfo {

	/**
	 * 检测时间
	 */
	private String detectData;

	/**
	 * 检测员
	 */
	private String inspector;
}
