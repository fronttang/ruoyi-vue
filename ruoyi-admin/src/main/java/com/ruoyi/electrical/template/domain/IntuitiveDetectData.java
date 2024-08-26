package com.ruoyi.electrical.template.domain;

import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 直观检测表内容对象 intuitive_detect_data
 * 
 * @author fronttang
 * @date 2024-06-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IntuitiveDetectData extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 模板ID */
	@Excel(name = "模板ID")
	private Long templateId;

	/** 检测表标题 */
	@Excel(name = "检测表标题")
	private Long detectTitle;

	/** 类型 */
	@Excel(name = "类型")
	private String type;

	/** 业主单元类型 */
	@Excel(name = "业主单元类型")
	private String unitType;

	/** 模块类型（一级，二级） */
	@Excel(name = "模块类型", readConverterExp = "一级，二级")
	private String moduleType;

	/** 检测板块 */
	@Excel(name = "检测板块")
	private String detectModule;

	/** 上级模块ID */
	@Excel(name = "上级模块ID")
	private Long parentId;

	/** 一级编号 */
	@Excel(name = "一级编号")
	private String firstCode;

	/** 一级编号内容 */
	@Excel(name = "一级编号内容")
	private String firstContent;

	/** 二级编号 */
	@Excel(name = "二级编号")
	private String secondaryCode;

	/** 二级编号内容 */
	@Excel(name = "二级编号内容")
	private String secondaryContent;

	/** 权重 */
	@Excel(name = "权重")
	private Double weights;

	/** 权重（集中式含储能） */
	private Double weightsCEs;

	/** 权重（集中式不含储能） */
	private Double weightsCNes;

	/** 权重（分散式含储能） */
	private Double weightsDEs;

	/** 权重（分散式不含储能） */
	private Double weightsDNes;

	/** 输出格式 */
	@Excel(name = "输出格式")
	private String output;

	/** 最高扣分数 */
	@Excel(name = "最高扣分数")
	private Long maxScore;

	/** 1展示，2计分 */
	@Excel(name = "1展示，2计分")
	private String view;

	/** 展示模块上级 */
	@Excel(name = "展示模块上级")
	private Long viewParentId;

	/** 归属1非车载充电桩2交流充电桩 */
	private List<String> attribution;

	private List<IntuitiveDetectDanger> dangers;

	private Integer danger;

	private String level;

}
