package com.ruoyi.electrical.danger.domain;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 红外判定对象 charge_infrared
 * 
 * @author fronttang
 * @date 2024-08-11
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ChargeInfrared extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 业主单元ID */
	private Long unitId;

	/** 业主单元名称 */
	private String unitName;

	/** 楼栋ID */
	private Long buildingId;

	/** 楼栋名称 */
	private String buildingName;

	/** 公共区域/户 */
	private Long unitAreaId;

	/** 公共区域/户 */
	private String areaName;

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
	 * 检测单位ID
	 */
	private Long detectId;

	/**
	 * 检测单位名称
	 */
	private String detectName;

	/** 类型 三相/单相 */
	private String type;

	/** 被测设备名称 */
	private String deviceName;

	/** 设备编号 */
	private String deviceCode;

	/** 图片编号 */
	private String imageCode;

	/** 现场检测图 */
	private String inspectionPic;

	/** 判定状态 */
	private String result;

	/** 红外判定图 */
	private String infraredPic;

	/**
	 * 搜索关键字
	 */
	private String keyword;

	/**
	 * 开始时间
	 */
	private Date startDate;

	/**
	 * 结束时间
	 */
	private Date endDate;

	private Long projectId;

}
