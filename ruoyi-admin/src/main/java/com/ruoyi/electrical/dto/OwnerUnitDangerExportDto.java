package com.ruoyi.electrical.dto;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitDangerExportDto extends OwnerUnitDanger {

	private static final long serialVersionUID = 1L;

	/**
	 * 检测表数据ID
	 */
	private Long dataId;

	/**
	 * 一级编号
	 */
	private String firstCode;

	/**
	 * 一级编号内容
	 */
	private String firstContent;

	/**
	 * 最高扣分数
	 */
	private Long maxScore;

	/**
	 * 扣分
	 */
	private Long score;

}
