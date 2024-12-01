package com.ruoyi.electrical.design.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 勘探数据查询对象 electricity_project
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ElectricityProject extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	/** 项目名称 */
	private String name;

}
