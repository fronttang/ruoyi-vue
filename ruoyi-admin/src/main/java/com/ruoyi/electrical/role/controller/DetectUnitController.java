package com.ruoyi.electrical.role.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.service.IDetectUnitService;

import cn.hutool.core.util.StrUtil;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检测单位Controller
 * 
 * @author fronttang
 * @date 2024-06-18
 */
@RestController
@RequestMapping("/projectrole/DetectUnit")
public class DetectUnitController extends BaseController {
	@Autowired
	private IDetectUnitService detectUnitService;

	/**
	 * 查询检测单位列表
	 */
	@PreAuthorize("@ss.hasPermi('projectrole:DetectUnit:list')")
	@GetMapping("/list")
	public TableDataInfo list(DetectUnit detectUnit) {
		startPage();
		List<DetectUnit> list = detectUnitService.selectDetectUnitList(detectUnit);
		return getDataTable(list);
	}

	/**
	 * 导出检测单位列表
	 */
	@PreAuthorize("@ss.hasPermi('projectrole:DetectUnit:export')")
	@Log(title = "检测单位", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, DetectUnit detectUnit) {
		List<DetectUnit> list = detectUnitService.selectDetectUnitList(detectUnit);
		ExcelUtil<DetectUnit> util = new ExcelUtil<DetectUnit>(DetectUnit.class);
		util.exportExcel(response, list, "检测单位数据");
	}

	/**
	 * 获取检测单位详细信息
	 */
	@PreAuthorize("@ss.hasPermi('projectrole:DetectUnit:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(detectUnitService.selectDetectUnitById(id));
	}

	/**
	 * 新增检测单位
	 */
	@PreAuthorize("@ss.hasPermi('projectrole:DetectUnit:add')")
	@Log(title = "检测单位", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody DetectUnit detectUnit) {
		DetectUnit query = new DetectUnit();
		query.setName(detectUnit.getName());
		if (detectUnitService.checkDetectUnitName(query) > 0) {
			return error(StrUtil.format("已经存在名为[{}]的检测单位", detectUnit.getName()));
		}
		return toAjax(detectUnitService.insertDetectUnit(detectUnit));
	}

	/**
	 * 修改检测单位
	 */
	@PreAuthorize("@ss.hasPermi('projectrole:DetectUnit:edit')")
	@Log(title = "检测单位", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody DetectUnit detectUnit) {

		DetectUnit query = new DetectUnit();
		query.setName(detectUnit.getName());
		query.setId(detectUnit.getId());

		if (detectUnitService.checkDetectUnitName(query) > 0) {
			return error(StrUtil.format("已经存在名为[{}]的检测单位", detectUnit.getName()));
		}

		return toAjax(detectUnitService.updateDetectUnit(detectUnit));
	}

	/**
	 * 删除检测单位
	 */
	@PreAuthorize("@ss.hasPermi('projectrole:DetectUnit:remove')")
	@Log(title = "检测单位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(detectUnitService.deleteDetectUnitByIds(ids));
	}

	@Log(title = "检测单位", businessType = BusinessType.OTHER)
	@GetMapping("/dict")
	public AjaxResult dict() {
		return success(detectUnitService.selectDetectUnitDict());
	}
}
