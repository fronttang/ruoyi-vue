package com.ruoyi.electrical.project.controller;

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
import com.ruoyi.electrical.project.domain.AreaDict;
import com.ruoyi.electrical.project.service.IAreaDictService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 区域字典Controller
 * 
 * @author fronttang
 * @date 2024-06-20
 */
@RestController
@RequestMapping("/project/AreaDict")
public class AreaDictController extends BaseController
{
    @Autowired
    private IAreaDictService areaDictService;

    /**
     * 查询区域字典列表
     */
    @PreAuthorize("@ss.hasPermi('project:AreaDict:list')")
    @GetMapping("/list")
    public TableDataInfo list(AreaDict areaDict)
    {
        startPage();
        List<AreaDict> list = areaDictService.selectAreaDictList(areaDict);
        return getDataTable(list);
    }

    /**
     * 导出区域字典列表
     */
    @PreAuthorize("@ss.hasPermi('project:AreaDict:export')")
    @Log(title = "区域字典", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AreaDict areaDict)
    {
        List<AreaDict> list = areaDictService.selectAreaDictList(areaDict);
        ExcelUtil<AreaDict> util = new ExcelUtil<AreaDict>(AreaDict.class);
        util.exportExcel(response, list, "区域字典数据");
    }

    /**
     * 获取区域字典详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:AreaDict:query')")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable("dictCode") Long dictCode)
    {
        return success(areaDictService.selectAreaDictByDictCode(dictCode));
    }

    /**
     * 新增区域字典
     */
    @PreAuthorize("@ss.hasPermi('project:AreaDict:add')")
    @Log(title = "区域字典", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AreaDict areaDict)
    {
    	areaDict.setDictValue(System.currentTimeMillis()+"");
        return toAjax(areaDictService.insertAreaDict(areaDict));
    }

    /**
     * 修改区域字典
     */
    @PreAuthorize("@ss.hasPermi('project:AreaDict:edit')")
    @Log(title = "区域字典", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AreaDict areaDict)
    {
        return toAjax(areaDictService.updateAreaDict(areaDict));
    }

    /**
     * 删除区域字典
     */
    @PreAuthorize("@ss.hasPermi('project:AreaDict:remove')")
    @Log(title = "区域字典", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes)
    {
        return toAjax(areaDictService.deleteAreaDictByDictCodes(dictCodes));
    }
}
