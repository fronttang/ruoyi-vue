package com.ruoyi.electrical.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class OwnerUnitReportVo {

	/**
	 * 报告ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long reportId;

	/**
	 * 业主单元ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long unitId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 报告类型1初检,2复检
	 */
	private String type;

	/**
	 * 高风险类型
	 */
	private String highRiskType;

	/**
	 * 报告状态
	 */
	private String status;

	/**
	 * 是否有驳回记录（前端需要显示是否驳回）
	 */
	private String reject;

	/**
	 * 检测单位ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long detectId;

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
	 * 制式WORD报告
	 */
	private String wordFile;

	/**
	 * 归档PDF报告地址
	 */
	private String archivedPdf;

	/**
	 * 归档word报告地址
	 */
	private String archivedWord;

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

}
