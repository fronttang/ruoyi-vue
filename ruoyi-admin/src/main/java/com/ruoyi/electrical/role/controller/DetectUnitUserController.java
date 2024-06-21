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
import com.ruoyi.electrical.role.domain.DetectUnitUser;
import com.ruoyi.electrical.role.service.IDetectUnitUserService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检测单位账号Controller
 * 
 * @author fronttang
 * @date 2024-06-18
 */
@RestController
@RequestMapping("/projectrole/detectUnitUser")
public class DetectUnitUserController extends BaseController
{
    @Autowired
    private IDetectUnitUserService detectUnitUserService;
    
    @Autowired
    private ISysUserService userService;

    /**
     * 查询检测单位账号列表
     */
    @PreAuthorize("@ss.hasPermi('projectrole:detectUnitUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(DetectUnitUser detectUnitUser)
    {
        startPage();
        List<DetectUnitUser> list = detectUnitUserService.selectDetectUnitUserList(detectUnitUser);
        return getDataTable(list);
    }

    /**
     * 导出检测单位账号列表
     */
    @PreAuthorize("@ss.hasPermi('projectrole:detectUnitUser:export')")
    @Log(title = "检测单位账号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DetectUnitUser detectUnitUser)
    {
        List<DetectUnitUser> list = detectUnitUserService.selectDetectUnitUserList(detectUnitUser);
        ExcelUtil<DetectUnitUser> util = new ExcelUtil<DetectUnitUser>(DetectUnitUser.class);
        util.exportExcel(response, list, "检测单位账号数据");
    }

    /**
     * 获取检测单位账号详细信息
     */
    @PreAuthorize("@ss.hasPermi('projectrole:detectUnitUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(detectUnitUserService.selectDetectUnitUserById(id));
    }

    /**
     * 新增检测单位账号
     */
    @PreAuthorize("@ss.hasPermi('projectrole:detectUnitUser:add')")
    @Log(title = "检测单位账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DetectUnitUser detectUnitUser)
    {
    	detectUnitUser.setStatus("0");
    	detectUnitUser.setPassword(SecurityUtils.encryptPassword(detectUnitUser.getPassword()));
    	
    	if (!userService.checkUserNameUnique(detectUnitUser.getId(), detectUnitUser.getAccount()))
        {
            return error("新增用户'" + detectUnitUser.getAccount() + "'失败，登录账号已存在");
        }
    	
        return toAjax(detectUnitUserService.insertDetectUnitUser(detectUnitUser));
    }

    /**
     * 修改检测单位账号
     */
    @PreAuthorize("@ss.hasPermi('projectrole:detectUnitUser:edit')")
    @Log(title = "检测单位账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DetectUnitUser detectUnitUser)
    {
    	if (!userService.checkUserNameUnique(detectUnitUser.getId(), detectUnitUser.getAccount()))
        {
            return error("修改用户'" + detectUnitUser.getAccount() + "'失败，登录账号已存在");
        }
    	DetectUnitUser selectDetectUnitUser = detectUnitUserService.selectDetectUnitUserById(detectUnitUser.getId());
    	if(selectDetectUnitUser != null) {
    		if(selectDetectUnitUser.getPassword().equals(detectUnitUser.getPassword())){
    			detectUnitUser.setPassword(null);
    		} else {
    			detectUnitUser.setPassword(SecurityUtils.encryptPassword(detectUnitUser.getPassword()));
    		}
    	}
        return toAjax(detectUnitUserService.updateDetectUnitUser(detectUnitUser));
    }

    /**
     * 删除检测单位账号
     */
    @PreAuthorize("@ss.hasPermi('projectrole:detectUnitUser:remove')")
    @Log(title = "检测单位账号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(detectUnitUserService.deleteDetectUnitUserByIds(ids));
    }
    
    @Log(title = "检测单位账号字典", businessType = BusinessType.OTHER)
	@GetMapping("/dict/{type}/{detectId}")
    public AjaxResult dict(@PathVariable String type, @PathVariable Long detectId)
    {
        return success(detectUnitUserService.sysUserDictByType(type, detectId));
    }
    
    @Log(title = "检测单位账号字典", businessType = BusinessType.OTHER)
	@GetMapping("/dict")
    public AjaxResult dict()
    {
        return success(detectUnitUserService.sysUserDict());
    }
    
    
}
