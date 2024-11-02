package com.ruoyi.electrical.project.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 充电桩对象 charging_pile
 * 
 * @author ruoyi
 * @date 2024-07-18
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ChargingPile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/**
	 * 原始充电桩ID
	 */
	private Long originalId;

	/** 充电站ID */
	@Excel(name = "充电站ID")
	private Long unitId;

	/** 名称 */
	@Excel(name = "名称")
	private String name;

	/** 自编号 */
	@Excel(name = "自编号")
	private String code;

	/** 轮次 */
	@Excel(name = "轮次")
	private Long rounds;

	/** 类型 */
	@Excel(name = "类型")
	private String type;

	/** 品牌 */
	@Excel(name = "品牌")
	private String brand;

	/** 型号 */
	@Excel(name = "型号")
	private String model;

	/** 功率 */
	@Excel(name = "功率")
	private String power;

	/** 出厂编号 */
	@Excel(name = "出厂编号")
	private String serialNumber;
}
