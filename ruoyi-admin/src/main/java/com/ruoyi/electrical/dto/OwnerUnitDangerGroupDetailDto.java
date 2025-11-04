package com.ruoyi.electrical.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OwnerUnitDangerGroupDetailDto {

	/**
	 * 业主单元列表
	 */
	private Long[] ids;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 业主单元名称
	 */
	private String name;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 初检状态
	 */
	private String initialStatus;

	/**
	 * 复检状态
	 */
	private String reviewStatus;

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

	/**
	 * 高风险类型
	 */
	private String highRiskType;

	/**
	 * 开始 初检时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startInitialDate;

	/**
	 * 结束 初检时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endInitialDate;

	/**
	 * 开始 复检时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startReviewDate;

	/**
	 * 结束 复检时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endReviewDate;

}
