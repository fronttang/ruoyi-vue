package com.ruoyi.electrical.report.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.electrical.dto.OwnerUnitReportDto;
import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.dto.WorkerRoleSettingDto;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.domain.OwnerUnitReportLog;
import com.ruoyi.electrical.report.mapper.OwnerUnitReportLogMapper;
import com.ruoyi.electrical.report.mapper.OwnerUnitReportMapper;
import com.ruoyi.electrical.report.service.IChargingStationInitialReportService;
import com.ruoyi.electrical.report.service.IHighFireRiskInitialReportService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.report.service.IUrbanVillageUnitInitialReportService;
import com.ruoyi.electrical.vo.OwnerUnitReportVo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 初检报告Service业务层处理
 * 
 * @author fronttang
 * @date 2024-07-15
 */
@Slf4j
@Service
public class OwnerUnitReportServiceImpl implements IOwnerUnitReportService {

	@Autowired
	private OwnerUnitReportMapper ownerUnitReportMapper;
	
	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private OwnerUnitReportLogMapper reportLogMapper;

	@Autowired
	private RedisCache redisCache;
	
	@Autowired
	private IUrbanVillageUnitInitialReportService urbanVillageUnitInitialReportService;

	@Autowired
	private IHighFireRiskInitialReportService fireRiskInitialReportService;

	@Autowired
	private IChargingStationInitialReportService chargingStationInitialReportService;

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

		OwnerUnitReport report = ownerUnitReportMapper.selectOwnerUnitReportByUnitIdAndType(unitId, type);
		if (report == null) {
			report = new OwnerUnitReport();
			report.setId(IdUtil.getSnowflake().nextId());
			report.setType(type);
			try {
				LoginUser loginUser = SecurityUtils.getLoginUser();
				report.setInspector(loginUser.getUser().getNickName());
				report.setInspectorId(loginUser.getUserId());
			} catch (Exception e) {
				log.error("", e);
			}
			report.setUnitId(unitId);
			if("1".equals(type)) {
				report.setDetectData(new Date());
			}
			report.setDetectStatus("0");
			report.setStatus("0");
			report.setCreateTime(DateUtils.getNowDate());
			report.setUpdateTime(DateUtils.getNowDate());

			ownerUnitReportMapper.insertOwnerUnitReport(report);
		}
		return report;
	}
	
	@Override
	public OwnerUnitReport selectOwnerUnitReportByUnitIdAndTypeAndCode(Long unitId, String type, String code) {

		OwnerUnitReport report = ownerUnitReportMapper.selectOwnerUnitReportByUnitIdAndType(unitId, type);
		if (report == null) {
			report = new OwnerUnitReport();
			report.setId(IdUtil.getSnowflake().nextId());
			report.setType(type);
			try {
				LoginUser loginUser = SecurityUtils.getLoginUser();
				report.setInspector(loginUser.getUser().getNickName());
				report.setInspectorId(loginUser.getUserId());
			} catch (Exception e) {
				log.error("", e);
			}
			report.setCode(code);
			report.setUnitId(unitId);
			if ("1".equals(type)) {
				report.setDetectData(new Date());
			}
			report.setDetectStatus("0");
			report.setStatus("0");
			report.setCreateTime(DateUtils.getNowDate());
			report.setUpdateTime(DateUtils.getNowDate());

			ownerUnitReportMapper.insertOwnerUnitReport(report);
		} else {
			report.setCode(code);
			ownerUnitReportMapper.updateOwnerUnitReport(report);
		}
		
		return report;
	}

	/**
	 * 操作类型 1初检 2提交初审 3初审驳回 4初审通过提交复审 5复审驳回 6完成复审 7重置为未初审核
	 * 
	 * 报告状态 0未审核，1待初审 2待复审 3完成
	 */
	@Override
	@Transactional
	public boolean pass(Long reportId) {

		OwnerUnitReport report = ownerUnitReportMapper.selectOwnerUnitReportById(reportId);
		if (report == null) {
			return false;
		}

		SysUser user = SecurityUtils.getLoginUser().getUser();

		WorkerRoleSettingDto workerRole = redisCache
				.getCacheObject(CacheConstants.USER_PROJECT_ROLE_KEY + user.getUserId());

		String workerRoleId = "";
		String workerRoleName = "";

		if (workerRole != null) {
			workerRoleId = workerRole.getId();
			workerRoleName = workerRole.getName();
		}

		OwnerUnitReport update = new OwnerUnitReport();
		update.setId(reportId);
		update.setReject("0");
		update.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
		update.setUpdateTime(DateUtils.getNowDate());

		OwnerUnitReportLog reportLog = new OwnerUnitReportLog();
		reportLog.setReportId(reportId);
		reportLog.setOperator(user.getNickName());
		reportLog.setOperatorId(user.getUserId());
		reportLog.setOperatorRole(workerRoleId);
		reportLog.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		reportLog.setCreateTime(DateUtils.getNowDate());

		// 未审核 -> 待初审核
		if ("0".equalsIgnoreCase(report.getStatus())) {
			update.setStatus("1");// 2待复审

			reportLog.setOperationType("2");// 2提交初审
			reportLog.setContent(StrUtil.format("由{}（{}）提交初审", workerRoleName, user.getNickName()));

		} else if ("1".equalsIgnoreCase(report.getStatus())) {
			// 初审核通过提交复审核

			update.setStatus("2");// 2待复审

			reportLog.setOperationType("6");// 6完成复审
			reportLog.setContent(StrUtil.format("由{}（{}）通过初审并提交复审", workerRoleName, user.getNickName()));
		} else if ("2".equalsIgnoreCase(report.getStatus())) {
			// 复审通过完成

			update.setStatus("3");// 3完成

			reportLog.setOperationType("4");// 4初审通过提交复审
			reportLog.setContent(StrUtil.format("由{}（{}）通过复审并完结", workerRoleName, user.getNickName()));

		} else {
			return false;
		}

		ownerUnitReportMapper.updateOwnerUnitReport(update);
		// 添加日志
		reportLogMapper.insertOwnerUnitReportLog(reportLog);

		return true;
	}

	@Override
	@Transactional
	public boolean notPass(OwnerUnitReportPassDto data) {

		OwnerUnitReport report = ownerUnitReportMapper.selectOwnerUnitReportById(data.getReportId());
		if (report == null) {
			return false;
		}

		SysUser user = SecurityUtils.getLoginUser().getUser();

		WorkerRoleSettingDto workerRole = redisCache
				.getCacheObject(CacheConstants.USER_PROJECT_ROLE_KEY + user.getUserId());

		String workerRoleId = "";
		String workerRoleName = "";

		if (workerRole != null) {
			workerRoleId = workerRole.getId();
			workerRoleName = workerRole.getName();
		}

		OwnerUnitReport update = new OwnerUnitReport();
		update.setId(report.getId());
		update.setReject("1");
		update.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
		update.setUpdateTime(DateUtils.getNowDate());

		OwnerUnitReportLog reportLog = new OwnerUnitReportLog();
		reportLog.setReportId(report.getId());
		reportLog.setOperator(user.getNickName());
		reportLog.setOperatorId(user.getUserId());
		reportLog.setOperatorRole(workerRoleId);
		reportLog.setRemark(data.getRemark());
		reportLog.setOperationPic(data.getOperationPic());
		reportLog.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		reportLog.setCreateTime(DateUtils.getNowDate());

		// 报告状态 0未审核，1待初审 2待复审 3完成

		// 操作类型 1初检 2提交初审 3初审驳回 4初审通过提交复审 5复审驳回 6完成复审

		// 状态 未审核 驳回不做处理
		if ("0".equalsIgnoreCase(report.getStatus())) {
			return true;
		} else if ("1".equalsIgnoreCase(report.getStatus())) {
			// 报告状态 0未审核，1待初审 2待复审 3完成

			update.setStatus("0");// 0未审核

			reportLog.setOperationType("3");// 3初审驳回
			reportLog.setContent(StrUtil.format("由{}（{}）驳回", workerRoleName, user.getNickName()));
		} else if ("2".equalsIgnoreCase(report.getStatus())) {

			update.setStatus("1");// 1待初审

			reportLog.setOperationType("5");// 5复审驳回
			reportLog.setContent(StrUtil.format("由{}（{}）驳回", workerRoleName, user.getNickName()));

		} else {
			return false;
		}

		ownerUnitReportMapper.updateOwnerUnitReport(update);
		// 添加日志
		reportLogMapper.insertOwnerUnitReportLog(reportLog);

		return true;
	}

	/**
	 * 7重置为未初审
	 */
	@Override
	@Transactional
	public boolean resetStatusOwnerUnitReportByUnitIdAndType(Long unitId, String type) {

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
			report.setCreateTime(DateUtils.getNowDate());
			report.setUpdateTime(DateUtils.getNowDate());

			ownerUnitReportMapper.insertOwnerUnitReport(report);
		} else {

			OwnerUnitReport update = new OwnerUnitReport();
			update.setId(report.getId());
			update.setStatus("0");
			update.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
			update.setUpdateTime(DateUtils.getNowDate());

			SysUser user = SecurityUtils.getLoginUser().getUser();

			WorkerRoleSettingDto workerRole = redisCache
					.getCacheObject(CacheConstants.USER_PROJECT_ROLE_KEY + user.getUserId());

			String workerRoleId = "";
			String workerRoleName = "";

			if (workerRole != null) {
				workerRoleId = workerRole.getId();
				workerRoleName = workerRole.getName();
			}

			OwnerUnitReportLog reportLog = new OwnerUnitReportLog();
			reportLog.setReportId(report.getId());
			reportLog.setOperator(user.getNickName());
			reportLog.setOperatorId(user.getUserId());
			reportLog.setOperatorRole(workerRoleId);
			reportLog.setRemark(null);
			reportLog.setOperationPic(null);
			reportLog.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
			reportLog.setCreateTime(DateUtils.getNowDate());
			reportLog.setOperationType("7");// 7重置为未初审
			reportLog.setContent(StrUtil.format("由{}（{}）重置为未审核", workerRoleName, user.getNickName()));

			ownerUnitReportMapper.updateOwnerUnitReport(update);
			// 添加日志
			reportLogMapper.insertOwnerUnitReportLog(reportLog);
		}
		return true;

	}

	@Override
	public OwnerUnitReport selectOwnerUnitReportById(Long id) {
		return ownerUnitReportMapper.selectOwnerUnitReportById(id);
	}

	@Override
	public int updateOwnerUnitReport(OwnerUnitReport ownerUnitReport) {
		return ownerUnitReportMapper.updateOwnerUnitReport(ownerUnitReport);
	}

	@Override
	public boolean setReportDate(OwnerUnitReport report) {

		Date startDate = report.getStartDate();
		Date endDate = report.getEndDate();

		if (startDate == null || endDate == null) {
			return false;
		}

		long start = startDate.getTime();
		long end = endDate.getTime();

		Random random = new Random();
		long reportDate = start + (long) (random.nextDouble() * (end - start + 1));

		OwnerUnitReport update = new OwnerUnitReport();
		update.setId(report.getId());
		update.setStartDate(startDate);
		update.setEndDate(endDate);
		update.setReportDate(new Date(reportDate));

		ownerUnitReportMapper.updateOwnerUnitReport(update);

		return true;
	}

	@Override
	public List<OwnerUnitReportVo> selectOwnerUnitReportListByUnitIds(Long[] unitIds, Long projectId, String type) {
		return ownerUnitReportMapper.selectOwnerUnitReportListByUnitIds(unitIds, projectId, type);
	}
	
	@Override
	public AjaxResult reportGenerate(Long unitId, String type) {
		
		OwnerUnitReport report = this.selectOwnerUnitReportByUnitIdAndType(unitId, type);
		if (report == null) {
			return AjaxResult.error();
		}

		OwnerUnit ownerUnit = ownerUnitService.selectOwnerUnitById(unitId);

		if (ownerUnit == null) {
			return AjaxResult.error();
		}
		
		if ("1".equalsIgnoreCase(report.getType())) {

			// 城中村 / 工业园
			if ("1".equalsIgnoreCase(ownerUnit.getType()) || "2".equalsIgnoreCase(ownerUnit.getType())) {
				return urbanVillageUnitInitialReportService.initialReport(report.getId());
			} else if ("3".equalsIgnoreCase(ownerUnit.getType())) {
				// 高风险
				return fireRiskInitialReportService.initialReport(report.getId());

			} else if ("4".equalsIgnoreCase(ownerUnit.getType())) {
				// 充电站
				return chargingStationInitialReportService.initialReport(report.getId());

			} else {
				return AjaxResult.error();
			}
		} else if ("2".equalsIgnoreCase(report.getType())) {

			// 城中村 / 工业园
			if ("1".equalsIgnoreCase(ownerUnit.getType()) || "2".equalsIgnoreCase(ownerUnit.getType())) {
				return urbanVillageUnitInitialReportService.reviewReport(report.getId());
			} else if ("3".equalsIgnoreCase(ownerUnit.getType())) {
				// 高风险
				return fireRiskInitialReportService.reviewReport(report.getId());

			} else if ("4".equalsIgnoreCase(ownerUnit.getType())) {
				// 充电站
				return chargingStationInitialReportService.reviewReport(report.getId());

			} else {
				return AjaxResult.error();
			}
		} else {
			return AjaxResult.error();
		}
	}

}
