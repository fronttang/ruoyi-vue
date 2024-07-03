package com.ruoyi.electrical.dto;

import java.util.Arrays;

import javax.validation.constraints.NotNull;

public class ProjectWorkerAreaDto {

	@NotNull(message = "类型不能为空")
	private String type = "1";

	private Long[] workerIds;

	private Long[] areaIds;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long[] getWorkerIds() {
		return workerIds;
	}

	public void setWorkerIds(Long[] workerIds) {
		this.workerIds = workerIds;
	}

	public Long[] getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(Long[] areaIds) {
		this.areaIds = areaIds;
	}

	@Override
	public String toString() {
		return "ProjectWorkerAreaDto [type=" + type + ", workerIds=" + Arrays.toString(workerIds) + ", areaIds="
				+ Arrays.toString(areaIds) + "]";
	}

}
