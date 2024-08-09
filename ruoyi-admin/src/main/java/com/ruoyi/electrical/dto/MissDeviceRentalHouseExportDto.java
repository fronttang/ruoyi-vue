package com.ruoyi.electrical.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class MissDeviceRentalHouseExportDto {

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
	 * 所属网格
	 */
	@Excel(name = "所属网格", orderNum = "3")
	private String grid;

	/**
	 * 检测地址
	 */
	@Excel(name = "场所地址", orderNum = "4")
	private String address;

	/**
	 * 建筑层数
	 */
	@Excel(name = "地面楼层", orderNum = "5")
	private Long layers;

	/**
	 * 户数
	 */
	@Excel(name = "房屋总套数", orderNum = "6")
	private Long doorNumber;

	/**
	 * 自建房情况
	 */
	@Excel(name = "自建房情况", orderNum = "7")
	private String item1 = "出租";

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
	 * 箭头指示标志灯
	 */
	@Excel(name = "箭头指示标志灯", orderNum = "11")
	private Integer indicator;

	/**
	 * 安全出口标志灯
	 */
	@Excel(name = "安全出口标志灯", orderNum = "12")
	private Integer exitSignLights;

	/**
	 * 应急照明灯具
	 */
	@Excel(name = "应急照明灯具", orderNum = "13")
	private Integer emergencyLighting;

	/**
	 * 4kgABC干粉灭火器
	 */
	@Excel(name = "4kgABC干粉灭火器", orderNum = "14")
	private Integer kg4ABC;

	/**
	 * 独立式感烟火灾探测报警器
	 */
	@Excel(name = "独立式感烟火灾探测报警器", orderNum = "15")
	private Integer fireAlarm;

	/**
	 * 消防软管卷盘
	 */
	@Excel(name = "消防软管卷盘", orderNum = "16")
	private Integer fireHoseReels;

	/**
	 * 火灾自动报警主机
	 */
	@Excel(name = "火灾自动报警主机", orderNum = "17")
	private Integer alarmHost;

	/**
	 * 火灾声光报警器
	 */
	@Excel(name = "火灾声光报警器", orderNum = "18")
	private Integer alarm;

	/**
	 * 手动报警按钮
	 */
	@Excel(name = "手动报警按钮", orderNum = "19")
	private Integer manualAlarm;

	/**
	 * 点型光电感烟探测器
	 */
	@Excel(name = "点型光电感烟探测器", orderNum = "20")
	private Integer smokeDetectors;

}
