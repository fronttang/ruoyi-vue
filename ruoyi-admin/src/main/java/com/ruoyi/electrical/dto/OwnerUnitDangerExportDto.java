package com.ruoyi.electrical.dto;

import lombok.Data;

@Data
public class OwnerUnitDangerExportDto {

	/**
	 * 一级编号
	 */
	private String firstCode;

	/**
	 * 一级编号内容
	 */
	private String firstContent;

	/**
	 * 最高扣分数
	 */
	private Double maxScore;

	/**
	 * 扣分
	 */
	private Double score;

	/**
	 * 检测表ID
	 */
	private Long formId;

	/**
	 * 检测表名称
	 */
	private String formName;

	/**
	 * 检测表数据ID
	 */
	private Long formDataId;

	/**
	 * 检测表数据名称
	 */
	private String formDataName;

	/**
	 * 隐患名称
	 */
	private Long dangerId;

	/**
	 * 检测项最大扣分
	 */
	private Double dataMaxScore;

	/**
	 * 计分模式
	 */
	private String accMethod;

	/**
	 * 隐患描述
	 */
	private String description;

	/**
	 * 整改建议
	 */
	private String suggestions;

	/**
	 * 隐患图片
	 */
	private String dangerPic;

	/**
	 * 检测员ID
	 */
	private Long inspectorId;

	/**
	 * 隐患等级
	 */
	private String level;

	/**
	 * 整体外观图
	 */
	private String overallPic;

	/**
	 * 现场检测图
	 */
	private String inspectionPic;

	/**
	 * 整改图
	 */
	private String rectificationPic;

}
