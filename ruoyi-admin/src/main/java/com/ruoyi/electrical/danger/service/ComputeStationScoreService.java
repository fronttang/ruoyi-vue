package com.ruoyi.electrical.danger.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.dto.station.StationFormData;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

public class ComputeStationScoreService {

	public BigDecimal compute(String detectModule, String stationType, List<StationFormData> scoreDatas,
			List<OwnerUnitDanger> dangs) {
		final List<OwnerUnitDanger> dangers = new ArrayList<OwnerUnitDanger>();

		if (dangs != null) {
			dangers.addAll(dangs);
		}

		BigDecimal result = new BigDecimal(100);
		if (CollUtil.isNotEmpty(scoreDatas)) {

			boolean esflag = false;

			// 集中式
			final boolean concentrated = "1".equalsIgnoreCase(stationType);
			// 分散式
			final boolean dispersion = "2".equalsIgnoreCase(stationType);

			if (StrUtil.isNotBlank(detectModule)) {
				String[] modules = detectModule.split(",");
				if (modules != null) {

					List<String> moduleList = Arrays.asList(modules);
					// 储能
					esflag = moduleList.contains("4");
				}
			}

			final boolean es = esflag;

			Optional<BigDecimal> reduce = scoreDatas.stream().map((d) -> {

				if ("常用其他隐患".equalsIgnoreCase(d.getFirstContent()) || "添加其他隐患".equalsIgnoreCase(d.getFirstContent())) {
					Map<Long, Long> otherDangerMap = dangers.stream()
							.filter((a) -> Objects.nonNull(a.getFormDataId()) && a.getFormDataId().equals(d.getId()))
							.filter((a) -> Objects.nonNull(a.getDangerId()))
							.collect(Collectors.groupingBy(OwnerUnitDanger::getDangerId, Collectors.counting()));
					BigDecimal total = BigDecimal.ZERO;
					Set<Entry<Long, Long>> entrySet = otherDangerMap.entrySet();
					Iterator<Entry<Long, Long>> iterator = entrySet.iterator();
					while (iterator.hasNext()) {
						Entry<Long, Long> next = iterator.next();
						Long count = next.getValue();
						if (count > 0) {
							total = total.add(new BigDecimal(0.5));
						}
					}
					return total;

				} else {
					long count = dangers.stream()
							.filter((a) -> Objects.nonNull(a.getFormDataId()) && a.getFormDataId().equals(d.getId()))
							.count();
					if (count > 0) {

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
					} else {
						return BigDecimal.ZERO;
					}
				}
			}).reduce(BigDecimal::add);

			if (reduce.isPresent()) {
				BigDecimal score = reduce.get();
				result = result.subtract(score);
			}
		}
		long count = dangers.stream()
				.filter((d) -> Objects.isNull(d.getFormDataId()) && Objects.isNull(d.getDangerId())).count();
		BigDecimal otherDanger = new BigDecimal(0.5).multiply(new BigDecimal(count));
		result = result.subtract(otherDanger);

		return result;
	}
}
