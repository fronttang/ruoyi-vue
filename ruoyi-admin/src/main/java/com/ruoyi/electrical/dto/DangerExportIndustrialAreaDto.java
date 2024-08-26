package com.ruoyi.electrical.dto;

import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

@Data
public class DangerExportIndustrialAreaDto {

	/**
	 * ID
	 */
	@Excel(name = "编号", orderNum = "0", needMerge = true)
	private Long id;

	/**
	 * 所属街道
	 */
	@Excel(name = "所属街道", orderNum = "1", needMerge = true)
	private String streetName;

	/**
	 * 名称
	 */
	@Excel(name = "工业园区名称", orderNum = "2", needMerge = true)
	private String name;

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "园区管理员", orderNum = "3", needMerge = true)
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "联系方式", orderNum = "4", needMerge = true)
	private String phone;

	/**
	 * 检测日期
	 */
	@Excel(name = "检测起止日期", orderNum = "5", needMerge = true)
	private String detectDate;

	/**
	 * 检测面积
	 */
	@Excel(name = "检测面积", orderNum = "6", needMerge = true)
	private String acreage;

	/**
	 * 隐患总数量
	 */
	@Excel(name = "隐患数量", orderNum = "13", needMerge = true)
	private Long danger;

	/**
	 * A类隐患数量
	 */
	@Excel(name = "A类隐患数量", orderNum = "14", needMerge = true)
	private Long dangersA;

	/**
	 * B类隐患数量
	 */
	@Excel(name = "B类隐患数量", orderNum = "15", needMerge = true)
	private Long dangersB;

	/**
	 * C类隐患数量
	 */
	@Excel(name = "C类隐患数量", orderNum = "16", needMerge = true)
	private Long dangersC;

	/**
	 * 整改期限
	 */
	@Excel(name = "整改期限", orderNum = "17", needMerge = true)
	private String deadline = "15天";

	/**
	 * 备注
	 */
	@Excel(name = "备注", orderNum = "18", needMerge = true)
	private String remark;

	/**
	 * 隐患列表
	 */
	@ExcelCollection(name = "隐患列表", orderNum = "7")
	private List<DangerExportIndustrialAreaDangerDto> dangers;

	@Data
	public static class DangerExportIndustrialAreaDangerDto {

		/**
		 * 序号
		 */
		@Excel(name = "序号", orderNum = "7")
		private String no;

		/**
		 * 隐患等级
		 */
		@Excel(name = "隐患等级", orderNum = "8")
		private String level;

		/**
		 * 存在隐患内容
		 */
		@Excel(name = "隐患描述", orderNum = "9")
		private String description;

		/**
		 * 整改建议
		 */
		@Excel(name = "整改建议", orderNum = "10")
		private String suggestions;

		/**
		 * 隐患位置
		 */
		@Excel(name = "隐患位置", orderNum = "11")
		private String location;

		/**
		 * 隐患代表照片
		 */
		@Excel(name = "隐患代表照片", orderNum = "12", type = 2, imageType = 2)
		private byte[] dangerPic;

	}
}
