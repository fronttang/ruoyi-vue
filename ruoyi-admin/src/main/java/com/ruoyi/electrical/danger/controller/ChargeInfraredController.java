package com.ruoyi.electrical.danger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.electrical.danger.domain.ChargeInfrared;
import com.ruoyi.electrical.danger.service.IChargeInfraredService;

/**
 * 红外判定Controller
 * 
 * @author fronttang
 * @date 2024-08-11
 */
@RestController
@RequestMapping("/danger/infrared")
public class ChargeInfraredController extends BaseController {
	@Autowired
	private IChargeInfraredService chargeInfraredService;

	/**
	 * 查询红外判定列表
	 */
	@PreAuthorize("@ss.hasPermi('danger:infrared:list')")
	@GetMapping("/list")
	public TableDataInfo list(ChargeInfrared chargeInfrared) {
		startPage();
		List<ChargeInfrared> list = chargeInfraredService.selectChargeInfraredList(chargeInfrared);
		return getDataTable(list);
	}

	/**
	 * 修改红外判定
	 */
	// @PreAuthorize("@ss.hasPermi('project:infrared:edit')")
	@Log(title = "红外判定", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody ChargeInfrared chargeInfrared) {
		return toAjax(chargeInfraredService.updateChargeInfrared(chargeInfrared));
	}

}
