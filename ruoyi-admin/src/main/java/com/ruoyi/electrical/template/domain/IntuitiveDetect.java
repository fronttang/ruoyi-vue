package com.ruoyi.electrical.template.domain;

import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 直观检测标题对象 intuitive_detect
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IntuitiveDetect extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 模板ID */
	@Excel(name = "模板ID")
	private Long templateId;

	/** 检测表名称 */
	@Excel(name = "检测表名称")
	private String name;

	/** 类型 */
	@Excel(name = "类型")
	private String type;

	/** 代号 */
	@Excel(name = "代号")
	private String code;

	/** 业主单元类型 */
	@Excel(name = "业主单元类型")
	private String unitType;

	/** 归属1公共区域2户3配电房 */
	@Excel(name = "归属1公共区域2户3配电房")
	private List<String> attribution;

	/**
	 * 检测表内容
	 */
	private List<IntuitiveDetectData> datas;
}
