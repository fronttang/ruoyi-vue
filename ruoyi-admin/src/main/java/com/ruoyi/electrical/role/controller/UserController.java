package com.ruoyi.electrical.role.controller;

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
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.role.domain.DetectUnitUser;
import com.ruoyi.electrical.role.service.IDetectUnitUserService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 用户账号Controller
 * 
 * @author fronttang
 * @date 2024-06-18
 */
@RestController
@RequestMapping("/electrical/user")
public class UserController extends BaseController {

	private static final String HIDE_PASSWORD = "********";

	@Autowired
	private IDetectUnitUserService detectUnitUserService;

	@Autowired
	private ISysUserService userService;

	@Autowired
	private IProjectService projectService;

	/**
	 * 查询账号列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(DetectUnitUser detectUnitUser) {
		startPage();
		List<DetectUnitUser> list = detectUnitUserService.selectDetectUnitUserList(detectUnitUser);
		return getDataTable(list);
	}

	/**
	 * 导出检测单位账号列表
	 */
	@Log(title = "账号", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, DetectUnitUser detectUnitUser) {
		List<DetectUnitUser> list = detectUnitUserService.selectDetectUnitUserList(detectUnitUser);
		ExcelUtil<DetectUnitUser> util = new ExcelUtil<DetectUnitUser>(DetectUnitUser.class);
		util.exportExcel(response, list, "检测单位账号数据");
	}

	/**
	 * 获取账号详细信息
	 */
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {

		DetectUnitUser user = detectUnitUserService.selectDetectUnitUserById(id);
		user.setPassword(HIDE_PASSWORD);

		return success(user);
	}

	/**
	 * 新增账号
	 */
	@Log(title = "账号", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody DetectUnitUser detectUnitUser) {
		detectUnitUser.setStatus("0");
		detectUnitUser.setPassword(SecurityUtils.encryptPassword(detectUnitUser.getPassword()));

		if (!userService.checkUserNameUnique(detectUnitUser.getId(), detectUnitUser.getAccount())) {
			return error("新增失败,登录账号[" + detectUnitUser.getAccount() + "]已存在");
		}

		if (detectUnitUser.getProjectId() != null) {
			Project project = projectService.selectProjectById(detectUnitUser.getProjectId());
			detectUnitUser.setDetectId(project.getDetectId());
		}

		return toAjax(detectUnitUserService.insertDetectUnitUser(detectUnitUser));
	}

	/**
	 * 修改账号
	 */
	@PreAuthorize("@ss.hasPermi('projectrole:detectUnitUser:edit')")
	@Log(title = "账号", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody DetectUnitUser detectUnitUser) {
		if (!userService.checkUserNameUnique(detectUnitUser.getId(), detectUnitUser.getAccount())) {
			return error("修改失败,登录账号[" + detectUnitUser.getAccount() + "]已存在");
		}
		if (HIDE_PASSWORD.equalsIgnoreCase(detectUnitUser.getPassword())) {
			detectUnitUser.setPassword(null);
		} else {
			detectUnitUser.setPassword(SecurityUtils.encryptPassword(detectUnitUser.getPassword()));
		}

		return toAjax(detectUnitUserService.updateDetectUnitUser(detectUnitUser));
	}

	/**
	 * 删除账号
	 */
	@PreAuthorize("@ss.hasPermi('projectrole:detectUnitUser:remove')")
	@Log(title = "账号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(detectUnitUserService.deleteDetectUnitUserByIds(ids));
	}

	@Log(title = "账号字典", businessType = BusinessType.OTHER)
	@GetMapping("/dict/{type}/{projectId}")
	public AjaxResult dict(@PathVariable String type, @PathVariable Long projectId) {
		return success(detectUnitUserService.sysUserDictByTypeAndProjectId(type, projectId));
	}

	@Log(title = "账号字典", businessType = BusinessType.OTHER)
	@GetMapping("/dict")
	public AjaxResult dict() {
		return success(detectUnitUserService.sysUserDict());
	}

}
