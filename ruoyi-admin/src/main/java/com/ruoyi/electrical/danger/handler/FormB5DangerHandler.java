package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB5;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("B5")
public class FormB5DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(OwnerUnitDanger vo) {

		String level = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			level = "";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			level = "B";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDanger vo) {

		String description = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			description = "安装高度检测结果，符合要求；";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			description = "等潮湿场所插座安装高度低于1.5m，不符合要求。";
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {

		String suggestions = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			suggestions = "/";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			suggestions = "等潮湿场所插座应采用密封型并带保护接地线触头的保护型插座，安装高度不低于1.5m";
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB5 formb = getFormb(vo);
		if (formb != null) {

			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}", location1, deviceName);
		}

		return location;
	}

	private FormB5 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB5 formb = data.toJavaObject(FormB5.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDanger vo) {
		FormB5 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDanger vo) {
		FormB5 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

	@Override
	public String getReportLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB5 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}{}{}", buildingName, unitAreaName, location1, deviceName);
		}

		return location;
	}
}
