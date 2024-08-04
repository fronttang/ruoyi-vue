package com.ruoyi.electrical.project.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitImportHighRentalHouseDto extends OwnerUnitImportHighDto {

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "业主", orderNum = "7")
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "联系电话", orderNum = "8")
	private String phone;

	/**
	 * 建筑面积
	 */
	@Excel(name = "建筑面积", orderNum = "9")
	private String acreage;

	/**
	 * 户数
	 */
	@Excel(name = "租户套间数", orderNum = "10")
	private Long doorNumber;

}
