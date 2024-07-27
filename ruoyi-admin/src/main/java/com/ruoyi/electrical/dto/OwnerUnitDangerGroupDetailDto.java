package com.ruoyi.electrical.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OwnerUnitDangerGroupDetailDto {

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
	private Date startInitialDate;

	/**
	 * 结束 初检时间
	 */
	private Date endInitialDate;

	/**
	 * 开始 复检时间
	 */
	private Date startReviewDate;

	/**
	 * 结束 复检时间
	 */
	private Date endReivewDate;

}
