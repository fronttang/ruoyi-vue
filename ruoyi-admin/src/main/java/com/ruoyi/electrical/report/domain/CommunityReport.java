package com.ruoyi.electrical.report.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 社区报告对象 community_report
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommunityReport extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/** 项目ID */
	@Excel(name = "项目ID")
	private Long projectId;

	/** 区县 */
	@Excel(name = "区县")
	private String district;

	private String districtName;

	/** 街道 */
	@Excel(name = "街道")
	private String street;

	private String streetName;

	/** 社区 */
	@Excel(name = "社区")
	private String community;

	private String communityName;

	/** 报告类型1初检,2复检 */
	@Excel(name = "报告类型1初检,2复检")
	private String type;

	/** 报告编号 */
	@Excel(name = "报告编号")
	private String code;

	/** 得分 */
	@Excel(name = "得分")
	private BigDecimal score;

	/** 报告状态 */
	@Excel(name = "报告状态")
	private String status;

	/** 驳回状态1是0否 */
	@Excel(name = "驳回状态1是0否")
	private String reject;

	/** 制式WORD报告 */
	@Excel(name = "制式WORD报告")
	private String wordFile;

	/** 制式WORD报告版本 */
	@Excel(name = "制式WORD报告版本")
	private Integer wordFileVersion;

	/** 归档PDF报告地址 */
	@Excel(name = "归档PDF报告地址")
	private String archivedPdf;

	/** 归档WORD报告地址 */
	@Excel(name = "归档WORD报告地址")
	private String archivedWord;

	/** 归档WORD报告地址版本 */
	@Excel(name = "归档WORD报告地址版本")
	private Integer archivedWordVersion;
}
