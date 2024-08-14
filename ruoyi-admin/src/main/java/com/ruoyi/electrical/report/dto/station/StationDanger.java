package com.ruoyi.electrical.report.dto.station;

import lombok.Data;

@Data
public class StationDanger {

	/**
	 * 充电桩名称
	 */
	private String chargingPileName;

	/**
	 * 隐患等级
	 */
	private String level;

	/**
	 * 隐患描述
	 */
	private String description;

	/**
	 * 整改建议
	 */
	private String suggestions;

	/**
	 * 位置
	 */
	private Object location;
}
