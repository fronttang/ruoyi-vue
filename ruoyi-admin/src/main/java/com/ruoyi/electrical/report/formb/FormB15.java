package com.ruoyi.electrical.report.formb;

import com.ruoyi.electrical.report.annotation.Formb;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Formb("B15")
public class FormB15 extends BaseFormB {

	/**
	 * 被测设备名称
	 */
	@ApiModelProperty("被测设备名称")
	private String deviceName;

	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

	/**
	 * 辐射率
	 */
	@ApiModelProperty("辐射率")
	private String radiation;

	/**
	 * 检测温度
	 */
	@ApiModelProperty("检测温度")
	private String temperature;

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
