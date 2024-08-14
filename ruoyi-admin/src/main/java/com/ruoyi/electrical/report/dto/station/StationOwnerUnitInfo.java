package com.ruoyi.electrical.report.dto.station;

import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class StationOwnerUnitInfo {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 检测地址
	 */
	private String address;

	/**
	 * 委托单位
	 */
	private String entrust;

	/**
	 * 联系人/负责人/业主
	 */
	private String contact;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 充电站类型
	 */
	private String stationType;

	private TextRenderData stationType1;

	private TextRenderData stationType2;

	public TextRenderData getStationType1() {
		if ("1".equalsIgnoreCase(this.stationType)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getStationType2() {
		if ("2".equalsIgnoreCase(this.stationType)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 轮次
	 */
	private Long rounds;

	/**
	 * 检测模块
	 */
	private String detectModule;

	/**
	 * 运营单位
	 */
	private String operating;

	/**
	 * 物业类型
	 */
	private String propertyType;

	private TextRenderData propertyType1;

	private TextRenderData propertyType2;

	private TextRenderData propertyType3;

	private TextRenderData propertyType4;

	public TextRenderData getPropertyType1() {
		if ("1".equalsIgnoreCase(this.stationType)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getPropertyType2() {
		if ("2".equalsIgnoreCase(this.stationType)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getPropertyType3() {
		if ("3".equalsIgnoreCase(this.stationType)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getPropertyType4() {
		if ("4".equalsIgnoreCase(this.stationType)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 物业类型名称(物业类型为其他时输入)
	 */
	private String propertyName;

	/**
	 * 全景图
	 */
	private String panoramaPic;

	private FilePictureRenderData panoramaPicture;

	public FilePictureRenderData getPanoramaPicture() {
		if (StrUtil.isBlank(this.panoramaPic)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.panoramaPic, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

	/**
	 * 点位图
	 */
	private String stationPic;

	private FilePictureRenderData stationPicture;

	public FilePictureRenderData getStationPicture() {
		if (StrUtil.isBlank(this.stationPic)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.stationPic, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

	/**
	 * 温度
	 */
	private String temperature;

	/**
	 * 湿度
	 */
	private String humidity;

	/**
	 * 区县
	 */
	private String district;

	private String districtName;

	/**
	 * 街道
	 */
	private String street;

	private String streetName;
}
