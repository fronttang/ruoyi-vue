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
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检测内容隐患Controller
 * 
 * @author fronttang
 * @date 2024-06-23
 */
@RestController
@RequestMapping("/template/IntuitiveDetectDanger")
public class IntuitiveDetectDangerController extends BaseController
{
    @Autowired
    private IIntuitiveDetectDangerService intuitiveDetectDangerService;

    /**
     * 查询检测内容隐患列表
     */
    //@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectDanger:list')")
    @GetMapping("/list")
    public TableDataInfo list(IntuitiveDetectDanger intuitiveDetectDanger)
    {
        startPage();
        List<IntuitiveDetectDanger> list = intuitiveDetectDangerService.selectIntuitiveDetectDangerList(intuitiveDetectDanger);
        return getDataTable(list);
    }

    /**
     * 导出检测内容隐患列表
     */
    //@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectDanger:export')")
    @Log(title = "检测内容隐患", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IntuitiveDetectDanger intuitiveDetectDanger)
    {
        List<IntuitiveDetectDanger> list = intuitiveDetectDangerService.selectIntuitiveDetectDangerList(intuitiveDetectDanger);
        ExcelUtil<IntuitiveDetectDanger> util = new ExcelUtil<IntuitiveDetectDanger>(IntuitiveDetectDanger.class);
        util.exportExcel(response, list, "检测内容隐患数据");
    }

    /**
     * 获取检测内容隐患详细信息
     */
    //@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectDanger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(intuitiveDetectDangerService.selectIntuitiveDetectDangerById(id));
    }

    /**
     * 新增检测内容隐患
     */
    //@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectDanger:add')")
    @Log(title = "检测内容隐患", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IntuitiveDetectDanger intuitiveDetectDanger)
    {
        return toAjax(intuitiveDetectDangerService.insertIntuitiveDetectDanger(intuitiveDetectDanger));
    }

    /**
     * 修改检测内容隐患
     */
    //@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectDanger:edit')")
    @Log(title = "检测内容隐患", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IntuitiveDetectDanger intuitiveDetectDanger)
    {
        return toAjax(intuitiveDetectDangerService.updateIntuitiveDetectDanger(intuitiveDetectDanger));
    }

    /**
     * 删除检测内容隐患
     */
    //@PreAuthorize("@ss.hasPermi('template:IntuitiveDetectDanger:remove')")
    @Log(title = "检测内容隐患", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(intuitiveDetectDangerService.deleteIntuitiveDetectDangerByIds(ids));
    }
}
