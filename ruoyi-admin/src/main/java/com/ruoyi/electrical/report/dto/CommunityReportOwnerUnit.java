package com.ruoyi.electrical.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.electrical.report.dto.high.HighDangerInfo;

import lombok.Data;

@Data
public class CommunityReportOwnerUnit {

	/**
	 * 业主单元ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 高风险类型
	 */
	private String type;

	/**
	 * 报告ID
	 */
	private Long reportId;

	/**
	 * 报告状态
	 */
	private String status;

	/**
	 * 得分
	 */
	private BigDecimal score;

	/**
	 * 隐患明细
	 */
	private List<HighDangerInfo> danger = new ArrayList<HighDangerInfo>();
}
