package com.ruoyi.electrical.dto;

import java.util.List;

import com.ruoyi.electrical.dto.DangerExportRentalHouseDto.OwnerUnitDangerDataExportDto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

@Data
public class DangerExportSmallDto implements IDangerExportDto {

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
	 * 建筑面积
	 */
	@Excel(name = "面积", orderNum = "9", needMerge = true)
	private String acreage;

	/**
	 * 网络照片
	 */
	@Excel(name = "营业执照", orderNum = "10", type = 2, imageType = 2, needMerge = true)
	private byte[] businessLicense;

	/**
	 * 经营状态
	 */
	@Excel(name = "经营状态", orderNum = "11", needMerge = true)
	private String openStatus = "正常";

	/**
	 * 总分
	 */
	@Excel(name = "总得分", orderNum = "12", needMerge = true)
	private Double totalScore;

	/**
	 * 隐患列表
	 */
	@ExcelCollection(name = "扣分标准", orderNum = "13")
	private List<OwnerUnitDangerDataExportDto> dangers;

}
