package com.ruoyi.electrical.template.controller;

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
import com.ruoyi.electrical.template.domain.DetectTemplate;
import com.ruoyi.electrical.template.service.IDetectTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模板列表Controller
 * 
 * @author fronttang
 * @date 2024-06-24
 */
@RestController
@RequestMapping("/template/Template")
public class DetectTemplateController extends BaseController
{
    @Autowired
    private IDetectTemplateService detectTemplateService;

    /**
     * 查询模板列表列表
     */
    @PreAuthorize("@ss.hasPermi('template:Template:list')")
    @GetMapping("/list")
    public TableDataInfo list(DetectTemplate detectTemplate)
    {
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
    public void export(HttpServletResponse response, DetectTemplate detectTemplate)
    {
        List<DetectTemplate> list = detectTemplateService.selectDetectTemplateList(detectTemplate);
        ExcelUtil<DetectTemplate> util = new ExcelUtil<DetectTemplate>(DetectTemplate.class);
        util.exportExcel(response, list, "模板列表数据");
    }

    /**
     * 获取模板列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('template:Template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(detectTemplateService.selectDetectTemplateById(id));
    }

    /**
     * 新增模板列表
     */
    @PreAuthorize("@ss.hasPermi('template:Template:add')")
    @Log(title = "模板列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DetectTemplate detectTemplate)
    {
        return toAjax(detectTemplateService.insertDetectTemplate(detectTemplate));
    }

    /**
     * 修改模板列表
     */
    @PreAuthorize("@ss.hasPermi('template:Template:edit')")
    @Log(title = "模板列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DetectTemplate detectTemplate)
    {
        return toAjax(detectTemplateService.updateDetectTemplate(detectTemplate));
    }

    /**
     * 删除模板列表
     */
    @PreAuthorize("@ss.hasPermi('template:Template:remove')")
    @Log(title = "模板列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(detectTemplateService.deleteDetectTemplateByIds(ids));
    }
    
    @Log(title = "模板列表", businessType = BusinessType.OTHER)
	@GetMapping("/dict")
    public AjaxResult dict()
    {
        return success(detectTemplateService.selectDetectTemplateDict());
    }
}
