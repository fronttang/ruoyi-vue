package com.ruoyi.electrical.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class MissDeviceSmallJson {

	/**
	 * 甲级防火门
	 */
	@Excel(name = "甲级防火门", orderNum = "7")
	private Integer fireDoorA;

	/**
	 * 乙级防火门
	 */
	@Excel(name = "乙级防火门", orderNum = "8")
	private Integer fireDoorB;

	/**
	 * 紧急逃生口
	 */
	@Excel(name = "紧急逃生口", orderNum = "9")
	private Integer emergencyExit;

	/**
	 * 逃生缓降器
	 */
	@Excel(name = "逃生缓降器", orderNum = "10")
	private Integer escape;

	/**
	 * 应急照明灯具
	 */
	@Excel(name = "应急照明灯具", orderNum = "11")
	private Integer emergencyLighting;

	/**
	 * 智能红外体感无线感烟探测报警器
	 */
	@Excel(name = "智能红外体感无线感烟探测报警器", orderNum = "12")
	private Integer smokeDetectors;

	/**
	 * 独立式感烟火灾报警器
	 */
	@Excel(name = "独立式感烟火灾报警器", orderNum = "13")
	private Integer fireAlarm;

	/**
	 * 4kg手提式干粉灭火器
	 */
	@Excel(name = "4kg手提式干粉灭火器", orderNum = "14")
	private Integer kg4ABC;

	/**
	 * 消防软管
	 */
	@Excel(name = "消防软管", orderNum = "15")
	private Integer fireHose;

	/**
	 * 简易喷淋
	 */
	@Excel(name = "简易喷淋", orderNum = "16")
	private Integer spray;

	/**
	 * 可燃气探测装置
	 */
	@Excel(name = "可燃气探测装置", orderNum = "17")
	private Integer combustible;

	/**
	 * 燃气自动切断阀
	 */
	@Excel(name = "燃气自动切断阀", orderNum = "18")
	private Integer automaticShutOff;

	/**
	 * 安全出口
	 */
	@Excel(name = "安全出口", orderNum = "19")
	private Integer exit;
}
