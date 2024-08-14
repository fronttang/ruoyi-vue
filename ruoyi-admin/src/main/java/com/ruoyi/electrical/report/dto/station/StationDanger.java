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
	private String location;

	/**
	 * 一级编号
	 */
	private String firstCode;

	/**
	 * 权重（集中式含储能）
	 */
	private Double weightsCEs;

	/**
	 * 权重（集中式不含储能）
	 */
	private Double weightsCNes;

	/**
	 * 权重（分散式含储能）
	 */
	private Double weightsDEs;

	/**
	 * 权重（分散式不含储能）
	 */
	private Double weightsDNes;

	/**
	 * 合并
	 */
	private boolean merge = false;
}
