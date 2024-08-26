package com.ruoyi.electrical.dto;

import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

@Data
public class DangerReviewExportUrbanVillageDto {

	/**
	 * ID
	 */
	@Excel(name = "编号", orderNum = "0", needMerge = true)
	private Long id;

	/**
	 * 报告编号
	 */
	@Excel(name = "报告编号", orderNum = "1", needMerge = true)
	private String reivewCode;

	/**
	 * 社区
	 */
	@Excel(name = "社区", orderNum = "2", needMerge = true)
	private String communityName;

	/**
	 * 村
	 */
	@Excel(name = "村名", orderNum = "3", needMerge = true)
	private String hamletName;

	/**
	 * 名称
	 */
	@Excel(name = "场所地址", orderNum = "4", needMerge = true)
	private String name;

	/**
	 * 复查检测日期
	 */
	@Excel(name = "复查检测日期", orderNum = "5", needMerge = true)
	private String detectDate;

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "楼栋业主", orderNum = "14", needMerge = true)
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "业主电话", orderNum = "15", needMerge = true)
	private String phone;

	/**
	 * 网格员
	 */
	@Excel(name = "网格员", orderNum = "16", needMerge = true)
	private String gridman;

	/**
	 * 网格员电话
	 */
	@Excel(name = "网格员电话", orderNum = "17", needMerge = true)
	private String gridmanPhone;

	/**
	 * 房间数
	 */
	@Excel(name = "房间数", orderNum = "18", needMerge = true)
	private Long doorNumber;

	/**
	 * 入户检测房间数量
	 */
	@Excel(name = "入户检测房间数量", orderNum = "19", needMerge = true)
	private Long detectDoorNumber = 0L;

	/**
	 * 入户率
	 */
	@Excel(name = "入户率", orderNum = "20", needMerge = true)
	private String householdRate;

	/**
	 * 隐患已整改数量
	 */
	@Excel(name = "隐患已整改数量", orderNum = "21", needMerge = true)
	private Long finishs;

	/**
	 * 隐患须整改数量
	 */
	@Excel(name = "隐患须整改数量", orderNum = "22", needMerge = true)
	private Long unFinishs;

	/**
	 * 隐患总数量
	 */
	@Excel(name = "隐患总数量", orderNum = "23", needMerge = true)
	private Long danger;

	/**
	 * A类隐患数量
	 */
	@Excel(name = "A类隐患数量", orderNum = "24", needMerge = true)
	private Long dangersA;

	/**
	 * B类隐患数量
	 */
	@Excel(name = "B类隐患数量", orderNum = "25", needMerge = true)
	private Long dangersB;

	/**
	 * C类隐患数量
	 */
	@Excel(name = "C类隐患数量", orderNum = "26", needMerge = true)
	private Long dangersC;

	/**
	 * 公共区域隐患数量
	 */
	@Excel(name = "公共区域隐患数量", orderNum = "27", needMerge = true)
	private Long dangerAreaType1;

	/**
	 * 户内隐患数量
	 */
	@Excel(name = "户内隐患数量", orderNum = "28", needMerge = true)
	private Long dangerAreaType2;

	/**
	 * 整改率
	 */
	@Excel(name = "整改率", orderNum = "29", needMerge = true)
	private String correctionRate;

	/**
	 * 总漏电保护装置额定电电流是否合格
	 */
	@Excel(name = "总漏电保护装置额定电电流是否合格", orderNum = "30", needMerge = true)
	private String item1;

	/**
	 * 总漏电保护装置检测是否已整改
	 */
	@Excel(name = "总漏电保护装置检测是否已整改", orderNum = "31", needMerge = true)
	private String item2;

	/**
	 * 楼层漏电保护装置检测是否已整改
	 */
	@Excel(name = "楼层漏电保护装置检测是否已整改", orderNum = "32", needMerge = true)
	private String item3;

	/**
	 * 户内漏电保护装置检测是否已整改
	 */
	@Excel(name = "户内漏电保护装置检测是否已整改", orderNum = "33", needMerge = true)
	private String item4;

	/**
	 * 浴室漏电保护开关检测是否已整改
	 */
	@Excel(name = "浴室漏电保护开关检测是否已整改", orderNum = "34", needMerge = true)
	private String item5;

	/**
	 * 电表容量是否已整改
	 */
	@Excel(name = "电表容量是否已整改", orderNum = "35", needMerge = true)
	private String item6;

	/**
	 * 备注
	 */
	@Excel(name = "备注", orderNum = "36", needMerge = true)
	private String remark;

	/**
	 * 隐患列表
	 */
	@ExcelCollection(name = "隐患列表", orderNum = "6")
	private List<DangerReivewExportUrbanVillageDangerDto> dangers;

	@Data
	public static class DangerReivewExportUrbanVillageDangerDto {

		/**
		 * 隐患分类
		 */
		@Excel(name = "隐患分类", orderNum = "6")
		private String level;

		/**
		 * 序号
		 */
		@Excel(name = "序号", orderNum = "7")
		private String no;

		/**
		 * 存在隐患内容
		 */
		@Excel(name = "存在隐患内容", orderNum = "8")
		private String description;

		/**
		 * 整改建议
		 */
		@Excel(name = "整改建议", orderNum = "9")
		private String suggestions;

		/**
		 * 已整改隐患区域
		 */
		@Excel(name = "已整改隐患区域", orderNum = "10")
		private String finishLocation;

		/**
		 * 须整改隐患区域
		 */
		@Excel(name = "须整改隐患区域", orderNum = "11")
		private String unFinishLocation;

		/**
		 * 复查区域
		 */
		@Excel(name = "复查区域", orderNum = "12")
		private String reviewLocation;

		/**
		 * 整改情况
		 */
		@Excel(name = "整改情况", orderNum = "13")
		private String reivewStatus;
	}
}
