package com.ruoyi.electrical.project.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitImportHighResidentialDto extends OwnerUnitImportHighDto {

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "物业园区负责人", orderNum = "7")
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "联系电话", orderNum = "8")
	private String phone;

	/**
	 * 建筑层数
	 */
	@Excel(name = "建筑楼层", orderNum = "9")
	private Long layers;

	/**
	 * 建筑面积
	 */
	@Excel(name = "建筑面积", orderNum = "10")
	private String acreage;

	/**
	 * 户数
	 */
	@Excel(name = "房间套间数", orderNum = "11")
	private Long doorNumber;

}
