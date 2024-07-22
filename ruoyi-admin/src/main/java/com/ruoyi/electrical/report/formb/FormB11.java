package com.ruoyi.electrical.report.formb;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB11 {

	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

	/**
	 * 插座接地线检测结果
	 */
	@ApiModelProperty("插座接地线检测结果")
	private String testResults;

	/**
	 * 其他隐患检测
	 */
	@ApiModelProperty("其他隐患检测")
	private String other;

	/**
	 * 判定结果 合格/不合格
	 */
	@ApiModelProperty("判定结果 合格/不合格")
	private String result;

	/**
	 * 整体外观图
	 */
	@ApiModelProperty("整体外观图")
	private String overallPic;

	/**
	 * 现场检测图
	 */
	@ApiModelProperty("现场检测图")
	private String inspectionPic;

}
