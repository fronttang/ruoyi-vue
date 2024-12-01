package com.ruoyi.electrical.design.type;

import java.util.List;

import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.electrical.design.domain.ElectricityProjectDeviceImage.DeviceImages;
import com.ruoyi.electrical.type.ListTypeHandler;

public class DeviceImagesTypeHandler extends ListTypeHandler<DeviceImages> {

	@Override
	protected TypeReference<List<DeviceImages>> specificType() {
		return new TypeReference<List<DeviceImages>>() {
		};
	}

}
