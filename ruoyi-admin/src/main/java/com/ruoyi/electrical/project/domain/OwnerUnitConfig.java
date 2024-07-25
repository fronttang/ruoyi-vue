package com.ruoyi.electrical.project.domain;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 业主单元消防配置对象 owner_unit_config
 * 
 * @author ruoyi
 * @date 2024-07-25
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 业主单元ID */
	private Long unitId;

	/** 消防配置 */
	@Excel(name = "消防配置")
	private JSONObject config;

}
