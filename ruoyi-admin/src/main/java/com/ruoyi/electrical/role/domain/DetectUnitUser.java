package com.ruoyi.electrical.role.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 账号对象
 * 
 * @author fronttang
 * @date 2024-06-18
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DetectUnitUser extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	private Long id;

	/** 检测单位ID */
	@Excel(name = "检测单位ID")
	private Long detectId;

	/** 检测单位 */
	@Excel(name = "检测单位")
	private String detectName;

	/** 账号 */
	@Excel(name = "账号")
	private String account;

	/** 名称 */
	@Excel(name = "名称")
	private String name;

	/** 密码 */
	@Excel(name = "密码")
	private String password;

	/** 状态 */
	@Excel(name = "状态")
	private String status;

	/** 1.管理员，2员工 */
	private String type;

	/** 记录员 */
	private String recorder;

	/** 区县 */
	@Excel(name = "区县")
	private String district;

	/** 街道 */
	@Excel(name = "街道")
	private String street;

	/** 社区 */
	@Excel(name = "社区")
	private String community;

	/** 村 */
	@Excel(name = "村")
	private String hamlet;

	private Long projectId;

	private String projectName;

	/**
	 * 账号类型
	 */
	private String accountType;

}
