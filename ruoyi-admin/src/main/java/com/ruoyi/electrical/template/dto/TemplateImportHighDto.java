package com.ruoyi.electrical.template.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget("TemplateExcelHighDto")
public class TemplateImportHighDto {

	@Excel(name = "新编号", orderNum = "0")
	private String code;

	@Excel(name = "报告标准", orderNum = "1")
	private String name;

	@Excel(name = "最大扣分数", orderNum = "2")
	private String maxScore;

	@Excel(name = "检测涉及区域", orderNum = "3")
	private String area;

	@Excel(name = "扣分选项", orderNum = "4")
	private String content;

	@Excel(name = "最大扣分", orderNum = "5")
	private String mScore;

	@Excel(name = "累计方式", orderNum = "6")
	private String accMethod;

	@Excel(name = "分值", orderNum = "7")
	private String score;

	@Excel(name = "重复数", orderNum = "8")
	private String no;

	@Excel(name = "隐患描述", orderNum = "9")
	private String description;

	@Excel(name = "整改建议", orderNum = "10")
	private String suggestions;

	@Excel(name = "隐患等级", orderNum = "11")
	private String level;

}
