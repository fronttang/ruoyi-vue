package com.ruoyi.electrical.dto;

import java.util.List;

import com.ruoyi.electrical.dto.DangerExportRentalHouseDto.OwnerUnitDangerFormExportDto;

public interface IDangerExportDto {

	List<OwnerUnitDangerFormExportDto> getForms();

	void setForms(List<OwnerUnitDangerFormExportDto> dangers);

	void setBusinessLicense(byte[] businessLicense);

	void setOpenStatus(String openStatus);

	void setTotalScore(String totalScore);

	void setId(Long id);

	void setDoorPicture(byte[] doorPicture);

	void setMngQrcodePicture(byte[] mngQrcode);

	void setInspector(String inspector);

	void setInspectorDate(String inspectorDate);
}
