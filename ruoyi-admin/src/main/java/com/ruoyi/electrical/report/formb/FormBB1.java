package com.ruoyi.electrical.report.formb;

import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.ruoyi.electrical.danger.handler.IFormbDangerHandler;
import com.ruoyi.electrical.report.annotation.Formb;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Formb("BB1")
@SuppressWarnings("unused")
public class FormBB1 extends BaseFormB {

	/**
	 * 辐射率
	 */
	@ApiModelProperty("辐射率")
	private String radiation;

	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

	/**
	 * 被测设备名称
	 */
	@ApiModelProperty("被测设备名称")
	private String deviceName;

	/**
	 * 设备编号
	 */
	@ApiModelProperty("设备编号")
	private String deviceCode;

	/**
	 * 图片编号
	 */
	@ApiModelProperty("图片编号")
	private String imageCode;

	/**
	 * 最高温度（°C）
	 */
	@ApiModelProperty("最高温度")
	private String maxTemp;

	/**
	 * 额定电压（V）
	 */
	@ApiModelProperty("额定电压（V）")
	private String ratedVoltage;

	/**
	 * 额定电流（A)
	 */
	@ApiModelProperty("额定电流（A)")
	private String ratedCurrent;

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

	/**
	 * 判定结果 合格/不合格
	 */
	@ApiModelProperty("判定结果 合格/不合格")
	private String result;

	/**
	 * 合格
	 */

	private TextRenderData result1;

	/**
	 * 不合格
	 */
	private TextRenderData result2;

	public TextRenderData getResult1() {
		if (IFormbDangerHandler.QUALIFIED.equalsIgnoreCase(this.result)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getResult2() {
		if (IFormbDangerHandler.FAILURE.equalsIgnoreCase(this.result)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}
}
