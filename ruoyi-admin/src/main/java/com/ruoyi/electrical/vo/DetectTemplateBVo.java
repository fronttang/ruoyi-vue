package com.ruoyi.electrical.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;

public class DetectTemplateBVo {

	@NonNull
	private Long templateId;

	private List<String> views = new ArrayList<String>();

	private List<String> reports = new ArrayList<String>();

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public List<String> getViews() {
		return views;
	}

	public void setViews(List<String> views) {
		this.views = views;
	}

	public List<String> getReports() {
		return reports;
	}

	public void setReports(List<String> reports) {
		this.reports = reports;
	}

	@Override
	public String toString() {
		return "DetectTemplateBVo [templateId=" + templateId + ", views=" + views + ", reports=" + reports + "]";
	}

}
