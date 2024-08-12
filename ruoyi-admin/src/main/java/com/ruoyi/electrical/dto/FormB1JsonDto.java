package com.ruoyi.electrical.dto;

import com.ruoyi.electrical.report.formb.FormB1.SinglePhase;
import com.ruoyi.electrical.report.formb.FormB1.ThreePhase;

import lombok.Data;

@Data
public class FormB1JsonDto {

	/**
	 * 辐射率
	 */
	private String radiation;

	/**
	 * 天气
	 */
	private String weather;

	/**
	 * 测试距离（m)
	 */
	private String distance;

	/**
	 * 风速（m/s）
	 */
	private String windSpeed;

	/**
	 * 检测时间 yyyy-MM-dd
	 */
	private String detectionTime;

	/**
	 * 被测设备名称
	 */
	private String deviceName;

	/**
	 * 设备编号
	 */
	private String deviceCode;

	/**
	 * 图片编号
	 */
	private String imageCode;

	/**
	 * 额定电压（V）
	 */
	private String ratedVoltage;

	/**
	 * 额定电流（A)
	 */
	private String ratedCurrent;

	/**
	 * 整体外观图
	 */
	private String overallPic;

	/**
	 * 现场检测图
	 */
	private String inspectionPic;

	/**
	 * 单相/三相
	 */
	private String type;

	/**
	 * 三相检测
	 */
	private ThreePhase threePhase;

	/**
	 * 单相检测
	 */
	private SinglePhase singlePhase;

	/**
	 * 判定结果 合格/不合格
	 */
	private String result;

	/**
	 * 红外判定图
	 */
	private String infraredPic;

}
