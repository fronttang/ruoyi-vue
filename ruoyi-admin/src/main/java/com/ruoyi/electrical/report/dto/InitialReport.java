package com.ruoyi.electrical.report.dto;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.project.domain.Project;

import lombok.Data;

/**
 * 原始记录
 */
@Data
public class InitialReport {

	/**
	 * 编制日期
	 */
	private String createDate;

	/**
	 * 项目信息
	 */
	private Project project;

	/**
	 * 检测单位信息
	 */
	private DetectUnitInfo detect;

	/**
	 * 业主单元信息
	 */
	private OwnerUnitInfo unit;

	/**
	 * 报告信息
	 */
	private OwnerUnitReportInfo report;

	/**
	 * 设备列表
	 */
	List<DetectDeviceInfo> device;

	/**
	 * 检测表内容
	 */
	private List<DetectFormData> data;

	/**
	 * B1（带电设备红外检测-非变压器）
	 */
	private List<JSONObject> fromb1;

	/**
	 * B1（带电设备红外检测-变压器）
	 */
	private List<JSONObject> frombb1;

	/**
	 * B2（接地电阻值检测）
	 */
	private List<JSONObject> fromb2;

	/**
	 * B3（变配电装置距可燃物距离检测）
	 */
	private List<JSONObject> fromb3;

	/**
	 * B4（照明灯具距可燃物距离检测）
	 */
	private List<JSONObject> fromb4;

	/**
	 * B5（开关、插座安装高度检测）
	 */
	private List<JSONObject> fromb5;

	/**
	 * B6（室内漏电保护开关检测）
	 */
	private List<JSONObject> fromb6;

	/**
	 * B7（楼栋总漏保检测）
	 */
	private List<JSONObject> fromb7;

	/**
	 * B8（楼层总漏保检测）
	 */
	private List<JSONObject> fromb8;

	/**
	 * B9（电气设备对地电压）
	 */
	private List<JSONObject> fromb9;

	/**
	 * B10（计量电能表规格）
	 */
	private List<JSONObject> fromb10;

	/**
	 * B11（室内插座接线）
	 */
	private List<JSONObject> fromb11;

	/**
	 * B12（浴室热水器漏电保护开关）
	 */
	private List<JSONObject> fromb12;

	/**
	 * B13（绝缘电阻检测）
	 */
	private List<JSONObject> fromb13;

	/**
	 * B14（电气火灾监控探测器）
	 */
	private List<JSONObject> fromb14;

	/**
	 * B15（开关、插座温度检测）
	 */
	private List<JSONObject> fromb15;

}
