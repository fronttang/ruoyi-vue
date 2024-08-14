package com.ruoyi.electrical.report.dto.station;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StationPile {

	/**
	 * 充电系统检查
	 */
	private String name;

	/**
	 * 
	 */
	private List<StationPileForm> pileForms = new ArrayList<StationPileForm>();
}
