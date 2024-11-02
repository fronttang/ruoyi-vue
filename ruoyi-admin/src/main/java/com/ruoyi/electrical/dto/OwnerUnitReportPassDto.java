package com.ruoyi.electrical.dto;

import lombok.Data;

@Data
public class OwnerUnitReportPassDto {

	private Long reportId;

	private Long unitId;

	private String type;

	private String remark;

	private String operationPic;
}
