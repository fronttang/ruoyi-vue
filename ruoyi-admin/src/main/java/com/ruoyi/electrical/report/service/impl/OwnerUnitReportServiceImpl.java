package com.ruoyi.electrical.report.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.electrical.dto.OwnerUnitReportDto;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.mapper.OwnerUnitReportMapper;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.vo.OwnerUnitReportVo;

import cn.hutool.core.util.IdUtil;

/**
 * 初检报告Service业务层处理
 * 
 * @author fronttang
 * @date 2024-07-15
 */
@Service
public class OwnerUnitReportServiceImpl implements IOwnerUnitReportService {

	@Autowired
	private OwnerUnitReportMapper ownerUnitReportMapper;

	/**
	 * 查询初检报告列表
	 * 
	 * @param ownerUnitReport 初检报告
	 * @return 初检报告
	 */
	@Override
	public List<OwnerUnitReportVo> selectOwnerUnitReportList(OwnerUnitReportDto ownerUnitReport) {
		return ownerUnitReportMapper.selectOwnerUnitReportList(ownerUnitReport);
	}

	@Override
	public OwnerUnitReport selectOwnerUnitReportByUnitIdAndType(Long unitId, String type) {

		LoginUser loginUser = SecurityUtils.getLoginUser();

		OwnerUnitReport report = ownerUnitReportMapper.selectOwnerUnitReportByUnitIdAndType(unitId, type);
		if (report == null) {
			report = new OwnerUnitReport();
			report.setId(IdUtil.getSnowflake().nextId());
			report.setType(type);
			report.setInspector(loginUser.getUser().getNickName());
			report.setInspectorId(loginUser.getUserId());
			report.setUnitId(unitId);
			report.setDetectData(new Date());
			report.setDetectStatus("0");
			report.setStatus("0");
			report.setCreateTime(new Date());
			report.setUpdateTime(new Date());

			ownerUnitReportMapper.insertOwnerUnitReport(report);
		}
		return report;
	}

}
