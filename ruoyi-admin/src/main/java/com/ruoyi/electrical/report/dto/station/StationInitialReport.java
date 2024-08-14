package com.ruoyi.electrical.report.dto.station;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.electrical.report.dto.DetectUnitInfo;
import com.ruoyi.electrical.report.dto.OwnerUnitReportInfo;

import lombok.Data;

@Data
public class StationInitialReport {

	/**
	 * 业主单元信息
	 */
	private StationOwnerUnitInfo unit;

	/**
	 * 检测单位信息
	 */
	private DetectUnitInfo detect;

	/**
	 * 报告信息
	 */
	private OwnerUnitReportInfo report;

	/**
	 * 充电桩分组
	 */
	private List<ChargingPileInfo> pileGroup = new ArrayList<ChargingPileInfo>();

	/**
	 * 充电桩列表
	 */
	private List<ChargingPileInfo> piles = new ArrayList<ChargingPileInfo>();

	/**
	 * 检测项
	 */
	private List<StationForm> form = new ArrayList<StationForm>();

	/**
	 * 隐患列表
	 */
	private List<StationDanger> danger = new ArrayList<StationDanger>();

	/**
	 * 充电桩检查
	 */
	private StationPile pileForm = new StationPile();

}
