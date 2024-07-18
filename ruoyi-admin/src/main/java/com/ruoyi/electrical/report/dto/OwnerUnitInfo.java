package com.ruoyi.electrical.report.dto;

import java.util.Map;

import com.deepoove.poi.data.TextRenderData;

import lombok.Data;

@Data
public class OwnerUnitInfo {

	/**
	 * 
	 */
	private Long id;

	/**
	 * 检测地址
	 */
	private String address;

	/**
	 * 联系人/负责人/业主
	 */
	private String contact;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 建筑面积
	 */
	private String acreage;

	/**
	 * 建筑层数
	 */
	private Long layers;

	/**
	 * 建筑使用性质
	 */
	private Map<String, TextRenderData> nature;

	/**
	 * 检测内容
	 */
	private Map<String, TextRenderData> detectContent;

	/**
	 * 温度
	 */
	private String temperature;

	/**
	 * 湿度
	 */
	private String humidity;
}
