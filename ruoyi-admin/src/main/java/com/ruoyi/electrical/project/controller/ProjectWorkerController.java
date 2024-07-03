package com.ruoyi.electrical.project.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.electrical.dto.ProjectWorkerAreaDto;
import com.ruoyi.electrical.project.domain.ProjectWorker;
import com.ruoyi.electrical.project.service.IProjectWorkerAreaService;
import com.ruoyi.electrical.project.service.IProjectWorkerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目工作人员Controller
 * 
 * @author fronttang
 * @date 2024-07-02
 */
@RestController
@RequestMapping("/project/ProjectWorker")
public class ProjectWorkerController extends BaseController {

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@Autowired
	private IProjectWorkerAreaService projectWorkerAreaService;

	/**
	 * 查询项目工作人员列表
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectWorker:list')")
	@GetMapping("/list")
	public TableDataInfo list(ProjectWorker projectWorker) {
		startPage();
		List<ProjectWorker> list = projectWorkerService.selectProjectWorkerList(projectWorker);
		return getDataTable(list);
	}

	/**
	 * 导出项目工作人员列表
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectWorker:export')")
	@Log(title = "项目工作人员", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, ProjectWorker projectWorker) {
		List<ProjectWorker> list = projectWorkerService.selectProjectWorkerList(projectWorker);
		ExcelUtil<ProjectWorker> util = new ExcelUtil<ProjectWorker>(ProjectWorker.class);
		util.exportExcel(response, list, "项目工作人员数据");
	}

	/**
	 * 获取项目工作人员详细信息
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectWorker:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(projectWorkerService.selectProjectWorkerById(id));
	}

	/**
	 * 新增项目工作人员
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectWorker:add')")
	@Log(title = "项目工作人员", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody ProjectWorker projectWorker) {
		return toAjax(projectWorkerService.insertProjectWorker(projectWorker));
	}

	/**
	 * 修改项目工作人员
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectWorker:edit')")
	@Log(title = "项目工作人员", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody ProjectWorker projectWorker) {
		return toAjax(projectWorkerService.updateProjectWorker(projectWorker));
	}

	/**
	 * 删除项目工作人员
	 */
	@PreAuthorize("@ss.hasPermi('project:ProjectWorker:remove')")
	@Log(title = "项目工作人员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(projectWorkerService.deleteProjectWorkerByIds(ids));
	}

	@PostMapping("/area")
	public AjaxResult saveProjectWorkerArea(@RequestBody @Valid ProjectWorkerAreaDto data) {
		return toAjax(projectWorkerAreaService.saveProjectWorkerArea(data));
	}
}
