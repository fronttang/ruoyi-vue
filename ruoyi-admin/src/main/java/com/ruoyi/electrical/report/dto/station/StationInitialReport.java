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
	// private List<StationForm> form = new ArrayList<StationForm>();

	/**
	 * 1、整体安全检查
	 */
	private List<StationFormData> form1 = new ArrayList<StationFormData>();

	/**
	 * 2、用电安全检查
	 */
	private List<StationFormData> form2 = new ArrayList<StationFormData>();

	/**
	 * 4、消防设施安全检查
	 */
	private List<StationFormData> form4 = new ArrayList<StationFormData>();

	/**
	 * 5、储能系统检查
	 */
	private List<StationFormData> form5 = new ArrayList<StationFormData>();

	/**
	 * 隐患列表
	 */
	private List<StationDanger> danger = new ArrayList<StationDanger>();

	/**
	 * 充电桩检查
	 */
	private StationPile pileForm = new StationPile();

	/**
	 * 隐患图片
	 */
	private List<StationPic> dangerPic = new ArrayList<StationPic>();

	/**
	 * 代表照片
	 */
	private List<StationPic> pic = new ArrayList<StationPic>();

}
