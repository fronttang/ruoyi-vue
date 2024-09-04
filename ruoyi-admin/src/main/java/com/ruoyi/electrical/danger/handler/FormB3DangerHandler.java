package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB3;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("B3")
public class FormB3DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(OwnerUnitDanger vo) {

		String level = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			level = "";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			level = "A";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDanger vo) {

		String description = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			description = "变配电装置距离可燃物距离检测结果，符合要求；";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			description = "变配电装置距离可燃物距离小于0.6m，不符合要求；";
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
			suggestions = "清除周围堆放的可燃物";
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB3 formb = getFormb(vo);
		if (formb != null) {

			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}", location1, deviceName);
		}

		return location;
	}

	private FormB3 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB3 formb = data.toJavaObject(FormB3.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDanger vo) {
		FormB3 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDanger vo) {
		FormB3 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

	@Override
	public String getReportLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB3 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
			// String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ?
			// vo.getBuildingName() : "";
			String buildingName = "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}{}{}", buildingName, unitAreaName, location1, deviceName);
		}

		return location;
	}

}
