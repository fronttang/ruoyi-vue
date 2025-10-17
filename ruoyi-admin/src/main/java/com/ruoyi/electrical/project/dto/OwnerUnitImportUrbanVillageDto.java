package com.ruoyi.electrical.project.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 城中村/工业园
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitImportUrbanVillageDto extends OwnerUnitImportResultDto {

	/**
	 * 楼栋名称
	 */
	@Excel(name = "楼栋名称", orderNum = "0")
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
	 * 社区
	 */
	@Excel(name = "社区", orderNum = "3")
	private String community;

	/**
	 * 村
	 */
	@Excel(name = "村", orderNum = "4")
	private String hamlet;

	/**
	 * 编码
	 */
	@Excel(name = "房屋编码", orderNum = "5")
	private String code;

	/**
	 * 委托单位
	 */
	@Excel(name = "委托单位", orderNum = "6")
	private String entrust;

	/**
	 * 建筑使用性质
	 */
	@Excel(name = "建筑使用性质", orderNum = "7")
	private String nature;

	/**
	 * 建筑面积
	 */
	@Excel(name = "建筑面积", orderNum = "8")
	private String acreage;

	/**
	 * 建筑层数
	 */
	@Excel(name = "建筑层数", orderNum = "9")
	private Long layers;

	/**
	 * 联系人
	 */
	@Excel(name = "联系人", orderNum = "10")
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "联系电话", orderNum = "11")
	private String phone;

	/**
	 * 户数
	 */
	@Excel(name = "户数", orderNum = "12")
	private Long doorNumber;

	/**
	 * 检测地址
	 */
	@Excel(name = "检测地址", orderNum = "13")
	private String address;
	
	/**
	 * 检测内容
	 */
	@Excel(name = "检测内容", orderNum = "14")
	private String testContent;
}
