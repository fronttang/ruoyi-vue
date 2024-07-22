package com.ruoyi.electrical.danger.domain;

import java.util.Date;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
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

	/**
	 * 业主单元名称
	 */
	private String unitName;

	/**
	 * 楼栋名称
	 */
	private String buildingName;

	/**
	 * 公共区域/户名称
	 */
	private String areaName;

	/**
	 * 充电桩名称
	 */
	private String chargingPileName;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 检测单位ID
	 */
	private Long detectId;

	/** ID */
	private Long id;

	/** 单元ID */
	@Excel(name = "单元ID")
	private Long unitId;

	/** 楼栋ID */
	@Excel(name = "楼栋ID")
	private Long buildingId;

	/** 单元区域ID */
	@Excel(name = "单元区域ID")
	private Long unitAreaId;

	/** 充电桩ID */
	@Excel(name = "充电桩ID")
	private String chargingPileId;

	/** 轮次 */
	@Excel(name = "轮次")
	private Long rounds;

	/** 隐患ID */
	@Excel(name = "隐患ID")
	private Long dangerId;

	/** 检测表ID */
	@Excel(name = "检测表ID")
	private Long formId;

	/** 检测表编号 */
	@Excel(name = "检测表编号")
	private String formCode;

	/** 检测表类型A/B/C */
	@Excel(name = "检测表类型A/B/C")
	private String formType;

	/** 检测表数据ID */
	@Excel(name = "检测表数据ID")
	private Long formDataId;

	/** 隐患等级 */
	@Excel(name = "隐患等级")
	private String level;

	/** 隐患描述 */
	@Excel(name = "隐患描述")
	private String description;

	/** 整改建议 */
	@Excel(name = "整改建议")
	private String suggestions;

	/** 位置 */
	@Excel(name = "位置")
	private String location;

	/** 隐患图片 */
	@Excel(name = "隐患图片")
	private String dangerPic;

	/** 状态 */
	@Excel(name = "状态")
	private String status;

	/** 检测员 */
	@Excel(name = "检测员")
	private String inspector;

	/** 检测员ID */
	@Excel(name = "检测员ID")
	private Long inspectorId;

	/** 初检时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "初检时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date initialTime;

	/** B类表数据 */
	@Excel(name = "B类表数据")
	private JSONObject formb;

	/** 整改员 */
	@Excel(name = "整改员")
	private String rectification;

	/** 整改时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "整改时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date rectificationDate;

	/** 复检员 */
	@Excel(name = "复检员")
	private String reviewer;

	/** 复检时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "复检时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date reviewerDate;

	/** 整改图 */
	@Excel(name = "整改图")
	private String rectificationPic;

	/** 整改未通过原因 */
	@Excel(name = "整改未通过原因")
	private String reason;

	/** 检测图 */
	@Excel(name = "检测图")
	private String detectPic;

}
