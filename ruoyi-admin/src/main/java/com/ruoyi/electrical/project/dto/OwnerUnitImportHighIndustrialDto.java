package com.ruoyi.electrical.project.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitImportHighIndustrialDto extends OwnerUnitImportHighDto {

	/**
	 * 经营范围
	 */
	@Excel(name = "经营范围", orderNum = "7")
	private String businessScope;

	/**
	 * 消防安全负责人
	 */
	@Excel(name = "消防安全负责人", orderNum = "8")
	private String safetyIncharge;

	/**
	 * 消防安全负责人电话
	 */
	@Excel(name = "联系电话1", orderNum = "9")
	private String safetyInchargePhone;

	/**
	 * 消防安全管理人
	 */
	@Excel(name = "消防安全管理人", orderNum = "10")
	private String safetyManager;

	/**
	 * 消防安全管理人电话
	 */
	@Excel(name = "联系电话2", orderNum = "11")
	private String safetyManagerPhone;

	/**
	 * 员工人数
	 */
	@Excel(name = "员工个数", orderNum = "12")
	private Long staffs;

	/**
	 * 建筑面积
	 */
	@Excel(name = "使用面积", orderNum = "13")
	private String acreage;

	/**
	 * 有无证照
	 */
	@Excel(name = "有证无照", orderNum = "14")
	private String licence;

}
