package com.ruoyi.electrical.vo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DetectTemplateBVo {

	@NotNull(message = "模板ID不能为空")
	private Long templateId;

	private List<String> views1 = new ArrayList<String>();

	private List<String> views2 = new ArrayList<String>();

	private List<String> views3 = new ArrayList<String>();

	private List<String> reports = new ArrayList<String>();

}
