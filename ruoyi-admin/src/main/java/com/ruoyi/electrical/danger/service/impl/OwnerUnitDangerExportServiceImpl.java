package com.ruoyi.electrical.danger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.electrical.danger.mapper.OwnerUnitDangerExportMapper;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerExportService;
import com.ruoyi.electrical.dto.DangerExportQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

import cn.hutool.core.collection.CollUtil;

@Service
public class OwnerUnitDangerExportServiceImpl implements IOwnerUnitDangerExportService {

	@Autowired
	private OwnerUnitDangerExportMapper dangerExportMapper;

	@Override
	public List<DangerExportQueryDto> exportByQuery(OwnerUnitDangerGroupDetailDto data) {

		List<DangerExportQueryDto> exportByQuery = dangerExportMapper.exportByQuery(data);
		if (CollUtil.isNotEmpty(exportByQuery)) {

			exportByQuery.forEach((d) -> {
				d.setDangers(dangerExportMapper.queryDangersByUnitId(d.getId()));
			});
		}
		return exportByQuery;
	}

	@Override
	public List<DangerExportQueryDto> exportByUnitId(Long[] unitIds) {
		List<DangerExportQueryDto> exportByUnitId = dangerExportMapper.exportByUnitId(unitIds);
		if (CollUtil.isNotEmpty(exportByUnitId)) {

			exportByUnitId.forEach((d) -> {
				d.setDangers(dangerExportMapper.queryDangersByUnitId(d.getId()));
			});
		}
		return exportByUnitId;
	}

}
