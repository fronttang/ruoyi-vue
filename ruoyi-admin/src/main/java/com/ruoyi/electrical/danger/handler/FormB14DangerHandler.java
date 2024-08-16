package com.ruoyi.electrical.danger.handler;

import java.util.Objects;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB14;

@FormbDangerHandler("B14")
public class FormB14DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(OwnerUnitDanger vo) {

		String level = null;
		String type = getType(vo);
		if (FormB14.TYPE_RESIDUALCURRENT.equals(type)) {
			String residualCurrentResult = getResidualCurrentResult(vo);
			if (FAILURE.equalsIgnoreCase(residualCurrentResult)) {
				level = "A";
			}
		} else if (FormB14.TYPE_ALARMTIME.equals(type)) {
			String alarmTimeResult = getAlarmTimeResult(vo);
			if (FAILURE.equalsIgnoreCase(alarmTimeResult)) {
				level = "A";
			}
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDanger vo) {

		String description = null;
		String type = getType(vo);
		if (FormB14.TYPE_RESIDUALCURRENT.equals(type)) {
			String residualCurrentResult = getResidualCurrentResult(vo);
			if (FAILURE.equalsIgnoreCase(residualCurrentResult)) {
				description = "电气火灾监控探测器剩余电流报警功能测试时，探测器未在30s内发出报警信号。";
			}
		} else if (FormB14.TYPE_ALARMTIME.equals(type)) {
			String alarmTimeResult = getAlarmTimeResult(vo);
			if (FAILURE.equalsIgnoreCase(alarmTimeResult)) {
				description = "电气火灾监控探测器温度报警功能测试时，探测器未在30s内发出报警信号。";
			}
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {

		String suggestions = null;

		String type = getType(vo);
		if (FormB14.TYPE_RESIDUALCURRENT.equals(type)) {
			String residualCurrentResult = getResidualCurrentResult(vo);
			if (FAILURE.equalsIgnoreCase(residualCurrentResult)) {
				suggestions = "电气火灾监控探测器剩余电流报警功能测试时，探测器未在30s内发出报警信号。";
			}
		} else if (FormB14.TYPE_ALARMTIME.equals(type)) {
			String alarmTimeResult = getAlarmTimeResult(vo);
			if (FAILURE.equalsIgnoreCase(alarmTimeResult)) {
				suggestions = "电气火灾监控探测器温度报警功能测试时，探测器未在30s内发出报警信号。";
			}
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB14 formb = getFormb(vo);
		if (formb != null) {
			// TODO
		}
		return location;
	}

	private FormB14 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB14 formb = data.toJavaObject(FormB14.class);
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

	public String getResult(OwnerUnitDanger vo) {
		FormB14 formb = getFormb(vo);
		if (formb != null) {
			if ("residualCurrent".equals(formb.getType()) && Objects.nonNull(formb.getResidualCurrent())) {
				return formb.getResidualCurrent().getResult();
			} else if ("alarmTime".equals(formb.getType()) && Objects.nonNull(formb.getAlarmTime())) {
				return formb.getAlarmTime().getResult();
			}
		}
		return null;
	}

	private String getType(OwnerUnitDanger vo) {
		FormB14 formb = getFormb(vo);
		if (formb != null) {
			return formb.getType();
		}
		return null;
	}

	private String getResidualCurrentResult(OwnerUnitDanger vo) {
		FormB14 formb = getFormb(vo);
		if (formb != null) {
			if (formb.getResidualCurrent() != null) {
				return formb.getResidualCurrent().getResult();
			}
		}
		return null;
	}

	private String getAlarmTimeResult(OwnerUnitDanger vo) {
		FormB14 formb = getFormb(vo);
		if (formb != null) {
			if (formb.getAlarmTime() != null) {
				return formb.getAlarmTime().getResult();
			}
		}
		return null;
	}

	@Override
	public String getPicture(OwnerUnitDanger vo) {
		return null;
	}
}
