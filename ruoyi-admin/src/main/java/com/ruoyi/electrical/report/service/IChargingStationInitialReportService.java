package com.ruoyi.electrical.report.service;

import com.ruoyi.common.core.domain.AjaxResult;

public interface IChargingStationInitialReportService {

	AjaxResult initialReport(Long reportId);

	AjaxResult reviewReport(Long reportId);
}
