package com.ruoyi.electrical.report.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ruoyi.electrical.project.domain.Project;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

/**
 * 原始记录
 */
@Data
public class InitialReport {

	/**
	 * 编制日期
	 */
	private String createDate;

	/**
	 * 项目信息
	 */
	private Project project;

	/**
	 * 检测单位信息
	 */
	private DetectUnitInfo detect;

	/**
	 * 业主单元信息
	 */
	private OwnerUnitInfo unit;

	/**
	 * 报告信息
	 */
	private OwnerUnitReportInfo report;

	/**
	 * 设备列表
	 */
	List<DetectDeviceInfo> device;

	/**
	 * 检测表内容
	 */
	private List<DetectFormData> data;

	/**
	 * B表数据
	 */
	private Map<String, List<Object>> formb;

	/**
	 * 隐患数据 符合项 B表
	 */
	private List<UrbanVillageDanger> conformb = new ArrayList<UrbanVillageDanger>();

	/**
	 * 不符合项 B表
	 */
	private List<UrbanVillageDanger> nconformb = new ArrayList<UrbanVillageDanger>();

	/**
	 * 符合项
	 */
	private List<UrbanVillageDanger> conform = new ArrayList<UrbanVillageDanger>();

	/**
	 * 不符合项
	 */
	private List<UrbanVillageDanger> nconform = new ArrayList<UrbanVillageDanger>();

	/**
	 * 所有隐患
	 */
	@SuppressWarnings("unused")
	private List<UrbanVillageDanger> dangers = new ArrayList<UrbanVillageDanger>();

	public List<UrbanVillageDanger> getDangers() {
		List<UrbanVillageDanger> dangers = new ArrayList<UrbanVillageDanger>();

		if (CollUtil.isNotEmpty(this.nconformb)) {
			dangers.addAll(this.nconformb);
		}
		if (CollUtil.isNotEmpty(this.nconform)) {
			dangers.addAll(this.nconform);
		}
		return dangers;
	}

}
