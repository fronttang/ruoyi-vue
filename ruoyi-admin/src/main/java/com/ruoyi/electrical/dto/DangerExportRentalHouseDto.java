package com.ruoyi.electrical.dto;

import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

@Data
public class DangerExportRentalHouseDto implements IDangerExportDto {

	/**
	 * ID
	 */
	@Excel(name = "序号", orderNum = "0", needMerge = true)
	private Long id;

	/**
	 * 编码
	 */
	@Excel(name = "档案编号", orderNum = "1", needMerge = true)
	private String code;

	/**
	 * 街道
	 */
	@Excel(name = "街道", orderNum = "2", needMerge = true)
	private String streetName;

	/**
	 * 社区
	 */
	@Excel(name = "社区", orderNum = "3", needMerge = true)
	private String communityName;

	/**
	 * 所属网格
	 */
	@Excel(name = "所属网格", orderNum = "4", needMerge = true)
	private String grid;

	/**
	 * 名称
	 */
	@Excel(name = "场所名称", orderNum = "5", needMerge = true)
	private String name;

	/**
	 * 联系人/负责人/业主
	 */
	@Excel(name = "联系人", orderNum = "6", needMerge = true)
	private String contact;

	/**
	 * 联系电话
	 */
	@Excel(name = "联系电话", orderNum = "7", needMerge = true)
	private String phone;

	/**
	 * 检测地址
	 */
	@Excel(name = "地址", orderNum = "8", needMerge = true)
	private String address;

	/**
	 * 建筑层数
	 */
	@Excel(name = "楼层数", orderNum = "9", needMerge = true)
	private Long layers;

	/**
	 * 网络照片
	 */
	@Excel(name = "网格照片", orderNum = "10", type = 2, imageType = 2, needMerge = true)
	private byte[] businessLicense;

	/**
	 * 经营状态
	 */
	@Excel(name = "经营状态", orderNum = "11", needMerge = true)
	private String openStatus = "正常";

	/**
	 * 门头照
	 */
	@Excel(name = "整体照片", orderNum = "12", type = 2, imageType = 2, needMerge = true)
	private byte[] doorPicture = null;

	/**
	 * 检测人员
	 */
	@Excel(name = "检测人员", orderNum = "13", needMerge = true)
	private String inspector;

	/**
	 * 检测日期
	 */
	@Excel(name = "检测日期", orderNum = "14", needMerge = true)
	private String inspectorDate;

	/**
	 * 整改二维码
	 */
	@Excel(name = "整改二维码", orderNum = "15", type = 2, imageType = 2, needMerge = true)
	private byte[] mngQrcodePicture = null;

	/**
	 * 总分
	 */
	@Excel(name = "总得分", orderNum = "16", needMerge = true)
	private String totalScore;

	/**
	 * 隐患列表
	 */
	@ExcelCollection(name = "扣分标准", orderNum = "17")
	private List<OwnerUnitDangerFormExportDto> forms;

	@Data
	public static class OwnerUnitDangerFormExportDto {

		/**
		 * 扣分标准
		 */
		@Excel(name = "扣分标准", orderNum = "17", needMerge = true)
		private String maxScore;

		/**
		 * 扣分项
		 */
		@ExcelCollection(name = "扣分值", orderNum = "17")
		private List<OwnerUnitDangerFormDataExportDto> formDatas;

	}

	@Data
	public static class OwnerUnitDangerFormDataExportDto {

		/**
		 * 扣分值
		 */
		@Excel(name = "扣分值", orderNum = "18", needMerge = true)
		private String score;

		/**
		 * 隐患
		 */
		@ExcelCollection(name = "隐患", orderNum = "18")
		private List<OwnerUnitDangerInfoExportDto> dangers;

	}

	@Data
	public static class OwnerUnitDangerInfoExportDto {

		/**
		 * 隐患描述
		 */
		@Excel(name = "隐患描述", orderNum = "19")
		private String description;

		/**
		 * 隐患等级
		 */
		@Excel(name = "隐患等级", orderNum = "20")
		private String level;

		/**
		 * 备注
		 */
		@Excel(name = "备注", orderNum = "21")
		private String remark;

		/**
		 * 整改建议
		 */
		@Excel(name = "整改建议", orderNum = "22")
		private String suggestions;

		/**
		 * 隐患图片
		 */
		@Excel(name = "隐患图片1", orderNum = "23", type = 2, imageType = 2)
		private byte[] dangerPic1 = null;

		/**
		 * 隐患图片
		 */
		@Excel(name = "隐患图片2", orderNum = "24", type = 2, imageType = 2)
		private byte[] dangerPic2 = null;

		/**
		 * 整改情况
		 */
		@Excel(name = "整改情况", orderNum = "25")
		private String rectificationStatus;

		/**
		 * 整改图
		 */
		@Excel(name = "整改照片1", orderNum = "26", type = 2, imageType = 2)
		private byte[] rectificationPic1 = null;

		/**
		 * 整改图
		 */
		@Excel(name = "整改照片2", orderNum = "27", type = 2, imageType = 2)
		private byte[] rectificationPic2 = null;

	}
}
