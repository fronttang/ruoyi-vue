package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB15;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("B15")
public class FormB15DangerHandler implements IFormbDangerHandler {

	private static final String DEVICE_NAME_1 = "插座和照明开关";

	private static final String DEVICE_NAME_1A = "插座";

	private static final String DEVICE_NAME_1B = "照明开关";

	private static final String DEVICE_NAME_2 = "空调插座和插头";

	@Override
	public String getLevel(OwnerUnitDanger vo) {

		String level = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			level = "A";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDanger vo) {

		String description = null;
		String result = getResult(vo);
		String deviceName = getDeviceName(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			if (DEVICE_NAME_1.equalsIgnoreCase(deviceName) || DEVICE_NAME_1A.equalsIgnoreCase(deviceName)) {
				description = "固定插座处温升超过45K，存在温度缺陷";
			} else if (DEVICE_NAME_1B.equalsIgnoreCase(deviceName)) {
				description = "照明开关处温升超过45K，存在温度缺陷";
			} else if (DEVICE_NAME_2.equalsIgnoreCase(deviceName)) {
				description = "空调电源线插头和插座接触处温升超过60K，存在温度缺陷";
			}
		}

		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {

		String suggestions = null;
		String result = getResult(vo);
		String deviceName = getDeviceName(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			if (DEVICE_NAME_1.equalsIgnoreCase(deviceName) || DEVICE_NAME_1A.equalsIgnoreCase(deviceName)) {
				suggestions = "检查固定插座接线是否接触不良或更换固定插座";
			} else if (DEVICE_NAME_1B.equalsIgnoreCase(deviceName)) {
				suggestions = "检查照明接线是否接触不良或更换固定插座";
			} else if (DEVICE_NAME_2.equalsIgnoreCase(deviceName)) {
				suggestions = "空调更换电源线插头及插座，插头与插座的接触良好";
			}
		}

		return suggestions;
	}
	
	@Override
	public Boolean isImportant(OwnerUnitDanger vo) {
		Boolean important = false;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			important = true;
		}
		return important;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB15 formb = getFormb(vo);
		if (formb != null) {
			location = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
		}

		return location;
	}

	@Override
	public String getReportLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB15 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
			// String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ?
			// vo.getBuildingName() : "";
			String buildingName = "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

	private FormB15 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB15 formb = data.toJavaObject(FormB15.class);
				return formb;
			}
		}
		return null;
	}

	private String getDeviceName(OwnerUnitDanger vo) {
		FormB15 formb = getFormb(vo);
		if (formb != null) {
			return formb.getDeviceName();
		}
		return null;
	}

	@Override
	public boolean isSummary(OwnerUnitDanger vo) {
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			return true;
		}
		return false;
	}

	@Override
	public String getResult(OwnerUnitDanger vo) {
		FormB15 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDanger vo) {
		FormB15 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

	@Override
	public String getLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB15 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

}
