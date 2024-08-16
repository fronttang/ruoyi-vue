package com.ruoyi.electrical.report.dto.station;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

@Data
public class StationDanger {

	/**
	 * 检测模块
	 */
	private String formId;

	/**
	 * 隐患ID
	 */
	private Long dangerId;

	/**
	 * 检测表编号
	 */
	private String formCode;

	/**
	 * 检测表类型A/B/C
	 */
	private String formType;

	/**
	 * 检测表数据ID
	 */
	private Long formDataId;

	/**
	 * 充电桩名称
	 */
	private String chargingPileName;

	/**
	 * 充电桩ID
	 */
	private List<Long> chargingPileId;

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

	/**
	 * 位置合集
	 */
	private List<String> locations = new ArrayList<String>();

	public String getLocation() {

		if (CollUtil.isNotEmpty(locations)) {
			this.location = String.join("、", locations);
		}
		return this.location;
	}
}
