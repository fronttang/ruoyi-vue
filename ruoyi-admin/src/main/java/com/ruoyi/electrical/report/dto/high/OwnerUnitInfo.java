package com.ruoyi.electrical.report.dto.high;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class OwnerUnitInfo {

	/** 名称 */
	private String name;

	/** 检测地址 */
	private String address;

	/** 联系人/负责人/业主 */
	private String contact;

	/** 联系电话 */
	private String phone;

	/** 建筑面积 */
	private String acreage;

	/** 建筑层数 */
	private Long layers;

	/** 户数 */
	private Long doorNumber;

	/** 高风险类型 */
	private String highRiskType;

	/** 单位类型 */
	private String unitType;

	/** 场所类型 */
	private String venueType;

	/** 经营范围 */
	private String businessScope;

	/** 消防安全负责人 */
	private String safetyIncharge;

	/** 消防安全负责人电话 */
	private String safetyInchargePhone;

	/** 消防安全管理人 */
	private String safetyManager;

	/** 消防安全管理人电话 */
	private String safetyManagerPhone;

	/** 员工人数 */
	private Long staffs;

	/** 有无证照 */
	private String licence;

	/** 消防安全重点单位 */
	private String safetyKeyUnit;

	/**
	 * 区县
	 */
	private String district;

	private String districtName;

	/**
	 * 街道
	 */
	private String street;

	private String streetName;

	/**
	 * 社区
	 */
	private String community;

	private String communityName;

	/**
	 * 村
	 */
	private String hamlet;

	private String hamletName;

	/**
	 * 消防配置
	 */
	private Map<String, Object> config = new HashMap<String, Object>();

}
