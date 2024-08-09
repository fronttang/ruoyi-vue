package com.ruoyi.electrical.danger.service;

import java.util.List;

import com.ruoyi.electrical.dto.MissDeviceExportQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

public interface IMissDeviceService {

	List<MissDeviceExportQueryDto> exportByQuery(OwnerUnitDangerGroupDetailDto data);

	List<MissDeviceExportQueryDto> exportByUnitId(Long[] unitIds);
}
