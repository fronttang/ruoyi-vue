package com.ruoyi.electrical.report.dto.high;

import java.util.HashMap;
import java.util.Map;

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
public class OwnerUnitInfo {

	private Long id;

	/** 名称 */
	private String name;

	/**
	 * 编码
	 */
	private String code;

	/** 检测地址 */
	private String address;

	/** 联系人/负责人/业主 */
	private String contact;

	/** 联系电话 */
	private String phone;

	/** 建筑面积 */
	private String acreage;

	/** 建筑层数 */
	private Long layers;

	/** 户数 */
	private Long doorNumber;

	/** 高风险类型 */
	private String highRiskType;

	/** 单位类型 */
	// private String unitType;

	/** 场所类型 */
	private String venueType;

	/** 经营范围 */
	private String businessScope;

	/** 消防安全负责人 */
	private String safetyIncharge;

	/** 消防安全负责人电话 */
	private String safetyInchargePhone;

	/** 消防安全管理人 */
	private String safetyManager;

	/** 消防安全管理人电话 */
	private String safetyManagerPhone;

	/** 员工人数 */
	private Long staffs;

	/** 有无证照 */
	private String licence;

	/**
	 * 业主二维码
	 */
	private String mngQrcode;

	private FilePictureRenderData mngQrcodePicture;

	public FilePictureRenderData getMngQrcodePicture() {
		if (StrUtil.isBlank(this.mngQrcode)) {
			return null;
		}

		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.mngQrcode, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qrcode = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setWidth(150);
		pictureStyle.setHeight(150);
		qrcode.setPictureStyle(pictureStyle);
		return qrcode;
	}

	private FilePictureRenderData mngQrcodePicture1;

	public FilePictureRenderData getMngQrcodePicture1() {
		if (StrUtil.isBlank(this.mngQrcode)) {
			return null;
		}

		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.mngQrcode, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qrcode = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setWidth(260);
		pictureStyle.setHeight(260);
		qrcode.setPictureStyle(pictureStyle);
		return qrcode;
	}

	public String getLicence() {
		return "1".equalsIgnoreCase(this.licence) ? "有" : "无";
	}

	/** 消防安全重点单位 */
	private String fireSafetyUnit;

	private TextRenderData fireSafetyUnit1;

	private TextRenderData fireSafetyUnit0;

	public TextRenderData getFireSafetyUnit1() {
		if ("1".equalsIgnoreCase(this.fireSafetyUnit)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireSafetyUnit0() {
		if ("0".equalsIgnoreCase(this.fireSafetyUnit)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

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

	/**
	 * 社区
	 */
	private String community;

	private String communityName;

	/**
	 * 村
	 */
	private String hamlet;

	private String hamletName;

	/**
	 * 消防配置
	 */
	private Map<String, Object> config = new HashMap<String, Object>();

	/**
	 * 单位类型
	 */
	private Map<String, Object> unitType = new HashMap<String, Object>();

	/**
	 * 开门状态
	 */
	private String openStatus = "正常";

	/**
	 * 检测员
	 */
	private String inspector;

	/**
	 * 所属网格
	 */
	private String grid;
}
