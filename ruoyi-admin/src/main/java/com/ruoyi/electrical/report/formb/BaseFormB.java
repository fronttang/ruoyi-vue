package com.ruoyi.electrical.report.formb;

import lombok.Data;

@Data
public class BaseFormB {

	/**
	 * 温度
	 */
	private String temperature;

	/**
	 * 湿度
	 */
	private String humidity;

	/**
	 * 检测时间
	 */
	private String detectDate;
}
