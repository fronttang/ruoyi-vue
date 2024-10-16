package com.ruoyi.electrical.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.ByteArrayPictureRenderData;
import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

@Data
public class CommunityReportData {

	/**
	 * 区名称
	 */
	private String districtName;

	/**
	 * 街道名称
	 */
	private String streetName;

	/**
	 * 报告时间
	 */
	private String reportDate;

	/**
	 * 检测单位名称
	 */
	private String detectName;

	/**
	 * 三小场所
	 */
	private UnitTypeGroupData small;

	/**
	 * 出租屋
	 */
	private UnitTypeGroupData rentalHouse;

	/**
	 * 公共场所
	 */
	private UnitTypeGroupData publicPlaces;

	/**
	 * 工业企业
	 */
	private UnitTypeGroupData industrial;

	/**
	 * 住宅小区
	 */
	private UnitTypeGroupData residential;

	/**
	 * 累计数据
	 */
	private UnitTypeGroupData total;

	/**
	 * 分组数据
	 */
	private List<UnitTypeGroupData> groupData;

	/**
	 * 按高风险类型分组数据
	 */
	@Data
	public static class UnitTypeGroupData {

		/**
		 * 高风险类型名称
		 */
		private String name;

		/**
		 * 高风险类型
		 */
		private String type;

		/**
		 * 业主单元总数
		 */
		private Long unitTotal = 0L;

		/**
		 * 业主单元检测数
		 */
		private Long detectedUnit = 0L;

		/**
		 * 总得分
		 */
		private BigDecimal totalScore = BigDecimal.ZERO;

		/**
		 * 平均得分
		 */
		private BigDecimal averageScore = BigDecimal.ZERO;

		/**
		 * 总隐患
		 */
		private Long dangerTotal = 0L;

		/**
		 * 整改隐患
		 */
		private Long dangerFinish = 0L;

		/**
		 * 整改率
		 */
		private BigDecimal finishRate = BigDecimal.ZERO;

		/**
		 * 隐患汇总表
		 */
		private List<DangerInfo> dangerInfo = new ArrayList<CommunityReportData.UnitTypeGroupData.DangerInfo>();

		/**
		 * 饼图1
		 */
		private ByteArrayPictureRenderData pieImage1;

		/**
		 * 饼图2
		 */
		private ByteArrayPictureRenderData pieImage2;

		@Data
		public static class DangerInfo {

			/**
			 * 序号
			 */
			private Long code;

			/**
			 * 隐患描述
			 */
			private String description;

			/**
			 * 整改建议
			 */
			private String suggestions;

			/**
			 * 场所数量
			 */
			private Long unitCount;

			/**
			 * 占场所总数比例
			 */
			private BigDecimal unitRate;

			/**
			 * 问题数量
			 */
			private Long dangerCount;

			/**
			 * 占问题总数比例
			 */
			private BigDecimal dangerRate;

			/**
			 * 对应检查标准
			 */
			private String firstCode;

			/**
			 * 扣分
			 */
			private Double score;

			/**
			 * 隐患图片
			 */
			private List<String> dangerPics;

			@SuppressWarnings("unused")
			private FilePictureRenderData dangerPicture;

			public FilePictureRenderData getDangerPicture() {
				if (CollUtil.isEmpty(dangerPics)) {
					return null;
				}
				// 本地资源路径
				String localPath = RuoYiConfig.getProfile();
				// 数据库资源地址
				String filePath = localPath + StringUtils.substringAfter(dangerPics.get(0), Constants.RESOURCE_PREFIX);

				FilePictureRenderData qualification = new FilePictureRenderData(filePath);
				PictureStyle pictureStyle = new PictureStyle();
				pictureStyle.setScalePattern(WidthScalePattern.FIT);
				qualification.setPictureStyle(pictureStyle);
				return qualification;
			}
		}

	}
}
