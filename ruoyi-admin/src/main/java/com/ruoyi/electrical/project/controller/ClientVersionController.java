package com.ruoyi.electrical.project.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.electrical.project.domain.ClientVersion;
import com.ruoyi.electrical.project.service.IClientVersionService;

/**
 * 版本更新Controller
 * 
 * @author fronttang
 * @date 2024-07-31
 */
@RestController
@RequestMapping("/project/version")
public class ClientVersionController extends BaseController
{
    @Autowired
    private IClientVersionService clientVersionService;

    /**
     * 查询版本更新列表
     */
    @PreAuthorize("@ss.hasPermi('project:version:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClientVersion clientVersion)
    {
        startPage();
        List<ClientVersion> list = clientVersionService.selectClientVersionList(clientVersion);
        return getDataTable(list);
    }

    /**
     * 导出版本更新列表
     */
    @PreAuthorize("@ss.hasPermi('project:version:export')")
    @Log(title = "版本更新", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClientVersion clientVersion)
    {
        List<ClientVersion> list = clientVersionService.selectClientVersionList(clientVersion);
        ExcelUtil<ClientVersion> util = new ExcelUtil<ClientVersion>(ClientVersion.class);
        util.exportExcel(response, list, "版本更新数据");
    }

    /**
     * 获取版本更新详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:version:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(clientVersionService.selectClientVersionById(id));
    }

    /**
     * 新增版本更新
     */
    @PreAuthorize("@ss.hasPermi('project:version:add')")
    @Log(title = "版本更新", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClientVersion clientVersion)
    {
        return toAjax(clientVersionService.insertClientVersion(clientVersion));
    }

    /**
     * 修改版本更新
     */
    @PreAuthorize("@ss.hasPermi('project:version:edit')")
    @Log(title = "版本更新", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClientVersion clientVersion)
    {
        return toAjax(clientVersionService.updateClientVersion(clientVersion));
    }

    /**
     * 删除版本更新
     */
    @PreAuthorize("@ss.hasPermi('project:version:remove')")
    @Log(title = "版本更新", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(clientVersionService.deleteClientVersionByIds(ids));
    }
    
	@GetMapping("/latest")
	public AjaxResult latest(@RequestParam String client) {

		// 取最新的版本数据
		return success(clientVersionService.getLatestClientVersion(client));

	}
}
