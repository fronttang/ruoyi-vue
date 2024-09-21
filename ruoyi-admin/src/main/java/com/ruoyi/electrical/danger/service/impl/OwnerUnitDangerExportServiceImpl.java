package com.ruoyi.electrical.danger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.mapper.OwnerUnitDangerExportMapper;
import com.ruoyi.electrical.danger.mapper.OwnerUnitDangerMapper;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerExportService;
import com.ruoyi.electrical.dto.DangerExportQueryDto;
import com.ruoyi.electrical.dto.DangerExportStationQueryDto;
import com.ruoyi.electrical.dto.DangerExportUrbanVillageQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

import cn.hutool.core.collection.CollUtil;

@Service
public class OwnerUnitDangerExportServiceImpl implements IOwnerUnitDangerExportService {

	@Autowired
	private OwnerUnitDangerExportMapper dangerExportMapper;

	@Autowired
	private OwnerUnitDangerMapper ownerUnitDangerMapper;

	@Override
	public List<DangerExportQueryDto> exportByQuery(OwnerUnitDangerGroupDetailDto data) {

		List<DangerExportQueryDto> exportByQuery = dangerExportMapper.exportByQuery(data);
		if (CollUtil.isNotEmpty(exportByQuery)) {

			exportByQuery.forEach((d) -> {
				d.setDangers(ownerUnitDangerMapper.selectOwnerDangerHighReportByUnitId(d.getId()));
			});
		}
		return exportByQuery;
	}

	@Override
	public List<DangerExportQueryDto> exportByUnitId(Long[] unitIds) {
		List<DangerExportQueryDto> exportByUnitId = dangerExportMapper.exportByUnitId(unitIds);
		if (CollUtil.isNotEmpty(exportByUnitId)) {

			exportByUnitId.forEach((d) -> {
				d.setDangers(ownerUnitDangerMapper.selectOwnerDangerHighReportByUnitId(d.getId()));
			});
		}
		return exportByUnitId;
	}

	@Override
	public List<DangerExportUrbanVillageQueryDto> exportUrbanVillageByQuery(OwnerUnitDangerGroupDetailDto data) {
		List<DangerExportUrbanVillageQueryDto> exportData = dangerExportMapper.exportUrbanVillageByQuery(data);
		if (CollUtil.isNotEmpty(exportData)) {

			exportData.forEach((d) -> {
				OwnerUnitDanger query = new OwnerUnitDanger();
				query.setUnitId(d.getId());
				d.setDangers(ownerUnitDangerMapper.ownerUnitDangerList(query));
			});
		}
		return exportData;
	}

	@Override
	public List<DangerExportUrbanVillageQueryDto> exportUrbanVillageByUnitId(Long[] unitIds) {
		List<DangerExportUrbanVillageQueryDto> exportData = dangerExportMapper.exportUrbanVillageByUnitId(unitIds);
		if (CollUtil.isNotEmpty(exportData)) {

			exportData.forEach((d) -> {
				OwnerUnitDanger query = new OwnerUnitDanger();
				query.setUnitId(d.getId());
				d.setDangers(ownerUnitDangerMapper.ownerUnitDangerList(query));
			});
		}
		return exportData;
	}

	@Override
	public List<DangerExportStationQueryDto> exportStationByQuery(OwnerUnitDangerGroupDetailDto data,
			boolean allRounds) {
		List<DangerExportStationQueryDto> exportData = dangerExportMapper.exportStationByQuery(data);
		if (CollUtil.isNotEmpty(exportData)) {

			exportData.forEach((d) -> {
				OwnerUnitDanger query = new OwnerUnitDanger();
				query.setUnitId(d.getId());
				if (!allRounds) {
					query.setRounds(d.getRounds());
				}
				d.setDangers(ownerUnitDangerMapper.ownerUnitDangerListAllRounds(query));
			});
		}
		return exportData;
	}

	@Override
	public List<DangerExportStationQueryDto> exportStationByUnitId(Long[] unitIds, boolean allRounds) {
		List<DangerExportStationQueryDto> exportData = dangerExportMapper.exportStationByUnitId(unitIds);
		if (CollUtil.isNotEmpty(exportData)) {

			exportData.forEach((d) -> {
				OwnerUnitDanger query = new OwnerUnitDanger();
				query.setUnitId(d.getId());
				if (!allRounds) {
					query.setRounds(d.getRounds());
				}
				d.setDangers(ownerUnitDangerMapper.ownerUnitDangerListAllRounds(query));
			});
		}
		return exportData;
	}

}
