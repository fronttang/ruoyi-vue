package com.ruoyi.electrical.report.dto.high;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cn.hutool.core.collection.CollUtil;
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
		if (CollUtil.isNotEmpty(danger)) {
			Optional<Double> reduce = danger.stream().filter((d) -> Objects.nonNull(d.getScore()))
					.map(HighDangerInfo::getScore).reduce(Double::sum);
			return reduce.isPresent() ? reduce.get() : 0D;
		}
		return 0D;
	}
}
