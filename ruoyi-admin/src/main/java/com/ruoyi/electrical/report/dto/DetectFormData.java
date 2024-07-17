package com.ruoyi.electrical.report.dto;

import lombok.Data;

@Data
public class DetectFormData {

	/**
	 * 编号
	 */
	private String firstCode;

	/**
	 * 内容
	 */
	private Object firstContent;

	/**
	 * 隐患等级
	 */
	private String level;

	/**
	 * 
	 */
	private String result;
}
