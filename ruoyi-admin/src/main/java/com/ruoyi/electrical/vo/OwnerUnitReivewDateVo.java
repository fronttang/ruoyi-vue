package com.ruoyi.electrical.vo;

import lombok.Data;

@Data
public class OwnerUnitReivewDateVo {

	/**
	 * 开始复检时间
	 */
	private String startReviewDate;

	/**
	 * 结束复检时间
	 */
	private String endReviewDate;

	/**
	 * 检测人员
	 */
	private String reviewer;
}
