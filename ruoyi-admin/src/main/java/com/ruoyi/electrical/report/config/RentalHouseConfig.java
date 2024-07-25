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
 * 出租屋消防配置
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
@Data
@HighConfig("1")
@SuppressWarnings("unused")
public class RentalHouseConfig {

	/**
	 * 是否张贴电动自行车禁止入内标识
	 */
	private String isBicycles;

	private TextRenderData isBicycles1;

	private TextRenderData isBicycles0;

	public TextRenderData getIsBicycles1() {
		if ("1".equalsIgnoreCase(this.isBicycles)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getIsBicycles0() {
		if ("0".equalsIgnoreCase(this.isBicycles)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 是否张贴消防通道严禁堵塞标识
	 */
	private String isFirePassages;

	private TextRenderData isFirePassages1;

	private TextRenderData isFirePassages0;

	public TextRenderData getIsFirePassages1() {
		if ("1".equalsIgnoreCase(this.isFirePassages)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getIsFirePassages0() {
		if ("0".equalsIgnoreCase(this.isFirePassages)) {
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
	 * 室内消火栓系统有无1有0无
	 */
	private String hydrantSystemHas;

	private TextRenderData hydrantSystemHas1;

	private TextRenderData hydrantSystemHas0;

	public TextRenderData getHydrantSystemHas1() {
		if ("1".equalsIgnoreCase(this.hydrantSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getHydrantSystemHas0() {
		if ("0".equalsIgnoreCase(this.hydrantSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 室内消火栓系统是否完好1是0否
	 */
	private String hydrantSystemStatus;

	private TextRenderData hydrantSystemStatus1;

	private TextRenderData hydrantSystemStatus0;

	public TextRenderData getHydrantSystemStatus1() {
		if ("1".equalsIgnoreCase(this.hydrantSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getHydrantSystemStatus0() {
		if ("0".equalsIgnoreCase(this.hydrantSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 火灾自动报警系统有无1有0无
	 */
	private String alarmSystemHas;

	private TextRenderData alarmSystemHas1;

	private TextRenderData alarmSystemHas0;

	public TextRenderData getAlarmSystemHas1() {
		if ("1".equalsIgnoreCase(this.alarmSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAlarmSystemHas0() {
		if ("0".equalsIgnoreCase(this.alarmSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 火灾自动报警系统是否完好1是0否
	 */
	private String alarmSystemStatus;

	private TextRenderData alarmSystemStatus1;

	private TextRenderData alarmSystemStatus0;

	public TextRenderData getAlarmSystemStatus1() {
		if ("1".equalsIgnoreCase(this.alarmSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAlarmSystemStatus0() {
		if ("0".equalsIgnoreCase(this.alarmSystemStatus)) {
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

	private TextRenderData fireSeparationStatus0;

	private TextRenderData fireSeparationStatus1;

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

	/**
	 * 门头照
	 */
	private String doorPic;

	private String doorPicture;

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
