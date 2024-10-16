package com.ruoyi.electrical.danger.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.ruoyi.electrical.report.dto.high.HighDangerInfo;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComputeHighScoreService {

	public BigDecimal compute(List<HighDangerInfo> dangers) {

		BigDecimal totalScore = BigDecimal.ZERO;
		try {
			if (CollUtil.isNotEmpty(dangers)) {
				// 检测表最高扣分MAP
				Map<Long, BigDecimal> formMaxScoreMap = dangers.stream().filter((d) -> Objects.nonNull(d.getFormId()))
						.collect(Collectors.groupingBy(HighDangerInfo::getFormId,
								Collectors.toList()))
						.entrySet().stream().collect(
								Collectors
										.toMap((d) -> d.getKey(),
												(d) -> CollUtil.isNotEmpty(d.getValue())
														? Objects.nonNull(d.getValue().get(0).getMaxScore())
																? new BigDecimal(d.getValue().get(0).getMaxScore())
																: BigDecimal.ZERO
														: BigDecimal.ZERO));

				Map<Long, List<HighDangerInfo>> formMap = dangers.stream().filter((d) -> Objects.nonNull(d.getFormId()))
						.collect(Collectors.groupingBy(HighDangerInfo::getFormId, Collectors.toList()));

				Set<Entry<Long, List<HighDangerInfo>>> entrySet = formMap.entrySet();
				Iterator<Entry<Long, List<HighDangerInfo>>> iterator = entrySet.iterator();

				while (iterator.hasNext()) {

					Entry<Long, List<HighDangerInfo>> next = iterator.next();

					Long formId = next.getKey();
					List<HighDangerInfo> formDatas = next.getValue();

					BigDecimal formScore = computeFormDataScore(formDatas);

					BigDecimal formMaxScore = formMaxScoreMap.get(formId);

					if (formMaxScore.compareTo(BigDecimal.ZERO) > 0 && formScore.compareTo(formMaxScore) >= 0) {
						totalScore = totalScore.add(formMaxScore);
					} else {
						totalScore = totalScore.add(formScore);
					}
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		totalScore = new BigDecimal(100).subtract(totalScore);
		totalScore.setScale(2, RoundingMode.HALF_UP);
		return totalScore;
	}

	private BigDecimal computeFormDataScore(List<HighDangerInfo> formDatas) {
		// 检测项最高扣分数
		Map<Long, BigDecimal> formDataMaxScoreMap = formDatas.stream().filter((d) -> Objects.nonNull(d.getFormDataId()))
				.collect(Collectors.groupingBy(HighDangerInfo::getFormDataId, Collectors.toList())).entrySet().stream()
				.collect(Collectors.toMap((d) -> d.getKey(), (d) -> CollUtil.isNotEmpty(d.getValue())
						? Objects.nonNull(d.getValue().get(0).getDataMaxScore())
								? new BigDecimal(d.getValue().get(0).getDataMaxScore())
								: BigDecimal.ZERO
						: BigDecimal.ZERO));

		Map<Long, List<HighDangerInfo>> formDataMap = formDatas.stream()
				.filter((d) -> Objects.nonNull(d.getFormDataId()))
				.collect(Collectors.groupingBy(HighDangerInfo::getFormDataId, Collectors.toList()));

		Set<Entry<Long, List<HighDangerInfo>>> entrySet = formDataMap.entrySet();
		Iterator<Entry<Long, List<HighDangerInfo>>> iterator = entrySet.iterator();

		BigDecimal formDataTotal = BigDecimal.ZERO;

		while (iterator.hasNext()) {
			Entry<Long, List<HighDangerInfo>> next = iterator.next();

			Long formDataId = next.getKey();
			List<HighDangerInfo> dangers = next.getValue();

			BigDecimal dangerScore = computeDangerScore(dangers);
			BigDecimal formDataMaxScore = formDataMaxScoreMap.get(formDataId);

			if (formDataMaxScore.compareTo(BigDecimal.ZERO) > 0 && dangerScore.compareTo(formDataMaxScore) >= 0) {
				formDataTotal = formDataTotal.add(formDataMaxScore);
			} else {
				formDataTotal = formDataTotal.add(dangerScore);
			}
		}
		return formDataTotal;
	}

	private BigDecimal computeDangerScore(List<HighDangerInfo> dangers) {
		BigDecimal total = BigDecimal.ZERO;

		Map<String, List<HighDangerInfo>> accDangerMap = dangers.stream()
				.filter((d) -> Objects.nonNull(d.getAccMethod()))
				.collect(Collectors.groupingBy(HighDangerInfo::getAccMethod, Collectors.toList()));

		if (CollUtil.isNotEmpty(accDangerMap)) {

			Set<Entry<String, List<HighDangerInfo>>> entrySet = accDangerMap.entrySet();
			Iterator<Entry<String, List<HighDangerInfo>>> iterator = entrySet.iterator();

			while (iterator.hasNext()) {
				Entry<String, List<HighDangerInfo>> next = iterator.next();
				String accMethod = next.getKey();
				List<HighDangerInfo> accDangers = next.getValue();

				if (CollUtil.isNotEmpty(accDangers)) {
					// 按项累积
					if ("1".equalsIgnoreCase(accMethod)) {

						BigDecimal accTotal = computeAccMethod1(accDangers);

						if (accTotal != null) {
							total = total.add(accTotal);
						}
					} else if ("2".equalsIgnoreCase(accMethod)) {
						// 按个数累计
						Optional<BigDecimal> reduce = accDangers.stream().filter((d) -> Objects.nonNull(d.getScore()))
								.map((d) -> {
									return new BigDecimal(d.getScore());
								}).reduce(BigDecimal::add);

						if (reduce.isPresent()) {
							total = total.add(reduce.get());
						}
					}
				}
			}
		}
		return total;
	}

	/**
	 * 按项累积计算
	 * 
	 * @param accDangers
	 * @return
	 */
	private BigDecimal computeAccMethod1(List<HighDangerInfo> accDangers) {
		BigDecimal total = BigDecimal.ZERO;

		if (CollUtil.isNotEmpty(accDangers)) {

			Map<Long, List<HighDangerInfo>> accDangerMap = accDangers.stream()
					.filter((d) -> Objects.nonNull(d.getDangerId()))
					.collect(Collectors.groupingBy(HighDangerInfo::getDangerId, Collectors.toList()));
			if (CollUtil.isNotEmpty(accDangerMap)) {

				Set<Entry<Long, List<HighDangerInfo>>> entrySet = accDangerMap.entrySet();
				Iterator<Entry<Long, List<HighDangerInfo>>> iterator = entrySet.iterator();

				while (iterator.hasNext()) {
					Entry<Long, List<HighDangerInfo>> next = iterator.next();

					// Long dangerId = next.getKey();
					List<HighDangerInfo> dangers = next.getValue();

					if (CollUtil.isNotEmpty(dangers) && Objects.nonNull(dangers.get(0).getScore())) {
						total = total.add(new BigDecimal(dangers.get(0).getScore()));
					}
				}
			}
		}
		return total;
	}
}
