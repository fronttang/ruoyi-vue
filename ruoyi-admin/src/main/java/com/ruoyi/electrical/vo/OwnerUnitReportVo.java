package com.ruoyi.electrical.vo;

import lombok.Data;

@Data
public class OwnerUnitReportVo {

	/**
	 * 报告ID
	 */
	private Long reportId;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 报告类型1初检,2复检
	 */
	private String type;

	/**
	 * 高风险类型
	 */
	private String highRiskType;

	/**
	 * 报告状态
	 */
	private String status;

	/**
	 * 是否有驳回记录（前端需要显示是否驳回）
	 */
	private String isReject;

	/**
	 * 检测单位ID
	 */
	private Long detectId;

	/**
	 * 区县
	 */
	private String district;

	/**
	 * 街道
	 */
	private String street;

	/**
	 * 社区
	 */
	private String community;

	/**
	 * 村
	 */
	private String hamlet;

}
