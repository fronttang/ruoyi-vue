package com.ruoyi.electrical.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.domain.ProjectArea;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.project.service.IProjectAreaService;
import com.ruoyi.electrical.project.service.IProjectService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 业主单元Controller
 * 
 * @author fronttang
 * @date 2024-06-21
 */
@RestController
@RequestMapping("/project/OwnerUnit")
public class OwnerUnitController extends BaseController {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectAreaService projectAreaService;

	@Autowired
	private RedisCache redisCache;

	/**
	 * 查询业主单元列表
	 */
	@PreAuthorize("@ss.hasPermi('project:OwnerUnit:list')")
	@GetMapping("/list")
	public TableDataInfo list(OwnerUnit ownerUnit) {
		startPage();
		List<OwnerUnit> list = ownerUnitService.selectOwnerUnitList(ownerUnit);
		return getDataTable(list);
	}

	/**
	 * 导出业主单元列表
	 */
	@PreAuthorize("@ss.hasPermi('project:OwnerUnit:export')")
	@Log(title = "业主单元", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, OwnerUnit ownerUnit) {
		List<OwnerUnit> list = ownerUnitService.selectOwnerUnitList(ownerUnit);
		ExcelUtil<OwnerUnit> util = new ExcelUtil<OwnerUnit>(OwnerUnit.class);
		util.exportExcel(response, list, "业主单元数据");
	}

	/**
	 * 获取业主单元详细信息
	 */
	@PreAuthorize("@ss.hasPermi('project:OwnerUnit:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(ownerUnitService.selectOwnerUnitById(id));
	}

	/**
	 * 新增业主单元
	 */
	@PreAuthorize("@ss.hasPermi('project:OwnerUnit:add')")
	@Log(title = "业主单元", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody OwnerUnit ownerUnit) {
		if (ownerUnit.getProjectId() != null) {
			Project project = projectService.selectProjectById(ownerUnit.getProjectId());
			ownerUnit.setDetectId(project.getDetectId());
		}

		ProjectArea projectArea = projectAreaService.selectProjectAreaById(ownerUnit.getArea());
		if (projectArea != null) {
			ownerUnit.setDistrict(projectArea.getDistrict());
			ownerUnit.setStreet(projectArea.getStreet());
			ownerUnit.setCommunity(projectArea.getCommunity());
			ownerUnit.setHamlet(projectArea.getHamlet());
		}

		// 同名检查
		if (ownerUnitService.checkOwnerUnitName(ownerUnit) > 0) {
			return AjaxResult.error(StrUtil.format("该项目区域下已存在名为[{}]的业主单元", ownerUnit.getName()));
		}

		return toAjax(ownerUnitService.insertOwnerUnit(ownerUnit));
	}

	/**
	 * 修改业主单元
	 */
	@PreAuthorize("@ss.hasPermi('project:OwnerUnit:edit')")
	@Log(title = "业主单元", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody OwnerUnit ownerUnit) {

		if (ownerUnit.getProjectId() != null) {
			Project project = projectService.selectProjectById(ownerUnit.getProjectId());
			ownerUnit.setDetectId(project.getDetectId());
		}

		ProjectArea projectArea = projectAreaService.selectProjectAreaById(ownerUnit.getArea());
		if (projectArea != null) {
			ownerUnit.setDistrict(projectArea.getDistrict());
			ownerUnit.setStreet(projectArea.getStreet());
			ownerUnit.setCommunity(projectArea.getCommunity());
			ownerUnit.setHamlet(projectArea.getHamlet());
		}

		return toAjax(ownerUnitService.updateOwnerUnit(ownerUnit));
	}

	/**
	 * 删除业主单元
	 */
	@PreAuthorize("@ss.hasPermi('project:OwnerUnit:remove')")
	@Log(title = "业主单元", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(ownerUnitService.deleteOwnerUnitByIds(ids));
	}

	@GetMapping("/rounds/{unitId}")
	public AjaxResult startRounds(@PathVariable Long unitId) {

		List<String> steps = redisCache.getCacheObject(CacheConstants.UNIT_ROUND_STEP + unitId);

		String status = redisCache.getCacheObject(CacheConstants.UNIT_ROUND_STEP_STATUS + unitId);

		if (CollUtil.isNotEmpty(steps) && steps.size() < 4 && !"error".equalsIgnoreCase(status)) {
			return AjaxResult.error("还有推进中的轮次，请稍后重试。");
		}
		boolean success = ownerUnitService.startRounds(unitId);
		return toAjax(success);
	}

	@GetMapping("/rounds/step/{unitId}")
	public AjaxResult roundsInfo(@PathVariable Long unitId) {

		OwnerUnit unitUnit = ownerUnitService.selectOwnerUnitById(unitId);
		AjaxResult ajax = AjaxResult.success();
		if (unitUnit != null) {
			ajax.put("rounds", unitUnit.getRounds());
			List<String> steps = redisCache.getCacheObject(CacheConstants.UNIT_ROUND_STEP + unitId);
			ajax.put("steps", steps);
			String status = redisCache.getCacheObject(CacheConstants.UNIT_ROUND_STEP_STATUS + unitId);
			ajax.put("status", status);
		}
		return ajax;
	}
}
