package com.ruoyi.electrical.dto;

import java.util.Date;
import java.util.List;

import com.ruoyi.electrical.report.dto.high.HighDangerInfo;

import lombok.Data;

@Data
public class DangerExportQueryDto {

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
	 * 所属网格
	 */
	private String grid;

	/**
	 * 检测地址
	 */
	private String address;

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
	 * 联系人/负责人/业主
	 */
	private String contact;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 高风险类型
	 */
	private String highRiskType;

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
	 * 消防安全负责人
	 */
	private String safetyIncharge;

	/**
	 * 消防安全负责人电话
	 */
	private String safetyInchargePhone;

	/**
	 * 消防安全管理人
	 */
	private String safetyManager;

	/**
	 * 消防安全管理人电话
	 */
	private String safetyManagerPhone;

	/**
	 * 无法检测原因
	 */
	private String isTestReason;

	/**
	 * 初检状态
	 */
	private String initialStatus;

	/**
	 * 营业执照
	 */
	private String businessLicense;

	/**
	 * 门头照
	 */
	private String doorPic;

	/**
	 * 整改二维码
	 */
	private String mngQrcode;

	/**
	 * 检测员ID
	 */
	private Long inspectorId;

	/**
	 * 初测日期
	 */
	private Date initialDate;

	/**
	 * 隐患列表
	 */
	private List<HighDangerInfo> dangers;
}
