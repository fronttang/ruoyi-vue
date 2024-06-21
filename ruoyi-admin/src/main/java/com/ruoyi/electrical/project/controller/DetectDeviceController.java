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
import com.ruoyi.electrical.project.domain.DetectDevice;
import com.ruoyi.electrical.project.service.IDetectDeviceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检测仪器Controller
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@RestController
@RequestMapping("/project/DetectDevice")
public class DetectDeviceController extends BaseController
{
    @Autowired
    private IDetectDeviceService detectDeviceService;

    /**
     * 查询检测仪器列表
     */
    @PreAuthorize("@ss.hasPermi('project:DetectDevice:list')")
    @GetMapping("/list")
    public TableDataInfo list(DetectDevice detectDevice)
    {
        startPage();
        List<DetectDevice> list = detectDeviceService.selectDetectDeviceList(detectDevice);
        return getDataTable(list);
    }

    /**
     * 导出检测仪器列表
     */
    @PreAuthorize("@ss.hasPermi('project:DetectDevice:export')")
    @Log(title = "检测仪器", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DetectDevice detectDevice)
    {
        List<DetectDevice> list = detectDeviceService.selectDetectDeviceList(detectDevice);
        ExcelUtil<DetectDevice> util = new ExcelUtil<DetectDevice>(DetectDevice.class);
        util.exportExcel(response, list, "检测仪器数据");
    }

    /**
     * 获取检测仪器详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:DetectDevice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(detectDeviceService.selectDetectDeviceById(id));
    }

    /**
     * 新增检测仪器
     */
    @PreAuthorize("@ss.hasPermi('project:DetectDevice:add')")
    @Log(title = "检测仪器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DetectDevice detectDevice)
    {
        return toAjax(detectDeviceService.insertDetectDevice(detectDevice));
    }

    /**
     * 修改检测仪器
     */
    @PreAuthorize("@ss.hasPermi('project:DetectDevice:edit')")
    @Log(title = "检测仪器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DetectDevice detectDevice)
    {
        return toAjax(detectDeviceService.updateDetectDevice(detectDevice));
    }

    /**
     * 删除检测仪器
     */
    @PreAuthorize("@ss.hasPermi('project:DetectDevice:remove')")
    @Log(title = "检测仪器", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(detectDeviceService.deleteDetectDeviceByIds(ids));
    }
}
