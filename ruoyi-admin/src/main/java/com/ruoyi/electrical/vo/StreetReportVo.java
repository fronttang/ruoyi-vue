package com.ruoyi.electrical.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class StreetReportVo {

	/**
	 * 报告ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long reportId;

	/**
	 * 报告类型1初检,2复检
	 */
	private String type;

	/**
	 * 报告状态
	 */
	private String status;

	/**
	 * 是否有驳回记录（前端需要显示是否驳回）
	 */
	private String reject;

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

}
