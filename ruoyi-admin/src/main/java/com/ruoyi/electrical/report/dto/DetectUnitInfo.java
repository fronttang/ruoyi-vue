package com.ruoyi.electrical.report.dto;

import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class DetectUnitInfo {

	/** id */
	private Long id;

	/** 名称 */
	private String name;

	/** 简称 */
	private String shortName;

	/** 受控编号 */
	private String controlledNumber;

	/** 电话 */
	private String phone;

	/** 地址 */
	private String address;

	/** 联系人 */
	private String contact;

	/** 联系电话 */
	private String contactPhone;

	/** logo */
	private String logo;

	@SuppressWarnings("unused")
	private FilePictureRenderData logoPic;

	public FilePictureRenderData getLogoPic() {
		if (StrUtil.isBlank(this.logo)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.logo, Constants.RESOURCE_PREFIX);

		FilePictureRenderData logo = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setWidth(50);
		pictureStyle.setHeight(50);
		// pictureStyle.setScalePattern(WidthScalePattern.FIT);
		logo.setPictureStyle(pictureStyle);
		return logo;
	}

	/** 营业执照 */
	private String businessLicense;

	/** 资质 */
	private String qualification;

	@SuppressWarnings("unused")
	private FilePictureRenderData qualificationPic;

	public FilePictureRenderData getQualificationPic() {
		if (StrUtil.isBlank(this.qualification)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.qualification, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

}
