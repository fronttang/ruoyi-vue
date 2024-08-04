package com.ruoyi.electrical.project.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class OwnerUnitImportResultDto {

	/**
	 * 导入结果
	 */
	@Excel(name = "导入结果", orderNum = "100")
	private String result;
}
