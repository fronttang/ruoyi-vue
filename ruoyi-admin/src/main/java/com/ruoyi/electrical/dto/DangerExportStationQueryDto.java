package com.ruoyi.electrical.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;

import lombok.Data;

@Data
public class DangerExportStationQueryDto {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 检测地址
	 */
	private String address;

	/**
	 * 检测模块
	 */
	private String detectModule;

	/**
	 * 建筑层数
	 */
	private Long layers;

	/**
	 * 建筑面积
	 */
	private String acreage;

	/**
	 * 户数
	 */
	private Long doorNumber;

	/**
	 * 添加的户数
	 */
	private Long areas;

	/**
	 * 联系人/负责人/业主
	 */
	private String contact;

	/**
	 * 联系电话
	 */
	private String phone;

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
	 * 无法检测原因
	 */
	private String isTestReason;

	/**
	 * 初检状态
	 */
	private String initialStatus;

	/**
	 * 检测开始时间
	 */
	private Date testStartDate;

	/**
	 * 检测结束时间
	 */
	private Date testEndDate;

	/**
	 * 检测单位名称
	 */
	private String detectName;

	/**
	 * 网格员
	 */
	private String gridman;

	/**
	 * 网格员电话
	 */
	private String gridmanPhone;

	/**
	 * 是否开启隐患通知单1开启
	 */
	private String isDangerNotice;

	/**
	 * 现场检测图
	 */
	private String inspectionPic;

	/**
	 * 复检时间
	 */
	private Date reviewDate;

	/**
	 * 复检编号
	 */
	private String reivewCode;

	/**
	 * 初检时间
	 */
	private Date initialDate;

	/**
	 * 运营单位
	 */
	private String operating;

	/**
	 * 投入运营时间
	 */
	private String operatingDate;

	/**
	 * 场站服务车辆
	 */
	private String vehicles;

	/**
	 * 有无休息室1有0无
	 */
	private String lounge;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 建设明细
	 */
	private String details;

	/**
	 * 充电站类型
	 */
	private String stationType;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 隐患列表
	 */
	private List<OwnerUnitDanger> dangers = new ArrayList<OwnerUnitDanger>();
}
