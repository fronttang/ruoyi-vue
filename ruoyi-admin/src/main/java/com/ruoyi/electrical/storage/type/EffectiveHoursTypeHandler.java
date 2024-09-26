package com.ruoyi.electrical.storage.type;

import java.util.List;

import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.electrical.storage.domain.PhotovoltaicConfig.EffectiveHours;
import com.ruoyi.electrical.type.ListTypeHandler;

public class EffectiveHoursTypeHandler extends ListTypeHandler<EffectiveHours> {

	@Override
	protected TypeReference<List<EffectiveHours>> specificType() {
		return new TypeReference<List<EffectiveHours>>() {
		};
	}

}
