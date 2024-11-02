package com.ruoyi.electrical.report.dto.station;

import java.util.List;

import lombok.Data;

@Data
public class StationFormData {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 一级编号
	 */
	private String firstCode;

	/**
	 * 一级编号内容
	 */
	private String firstContent;

	/**
	 * 检测板块
	 */
	private String detectModule;

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
	 * 归属1非车载充电桩2交流充电桩
	 */
	private List<String> attribution;

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
		return dangers != null && dangers > 0 ? "发现风险点" : "未发现风险点";
	}

}
