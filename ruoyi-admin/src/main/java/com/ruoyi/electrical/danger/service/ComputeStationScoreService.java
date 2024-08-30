package com.ruoyi.electrical.danger.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ruoyi.electrical.report.dto.station.StationFormData;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

public class ComputeStationScoreService {

	public BigDecimal compute(String detectModule, String stationType, List<StationFormData> scoreDatas) {
		BigDecimal result = new BigDecimal(100);
		if (CollUtil.isNotEmpty(scoreDatas) && StrUtil.isNotBlank(detectModule)) {
			String[] modules = detectModule.split(",");
			if (modules != null) {

				List<String> moduleList = Arrays.asList(modules);
				// 储能
				final boolean es = moduleList.contains("4");

				// 集中式
				final boolean concentrated = "1".equalsIgnoreCase(stationType);
				// 分散式
				final boolean dispersion = "2".equalsIgnoreCase(stationType);

				Optional<BigDecimal> reduce = scoreDatas.stream().map((d) -> {

					if (es) {
						if (concentrated && Objects.nonNull(d.getWeightsCEs())) {
							return new BigDecimal(100).multiply(new BigDecimal(0.99))
									.multiply(new BigDecimal(d.getWeightsCEs()));
						} else if (dispersion && Objects.nonNull(d.getWeightsDEs())) {
							return new BigDecimal(100).multiply(new BigDecimal(0.99))
									.multiply(new BigDecimal(d.getWeightsDEs()));
						}
					} else {
						if (concentrated && Objects.nonNull(d.getWeightsCNes())) {
							return new BigDecimal(100).multiply(new BigDecimal(0.99))
									.multiply(new BigDecimal(d.getWeightsCNes()));
						} else if (dispersion && Objects.nonNull(d.getWeightsDNes())) {
							return new BigDecimal(100).multiply(new BigDecimal(0.99))
									.multiply(new BigDecimal(d.getWeightsDNes()));
						}
					}
					return BigDecimal.ZERO;
				}).reduce(BigDecimal::add);

				if (reduce.isPresent()) {
					BigDecimal score = reduce.get();
					result = result.subtract(score);
				}
			}
		}
		return result;
	}
}
