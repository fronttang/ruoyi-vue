package com.ruoyi.electrical.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class MissDeviceSmallExportDto {

	/**
	 * ID
	 */
	@Excel(name = "序号", orderNum = "0")
	private Long id;

	/**
	 * 编码
	 */
	@Excel(name = "原始序号", orderNum = "1")
	private String code;

	/**
	 * 名称
	 */
	@Excel(name = "场所名称", orderNum = "2")
	private String name;

	/**
	 * 检测地址
	 */
	@Excel(name = "场所地址", orderNum = "3")
	private String address;

	/**
	 * 建筑面积
	 */
	@Excel(name = "面积", orderNum = "4")
	private String acreage;

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "负责人", orderNum = "5")
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "负责人电话", orderNum = "6")
	private String phone;

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
