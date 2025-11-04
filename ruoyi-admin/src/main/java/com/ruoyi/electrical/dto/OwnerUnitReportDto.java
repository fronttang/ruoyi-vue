package com.ruoyi.electrical.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	private String remark;

	private String operationPic;
	
	/**
	 * 报告状态
	 */
	private String detectStatus;

	/**
	 * 编制开始日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	/**
	 * 编制结束日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	private Long[] unitIds;

}
