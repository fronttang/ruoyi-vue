package com.ruoyi.electrical.danger.domain;

import java.util.Date;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.electrical.danger.handler.FormbDangerHandlerFactory;
import com.ruoyi.electrical.danger.handler.IFormbDangerHandler;

import cn.hutool.core.util.StrUtil;
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
	 * 楼栋类型
	 */
	private String buildingType;

	/**
	 * 公共区域/户名称
	 */
	private String areaName;

	/**
	 * 公共区域/户类型
	 */
	private String areaType;

	/**
	 * 充电桩名称
	 */
	private String chargingPileName;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 项目类型
	 */
	private String projectType;

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

	/**
	 * B14的ID
	 */
	private Long b14;

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

	/**
	 * 整体外观图
	 */
	private String overallPic;

	/**
	 * 现场检测图
	 */
	private String inspectionPic;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 报告位置
	 */
	@SuppressWarnings("unused")
	private String reportLocation;

	/**
	 * 报告位置
	 * 
	 * @return
	 */
	public String getReportLocation() {

		// ProjectType type = enumutil
		// 城中村/工业园
		if ("1".equalsIgnoreCase(this.projectType) || "2".equalsIgnoreCase(this.projectType)) {

			if ("B".equalsIgnoreCase(this.formType)) {
				if (StrUtil.isNotBlank(this.formCode)) {
					IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory
							.getFormbDangerHander(this.formCode);
					if (formbDangerHander != null) {
						return formbDangerHander.getReportLocation(this);
					}
				}
			}
		}
		String unitAreaName = StrUtil.isNotBlank(this.areaName) ? this.areaName : "";
		// String buildingName = StrUtil.isNotBlank(this.buildingName) ?
		// this.buildingName : "";
		return StrUtil.format("{}{}", unitAreaName, this.location);
	}

	/**
	 * 列表及详情位置
	 * 
	 * @return
	 */
	public String getLocation() {

		// ProjectType type = enumutil
		// 城中村/工业园
		if ("1".equalsIgnoreCase(this.projectType) || "2".equalsIgnoreCase(this.projectType)) {

			if ("B".equalsIgnoreCase(this.formType)) {
				if (StrUtil.isNotBlank(this.formCode)) {
					IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory
							.getFormbDangerHander(this.formCode);
					if (formbDangerHander != null) {
						return formbDangerHander.getInfoLocation(this);
					}
				}
			}
		} else if ("4".equalsIgnoreCase(this.projectType)) {
			// 充电桩
			if (StrUtil.isNotBlank(this.chargingPileName)) {
				return StrUtil.format("{}{}", this.chargingPileName, this.location);
			}
		}

		return this.location;
	}

	public String getDescription() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getDescription(this);
				}
			}
		}

		return this.description;
	}

	public String getSuggestions() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getSuggestions(this);
				}
			}
		}

		return this.suggestions;
	}

	public String getLevel() {
		// B表隐患位置处理
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getLevel(this);
				}
			}
		}

		return this.level;
	}

	public boolean isSummary() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.isSummary(this);
				}
			}
		}
		return false;
	}

	public String getResult() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getResult(this);
				}
			}
		}
		return null;
	}

	public String getPicture() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getPicture(this);
				}
			}
		}
		return this.dangerPic;
	}

	public String getStatus() {
		if ("B".equalsIgnoreCase(this.formType)) {
			String result = getResult();
			if (IFormbDangerHandler.QUALIFIED.equals(result)) {
				// 非隐患
				return "9";
			} else if (IFormbDangerHandler.FAILURE.equals(result)) {
				return this.status;
			} else {
				return "";
			}
		}
		return this.status;
	}
}
