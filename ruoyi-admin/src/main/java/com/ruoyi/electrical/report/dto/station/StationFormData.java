package com.ruoyi.electrical.report.dto.station;

import lombok.Data;

@Data
public class StationFormData {

	/**
	 * 一级编号
	 */
	private String firstCode;

	/**
	 * 一级编号内容
	 */
	private String firstContent;

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
	 * 隐患数
	 */
	private Integer dangers;

	/**
	 * 结果
	 */
	@SuppressWarnings("unused")
	private String result;

	public String getResult() {
		return dangers != null && dangers > 0 ? "有风险" : "无风险";
	}

}
