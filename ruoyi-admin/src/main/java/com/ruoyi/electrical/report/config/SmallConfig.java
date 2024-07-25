package com.ruoyi.electrical.report.config;

import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.electrical.report.annotation.HighConfig;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * <p>
 * 三小场所
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Data
@SuppressWarnings("unused")
@HighConfig("2")
public class SmallConfig {

	/**
	 * 签订安全承诺书1是0否
	 */
	private String safetyCommitment;

	private TextRenderData safetyCommitment1;

	private TextRenderData safetyCommitment0;

	public TextRenderData getSafetyCommitment1() {
		if ("1".equalsIgnoreCase(this.safetyCommitment)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getSafetyCommitment0() {
		if ("0".equalsIgnoreCase(this.safetyCommitment)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 张贴违规住人海报1是0否
	 */
	private String illegalResidence;

	private TextRenderData illegalResidence1;

	private TextRenderData illegalResidence0;

	public TextRenderData getIllegalResidence1() {
		if ("1".equalsIgnoreCase(this.illegalResidence)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getIllegalResidence0() {
		if ("0".equalsIgnoreCase(this.illegalResidence)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * ABC干粉灭火器数量
	 */
	private Integer abcDpfeQuantity;

	/**
	 * ABC干粉灭火器是否完好1是0否
	 */
	private String abcDpfeStatus;

	private TextRenderData abcDpfeStatus1;

	private TextRenderData abcDpfeStatus0;

	public TextRenderData getAbcDpfeStatus1() {
		if ("1".equalsIgnoreCase(this.abcDpfeStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAbcDpfeStatus0() {
		if ("0".equalsIgnoreCase(this.abcDpfeStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 应急照明灯有无1有0无
	 */
	private String emergencyLightingHas;

	private TextRenderData emergencyLightingHas1;

	private TextRenderData emergencyLightingHas0;

	public TextRenderData getEmergencyLightingHas1() {
		if ("1".equalsIgnoreCase(this.emergencyLightingHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getEmergencyLightingHas0() {
		if ("0".equalsIgnoreCase(this.emergencyLightingHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 应急照明灯是否完好1是0否
	 */
	private String emergencyLightingStatus;

	private TextRenderData emergencyLightingStatus1;

	private TextRenderData emergencyLightingStatus0;

	public TextRenderData getEmergencyLightingStatus1() {
		if ("1".equalsIgnoreCase(this.emergencyLightingStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getEmergencyLightingStatus0() {
		if ("0".equalsIgnoreCase(this.emergencyLightingStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 疏散指示标志有无1有0无
	 */
	private String evacuationSignsHas;

	private TextRenderData evacuationSignsHas1;

	private TextRenderData evacuationSignsHas0;

	public TextRenderData getEvacuationSignsHas1() {
		if ("1".equalsIgnoreCase(this.evacuationSignsHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getEvacuationSignsHas0() {
		if ("0".equalsIgnoreCase(this.evacuationSignsHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 疏散指示标志是否完好1是0否
	 */
	private String evacuationSignsStatus;

	private TextRenderData evacuationSignsStatus1;

	private TextRenderData evacuationSignsStatus0;

	public TextRenderData getEvacuationSignsStatus1() {
		if ("1".equalsIgnoreCase(this.evacuationSignsStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getEvacuationSignsStatus0() {
		if ("0".equalsIgnoreCase(this.evacuationSignsStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 独立式感烟探测器有无1有0无
	 */
	private String smokeDetectorHas;

	private TextRenderData smokeDetectorHas1;

	private TextRenderData smokeDetectorHas0;

	public TextRenderData getSmokeDetectorHas1() {
		if ("1".equalsIgnoreCase(this.smokeDetectorHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getSmokeDetectorHas0() {
		if ("0".equalsIgnoreCase(this.smokeDetectorHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 独立式感烟探测器是否完好1是0否
	 */
	private String smokeDetectorStatus;

	private TextRenderData smokeDetectorStatus1;

	private TextRenderData smokeDetectorStatus0;

	public TextRenderData getSmokeDetectorStatus1() {
		if ("1".equalsIgnoreCase(this.smokeDetectorStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getSmokeDetectorStatus0() {
		if ("0".equalsIgnoreCase(this.smokeDetectorStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 消防软管卷盘有无1有0无
	 */
	private String fireHoseReelsHas;

	private TextRenderData fireHoseReelsHas1;

	private TextRenderData fireHoseReelsHas0;

	public TextRenderData getFireHoseReelsHas1() {
		if ("1".equalsIgnoreCase(this.fireHoseReelsHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireHoseReelsHas0() {
		if ("0".equalsIgnoreCase(this.fireHoseReelsHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 消防软管卷盘是否完好1是0否
	 */
	private String fireHoseReelsStatus;

	private TextRenderData fireHoseReelsStatus1;

	private TextRenderData fireHoseReelsStatus0;

	public TextRenderData getFireHoseReelsStatus1() {
		if ("1".equalsIgnoreCase(this.fireHoseReelsStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireHoseReelsStatus0() {
		if ("0".equalsIgnoreCase(this.fireHoseReelsStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 防火分隔设施有无1有0无
	 */
	private String fireSeparationHas;

	private TextRenderData fireSeparationHas1;

	private TextRenderData fireSeparationHas0;

	public TextRenderData getFireSeparationHas1() {
		if ("1".equalsIgnoreCase(this.fireSeparationHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireSeparationHas0() {
		if ("0".equalsIgnoreCase(this.fireSeparationHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 防火分隔设施是否完好1是0否
	 */
	private String fireSeparationStatus;

	private TextRenderData fireSeparationStatus1;

	private TextRenderData fireSeparationStatus0;

	public TextRenderData getFireSeparationStatus1() {
		if ("1".equalsIgnoreCase(this.fireSeparationStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireSeparationStatus0() {
		if ("0".equalsIgnoreCase(this.fireSeparationStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 营业执照
	 */
	private String businessLicense;

	private FilePictureRenderData businessLicensePic;

	public FilePictureRenderData getBusinessLicensePic() {
		if (StrUtil.isBlank(this.businessLicense)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.businessLicense, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

	/**
	 * 门头照
	 */
	private String doorPic;

	private FilePictureRenderData doorPicture;

	public FilePictureRenderData getDoorPicture() {
		if (StrUtil.isBlank(this.doorPic)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.doorPic, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

}
