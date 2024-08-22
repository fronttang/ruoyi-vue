package com.ruoyi.electrical.report.service;

public interface IHighFireRiskInitialReportService {

	int initialReport(Long reportId);

	int reviewReport(Long reportId);
}
