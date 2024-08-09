package com.ruoyi.electrical.dto;

import java.util.List;

import lombok.Data;

@Data
public class MissDeviceExportQueryDto {

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
	 * 缺失设备数据
	 */
	private List<MissDeviceJsonDataDto> devices;
}
