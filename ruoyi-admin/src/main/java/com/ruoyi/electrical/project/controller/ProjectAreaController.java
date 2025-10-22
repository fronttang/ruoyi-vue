package com.ruoyi.electrical.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.electrical.project.domain.ProjectArea;
import com.ruoyi.electrical.project.service.IProjectAreaService;
import com.ruoyi.electrical.vo.ProjectAreaTree;

/**
 * 项目区域Controller
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@RestController
@RequestMapping("/project/ProjectArea")
public class ProjectAreaController extends BaseController {
	@Autowired
	private IProjectAreaService projectAreaService;

	/**
	 * 查询项目区域列表
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectArea:list')")
	@GetMapping("/list")
	public TableDataInfo list(ProjectArea projectArea) {
		startPage();
		List<ProjectArea> list = projectAreaService.selectProjectAreaList(projectArea);
		return getDataTable(list);
	}

	/**
	 * 导出项目区域列表
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectArea:export')")
	@Log(title = "项目区域", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, ProjectArea projectArea) {
		List<ProjectArea> list = projectAreaService.selectProjectAreaList(projectArea);
		ExcelUtil<ProjectArea> util = new ExcelUtil<ProjectArea>(ProjectArea.class);
		util.exportExcel(response, list, "项目区域数据");
	}

	/**
	 * 获取项目区域详细信息
	 */
	//@PreAuthorize("@ss.hasPermi('project:ProjectArea:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(projectAreaService.selectProjectAreaById(id));
	}

	/**
	 * 新增项目区域
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectArea:add')")
	@Log(title = "项目区域", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody ProjectArea projectArea) {
		return toAjax(projectAreaService.insertProjectArea(projectArea));
	}

	/**
	 * 修改项目区域
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectArea:edit')")
	@Log(title = "项目区域", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody ProjectArea projectArea) {
		return toAjax(projectAreaService.updateProjectArea(projectArea));
	}

	/**
	 * 删除项目区域
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectArea:remove')")
	@Log(title = "项目区域", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(projectAreaService.deleteProjectAreaByIds(ids));
	}

	@Log(title = "项目区域", businessType = BusinessType.OTHER)
	@GetMapping("/dict/{projectId}")
	public AjaxResult dict(@PathVariable Long projectId) {
		return success(projectAreaService.queryProjectAreaDictByProjectId(projectId));
	}

	@Log(title = "项目区域", businessType = BusinessType.OTHER)
	@GetMapping("/dict")
	public AjaxResult dict() {
		return success(projectAreaService.queryProjectAreaDict());
	}

	@GetMapping("/dict/{type}/{projectId}")
	public AjaxResult dictOption(@PathVariable("projectId") Long projectId, @PathVariable("type") String type) {
		return success(projectAreaService.queryProjectAreaDictByProjectIdAndType(projectId, type));
	}

	@GetMapping("/tree/{projectId}")
	public AjaxResult areaTree(@PathVariable("projectId") Long projectId) {

		List<ProjectArea> projectAreas = projectAreaService.queryProjectAreaByProjectId(projectId);

		Map<String, ProjectAreaTree> districtMap = new HashMap<String, ProjectAreaTree>();
		Map<String, ProjectAreaTree> streetMap = new HashMap<String, ProjectAreaTree>();
		Map<String, ProjectAreaTree> communityMap = new HashMap<String, ProjectAreaTree>();
		Map<String, ProjectAreaTree> hamletMap = new HashMap<String, ProjectAreaTree>();
		if (!CollectionUtils.isEmpty(projectAreas)) {
			projectAreas.forEach((area) -> {

				if (StringUtils.hasText(area.getDistrict())) {
					ProjectAreaTree districtVo = districtMap.get(area.getDistrict());
					if (districtVo == null) {
						districtVo = new ProjectAreaTree();
						districtVo.setId(area.getDistrict());
						districtVo.setLabel(area.getDistrictName());
						districtVo.setArea(area);
						districtMap.put(area.getDistrict(), districtVo);
					}

					if (StringUtils.hasText(area.getStreet())) {
						ProjectAreaTree streetVo = streetMap.get(area.getStreet());
						if (streetVo == null) {
							streetVo = new ProjectAreaTree();
							streetVo.setId(area.getStreet());
							streetVo.setLabel(area.getStreetName());
							streetVo.setArea(area);
							streetMap.put(area.getStreet(), streetVo);
							districtVo.getChildren().add(streetVo);
						}
						if (StringUtils.hasText(area.getCommunity())) {
							ProjectAreaTree communityVo = communityMap.get(area.getCommunity());
							if (communityVo == null) {
								communityVo = new ProjectAreaTree();
								communityVo.setId(area.getCommunity());
								communityVo.setLabel(area.getCommunityName());
								communityVo.setArea(area);
								communityMap.put(area.getCommunity(), communityVo);
								streetVo.getChildren().add(communityVo);
							}
							if (StringUtils.hasText(area.getHamlet())) {
								ProjectAreaTree hamletVo = hamletMap.get(area.getHamlet());
								if (hamletVo == null) {
									hamletVo = new ProjectAreaTree();
									hamletVo.setId(area.getHamlet());
									hamletVo.setLabel(area.getHamletName());
									hamletVo.setArea(area);
									hamletMap.put(area.getHamlet(), hamletVo);
									communityVo.getChildren().add(hamletVo);
								}
							}
						}
					}
				}

			});
		}
		List<ProjectAreaTree> result = new ArrayList<ProjectAreaTree>(districtMap.values());
		return success(result);
	}

}
