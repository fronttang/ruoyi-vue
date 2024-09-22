package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormBB1;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("BB1")
public class FormBB1DangerHandler implements IFormbDangerHandler {

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
			description = "红外检测正常，符合要求；";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			description = "红外检测存在热缺陷，不符合要求；";
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {

		String suggestions = null;
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			suggestions = "";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			suggestions = "检修变压器";
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormBB1 formb = getFormb(vo);
		if (formb != null) {
			location = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";
		}

		return location;
	}

	@Override
	public String getReportLocation(OwnerUnitDanger vo) {

		String location = null;
		FormBB1 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
			// String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ?
			// vo.getBuildingName() : "";
			String buildingName = "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, deviceName);
		}

		return location;
	}

	private FormBB1 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormBB1 formb = data.toJavaObject(FormBB1.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDanger vo) {
		FormBB1 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDanger vo) {
		FormBB1 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

	@Override
	public String getLocation(OwnerUnitDanger vo) {

		String location = null;
		FormBB1 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String deviceName = StrUtil.isNotBlank(formb.getDeviceName()) ? formb.getDeviceName() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, deviceName);
		}

		return location;
	}

}
