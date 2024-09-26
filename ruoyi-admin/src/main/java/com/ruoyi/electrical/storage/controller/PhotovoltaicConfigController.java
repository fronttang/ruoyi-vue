package com.ruoyi.electrical.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.electrical.storage.domain.PhotovoltaicConfig;
import com.ruoyi.electrical.storage.service.IPhotovoltaicConfigService;
import com.ruoyi.system.service.ISysDictTypeService;

/**
 * 光伏参数Controller
 * 
 * @author fronttang
 * @date 2024-09-24
 */
@RestController
@RequestMapping("/storage/photovoltaic/config")
public class PhotovoltaicConfigController extends BaseController {
	@Autowired
	private IPhotovoltaicConfigService photovoltaicConfigService;

	@Autowired
	private ISysDictTypeService dictTypeService;

	/**
	 * 获取光伏参数详细信息
	 */
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		PhotovoltaicConfig photovoltaicConfig = photovoltaicConfigService.selectPhotovoltaicConfigById(id);
		if (photovoltaicConfig == null) {
			photovoltaicConfig = new PhotovoltaicConfig();
		}
		return success(photovoltaicConfig);
	}

	/**
	 * 修改光伏参数
	 */
	@Log(title = "光伏参数", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody PhotovoltaicConfig photovoltaicConfig) {
		photovoltaicConfig.setId(1L);

		PhotovoltaicConfig config = photovoltaicConfigService.selectPhotovoltaicConfigById(photovoltaicConfig.getId());
		if (config != null) {
			return toAjax(photovoltaicConfigService.updatePhotovoltaicConfig(photovoltaicConfig));
		}
		return toAjax(photovoltaicConfigService.insertPhotovoltaicConfig(photovoltaicConfig));
	}

	@GetMapping("/dict")
	public AjaxResult dict() {
		return success(dictTypeService.selectDictDataByType("photovoltaic_area"));
	}

}
