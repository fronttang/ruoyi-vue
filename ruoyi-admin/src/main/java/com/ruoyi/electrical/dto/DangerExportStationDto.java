package com.ruoyi.electrical.dto;

import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

@Data
public class DangerExportStationDto {

	/**
	 * ID
	 */
	@Excel(name = "序号", orderNum = "0", needMerge = true)
	private Long id;

	/**
	 * 街道
	 */
	@Excel(name = "所属街道", orderNum = "1", needMerge = true)
	private String streetName;

	/**
	 * 运营单位
	 */
	@Excel(name = "运营单位", orderNum = "2", needMerge = true)
	private String operating;

	/**
	 * 投入运营时间
	 */
	@Excel(name = "投入运营时间", orderNum = "3", needMerge = true)
	private String operatingDate;

	/**
	 * 名称
	 */
	@Excel(name = "充电站名称", orderNum = "4", needMerge = true)
	private String name;

	/**
	 * 场站服务车辆
	 */
	@Excel(name = "充电站服务车辆", orderNum = "5", needMerge = true)
	private String vehicles;

	/**
	 * 检测地址
	 */
	@Excel(name = "站点详细地址", orderNum = "6", needMerge = true)
	private String address;

	/**
	 * 充电桩数量
	 */
	@ExcelCollection(name = "充电桩数量", orderNum = "7")
	private List<StationPileInfo> pileInfo;

	@Data
	public static class StationPileInfo {
		/**
		 * 充电站充电桩总数
		 */
		@Excel(name = "总数", orderNum = "7", groupName = "场站充电桩", needMerge = true)
		private Long stationPileQuantity;

		/**
		 * 充电站充电桩总功率
		 */
		@Excel(name = "总功率（kw)", orderNum = "8", groupName = "场站充电桩", needMerge = true)
		private String stationPilePower;

		/**
		 * 非车载充电桩总数
		 */
		@Excel(name = "总数", orderNum = "9", groupName = "非车载充电桩", needMerge = true)
		private Long quantity1;

		/**
		 * 交流充电桩总数
		 */
		@Excel(name = "总数", orderNum = "10", groupName = "交流充电桩", needMerge = true)
		private Long quantity2;

	}

	/**
	 * 建设明细
	 */
	@Excel(name = "充电桩建设明细", orderNum = "11", needMerge = true)
	private String details;

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "联系人", orderNum = "12", needMerge = true)
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "联系方式", orderNum = "13", needMerge = true)
	private String phone;

	/**
	 * 有无休息室1有0无
	 */
	@Excel(name = "休息室", orderNum = "14", needMerge = true)
	private String lounge;

	public String getLounge() {
		if ("1".equalsIgnoreCase(this.lounge)) {
			return "有";
		}
		return "无";
	}

	/**
	 * 排查时间
	 */
	@Excel(name = "排查时间", orderNum = "15", needMerge = true)
	private String detectDate;

	/**
	 * 安全系数
	 */
	@Excel(name = "安全系数", orderNum = "22", needMerge = true)
	private String score;

	/**
	 * 风险等级
	 */
	@Excel(name = "风险等级", orderNum = "23", needMerge = true)
	private String level;

	/**
	 * 备注
	 */
	@Excel(name = "备注", orderNum = "24", needMerge = true)
	private String remark;

	/**
	 * 隐患列表
	 */
	@ExcelCollection(name = "隐患", orderNum = "16")
	private List<DangerExportStationDangerDto> dangers;

	@Data
	public static class DangerExportStationDangerDto {

		/**
		 * 隐患描述
		 */
		@Excel(name = "隐患描述", orderNum = "16")
		private String description;

		/**
		 * 严重程度
		 */
		@Excel(name = "严重程度", orderNum = "17")
		private String level;

		/**
		 * 整改建议
		 */
		@Excel(name = "整改建议", orderNum = "18")
		private String suggestions;

		/**
		 * 隐患照片
		 */
		@Excel(name = "隐患照片", orderNum = "19", type = 2, imageType = 2)
		private byte[] dangerPicture;

		/**
		 * 整改情况
		 */
		@Excel(name = "整改情况", orderNum = "20")
		private String status;

		public String getStatus() {
			if ("2".equalsIgnoreCase(this.status)) {
				return "已整改";
			} else if ("0".equalsIgnoreCase(this.status) || "1".equalsIgnoreCase(this.status)) {
				return "未整改";
			}
			return "";
		}

		/**
		 * 整改照片
		 */
		@Excel(name = "整改照片", orderNum = "21", type = 2, imageType = 2)
		private byte[] rectificationPicture;
	}
}
