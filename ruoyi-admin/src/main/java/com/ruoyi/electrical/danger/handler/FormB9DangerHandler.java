package com.ruoyi.electrical.danger.handler;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.report.formb.FormB9;

import cn.hutool.core.util.StrUtil;

@FormbDangerHandler("B9")
public class FormB9DangerHandler implements IFormbDangerHandler {

	@Override
	public String getLevel(OwnerUnitDanger vo) {
		String level = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			level = "A";
		}
		return level;
	}

	@Override
	public String getDescription(OwnerUnitDanger vo) {
		String description = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			description = StrUtil.format("金属外壳对地电压{}V，存在危险电压，不符合要求", getVoltage(vo));
		}
		return description;
	}

	@Override
	public String getSuggestions(OwnerUnitDanger vo) {
		String suggestions = null;
		String result = getResult(vo);
		if (FAILURE.equalsIgnoreCase(result)) {
			// 不合格
			suggestions = "检查故障点，消除危险电压。";
		}
		return suggestions;
	}

	@Override
	public String getInfoLocation(OwnerUnitDanger vo) {

		String location = null;
		FormB9 formb = getFormb(vo);
		if (formb != null) {
			location = StrUtil.isNotBlank(formb.getLocation()) ? formb.getLocation() : "";
		}

		return location;
	}

	private FormB9 getFormb(OwnerUnitDanger vo) {
		if (vo == null) {
			return null;
		}
		JSONObject json = vo.getFormb();
		if (json != null) {

			JSONObject data = json.getJSONObject("data");
			if (data != null) {
				FormB9 formb = data.toJavaObject(FormB9.class);
				return formb;
			}
		}
		return null;
	}

	private String getResult(OwnerUnitDanger vo) {
		FormB9 formb = getFormb(vo);
		if (formb != null) {
			return formb.getResult();
		}
		return null;
	}

	private String getVoltage(OwnerUnitDanger vo) {
		FormB9 formb = getFormb(vo);
		if (formb != null) {
			return formb.getVoltage();
		}
		return null;
	}

}
