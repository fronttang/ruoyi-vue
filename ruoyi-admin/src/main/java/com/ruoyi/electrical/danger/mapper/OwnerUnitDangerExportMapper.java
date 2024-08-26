package com.ruoyi.electrical.danger.mapper;

import java.util.List;

import com.ruoyi.electrical.dto.DangerExportQueryDto;
import com.ruoyi.electrical.dto.DangerExportUrbanVillageQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerExportDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

public interface OwnerUnitDangerExportMapper {

	List<DangerExportQueryDto> exportByQuery(OwnerUnitDangerGroupDetailDto data);

	List<DangerExportQueryDto> exportByUnitId(Long[] ids);

	List<OwnerUnitDangerExportDto> queryDangersByUnitId(Long unitId);

	List<DangerExportUrbanVillageQueryDto> exportUrbanVillageByQuery(OwnerUnitDangerGroupDetailDto data);

	List<DangerExportUrbanVillageQueryDto> exportUrbanVillageByUnitId(Long[] ids);
}
