package com.ruoyi.electrical.project.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitImportHighDto extends OwnerUnitImportResultDto {

	/**
	 * 编码
	 */
	@Excel(name = "档案编号", orderNum = "0")
	private String code;

	/**
	 * 名称
	 */
	@Excel(name = "场所名称", orderNum = "1")
	private String name;

	/**
	 * 场所类型（类型id）
	 */
	@Excel(name = "场所类型（类型id）", orderNum = "2")
	private String cslx;

	/**
	 * 区县
	 */
	@Excel(name = "区", orderNum = "3")
	private String district;

	/**
	 * 街道
	 */
	@Excel(name = "街道", orderNum = "4")
	private String street;

	/**
	 * 社区
	 */
	@Excel(name = "社区", orderNum = "5")
	private String community;

	/**
	 * 检测地址
	 */
	@Excel(name = "场所地址", orderNum = "6")
	private String address;

}
