package com.ruoyi.electrical.template.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
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
import com.ruoyi.electrical.template.domain.DetectTemplate;
import com.ruoyi.electrical.template.domain.DetectTemplateB;
import com.ruoyi.electrical.template.service.IDetectTemplateBService;
import com.ruoyi.electrical.template.service.IDetectTemplateService;
import com.ruoyi.electrical.vo.DetectTemplateBVo;

import cn.hutool.core.util.StrUtil;

/**
 * 模板列表Controller
 * 
 * @author fronttang
 * @date 2024-06-24
 */
@RestController
@RequestMapping("/template/Template")
public class DetectTemplateController extends BaseController {
	@Autowired
	private IDetectTemplateService detectTemplateService;

	@Autowired
	private IDetectTemplateBService templateBService;

	/**
	 * 查询模板列表列表
	 */
	@PreAuthorize("@ss.hasPermi('template:Template:list')")
	@GetMapping("/list")
	public TableDataInfo list(DetectTemplate detectTemplate) {
		startPage();
		List<DetectTemplate> list = detectTemplateService.selectDetectTemplateList(detectTemplate);
		return getDataTable(list);
	}

	/**
	 * 导出模板列表列表
	 */
	@PreAuthorize("@ss.hasPermi('template:Template:export')")
	@Log(title = "模板列表", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, DetectTemplate detectTemplate) {
		List<DetectTemplate> list = detectTemplateService.selectDetectTemplateList(detectTemplate);
		ExcelUtil<DetectTemplate> util = new ExcelUtil<DetectTemplate>(DetectTemplate.class);
		util.exportExcel(response, list, "模板列表数据");
	}

	/**
	 * 获取模板列表详细信息
	 */
	@PreAuthorize("@ss.hasPermi('template:Template:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(detectTemplateService.selectDetectTemplateById(id));
	}

	/**
	 * 新增模板列表
	 */
	@PreAuthorize("@ss.hasPermi('template:Template:add')")
	@Log(title = "模板列表", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody DetectTemplate detectTemplate) {

		DetectTemplate query = new DetectTemplate();
		query.setName(detectTemplate.getName());

		if (detectTemplateService.checkDetectTemplateName(query) > 0) {
			return error(StrUtil.format("已经存在名为[{}]的模板", query.getName()));
		}

		return toAjax(detectTemplateService.insertDetectTemplate(detectTemplate));
	}

	/**
	 * 修改模板列表
	 */
	@PreAuthorize("@ss.hasPermi('template:Template:edit')")
	@Log(title = "模板列表", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody DetectTemplate detectTemplate) {
		DetectTemplate query = new DetectTemplate();
		query.setName(detectTemplate.getName());
		query.setId(detectTemplate.getId());

		if (detectTemplateService.checkDetectTemplateName(query) > 0) {
			return error(StrUtil.format("已经存在名为[{}]的模板", query.getName()));
		}
		return toAjax(detectTemplateService.updateDetectTemplate(detectTemplate));
	}

	/**
	 * 删除模板列表
	 */
	@PreAuthorize("@ss.hasPermi('template:Template:remove')")
	@Log(title = "模板列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(detectTemplateService.deleteDetectTemplateByIds(ids));
	}

	@Log(title = "模板列表", businessType = BusinessType.OTHER)
	@GetMapping("/dict")
	public AjaxResult dict() {
		return success(detectTemplateService.selectDetectTemplateDict());
	}

	@Log(title = "模板列表", businessType = BusinessType.OTHER)
	@GetMapping("/dict/list")
	public AjaxResult dict(DetectTemplate detectTemplate) {
		return success(detectTemplateService.queryDetectTemplateDict(detectTemplate));
	}

	@GetMapping("/tableb/{templateId}")
	public AjaxResult getTemplateDetectb(@PathVariable Long templateId) {

		DetectTemplateBVo result = new DetectTemplateBVo();
		result.setTemplateId(templateId);
		List<DetectTemplateB> detectTemplateB = templateBService.selectDetectTemplateBByTemplateId(templateId);
		if (!CollectionUtils.isEmpty(detectTemplateB)) {
			List<String> views1 = detectTemplateB.stream().filter((data) -> {
				return "11".equalsIgnoreCase(data.getType());
			}).map(DetectTemplateB::getBid).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(views1)) {
				result.setViews1(views1);
			}

			List<String> views2 = detectTemplateB.stream().filter((data) -> {
				return "12".equalsIgnoreCase(data.getType());
			}).map(DetectTemplateB::getBid).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(views2)) {
				result.setViews2(views2);
			}

			List<String> views3 = detectTemplateB.stream().filter((data) -> {
				return "13".equalsIgnoreCase(data.getType());
			}).map(DetectTemplateB::getBid).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(views3)) {
				result.setViews3(views3);
			}

			List<String> reports = detectTemplateB.stream().filter((data) -> {
				return "2".equalsIgnoreCase(data.getType());
			}).map(DetectTemplateB::getBid).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(reports)) {
				result.setReports(reports);
			}
		}
		return success(result);
	}

	@PostMapping("/tableb")
	public AjaxResult saveTemplateDetectb(@RequestBody @Valid DetectTemplateBVo data) {
		return toAjax(templateBService.saveTemplateDetectb(data));
	}
}
