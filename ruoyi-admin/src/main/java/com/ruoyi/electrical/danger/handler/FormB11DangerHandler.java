package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB11;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("B11")
public class FormB11DangerHandler implements IFormbDangerHandler {

	// private static String TEST_RESULT_1 = "有接地线";

	private static String TEST_RESULT_2 = "无接地线";

	private static String OTHER_1 = "固定插座火零错位";

	private static String OTHER_2 = "固定插座回路缺零线";

	private static String OTHER_3 = "固定插座相地线错并缺地";

	private static String OTHER_4 = "固定插座无可靠保护接地线";

	@Override
	public String getLevel(OwnerUnitDanger vo) {

		String level = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			level = "B";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDanger vo) {
		String description = null;
		String result = getResult(vo);
		String other = getOther(vo);
		String testResults = getTestResults(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				description = "插座无接地线，不符合规范要求";
			} else if (OTHER_1.equalsIgnoreCase(other)) {
				description = "固定插座火零错位";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				description = "固定插座回路缺零线";
			} else if (OTHER_3.equalsIgnoreCase(other)) {
				description = "固定插座相地线错并缺地";
			} else if (OTHER_4.equalsIgnoreCase(other)) {
				description = "固定插座无可靠保护接地线";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {
		String suggestions = null;
		String result = getResult(vo);
		String other = getOther(vo);
		String testResults = getTestResults(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				suggestions = "插座回路新增保护接地线";
			} else if (OTHER_1.equalsIgnoreCase(other)) {
				suggestions = "插座按正确接法重新接线，面向插座面板左孔接零线，右孔接火线";
			} else if (OTHER_2.equalsIgnoreCase(other)) {
				suggestions = "检修线路，插座回路新增零线";
			} else if (OTHER_3.equalsIgnoreCase(other)) {
				suggestions = "插座回路新增保护接地线，插座按正确接法重新接线，面对插座左孔接零线，右孔接火线，上孔接地线";
			} else if (OTHER_4.equalsIgnoreCase(other)) {
				suggestions = "插座新增可靠保护接地线";
			}
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			location = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
		}

		return location;
	}

	private FormB11 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB11 formb = data.toJavaObject(FormB11.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public boolean isSummary(OwnerUnitDanger vo) {
		String result = getResult(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			return false;
		}
		return true;
	}

	@Override
	public String getResult(OwnerUnitDanger vo) {
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getTestResults(OwnerUnitDanger vo) {
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			return formb.getTestResults();
		}
		return null;
	}

	private String getOther(OwnerUnitDanger vo) {
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOther();
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDanger vo) {
		FormB11 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

	@Override
	public String getReportLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB11 formb = getFormb(vo);
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

	@Override
	public String getLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB11 formb = getFormb(vo);
		if (formb != null) {

			String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
			String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";
			String location1 = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";

			location = StrUtil.format("{}{}{}", buildingName, unitAreaName, location1);
		}

		return location;
	}
}
