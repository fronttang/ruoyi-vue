package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB7;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("B7")
public class FormB7DangerHandler implements IFormbDangerHandler {

	// private static String TEST_RESULT_1 = "投入使用且动作";

	private static String TEST_RESULT_2 = "未安装";

	private static String TEST_RESULT_3 = "测试不动作";

	private static String TEST_RESULT_4 = "未投入使用";

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
		String testResults = getTestResults(vo);

		// 合格
		if (QUALIFIED.equalsIgnoreCase(result)) {
			description = "楼栋电源进线处已安装总漏电保护开关，且可靠动作，符合规范要求";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				description = "楼栋电源进线处未安装总漏电保护开关，不符合规范要求";
			} else if (TEST_RESULT_3.equalsIgnoreCase(testResults)) {
				description = "楼栋总漏电保护开关仪器测试不动作";
			} else if (TEST_RESULT_4.equalsIgnoreCase(testResults)) {
				description = "楼栋已安装总漏电保护开关，但未投入使用。";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {
		String suggestions = null;
		String result = getResult(vo);
		String testResults = getTestResults(vo);

		// 合格
		if (QUALIFIED.equalsIgnoreCase(result)) {
			suggestions = "";
		} else if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (TEST_RESULT_2.equalsIgnoreCase(testResults)) {
				suggestions = "楼栋应安装总漏电保护开关，各级漏电保护开关的动作电流值与动作时间应协调配合，实现具有动作选择性的分级保护。";
			} else if (TEST_RESULT_3.equalsIgnoreCase(testResults)) {
				suggestions = "更换楼栋总漏电保护开关，各级漏电保护开关的动作电流值与动作时间应协调配合，实现具有动作选择性的分级保护。";
			} else if (TEST_RESULT_4.equalsIgnoreCase(testResults)) {
				suggestions = "楼栋已安装的总漏电保护开关应投入使用";
			}
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB7 formb = getFormb(vo);
		if (formb != null) {
			location = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
		}

		return location;
	}

	private FormB7 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB7 formb = data.toJavaObject(FormB7.class);
				return formb;
			}
		}
		return null;
	}

	private String getResult(OwnerUnitDanger vo) {
		FormB7 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getTestResults(OwnerUnitDanger vo) {
		FormB7 formb = getFormb(vo);
		if (formb != null) {
			return formb.getTestResults();
		}
		return null;
	}

}
