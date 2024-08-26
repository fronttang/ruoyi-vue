package com.ruoyi.electrical.report.dto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.alibaba.fastjson2.JSONObject;
import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
		locations = locations.stream().filter((d) -> StrUtil.isNotBlank(d)).collect(Collectors.toList());
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
		return buildFITFilePictureRenderData(this.pictures.get(0));
	}

	@SuppressWarnings("unused")
	private FilePictureRenderData picture2;

	public FilePictureRenderData getPicture2() {
		if (CollUtil.isEmpty(this.pictures) || this.pictures.size() <= 1) {
			return null;
		}
		return buildFITFilePictureRenderData(this.pictures.get(1));
	}

	@SuppressWarnings("unused")
	private FilePictureRenderData picture3;

	public FilePictureRenderData getPicture3() {
		if (CollUtil.isEmpty(this.pictures)) {
			return null;
		}
		return buildFilePictureRenderData(this.pictures.get(0));
	}

	@SuppressWarnings("unused")
	private FilePictureRenderData rectificationPicture;

	public FilePictureRenderData getRectificationPicture() {
		if (CollUtil.isEmpty(this.rectificationPics)) {
			return null;
		}
		return buildFITFilePictureRenderData(this.rectificationPics.get(0));
	}

	private FilePictureRenderData buildFITFilePictureRenderData(String filePath) {

		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		filePath = localPath + StringUtils.substringAfter(filePath, Constants.RESOURCE_PREFIX);

		FilePictureRenderData readerData = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		// pictureStyle.setHeight(300);
		// pictureStyle.setWidth(sourceImg.getWidth() * 300 / sourceImg.getHeight());
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		readerData.setPictureStyle(pictureStyle);

		return readerData;

	}

	private FilePictureRenderData buildFilePictureRenderData(String filePath) {

		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		filePath = localPath + StringUtils.substringAfter(filePath, Constants.RESOURCE_PREFIX);

		File picture = new File(filePath);
		try {
			BufferedImage sourceImg = ImageIO.read(picture);

			FilePictureRenderData readerData = new FilePictureRenderData(filePath);
			PictureStyle pictureStyle = new PictureStyle();
			pictureStyle.setHeight(150);
			pictureStyle.setWidth(sourceImg.getWidth() * 150 / sourceImg.getHeight());
			// pictureStyle.setScalePattern(WidthScalePattern.FIT);
			readerData.setPictureStyle(pictureStyle);

			return readerData;

		} catch (IOException e) {
		}
		return null;
	}
}
