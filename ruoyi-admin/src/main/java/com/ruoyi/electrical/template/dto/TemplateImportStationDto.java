package com.ruoyi.electrical.template.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class TemplateImportStationDto {

	/** 检测板块 */
	@Excel(name = "所属模块", orderNum = "0")
	private String detectModule;

	/** 归属1非车载充电桩2交流充电桩 */
	@Excel(name = "属性", orderNum = "1")
	private String attr;

	/** 一级编号 */
	@Excel(name = "编号", orderNum = "2", type = 10)
	private String firstCode;

	/** 权重 */
	@Excel(name = "组内排序权重", orderNum = "3")
	private Double weights;

	/** 权重（集中式不含储能） */
	@Excel(name = "集中式不含储能", orderNum = "4")
	private Double weightsCNes;

	/** 权重（集中式含储能） */
	@Excel(name = "集中式含储能", orderNum = "5")
	private Double weightsCEs;

	/** 权重（分散式不含储能） */
	@Excel(name = "分散式不含储能", orderNum = "6")
	private Double weightsDNes;

	/** 权重（分散式含储能） */
	@Excel(name = "分散式含储能", orderNum = "7")
	private Double weightsDEs;

	/** 一级编号内容 */
	@Excel(name = "检测内容", orderNum = "8")
	private String firstContent;

	/** 隐患描述 */
	@Excel(name = "隐患描述", orderNum = "9")
	private String description;

	/** 隐患等级 */
	@Excel(name = "隐患等级", orderNum = "10")
	private String level;

	/** 整改建议 */
	@Excel(name = "整改建议", orderNum = "11")
	private String suggestions;

}
