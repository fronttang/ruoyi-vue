package com.ruoyi.electrical.report.formb;

import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.electrical.danger.handler.IFormbDangerHandler;
import com.ruoyi.electrical.report.annotation.Formb;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Formb("B1")
@SuppressWarnings("unused")
public class FormB1 extends BaseFormB {

	public static final String THREE_PHASE = "三相";

	public static final String SINGLE_PHASE = "单相";

	/**
	 * 辐射率
	 */
	private String radiation;

	/**
	 * 天气
	 */
	private String weather;

	/**
	 * 测试距离（m)
	 */
	private String distance;

	/**
	 * 风速（m/s）
	 */
	private String windSpeed;

	/**
	 * 检测时间 yyyy-MM-dd
	 */
	private String detectionTime;

	/**
	 * 被测设备名称
	 */
	private String deviceName;

	/**
	 * 检测位置
	 */
	private String location;

	/**
	 * 设备编号
	 */
	private String deviceCode;

	/**
	 * 图片编号
	 */
	private String imageCode;

	/**
	 * 额定电压（V）
	 */
	private String ratedVoltage;

	/**
	 * 额定电流（A)
	 */
	private String ratedCurrent;

	/**
	 * 整体外观图
	 */
	private String overallPic;

	/**
	 * 现场检测图
	 */
	private String inspectionPic;

	/**
	 * 三相检测
	 */
	private ThreePhase threePhase = new ThreePhase();

	/**
	 * 单相检测
	 */
	private SinglePhase singlePhase = new SinglePhase();

	/**
	 * 类型 三相/单相
	 */
	private String type;

	/**
	 * 判定结果 合格/不合格
	 */
	private String result;

	/**
	 * 合格
	 */

	private TextRenderData result1;

	/**
	 * 不合格
	 */
	private TextRenderData result2;

	public TextRenderData getResult1() {
		if (IFormbDangerHandler.QUALIFIED.equalsIgnoreCase(this.result)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getResult2() {
		if (IFormbDangerHandler.FAILURE.equalsIgnoreCase(this.result)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 红外判定图
	 */
	private String infraredPic;

	private FilePictureRenderData infraredPicture;

	public FilePictureRenderData getInfraredPicture() {
		if (StrUtil.isBlank(this.infraredPic)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.infraredPic, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

	/**
	 * 单相检测
	 */
	@Data
	public static class SinglePhase {
		/**
		 * L
		 */
		private ABNCLData L = new ABNCLData();

		/**
		 * N
		 */
		private ABNCLData N = new ABNCLData();

		/**
		 * 电压（V）LN
		 */
		private String ln;
	}

	/**
	 * 三相检测
	 */
	@Data
	public static class ThreePhase {

		/**
		 * A
		 */
		private ABNCLData A = new ABNCLData();

		/**
		 * B
		 */
		private ABNCLData B = new ABNCLData();

		/**
		 * N
		 */
		private ABNCLData N = new ABNCLData();

		/**
		 * C
		 */
		private ABNCLData C = new ABNCLData();

		/**
		 * 电压（V）
		 */
		private VoltageData voltage = new VoltageData();
	}

	@Data
	public static class ABNCLData {

		/**
		 * 电源侧温度（℃）
		 */
		private String powerTemp;

		/**
		 * 负荷侧温度（℃）
		 */
		private String loadTemp;

		/**
		 * 电流（A）
		 */
		private String current;

	}

	/**
	 * 电压（V）
	 */
	@Data
	public static class VoltageData {

		/**
		 * A-B
		 */
		private String ab;

		/**
		 * A-C
		 */
		private String ac;

		/**
		 * B-C
		 */
		private String bc;
	}

}
