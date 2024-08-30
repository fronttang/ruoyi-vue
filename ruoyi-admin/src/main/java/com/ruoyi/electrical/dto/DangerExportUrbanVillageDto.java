package com.ruoyi.electrical.dto;

import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

@Data
public class DangerExportUrbanVillageDto {

	/**
	 * ID
	 */
	@Excel(name = "编号", orderNum = "0", needMerge = true)
	private Long id;

	/**
	 * 报告编号
	 */
	@Excel(name = "报告编号", orderNum = "1", needMerge = true)
	private String no = "/";

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
	@Excel(name = "场所名称", orderNum = "4", needMerge = true)
	private String name;

	/**
	 * 检测地址
	 */
	@Excel(name = "场所地址", orderNum = "5", needMerge = true)
	private String address;

	/**
	 * 检测日期
	 */
	@Excel(name = "排查检测日期", orderNum = "6", needMerge = true)
	private String detectDate;

	/**
	 * 是否开展电气线路检测
	 */
	@Excel(name = "是否开展电气线路检测", orderNum = "7", needMerge = true)
	private String circuit = "是";

	/**
	 * 检测单位名称
	 */
	@Excel(name = "检测单位", orderNum = "8", needMerge = true)
	private String detectName;

	/**
	 * 是否建立问题清单
	 */
	@Excel(name = "是否建立问题清单", orderNum = "9", needMerge = true)
	private String questions = "是";

	/**
	 * 整改期限
	 */
	@Excel(name = "整改期限", orderNum = "14", needMerge = true)
	private String deadline = "15天";

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "楼栋业主", orderNum = "15", needMerge = true)
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "业主电话", orderNum = "16", needMerge = true)
	private String phone;

	/**
	 * 网格员
	 */
	@Excel(name = "网格员", orderNum = "17", needMerge = true)
	private String gridman;

	/**
	 * 网格员电话
	 */
	@Excel(name = "网格员电话", orderNum = "18", needMerge = true)
	private String gridmanPhone;

	/**
	 * 房间数
	 */
	@Excel(name = "房间数", orderNum = "19", needMerge = true)
	private Long doorNumber;

	/**
	 * 入户检测房间数量
	 */
	@Excel(name = "入户检测房间数量", orderNum = "20", needMerge = true)
	private Long detectDoorNumber = 0L;

	/**
	 * 入户率
	 */
	@Excel(name = "入户率", orderNum = "21", needMerge = true)
	private String householdRate;

	/**
	 * 隐患总数量
	 */
	@Excel(name = "隐患总数量", orderNum = "22", needMerge = true)
	private Long danger;

	/**
	 * A类隐患数量
	 */
	@Excel(name = "A类隐患数量", orderNum = "23", needMerge = true)
	private Long dangersA;

	/**
	 * B类隐患数量
	 */
	@Excel(name = "B类隐患数量", orderNum = "24", needMerge = true)
	private Long dangersB;

	/**
	 * C类隐患数量
	 */
	@Excel(name = "C类隐患数量", orderNum = "25", needMerge = true)
	private Long dangersC;

	/**
	 * 公共区域隐患数量
	 */
	@Excel(name = "公共区域隐患数量", orderNum = "26", needMerge = true)
	private Long dangerAreaType1;

	/**
	 * 户内隐患数量
	 */
	@Excel(name = "户内隐患数量", orderNum = "27", needMerge = true)
	private Long dangerAreaType2;

	/**
	 * 楼栋是否开启隐患告知单
	 */
	@Excel(name = "楼栋是否开启隐患告知单", orderNum = "28", needMerge = true)
	private String isDangerNotice;

	public String getIsDangerNotice() {
		if ("1".equalsIgnoreCase(this.isDangerNotice)) {
			return "是";
		}
		return "否";
	}

	/**
	 * 接地装置检测是否符合
	 */
	@Excel(name = "接地装置检测是否符合", orderNum = "29", needMerge = true)
	private String b2;

	/**
	 * 总漏电保护装置额定电电流是否合格
	 */
	@Excel(name = "总漏电保护装置额定电电流是否合格", orderNum = "30", needMerge = true)
	private String b7CurrentResult;

	/**
	 * 总漏电保护装置检测是否合格
	 */
	@Excel(name = "总漏电保护装置检测是否合格", orderNum = "31", needMerge = true)
	private String b7;

	/**
	 * 楼层漏电保护装置检测是否合格
	 */
	@Excel(name = "楼层漏电保护装置检测是否合格", orderNum = "32", needMerge = true)
	private String b8;

	/**
	 * 户内漏电保护装置检测是否合格
	 */
	@Excel(name = "户内漏电保护装置检测是否合格", orderNum = "33", needMerge = true)
	private String b6;

	/**
	 * 浴室漏电保护开关检测是否合格
	 */
	@Excel(name = "浴室漏电保护开关检测是否合格", orderNum = "34", needMerge = true)
	private String b12;

	/**
	 * 电表容量是否合格
	 */
	@Excel(name = "电表容量是否合格", orderNum = "35", needMerge = true)
	private String b10;

	/**
	 * 楼栋总漏电保护开关额定动作电流
	 */
	@Excel(name = "楼栋总漏电保护开关额定动作电流", orderNum = "36", needMerge = true)
	private String b7Current;

	/**
	 * 楼栋检测工作照
	 */
	@Excel(name = "楼栋检测工作照", orderNum = "37", needMerge = true, type = 2, imageType = 2)
	private byte[] inspectionPicture = null;

	/**
	 * 备注
	 */
	@Excel(name = "备注", orderNum = "38", needMerge = true)
	private String remark;

	/**
	 * 隐患列表
	 */
	@ExcelCollection(name = "隐患列表", orderNum = "13")
	private List<DangerExportUrbanVillageDangerDto> dangers;

	@Data
	public static class DangerExportUrbanVillageDangerDto {

		/**
		 * 隐患分类
		 */
		@Excel(name = "隐患分类", orderNum = "10")
		private String level;

		/**
		 * 序号
		 */
		@Excel(name = "序号", orderNum = "11")
		private String no;

		/**
		 * 存在隐患内容
		 */
		@Excel(name = "存在隐患内容", orderNum = "12")
		private String description;

		/**
		 * 整改建议
		 */
		@Excel(name = "整改建议", orderNum = "13")
		private String suggestions;

	}
}
