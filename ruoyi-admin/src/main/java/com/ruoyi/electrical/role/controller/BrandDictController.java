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
import com.ruoyi.electrical.role.domain.BrandDict;
import com.ruoyi.electrical.role.service.IBrandDictService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 品牌生产厂家Controller
 * 
 * @author fronttang
 * @date 2024-07-30
 */
@RestController
@RequestMapping("/role/BrandDict")
public class BrandDictController extends BaseController {
	@Autowired
	private IBrandDictService brandDictService;

	/**
	 * 查询品牌生产厂家列表
	 */
	@PreAuthorize("@ss.hasPermi('role:BrandDict:list')")
	@GetMapping("/list")
	public TableDataInfo list(BrandDict brandDict) {
		startPage();
		List<BrandDict> list = brandDictService.selectBrandDictList(brandDict);
		return getDataTable(list);
	}

	/**
	 * 导出品牌生产厂家列表
	 */
	@PreAuthorize("@ss.hasPermi('role:BrandDict:export')")
	@Log(title = "品牌生产厂家", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, BrandDict brandDict) {
		List<BrandDict> list = brandDictService.selectBrandDictList(brandDict);
		ExcelUtil<BrandDict> util = new ExcelUtil<BrandDict>(BrandDict.class);
		util.exportExcel(response, list, "品牌生产厂家数据");
	}

	/**
	 * 获取品牌生产厂家详细信息
	 */
	@PreAuthorize("@ss.hasPermi('role:BrandDict:query')")
	@GetMapping(value = "/{dictCode}")
	public AjaxResult getInfo(@PathVariable("dictCode") Long dictCode) {
		return success(brandDictService.selectBrandDictByDictCode(dictCode));
	}

	/**
	 * 新增品牌生产厂家
	 */
	@PreAuthorize("@ss.hasPermi('role:BrandDict:add')")
	@Log(title = "品牌生产厂家", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody BrandDict brandDict) {
		brandDict.setDictValue(brandDict.getDictLabel());
		return toAjax(brandDictService.insertBrandDict(brandDict));
	}

	/**
	 * 修改品牌生产厂家
	 */
	@PreAuthorize("@ss.hasPermi('role:BrandDict:edit')")
	@Log(title = "品牌生产厂家", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody BrandDict brandDict) {
		brandDict.setDictValue(brandDict.getDictLabel());
		return toAjax(brandDictService.updateBrandDict(brandDict));
	}

	/**
	 * 删除品牌生产厂家
	 */
	@PreAuthorize("@ss.hasPermi('role:BrandDict:remove')")
	@Log(title = "品牌生产厂家", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictCodes}")
	public AjaxResult remove(@PathVariable Long[] dictCodes) {
		return toAjax(brandDictService.deleteBrandDictByDictCodes(dictCodes));
	}
}
