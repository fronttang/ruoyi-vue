package com.ruoyi.electrical.report.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.AjaxResult;

@RestController
@RequestMapping("/onlyoffice")
public class OnlyofficeController {

	@RequestMapping("/callback")
	public AjaxResult callback() {

		AjaxResult result = AjaxResult.success();
		result.put("error", 0);
		return result;
	}
}
