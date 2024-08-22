package com.ruoyi.electrical.report.dto;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

@Data
public class UrbanVillageDanger {

	/**
	 * 检测模块
	 */
	private String formId;

	/**
	 * 隐患ID
	 */
	private Long dangerId;

	/**
	 * 检测表编号
	 */
	private String formCode;

	/**
	 * 检测表类型A/B/C
	 */
	private String formType;

	/**
	 * 检测表数据ID
	 */
	private Long formDataId;

	/**
	 * B表数据
	 */
	private JSONObject formb;

	/**
	 * 隐患等级
	 */
	private String level;

	/**
	 * 隐患描述
	 */
	private String description;

	/**
	 * 整改建议
	 */
	private String suggestions;

	/**
	 * 位置
	 */
	private String location;

	/**
	 * 整改图
	 */
	private List<String> rectificationPics = new ArrayList<String>();

	/**
	 * 隐患图片
	 */
	private List<String> pictures = new ArrayList<String>();

	/**
	 * 位置合集
	 */
	private List<String> locations = new ArrayList<String>();

	public String getLocation() {

		if (CollUtil.isNotEmpty(locations)) {
			this.location = String.join("、", locations);
		}
		return this.location;
	}

	@SuppressWarnings("unused")
	private FilePictureRenderData picture1;

	public FilePictureRenderData getPicture1() {
		if (CollUtil.isEmpty(this.pictures)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.pictures.get(0), Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

	@SuppressWarnings("unused")
	private FilePictureRenderData picture2;

	public FilePictureRenderData getPicture2() {
		if (CollUtil.isEmpty(this.pictures) || this.pictures.size() <= 1) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.pictures.get(1), Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

	@SuppressWarnings("unused")
	private FilePictureRenderData rectificationPicture;

	public FilePictureRenderData getRectificationPicture() {
		if (CollUtil.isEmpty(this.rectificationPics)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath
				+ StringUtils.substringAfter(this.rectificationPics.get(0), Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}
}
