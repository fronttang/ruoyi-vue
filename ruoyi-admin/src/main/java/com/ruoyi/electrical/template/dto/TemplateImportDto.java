package com.ruoyi.electrical.template.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class TemplateImportDto {

	/** 代号 */
	@Excel(name = "报告编号", orderNum = "0")
	private String code;

	/** 检测表名称 */
	@Excel(name = "报告", orderNum = "1")
	private String name;

	/** 归属1公共区域2户3配电房 */
	@Excel(name = "检测涉及区域", orderNum = "2")
	private String attr;

	/** 类型 */
	@Excel(name = "类型", orderNum = "3")
	private String type;

	/** 权重 */
	@Excel(name = "排序值", orderNum = "4")
	private Double weights;

	/** 一级编号 */
	@Excel(name = "序号", orderNum = "5")
	private String firstCode;

	/** 一级编号内容 */
	@Excel(name = "报告标准", orderNum = "6")
	private String firstContent;

	/** 隐患等级 */
	@Excel(name = "隐患等级", orderNum = "7")
	private String level;

	/** 隐患描述 */
	@Excel(name = "隐患描述", orderNum = "8")
	private String description;

	/** 整改建议 */
	@Excel(name = "整改建议", orderNum = "9")
	private String suggestions;
}
