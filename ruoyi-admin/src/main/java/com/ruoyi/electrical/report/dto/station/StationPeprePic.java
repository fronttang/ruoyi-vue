package com.ruoyi.electrical.report.dto.station;

import lombok.Data;

@Data
public class StationPeprePic {
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 编号
	 */
	private String code;

	/**
	 * 轮次
	 */
	private Long rounds;

	/**
	 * 图片
	 */
	private String picture;
}
