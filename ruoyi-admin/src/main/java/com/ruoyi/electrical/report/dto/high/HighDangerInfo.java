package com.ruoyi.electrical.report.dto.high;

import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class HighDangerInfo {

	private Long formId;

	private String formName;

	private Long formDataId;

	private String formDataName;

	private Long dangerId;

	private Double maxScore;

	private Double dataMaxScore;

	private String accMethod;

	private String firstContent;

	private Double score;

	private String description;

	private String suggestions;

	private String dangerPic;

	/**
	 * 检测员ID
	 */
	private Long inspectorId;

	@SuppressWarnings("unused")
	private FilePictureRenderData dangerPicture;

	public FilePictureRenderData getDangerPicture() {
		if (StrUtil.isBlank(this.dangerPic)) {
			return null;
		}

		String[] splitPic = this.dangerPic.split(",");

		if (splitPic == null || splitPic.length <= 0) {
			return null;
		}

		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(splitPic[0], Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}
}
