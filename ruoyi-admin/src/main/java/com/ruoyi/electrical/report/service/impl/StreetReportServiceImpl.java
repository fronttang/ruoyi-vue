package com.ruoyi.electrical.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.electrical.danger.mapper.OwnerUnitDangerMapper;
import com.ruoyi.electrical.danger.service.ComputeHighScoreService;
import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.dto.WorkerRoleSettingDto;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.report.domain.OwnerUnitReportLog;
import com.ruoyi.electrical.report.domain.StreetReport;
import com.ruoyi.electrical.report.mapper.OwnerUnitReportLogMapper;
import com.ruoyi.electrical.report.mapper.StreetReportMapper;
import com.ruoyi.electrical.report.service.IStreetReportService;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.vo.StreetReportVo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 街道报告Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
@Slf4j
@Service
public class StreetReportServiceImpl implements IStreetReportService {

	@Autowired
	private StreetReportMapper streetReportMapper;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private OwnerUnitReportLogMapper reportLogMapper;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IDetectUnitService detectUnitService;

	@Override
	public List<StreetReportVo> selectStreetReportList(StreetReport report) {
		return streetReportMapper.selectStreetReportList(report);
	}

	@Override
	public StreetReport getStreetReportByAreaAndType(StreetReport data) {
		StreetReport report = streetReportMapper.getStreetReportByAreaAndType(data);

		if (report == null) {
			report = new StreetReport();
			report.setId(IdUtil.getSnowflake().nextId());
			report.setProjectId(data.getProjectId());
			report.setDistrict(data.getDistrict());
			report.setStreet(data.getStreet());
			report.setType("1");
			report.setStatus("0");
			report.setCreateTime(DateUtils.getNowDate());
			report.setUpdateTime(DateUtils.getNowDate());
			streetReportMapper.insertStreetReport(report);
		}
		return report;
	}

	@Override
	public boolean pass(Long reportId) {

		StreetReport report = streetReportMapper.selectStreetReportById(reportId);
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

		StreetReport update = new StreetReport();
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

		streetReportMapper.updateStreetReport(update);
		// 添加日志
		reportLogMapper.insertOwnerUnitReportLog(reportLog);

		return true;
	}

	@Override
	public boolean notPass(OwnerUnitReportPassDto data) {

		StreetReport report = streetReportMapper.selectStreetReportById(data.getReportId());
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

		StreetReport update = new StreetReport();
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

		streetReportMapper.updateStreetReport(update);
		// 添加日志
		reportLogMapper.insertOwnerUnitReportLog(reportLog);

		return true;
	}

	@Override
	public boolean resetStatusByReportId(Long reportId) {

		StreetReport report = streetReportMapper.selectStreetReportById(reportId);
		if (report == null) {
			return false;
		} else {

			StreetReport update = new StreetReport();
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

			streetReportMapper.updateStreetReport(update);
			// 添加日志
			reportLogMapper.insertOwnerUnitReportLog(reportLog);
		}
		return true;

	}

	@Autowired
	private OwnerUnitDangerMapper ownerUnitDangerMapper;

	public static final Map<String, String> HIGH_TYPE_NAME_MAP = new HashMap<String, String>();

	static {
		HIGH_TYPE_NAME_MAP.put("1", "出租屋");
		HIGH_TYPE_NAME_MAP.put("2", "三小场所");
		HIGH_TYPE_NAME_MAP.put("3", "住宅小区");
		HIGH_TYPE_NAME_MAP.put("4", "工业企业");
		HIGH_TYPE_NAME_MAP.put("5", "公共场所");
	}

	private ComputeHighScoreService compute = new ComputeHighScoreService();

	@Override
	public String wordReport(Long reportId) {
		return null;
	}

}
