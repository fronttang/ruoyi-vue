package com.ruoyi.electrical.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OwnerUnitDangerGroupDetailVo {

	/**
	 * 业主单元ID/楼栋
	 */
	private Long id;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 业主单元名称
	 */
	private String unitName;

	/**
	 * 楼栋名称
	 */
	private String name;

	/**
	 * 初检状态
	 */
	private String initialStatus;

	/**
	 * 初检时间
	 */
	private Date initialDate;

	/**
	 * 复检状态
	 */
	private String reviewStatus;

	/**
	 * 高风险类型
	 */
	private String highRiskType;

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

	/**
	 * 总隐患数
	 */
	private Integer dangers;

	/**
	 * 待整改数
	 */
	private Integer rectifications;

	/**
	 * 待复检数
	 */
	private Integer reexaminations;

	/**
	 * 完成数
	 */
	private Integer finishs;

}
