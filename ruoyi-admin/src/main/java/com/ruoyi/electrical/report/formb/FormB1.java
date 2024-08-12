package com.ruoyi.electrical.report.formb;

import com.ruoyi.electrical.report.annotation.Formb;

import lombok.Data;

@Data
@Formb("B1")
public class FormB1 {

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
	 * 判定结果 合格/不合格
	 */
	private String result;

	/**
	 * 红外判定图
	 */
	private String infraredPic;

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
