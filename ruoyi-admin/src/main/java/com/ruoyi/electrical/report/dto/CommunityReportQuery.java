package com.ruoyi.electrical.report.dto;

import lombok.Data;

@Data
public class CommunityReportQuery {

	/**
	 * 项目ID
	 */
	private Long projectId;

	/** 区县 */
	private String district;

	/** 街道 */
	private String street;

	/** 社区 */
	private String community;

}