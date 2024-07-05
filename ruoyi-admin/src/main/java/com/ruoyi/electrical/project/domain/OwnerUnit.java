package com.ruoyi.electrical.project.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业主单元对象 owner_unit
 * 
 * @author fronttang
 * @date 2024-06-22
 */
public class OwnerUnit extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

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

	/**
	 * 街道
	 */
	private String street;

	/**
	 * 社区
	 */
	private String community;

	/**
	 * 村
	 */
	private String hamlet;

	/** 委托单位 */
	@Excel(name = "委托单位")
	private String entrust;

	/** 管理员 */
	@Excel(name = "管理员")
	private Long manager;

	/** 网格员 */
	@Excel(name = "网格员")
	private Long gridman;

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
	private String safetyKeyUnit;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getDetectId() {
		return detectId;
	}

	public void setDetectId(Long detectId) {
		this.detectId = detectId;
	}

	public String getDetectName() {
		return detectName;
	}

	public void setDetectName(String detectName) {
		this.detectName = detectName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getHamlet() {
		return hamlet;
	}

	public void setHamlet(String hamlet) {
		this.hamlet = hamlet;
	}

	public String getEntrust() {
		return entrust;
	}

	public void setEntrust(String entrust) {
		this.entrust = entrust;
	}

	public Long getManager() {
		return manager;
	}

	public void setManager(Long manager) {
		this.manager = manager;
	}

	public Long getGridman() {
		return gridman;
	}

	public void setGridman(Long gridman) {
		this.gridman = gridman;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAcreage() {
		return acreage;
	}

	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}

	public Long getLayers() {
		return layers;
	}

	public void setLayers(Long layers) {
		this.layers = layers;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public Date getTestStartDate() {
		return testStartDate;
	}

	public void setTestStartDate(Date testStartDate) {
		this.testStartDate = testStartDate;
	}

	public Date getTestEndDate() {
		return testEndDate;
	}

	public void setTestEndDate(Date testEndDate) {
		this.testEndDate = testEndDate;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestContent() {
		return testContent;
	}

	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}

	public Long getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(Long doorNumber) {
		this.doorNumber = doorNumber;
	}

	public String getBuildman() {
		return buildman;
	}

	public void setBuildman(String buildman) {
		this.buildman = buildman;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getIncharge() {
		return incharge;
	}

	public void setIncharge(String incharge) {
		this.incharge = incharge;
	}

	public String getVenueType() {
		return venueType;
	}

	public void setVenueType(String venueType) {
		this.venueType = venueType;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getSafetyIncharge() {
		return safetyIncharge;
	}

	public void setSafetyIncharge(String safetyIncharge) {
		this.safetyIncharge = safetyIncharge;
	}

	public String getSafetyInchargePhone() {
		return safetyInchargePhone;
	}

	public void setSafetyInchargePhone(String safetyInchargePhone) {
		this.safetyInchargePhone = safetyInchargePhone;
	}

	public String getSafetyManager() {
		return safetyManager;
	}

	public void setSafetyManager(String safetyManager) {
		this.safetyManager = safetyManager;
	}

	public String getSafetyManagerPhone() {
		return safetyManagerPhone;
	}

	public void setSafetyManagerPhone(String safetyManagerPhone) {
		this.safetyManagerPhone = safetyManagerPhone;
	}

	public String getHighRiskType() {
		return highRiskType;
	}

	public void setHighRiskType(String highRiskType) {
		this.highRiskType = highRiskType;
	}

	public Long getStaffs() {
		return staffs;
	}

	public void setStaffs(Long staffs) {
		this.staffs = staffs;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getSafetyKeyUnit() {
		return safetyKeyUnit;
	}

	public void setSafetyKeyUnit(String safetyKeyUnit) {
		this.safetyKeyUnit = safetyKeyUnit;
	}

	public String getStationType() {
		return stationType;
	}

	public void setStationType(String stationType) {
		this.stationType = stationType;
	}

	public Long getRounds() {
		return rounds;
	}

	public void setRounds(Long rounds) {
		this.rounds = rounds;
	}

	public String getDetectModule() {
		return detectModule;
	}

	public void setDetectModule(String detectModule) {
		this.detectModule = detectModule;
	}

	public String getOperating() {
		return operating;
	}

	public void setOperating(String operating) {
		this.operating = operating;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPanoramaPic() {
		return panoramaPic;
	}

	public void setPanoramaPic(String panoramaPic) {
		this.panoramaPic = panoramaPic;
	}

	public String getStationPic() {
		return stationPic;
	}

	public void setStationPic(String stationPic) {
		this.stationPic = stationPic;
	}

	@Override
	public String toString() {
		return "OwnerUnit [id=" + id + ", name=" + name + ", type=" + type + ", detectId=" + detectId + ", detectName="
				+ detectName + ", projectId=" + projectId + ", projectName=" + projectName + ", area=" + area
				+ ", district=" + district + ", street=" + street + ", community=" + community + ", hamlet=" + hamlet
				+ ", entrust=" + entrust + ", manager=" + manager + ", gridman=" + gridman + ", address=" + address
				+ ", contact=" + contact + ", phone=" + phone + ", acreage=" + acreage + ", layers=" + layers
				+ ", nature=" + nature + ", testStartDate=" + testStartDate + ", testEndDate=" + testEndDate
				+ ", testDate=" + testDate + ", testContent=" + testContent + ", doorNumber=" + doorNumber
				+ ", buildman=" + buildman + ", unitType=" + unitType + ", incharge=" + incharge + ", venueType="
				+ venueType + ", businessScope=" + businessScope + ", safetyIncharge=" + safetyIncharge
				+ ", safetyInchargePhone=" + safetyInchargePhone + ", safetyManager=" + safetyManager
				+ ", safetyManagerPhone=" + safetyManagerPhone + ", highRiskType=" + highRiskType + ", staffs=" + staffs
				+ ", licence=" + licence + ", safetyKeyUnit=" + safetyKeyUnit + ", stationType=" + stationType
				+ ", rounds=" + rounds + ", detectModule=" + detectModule + ", operating=" + operating
				+ ", propertyType=" + propertyType + ", propertyName=" + propertyName + ", panoramaPic=" + panoramaPic
				+ ", stationPic=" + stationPic + "]";
	}

}
