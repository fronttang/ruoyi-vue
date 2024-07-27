package com.ruoyi.electrical.template.dto;

import lombok.Data;

/**
 * 直观检测标题对象 intuitive_detect
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@Data
public class IntuitiveDetectQuery {

	/** ID */
	private Long id;

	/** 模板ID */
	private Long templateId;

	/** 检测表名称 */
	private String name;

	/** 类型 */
	private String type;

	/** 代号 */
	private String code;

	/** 业主单元类型 */
	private String unitType;

	/** 归属1公共区域2户3配电房 */
	private String attribution;
}
