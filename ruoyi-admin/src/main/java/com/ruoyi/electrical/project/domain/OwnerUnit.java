package com.ruoyi.electrical.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 城中村电检对象 owner_unit
 * 
 * @author fronttang
 * @date 2024-06-21
 */
public class OwnerUnit extends BaseEntity
{
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

    /** 联系人 */
    @Excel(name = "联系人")
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
    //@JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "检测起止日期", width = 30, dateFormat = "yyyy-MM-dd")
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

    /** 消防安全负责人 */
    @Excel(name = "消防安全负责人")
    private String safetyIncharge;

    /** 消防安全管理人 */
    @Excel(name = "消防安全管理人")
    private String safetyManager;

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

    /** 检测模块 */
    @Excel(name = "检测模块")
    private String detectModule;

    /** 运营单位 */
    @Excel(name = "运营单位")
    private String operating;

    /** 物业类型 */
    @Excel(name = "物业类型")
    private String propertyType;

    /** 全景图 */
    @Excel(name = "全景图")
    private String panoramaPic;

    /** 点位图 */
    @Excel(name = "点位图")
    private String stationPic;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setDetectId(Long detectId) 
    {
        this.detectId = detectId;
    }

    public Long getDetectId() 
    {
        return detectId;
    }
    public void setDetectName(String detectName) 
    {
        this.detectName = detectName;
    }

    public String getDetectName() 
    {
        return detectName;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setArea(Long area) 
    {
        this.area = area;
    }

    public Long getArea() 
    {
        return area;
    }
    public void setEntrust(String entrust) 
    {
        this.entrust = entrust;
    }

    public String getEntrust() 
    {
        return entrust;
    }
    public void setManager(Long manager) 
    {
        this.manager = manager;
    }

    public Long getManager() 
    {
        return manager;
    }
    public void setGridman(Long gridman) 
    {
        this.gridman = gridman;
    }

    public Long getGridman() 
    {
        return gridman;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAcreage(String acreage) 
    {
        this.acreage = acreage;
    }

    public String getAcreage() 
    {
        return acreage;
    }
    public void setLayers(Long layers) 
    {
        this.layers = layers;
    }

    public Long getLayers() 
    {
        return layers;
    }
    public void setNature(String nature) 
    {
        this.nature = nature;
    }

    public String getNature() 
    {
        return nature;
    }
    public void setTestStartDate(Date testStartDate) 
    {
        this.testStartDate = testStartDate;
    }

    public Date getTestStartDate() 
    {
        return testStartDate;
    }
    public void setTestEndDate(Date testEndDate) 
    {
        this.testEndDate = testEndDate;
    }

    public Date getTestEndDate() 
    {
        return testEndDate;
    }
    public void setTestDate(String testDate) 
    {
        this.testDate = testDate;
    }

    public String getTestDate() 
    {
        return testDate;
    }
    public void setTestContent(String testContent) 
    {
        this.testContent = testContent;
    }

    public String getTestContent() 
    {
        return testContent;
    }
    public void setDoorNumber(Long doorNumber) 
    {
        this.doorNumber = doorNumber;
    }

    public Long getDoorNumber() 
    {
        return doorNumber;
    }
    public void setBuildman(String buildman) 
    {
        this.buildman = buildman;
    }

    public String getBuildman() 
    {
        return buildman;
    }
    public void setUnitType(String unitType) 
    {
        this.unitType = unitType;
    }

    public String getUnitType() 
    {
        return unitType;
    }
    public void setIncharge(String incharge) 
    {
        this.incharge = incharge;
    }

    public String getIncharge() 
    {
        return incharge;
    }
    public void setVenueType(String venueType) 
    {
        this.venueType = venueType;
    }

    public String getVenueType() 
    {
        return venueType;
    }
    public void setSafetyIncharge(String safetyIncharge) 
    {
        this.safetyIncharge = safetyIncharge;
    }

    public String getSafetyIncharge() 
    {
        return safetyIncharge;
    }
    public void setSafetyManager(String safetyManager) 
    {
        this.safetyManager = safetyManager;
    }

    public String getSafetyManager() 
    {
        return safetyManager;
    }
    public void setStaffs(Long staffs) 
    {
        this.staffs = staffs;
    }

    public Long getStaffs() 
    {
        return staffs;
    }
    public void setLicence(String licence) 
    {
        this.licence = licence;
    }

    public String getLicence() 
    {
        return licence;
    }
    public void setSafetyKeyUnit(String safetyKeyUnit) 
    {
        this.safetyKeyUnit = safetyKeyUnit;
    }

    public String getSafetyKeyUnit() 
    {
        return safetyKeyUnit;
    }
    public void setStationType(String stationType) 
    {
        this.stationType = stationType;
    }

    public String getStationType() 
    {
        return stationType;
    }
    public void setDetectModule(String detectModule) 
    {
        this.detectModule = detectModule;
    }

    public String getDetectModule() 
    {
        return detectModule;
    }
    public void setOperating(String operating) 
    {
        this.operating = operating;
    }

    public String getOperating() 
    {
        return operating;
    }
    public void setPropertyType(String propertyType) 
    {
        this.propertyType = propertyType;
    }

    public String getPropertyType() 
    {
        return propertyType;
    }
    public void setPanoramaPic(String panoramaPic) 
    {
        this.panoramaPic = panoramaPic;
    }

    public String getPanoramaPic() 
    {
        return panoramaPic;
    }
    public void setStationPic(String stationPic) 
    {
        this.stationPic = stationPic;
    }

    public String getStationPic() 
    {
        return stationPic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("type", getType())
            .append("detectId", getDetectId())
            .append("detectName", getDetectName())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("area", getArea())
            .append("entrust", getEntrust())
            .append("manager", getManager())
            .append("gridman", getGridman())
            .append("address", getAddress())
            .append("contact", getContact())
            .append("phone", getPhone())
            .append("acreage", getAcreage())
            .append("layers", getLayers())
            .append("nature", getNature())
            .append("testStartDate", getTestStartDate())
            .append("testEndDate", getTestEndDate())
            .append("testDate", getTestDate())
            .append("testContent", getTestContent())
            .append("doorNumber", getDoorNumber())
            .append("buildman", getBuildman())
            .append("unitType", getUnitType())
            .append("incharge", getIncharge())
            .append("venueType", getVenueType())
            .append("safetyIncharge", getSafetyIncharge())
            .append("safetyManager", getSafetyManager())
            .append("staffs", getStaffs())
            .append("licence", getLicence())
            .append("safetyKeyUnit", getSafetyKeyUnit())
            .append("stationType", getStationType())
            .append("detectModule", getDetectModule())
            .append("operating", getOperating())
            .append("propertyType", getPropertyType())
            .append("panoramaPic", getPanoramaPic())
            .append("stationPic", getStationPic())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
