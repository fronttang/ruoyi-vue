package com.ruoyi.electrical.report.dto.station;

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
public class StationPic {

	/**
	 * 图片地址1
	 */
	private String pic1;

	/**
	 * 编号1
	 */
	private String code1;

	/**
	 * 描述1
	 */
	private String description1;

	@SuppressWarnings("unused")
	private FilePictureRenderData picture1;

	public FilePictureRenderData getPicture1() {
		if (StrUtil.isBlank(this.pic1)) {
			return null;
		}

		List<String> pics = StrUtil.split(this.pic1, ",");

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

	/**
	 * 图片地址1
	 */
	private String pic2;

	/**
	 * 编号1
	 */
	private String code2;

	/**
	 * 描述1
	 */
	private String description2;

	@SuppressWarnings("unused")
	private FilePictureRenderData picture2;

	public FilePictureRenderData getPicture2() {
		if (StrUtil.isBlank(this.pic2)) {
			return null;
		}

		List<String> pics = StrUtil.split(this.pic2, ",");

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
