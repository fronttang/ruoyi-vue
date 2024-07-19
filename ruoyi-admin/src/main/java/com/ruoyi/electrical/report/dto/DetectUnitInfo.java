package com.ruoyi.electrical.report.dto;

import lombok.Data;

@Data
public class DetectUnitInfo {

	/** id */
	private Long id;

	/** 名称 */
	private String name;

	/** 简称 */
	private String shortName;

	/** 受控编号 */
	private String controlledNumber;

	/** 电话 */
	private String phone;

	/** 地址 */
	private String address;

	/** 联系人 */
	private String contact;

	/** 联系电话 */
	private String contactPhone;

	/** logo */
	private Object logo;

	/** 营业执照 */
	private String businessLicense;

	/** 资质 */
	private Object qualification;

}
