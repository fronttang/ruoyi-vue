package com.ruoyi.electrical.design.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

/**
 * 勘探数据查询对象 electricity_project
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Data
public class ElectricityProjectVo {

	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/** 项目名称 */
	private String name;

	/**
	 * 图片数量
	 */
	private Long images;

}
