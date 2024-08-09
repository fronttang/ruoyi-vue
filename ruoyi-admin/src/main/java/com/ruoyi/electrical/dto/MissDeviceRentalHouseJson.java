package com.ruoyi.electrical.dto;

import lombok.Data;

@Data
public class MissDeviceRentalHouseJson {

	/**
	 * 乙级防火门
	 */
	private Integer fireDoorB;

	/**
	 * 紧急逃生口
	 */
	private Integer emergencyExit;

	/**
	 * 逃生缓降器
	 */
	private Integer escape;

	/**
	 * 箭头指示标志灯
	 */
	private Integer indicator;

	/**
	 * 安全出口标志灯
	 */
	private Integer exitSignLights;

	/**
	 * 应急照明灯具
	 */
	private Integer emergencyLighting;

	/**
	 * 4kgABC干粉灭火器
	 */
	private Integer kg4ABC;

	/**
	 * 独立式感烟火灾探测报警器
	 */
	private Integer fireAlarm;

	/**
	 * 消防软管卷盘
	 */
	private Integer fireHoseReels;

	/**
	 * 火灾自动报警主机
	 */
	private Integer alarmHost;

	/**
	 * 火灾声光报警器
	 */
	private Integer alarm;

	/**
	 * 手动报警按钮
	 */
	private Integer manualAlarm;

	/**
	 * 点型光电感烟探测器
	 */
	private Integer smokeDetectors;
}
