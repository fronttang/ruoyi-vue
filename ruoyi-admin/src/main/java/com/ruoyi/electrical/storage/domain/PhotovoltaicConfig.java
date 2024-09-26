package com.ruoyi.electrical.storage.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 光伏参数对象 photovoltaic_config
 * 
 * @author fronttang
 * @date 2024-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhotovoltaicConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id = 1L;

	/** 光伏发电时间段（峰、平时段加权平均电价（度/元） */
	@Excel(name = "光伏发电时间段", readConverterExp = "峰=、平时段加权平均电价（度/元")
	private BigDecimal averagePrice;

	/** 首年衰减率（%） */
	@Excel(name = "首年衰减率", readConverterExp = "%=")
	private BigDecimal firstDecayRate;

	/** 逐步衰减率（%） */
	@Excel(name = "逐步衰减率", readConverterExp = "%=")
	private BigDecimal stepDecayRate;

	/** 光伏组件功率（W） */
	@Excel(name = "光伏组件功率", readConverterExp = "W=")
	private BigDecimal power;

	/** 年有效利用小时数设置 */
	@Excel(name = "年有效利用小时数设置")
	private List<EffectiveHours> effectiveHours = new ArrayList<EffectiveHours>();

	@Data
	public static class EffectiveHours {

		/**
		 * id
		 */
		private String id;

		/**
		 * 省市
		 */
		private String name;

		/**
		 * 年有效利用小时数
		 */
		private BigDecimal value;
	}

}
