package com.ruoyi.electrical.project.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitImportChargingStationDto extends OwnerUnitImportResultDto {

	/**
	 * 名称
	 */
	@Excel(name = "场站名", orderNum = "0")
	private String name;

	/**
	 * 区县
	 */
	@Excel(name = "区", orderNum = "1")
	private String district;

	/**
	 * 街道
	 */
	@Excel(name = "街道", orderNum = "2")
	private String street;

	/**
	 * 充电站类型
	 */
	@Excel(name = "类型", orderNum = "3")
	private String stationType;

	/**
	 * 所属街道
	 */
	@Excel(name = "所属街道", orderNum = "4")
	private String ssjd;

	/**
	 * 检测地址
	 */
	@Excel(name = "地址", orderNum = "5")
	private String address;

	/**
	 * 运营单位
	 */
	@Excel(name = "运营单位名称", orderNum = "6")
	private String operating;

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "运营单位负责人", orderNum = "7")
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "联系电话", orderNum = "8")
	private String phone;

	/**
	 * 委托单位
	 */
	@Excel(name = "委托单位名称", orderNum = "9")
	private String entrust;

	/**
	 * 物业类型
	 */
	@Excel(name = "物业类型", orderNum = "10")
	private String propertyType;

}
