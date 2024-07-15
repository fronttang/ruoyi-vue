package com.ruoyi.electrical.danger.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 隐患数据对象 owner_unit_danger
 * 
 * @author ruoyi
 * @date 2024-07-15
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitDanger extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 单元ID */
	private Long unitId;

	/**
	 * 业主单元名称
	 */
	private String unitName;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 检测单位ID
	 */
	private Long detectId;

	/** 楼栋ID */
	private Long buildingId;

	/**
	 * 楼栋名称
	 */
	private String buildingName;

	/** 单元区域ID */
	private Long unitAreaId;

	/**
	 * 公共区域/户名称
	 */
	private String areaName;

	/** 隐患ID */
	private Long dangerId;

	/** 检测表ID */
	private Long formId;

	/** 检测表编号 */
	private String formCode;

	/** 检测表类型A/B/C */
	private String formType;

	/** 隐患等级 */
	private String level;

	/** 隐患描述 */
	private String description;

	/** 整改建议 */
	private String suggestions;

	/** 位置 */
	private String location;

	/** 隐患图片 */
	private String dangerPic;

	/** 状态 */
	private String status;

	/** 检测员 */
	private String inspector;

	/** 检测员ID */
	private Long inspectorId;

	/** 初检时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date initialTime;

	/** B类表数据 */
	private String formb;

	/** 整改员 */
	private String rectification;

	/** 整改时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rectificationDate;

	/** 复检员 */
	private String reviewer;

	/** 复检时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date reviewerDate;

	/** 整改图 */
	private String rectificationPic;

	/** 整改未通过原因 */
	private String reason;

	/** 检测图 */
	private String detectPic;

}