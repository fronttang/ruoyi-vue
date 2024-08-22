package com.ruoyi.electrical.report.dto.high;

import java.util.List;

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
public class HighDangerInfo {

	/**
	 * 一级编号
	 */
	private String firstCode;

	/**
	 * 一级编号内容
	 */
	private String firstContent;

	/**
	 * 最高扣分数
	 */
	private Double maxScore;

	/**
	 * 扣分
	 */
	private Double score;

	/**
	 * 检测表ID
	 */
	private Long formId;

	/**
	 * 检测表名称
	 */
	private String formName;

	/**
	 * 检测表数据ID
	 */
	private Long formDataId;

	/**
	 * 检测表数据名称
	 */
	private String formDataName;

	/**
	 * 隐患名称
	 */
	private Long dangerId;

	/**
	 * 检测项最大扣分
	 */
	private Double dataMaxScore;

	/**
	 * 计分模式
	 */
	private String accMethod;

	/**
	 * 隐患描述
	 */
	private String description;

	/**
	 * 整改建议
	 */
	private String suggestions;

	/**
	 * 隐患图片
	 */
	private String dangerPic;

	/**
	 * 检测员ID
	 */
	private Long inspectorId;

	/**
	 * 隐患等级
	 */
	private String level;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态
	 */
	private String status;

	public String getStatus() {
		if ("2".equalsIgnoreCase(this.status)) {
			return "已整改";
		} else {
			return "未整改";
		}
	}

	/**
	 * 整体外观图
	 */
	private String overallPic;

	/**
	 * 现场检测图
	 */
	private String inspectionPic;

	/**
	 * 整改图
	 */
	private String rectificationPic;

	@SuppressWarnings("unused")
	private FilePictureRenderData rectificationPicture;

	public FilePictureRenderData getRectificationPicture() {
		if (StrUtil.isBlank(this.rectificationPic)) {
			return null;
		}

		List<String> pics = StrUtil.split(this.rectificationPic, ",");

		if (CollUtil.isEmpty(pics)) {
			return null;
		}

		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(pics.get(0), Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

	@SuppressWarnings("unused")
	private FilePictureRenderData dangerPicture;

	public FilePictureRenderData getDangerPicture() {
		if (StrUtil.isBlank(this.dangerPic)) {
			return null;
		}

		List<String> pics = StrUtil.split(this.dangerPic, ",");

		if (CollUtil.isEmpty(pics)) {
			return null;
		}

		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(pics.get(0), Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}
}
