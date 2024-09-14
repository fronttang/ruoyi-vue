package com.ruoyi.electrical.project.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 业主单元对象 owner_unit
 * 
 * @author fronttang
 * @date 2024-06-22
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnit extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/**
	 * 编码
	 */
	private String code;

	/** 名称 */
	@Excel(name = "名称")
	private String name;

	/** 业主单元类型 */
	@Excel(name = "业主单元类型")
	private String type;

	/** 检测单位ID */
	@Excel(name = "检测单位ID")
	private Long detectId;

	/** 检测单位名称 */
	@Excel(name = "检测单位名称")
	private String detectName;

	/** 项目ID */
	@Excel(name = "项目ID")
	private Long projectId;

	/** 项目名称 */
	@Excel(name = "项目名称")
	private String projectName;

	/** 区域 */
	@Excel(name = "区域")
	private Long area;

	/**
	 * 区县
	 */
	private String district;

	private String districtName;

	/**
	 * 街道
	 */
	private String street;

	private String streetName;

	/**
	 * 社区
	 */
	private String community;

	private String communityName;

	/**
	 * 村
	 */
	private String hamlet;

	private String hamletName;

	/** 委托单位 */
	@Excel(name = "委托单位")
	private String entrust;

	/** 管理员 */
	@Excel(name = "管理员")
	private Long manager;

	/** 网格员 */
	@Excel(name = "网格员")
	private Long gridman;

	/**
	 * 所属网格
	 */
	private String grid;

	/** 检测地址 */
	@Excel(name = "检测地址")
	private String address;

	/** 联系人/负责人/业主 */
	@Excel(name = "联系人/负责人/业主")
	private String contact;

	/** 联系电话 */
	@Excel(name = "联系电话")
	private String phone;

	/** 建筑面积 */
	@Excel(name = "建筑面积")
	private String acreage;

	/** 建筑层数 */
	@Excel(name = "建筑层数")
	private Long layers;

	/** 建筑使用性质 */
	@Excel(name = "建筑使用性质")
	private String nature;

	/**
	 * 温度
	 */
	private String temperature;

	/**
	 * 湿度
	 */
	private String humidity;

	/** 检测开始时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "检测开始时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date testStartDate;

	/** 检测结束时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "检测结束时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date testEndDate;

	/** 检测起止日期 */
	@Excel(name = "检测起止日期")
	private String testDate;

	/** 检测内容 */
	@Excel(name = "检测内容")
	private String testContent;

	/** 户数 */
	@Excel(name = "户数")
	private Long doorNumber;

	/** 楼栋长 */
	@Excel(name = "楼栋长")
	private String buildman;

	/** 单位类型 */
	@Excel(name = "单位类型")
	private String unitType;

	/** 负责人 */
	@Excel(name = "负责人")
	private String incharge;

	/** 场所类型 */
	@Excel(name = "场所类型")
	private String venueType;

	/** 经营范围 */
	@Excel(name = "经营范围")
	private String businessScope;

	/** 消防安全负责人 */
	@Excel(name = "消防安全负责人")
	private String safetyIncharge;

	/** 消防安全负责人电话 */
	@Excel(name = "消防安全负责人电话")
	private String safetyInchargePhone;

	/** 消防安全管理人 */
	@Excel(name = "消防安全管理人")
	private String safetyManager;

	/** 消防安全管理人电话 */
	@Excel(name = "消防安全管理人电话")
	private String safetyManagerPhone;

	/** 高风险类型 */
	@Excel(name = "高风险类型")
	private String highRiskType;

	/** 员工人数 */
	@Excel(name = "员工人数")
	private Long staffs;

	/** 有无证照 */
	@Excel(name = "有无证照")
	private String licence;

	/** 消防安全重点单位 */
	@Excel(name = "消防安全重点单位")
	private String fireSafetyUnit;

	/** 充电站类型 */
	@Excel(name = "充电站类型")
	private String stationType;

	/** 轮次 */
	@Excel(name = "轮次")
	private Long rounds;

	/** 检测模块 */
	@Excel(name = "检测模块")
	private String detectModule;

	/** 运营单位 */
	@Excel(name = "运营单位")
	private String operating;

	/** 物业类型 */
	@Excel(name = "物业类型")
	private String propertyType;

	/** 物业类型名称(物业类型为其他时输入) */
	@Excel(name = "物业类型名称(物业类型为其他时输入)")
	private String propertyName;

	/** 全景图 */
	@Excel(name = "全景图")
	private String panoramaPic;

	/** 点位图 */
	@Excel(name = "点位图")
	private String stationPic;

	/**
	 * 业主二维码
	 */
	private String mngQrcode;

	/**
	 * 天气
	 */
	private String weather;

	/**
	 * 风速（m/s）
	 */
	private String windSpeed;
}
