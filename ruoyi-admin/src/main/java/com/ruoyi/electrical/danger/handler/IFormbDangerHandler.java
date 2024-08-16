package com.ruoyi.electrical.danger.handler;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;

public interface IFormbDangerHandler {

	public static final String QUALIFIED = "合格";

	public static final String FAILURE = "不合格";

	/**
	 * 隐患等级
	 */
	String getLevel(OwnerUnitDanger vo);

	/**
	 * 隐患描述
	 */
	String getDescription(OwnerUnitDanger vo);

	/**
	 * 整改建议
	 */
	String getSuggestions(OwnerUnitDanger vo);

	/**
	 * 位置
	 */
	String getInfoLocation(OwnerUnitDanger vo);

	/**
	 * 结果
	 */
	String getResult(OwnerUnitDanger vo);

	/**
	 * 符合是否汇总
	 */
	default boolean isSummary(OwnerUnitDanger vo) {
		return true;
	}

	String getPicture(OwnerUnitDanger vo);
}
