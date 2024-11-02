package com.ruoyi.electrical.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportFileVo {

	/**
	 * 报告ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long reportId;

	/**
	 * 报告路径
	 */
	private String path;
}
