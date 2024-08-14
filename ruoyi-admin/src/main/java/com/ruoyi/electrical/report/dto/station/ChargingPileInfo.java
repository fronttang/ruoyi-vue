package com.ruoyi.electrical.report.dto.station;

import lombok.Data;

@Data
public class ChargingPileInfo {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 充电站ID
	 */
	private Long unitId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 自编号
	 */
	private String code;

	/**
	 * 轮次
	 */
	private Long rounds;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 型号
	 */
	private String model;

	/**
	 * 功率
	 */
	private String power;

	/**
	 * 出厂编号
	 */
	private String serialNumber;

	/**
	 * 数量
	 */
	private Integer count;

}
