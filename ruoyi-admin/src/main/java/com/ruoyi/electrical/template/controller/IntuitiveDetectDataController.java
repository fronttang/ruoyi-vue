package com.ruoyi.electrical.template.controller;

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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDataService;

/**
 * 直观检测表内容Controller
 * 
 * @author fronttang
 * @date 2024-06-23
 */
@RestController
@RequestMapping("/template/IntuitiveDetectData")
public class IntuitiveDetectDataController extends BaseController {
	@Autowired
	private IIntuitiveDetectDataService intuitiveDetectDataService;

	@Autowired
	private IIntuitiveDetectDangerService intuitiveDetectDangerService;

//    /**
//     * 查询直观检测表内容列表
//     */
//    @PreAuthorize("@ss.hasPermi('template:IntuitiveDetectData:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(IntuitiveDetectData intuitiveDetectData)
//    {
//        startPage();
//        List<IntuitiveDetectData> list = intuitiveDetectDataService.selectIntuitiveDetectDataList(intuitiveDetectData);
//        return getDataTable(list);
//    }

	/**
	 * 查询直观检测表内容列表
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectData:list')")
	@GetMapping("/list")
	public TableDataInfo list(IntuitiveDetectData intuitiveDetectData) {
		startPage();
		List<IntuitiveDetectData> list = intuitiveDetectDataService.selectIntuitiveDetectDataList(intuitiveDetectData);
		return getDataTable(list);
	}

	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectData:list')")
	@GetMapping("/list/view")
	public TableDataInfo listViewData(IntuitiveDetectData intuitiveDetectData) {
		startPage();
		List<IntuitiveDetectData> list = intuitiveDetectDataService
				.selectIntuitiveDetectDataViewList(intuitiveDetectData);
		return getDataTable(list);
	}

	/**
	 * 导出直观检测表内容列表
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectData:export')")
	@Log(title = "直观检测表内容", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, IntuitiveDetectData intuitiveDetectData) {
		List<IntuitiveDetectData> list = intuitiveDetectDataService.selectIntuitiveDetectDataList(intuitiveDetectData);
		ExcelUtil<IntuitiveDetectData> util = new ExcelUtil<IntuitiveDetectData>(IntuitiveDetectData.class);
		util.exportExcel(response, list, "直观检测表内容数据");
	}

	/**
	 * 获取直观检测表内容详细信息
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectData:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		IntuitiveDetectData intuitiveDetectData = intuitiveDetectDataService.selectIntuitiveDetectDataById(id);
		List<IntuitiveDetectDanger> dangers = intuitiveDetectDangerService.selectIntuitiveDetectDangersByDataId(id);
		AjaxResult result = AjaxResult.success();
		result.put("data", intuitiveDetectData);
		result.put("dangers", dangers);
		return result;
	}

	@GetMapping(value = "/view/{id}")
	public AjaxResult getViewInfo(@PathVariable("id") Long id) {
		IntuitiveDetectData intuitiveDetectData = intuitiveDetectDataService.selectIntuitiveDetectDataViewById(id);
		return success(intuitiveDetectData);
	}

	@PutMapping("/view/edit")
	public AjaxResult editView(@RequestBody IntuitiveDetectData intuitiveDetectData) {
		return toAjax(intuitiveDetectDataService.updateIntuitiveDetectDataView(intuitiveDetectData));
	}

	/**
	 * 新增直观检测表内容
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectData:add')")
	@Log(title = "直观检测表内容", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody IntuitiveDetectData intuitiveDetectData) {
		return toAjax(intuitiveDetectDataService.insertIntuitiveDetectData(intuitiveDetectData));
	}

	/**
	 * 修改直观检测表内容
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectData:edit')")
	@Log(title = "直观检测表内容", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody IntuitiveDetectData intuitiveDetectData) {
		return toAjax(intuitiveDetectDataService.updateIntuitiveDetectData(intuitiveDetectData));
	}

	/**
	 * 删除直观检测表内容
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectData:remove')")
	@Log(title = "直观检测表内容", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(intuitiveDetectDataService.deleteIntuitiveDetectDataByIds(ids));
	}

	@DeleteMapping("/view/{ids}")
	public AjaxResult removeView(@PathVariable Long[] ids) {
		return toAjax(intuitiveDetectDataService.deleteIntuitiveDetectDataViewByIds(ids));
	}

	@Log(title = "直观检测表内容字典", businessType = BusinessType.OTHER)
	@GetMapping("/dict")
	public AjaxResult dict(IntuitiveDetectData intuitiveDetectData) {
		return success(intuitiveDetectDataService.selectIntuitiveDetectDataDict(intuitiveDetectData));
	}
}
