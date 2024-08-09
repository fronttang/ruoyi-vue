package com.ruoyi.electrical.danger.mapper;

import java.util.List;

import com.ruoyi.electrical.dto.MissDeviceExportQueryDto;
import com.ruoyi.electrical.dto.MissDeviceJsonDataDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

public interface MissDeviceMapper {

	List<MissDeviceExportQueryDto> exportByQuery(OwnerUnitDangerGroupDetailDto data);

	List<MissDeviceExportQueryDto> exportByUnitId(Long[] ids);

	List<MissDeviceJsonDataDto> getMissDevicesByUnitId(Long unitId);
}
