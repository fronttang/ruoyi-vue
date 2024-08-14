package com.ruoyi.electrical.report.dto.station;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StationForm {

	/**
	 * 检测表名称
	 */
	private String name;

	/**
	 * 检测数据
	 */
	private List<StationFormData> data = new ArrayList<StationFormData>();
}
