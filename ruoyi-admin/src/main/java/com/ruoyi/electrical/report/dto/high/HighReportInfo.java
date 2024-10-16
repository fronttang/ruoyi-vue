package com.ruoyi.electrical.report.dto.high;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.electrical.danger.service.ComputeHighScoreService;

import lombok.Data;

@Data
public class HighReportInfo {

	/**
	 * 建档时间
	 */
	private String createDate;

	/**
	 * 业主单元信息
	 */
	private OwnerUnitInfo unit;

	/**
	 * 总得分
	 */
	@SuppressWarnings("unused")
	private Integer totalScore;

	/**
	 * 隐患明细
	 */
	private List<HighDangerInfo> danger = new ArrayList<HighDangerInfo>();

	public Double getTotalScore() {
		ComputeHighScoreService compute = new ComputeHighScoreService();
		return compute.compute(danger).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
	}

}
