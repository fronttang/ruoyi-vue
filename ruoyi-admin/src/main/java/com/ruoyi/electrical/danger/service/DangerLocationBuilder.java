package com.ruoyi.electrical.danger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

public class DangerLocationBuilder {

	public static List<String> build(List<OwnerUnitDanger> dangers) {
		List<String> locations = new ArrayList<String>();

		// 有楼栋的
		Map<String, List<OwnerUnitDanger>> buildDangerGroup = dangers.stream()
				.filter((d) -> StrUtil.isNotBlank(d.getBuildingName()))
				.collect(Collectors.groupingBy(OwnerUnitDanger::getBuildingName, Collectors.toList()));

		if (CollUtil.isNotEmpty(buildDangerGroup)) {

			buildDangerGroup.forEach((buildingName, buildingDangers) -> {

				List<String> buildingDangerLocations = buildingDangers.stream().map((d) -> d.getReportLocation())
						.filter((d) -> StrUtil.isNotBlank(d)).distinct().collect(Collectors.toList());

				String buildLocation = StrUtil.format("{}:{}", buildingName, String.join("、", buildingDangerLocations));
				locations.add(buildLocation);
			});
		}

		// 没有楼栋的
		List<String> dangerLocationList = dangers.stream().filter((d) -> StrUtil.isBlank(d.getBuildingName()))
				.map((d) -> d.getReportLocation()).filter((d) -> StrUtil.isNotBlank(d)).distinct()
				.collect(Collectors.toList());

		if (CollUtil.isNotEmpty(dangerLocationList)) {
			locations.add(String.join("、", dangerLocationList));
		}

		return locations;
	}

	public static String buildString(List<OwnerUnitDanger> dangers) {
		List<String> list = build(dangers);
		return String.join(";", list);
	}
}
