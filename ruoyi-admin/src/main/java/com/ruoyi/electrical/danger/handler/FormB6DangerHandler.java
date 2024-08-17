package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB6;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("B6")
public class FormB6DangerHandler implements IFormbDangerHandler {

	private static String ACTION_FILL = "填写";

	private static String ACTION_1000 = ">1000";

	private static String ACTION_NULL = "不动作";

	private static String MANUALTEST_ACTION = "手动动作";

	private static String MANUALTEST_NO_ACTION = "手动不动作";

	private static String OTHER_UN = "未安装";

	private static String OTHER_1 = "住户不允许断电无法进行测试";

	@Override
	public String getLevel(OwnerUnitDanger vo) {
		String level = null;
		String action = getAction(vo);
		String result = getResult(vo);
		String manualTest = getManualTest(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (ACTION_FILL.equalsIgnoreCase(action) || ACTION_1000.equalsIgnoreCase(action)
					|| ACTION_NULL.equalsIgnoreCase(action)) {
				// 填写/>1000/不动作
				level = "A";
			} else if (OTHER_UN.equalsIgnoreCase(other)) {
				// 未安装
				level = "B";
			} else if (MANUALTEST_NO_ACTION.equalsIgnoreCase(manualTest)) {
				// 手动不动作
				level = "A";
			}
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDanger vo) {
		String description = null;
		String action = getAction(vo);
		String result = getResult(vo);
		String manualTest = getManualTest(vo);
		String other = getOther(vo);

		// 合格
		if (QUALIFIED.equalsIgnoreCase(result)) {

			if (ACTION_FILL.equalsIgnoreCase(action)) {
				// 填写
				description = "剩余电流动作保护装置检测结果，符合要求。";
			}
		} else if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (ACTION_FILL.equalsIgnoreCase(action) || ACTION_1000.equalsIgnoreCase(action)
					|| ACTION_NULL.equalsIgnoreCase(action)) {
				// 填写/>1000/不动作
				description = "漏电保护开关动作时间大于100ms，不符合要求；";
			} else if (OTHER_UN.equalsIgnoreCase(other)) {
				// 未安装
				description = "未安装额定动作电流30mA漏电保护开关";
			} else if (MANUALTEST_NO_ACTION.equalsIgnoreCase(manualTest)) {
				// 手动不动作
				description = "漏电保护开关手动测试不动作";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {
		String suggestions = null;
		String action = getAction(vo);
		String result = getResult(vo);
		String manualTest = getManualTest(vo);
		String other = getOther(vo);

		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			if (ACTION_FILL.equalsIgnoreCase(action) || ACTION_1000.equalsIgnoreCase(action)
					|| ACTION_NULL.equalsIgnoreCase(action)) {
				// 填写/>1000/不动作
				suggestions = "更换新的漏电保护开关且动作时间不大于100ms";
			} else if (OTHER_UN.equalsIgnoreCase(other)) {
				// 未安装
				suggestions = "室内应安装额定动作电流为30mA漏电保护开关";
			} else if (MANUALTEST_NO_ACTION.equalsIgnoreCase(manualTest)) {
				// 手动不动作
				suggestions = "更换与线路用电负荷相匹配且动作可靠的漏电保护开关";
			}
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		// String location = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() :
		// "";
		return "漏电保护开关";
	}

	@Override
	public boolean isSummary(OwnerUnitDanger vo) {
		String result = getResult(vo);
		String action = getAction(vo);
		String other = getOther(vo);
		if (QUALIFIED.equalsIgnoreCase(result)) {
			if (MANUALTEST_ACTION.equalsIgnoreCase(action)) {
				return false;
			}
		} else if (FAILURE.equalsIgnoreCase(result)) {
			if (OTHER_1.equalsIgnoreCase(other)) {
				return false;
			}
		}
		return true;
	}

	private FormB6 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB6 formb = data.toJavaObject(FormB6.class);
				return formb;
			}
		}
		return null;
	}

	@Override
	public String getResult(OwnerUnitDanger vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getAction(OwnerUnitDanger vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getAction();
		}
		return null;
	}

	private String getManualTest(OwnerUnitDanger vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getManualTest();
		}
		return null;
	}

	private String getOther(OwnerUnitDanger vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOther();
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDanger vo) {
		FormB6 formb = getFormb(vo);
		if (formb != null) {
			return formb.getOverallPic();
		}
		return null;
	}

	@Override
	public String getReportLocation(OwnerUnitDanger vo) {

		String unitAreaName = StrUtil.isNotBlank(vo.getAreaName()) ? vo.getAreaName() : "";
		String buildingName = StrUtil.isNotBlank(vo.getBuildingName()) ? vo.getBuildingName() : "";

		return StrUtil.format("{}{}", buildingName, unitAreaName);

	}
}
