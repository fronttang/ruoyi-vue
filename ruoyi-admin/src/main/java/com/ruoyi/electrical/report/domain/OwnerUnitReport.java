package com.ruoyi.electrical.report.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 初检报告对象 owner_unit_report
 * 
 * @author fronttang
 * @date 2024-07-15
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitReport extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/** 业主单元ID */
	@Excel(name = "业主单元ID")
	private Long unitId;

	/** 是否开启隐患通知单1开启 */
	@Excel(name = "是否开启隐患通知单1开启")
	private String isDangerNotice;

	/** 是否完成1是0否 */
	@Excel(name = "是否完成1是0否")
	private String isComplete;

	/** 是否无法检测1是0否 */
	@Excel(name = "是否无法检测1是0否")
	private String isTest;

	/** 无法检测原因 */
	@Excel(name = "无法检测原因")
	private String isTestReason;

	/** 报告类型1初检,2复检 */
	@Excel(name = "报告类型1初检,2复检")
	private String type;

	/** 报告编号 */
	@Excel(name = "报告编号")
	private String code;

	/** 检测时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "检测时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date detectData;

	/** 检测状态 */
	@Excel(name = "检测状态")
	private String detectStatus;

	/** 检测员 */
	@Excel(name = "检测员")
	private String inspector;

	/** 检测员ID */
	@Excel(name = "检测员ID")
	private Long inspectorId;

	/** 报告状态 */
	@Excel(name = "报告状态")
	private String status;

	/**
	 * 驳回状态
	 */
	private String reject;

	/**
	 * 制式WORD报告
	 */
	private String wordFile;

	/**
	 * 制式WORD报告版本号
	 */
	private Integer wordFileVersion;

	/**
	 * 归档PDF报告地址
	 */
	private String archivedPdf;

	/**
	 * 归档WORD报告地址
	 */
	private String archivedWord;

	/**
	 * 归档WORD报告版本号
	 */
	private Integer archivedWordVersion;

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

	/**
	 * 编制日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date reportDate;

	public Date getReportDate() {
		if (reportDate == null) {
			return new Date();
		}
		return reportDate;
	}

}
