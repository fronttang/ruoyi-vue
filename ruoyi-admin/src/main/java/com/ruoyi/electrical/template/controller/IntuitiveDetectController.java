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
import com.ruoyi.electrical.template.domain.IntuitiveDetect;
import com.ruoyi.electrical.template.service.IIntuitiveDetectService;
import com.ruoyi.electrical.vo.DictVO;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 直观检测标题Controller
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@RestController
@RequestMapping("/template/IntuitiveDetect")
public class IntuitiveDetectController extends BaseController
{
    @Autowired
    private IIntuitiveDetectService intuitiveDetectService;

    /**
     * 查询直观检测标题列表
     */
    @PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:list')")
    @GetMapping("/list")
    public TableDataInfo list(IntuitiveDetect intuitiveDetect)
    {
        startPage();
        List<IntuitiveDetect> list = intuitiveDetectService.selectIntuitiveDetectList(intuitiveDetect);
        return getDataTable(list);
    }

    /**
     * 导出直观检测标题列表
     */
    @PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:export')")
    @Log(title = "直观检测标题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IntuitiveDetect intuitiveDetect)
    {
        List<IntuitiveDetect> list = intuitiveDetectService.selectIntuitiveDetectList(intuitiveDetect);
        ExcelUtil<IntuitiveDetect> util = new ExcelUtil<IntuitiveDetect>(IntuitiveDetect.class);
        util.exportExcel(response, list, "直观检测标题数据");
    }

    /**
     * 获取直观检测标题详细信息
     */
    @PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(intuitiveDetectService.selectIntuitiveDetectById(id));
    }

    /**
     * 新增直观检测标题
     */
    @PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:add')")
    @Log(title = "直观检测标题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IntuitiveDetect intuitiveDetect)
    {
        return toAjax(intuitiveDetectService.insertIntuitiveDetect(intuitiveDetect));
    }

    /**
     * 修改直观检测标题
     */
    @PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:edit')")
    @Log(title = "直观检测标题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IntuitiveDetect intuitiveDetect)
    {
        return toAjax(intuitiveDetectService.updateIntuitiveDetect(intuitiveDetect));
    }

    /**
     * 删除直观检测标题
     */
    @PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:remove')")
    @Log(title = "直观检测标题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(intuitiveDetectService.deleteIntuitiveDetectByIds(ids));
    }
    
    @Log(title = "直观检测标题字典", businessType = BusinessType.OTHER)
	@GetMapping("/dict/{templateId}")
    public AjaxResult dict(@PathVariable Long templateId)
    {
        return success(intuitiveDetectService.selectIntuitiveDetectDict(templateId));
    }
    
    @GetMapping("/dict")
    public AjaxResult dict(IntuitiveDetect intuitiveDetect)
    {
        List<DictVO> list = intuitiveDetectService.selectIntuitiveDetectListDict(intuitiveDetect);
        return success(list);
    }

}
