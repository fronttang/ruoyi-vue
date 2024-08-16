package com.ruoyi.electrical.report.formb;

import com.ruoyi.electrical.report.annotation.Formb;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Formb("B3")
public class FormB3 extends BaseFormB {

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
	 * 距可燃物距离（m）
	 */
	@ApiModelProperty("距可燃物距离")
	private String combustibles;

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
