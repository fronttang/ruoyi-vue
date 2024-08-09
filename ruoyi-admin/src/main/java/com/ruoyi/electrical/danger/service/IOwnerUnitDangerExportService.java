package com.ruoyi.electrical.danger.service;

import java.util.List;

import com.ruoyi.electrical.dto.DangerExportQueryDto;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;

public interface IOwnerUnitDangerExportService {

	List<DangerExportQueryDto> exportByQuery(OwnerUnitDangerGroupDetailDto data);

	List<DangerExportQueryDto> exportByUnitId(Long[] unitIds);

}
