package com.ruoyi.electrical.dto;

import java.util.List;

import com.ruoyi.electrical.dto.DangerExportRentalHouseDto.OwnerUnitDangerDataExportDto;

public interface IDangerExportDto {

	List<OwnerUnitDangerDataExportDto> getDangers();

	void setDangers(List<OwnerUnitDangerDataExportDto> dangers);

	void setBusinessLicense(byte[] businessLicense);

	void setOpenStatus(String openStatus);

	void setTotalScore(Long totalScore);
}
