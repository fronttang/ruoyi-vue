package com.ruoyi.electrical.danger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.electrical.danger.mapper.MissDeviceMapper;
import com.ruoyi.electrical.danger.service.IMissDeviceService;
import com.ruoyi.electrical.dto.MissDeviceExportQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

import cn.hutool.core.collection.CollUtil;

@Service
public class MissDeviceServiceImpl implements IMissDeviceService {

	@Autowired
	private MissDeviceMapper deviceMapper;

	@Override
	public List<MissDeviceExportQueryDto> exportByQuery(OwnerUnitDangerGroupDetailDto data) {

		List<MissDeviceExportQueryDto> exportByQuery = deviceMapper.exportByQuery(data);

		if (CollUtil.isNotEmpty(exportByQuery)) {

			exportByQuery.forEach((d) -> {
				d.setDevices(deviceMapper.getMissDevicesByUnitId(d.getId()));
			});

		}
		return exportByQuery;
	}

	@Override
	public List<MissDeviceExportQueryDto> exportByUnitId(Long[] unitIds) {

		List<MissDeviceExportQueryDto> exportByUnitId = deviceMapper.exportByUnitId(unitIds);
		if (CollUtil.isNotEmpty(exportByUnitId)) {

			exportByUnitId.forEach((d) -> {
				d.setDevices(deviceMapper.getMissDevicesByUnitId(d.getId()));
			});

		}
		return exportByUnitId;
	}
}