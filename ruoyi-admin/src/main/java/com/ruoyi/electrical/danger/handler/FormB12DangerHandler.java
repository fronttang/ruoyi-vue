package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB12;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("B12")
public class FormB12DangerHandler implements IFormbDangerHandler {

	private static String OTHER_1 = "未安装";

	private static String OTHER_2 = "测试不动作";

	@Override
	public String getLevel(OwnerUnitDanger vo) {
		String level = null;
		String result = getResult(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (OTHER_1.equalsIgnoreCase(other)) {
				level = "B";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				level = "A";
			}
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDanger vo) {
		String description = null;
		String result = getResult(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (OTHER_1.equalsIgnoreCase(other)) {
				description = "热水器电源回路未安装额定剩余动作电流为10mA的漏电保护开关";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				description = "漏电保护开关测试不动作";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {
		String suggestions = null;
		String result = getResult(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (OTHER_1.equalsIgnoreCase(other)) {
				suggestions = "浴室热水器电源回路加装额定剩余动作电流为10mA的漏电保护开关";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				suggestions = "更换规格相同的漏电保护开关";
			}
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB12 formb = getFormb(vo);
		if (formb != null) {
			location = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
		}

		return location;
	}

	@Override
	public String getReportLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB12 formb = getFormb(vo);
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

	private FormB12 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB12 formb = data.toJavaObject(FormB12.class);
				return formb;
			}
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
		FormB12 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getOther(OwnerUnitDanger vo) {
		FormB12 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOther();
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDanger vo) {
		FormB12 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

	@Override
	public String getLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB12 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}

}
