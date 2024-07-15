package com.ruoyi.electrical.dto;

import lombok.Data;

@Data
public class OwnerUnitReportDto {

	/**
	 * 项目ID
	 */
	private Long projectId;

	private Long unitId;

	/**
	 * 报告类型1初检,2复检
	 */
	private String type;

	/**
	 * 高风险类型
	 */
	private String highRiskType;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 区县
	 */
	private String district;

	/**
	 * 街道
	 */
	private String street;

	/**
	 * 社区
	 */
	private String community;

	/**
	 * 村
	 */
	private String hamlet;

}
