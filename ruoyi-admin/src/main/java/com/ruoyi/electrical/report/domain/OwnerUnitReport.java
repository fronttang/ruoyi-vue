package com.ruoyi.electrical.report.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 初检报告对象 owner_unit_report
 * 
 * @author fronttang
 * @date 2024-07-15
 */
public class OwnerUnitReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 业主单元ID */
    @Excel(name = "业主单元ID")
    private Long unitId;

    /** 是否开启隐患通知单1开启 */
    @Excel(name = "是否开启隐患通知单1开启")
    private String isDangerNotice;

    /** 是否完成1是0否 */
    @Excel(name = "是否完成1是0否")
    private String isComplete;

    /** 是否无法检测1是0否 */
    @Excel(name = "是否无法检测1是0否")
    private String isTest;

    /** 无法检测原因 */
    @Excel(name = "无法检测原因")
    private String isTestReason;

    /** 报告类型1初检,2复检 */
    @Excel(name = "报告类型1初检,2复检")
    private String type;

    /** 报告编号 */
    @Excel(name = "报告编号")
    private String code;

    /** 检测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectData;

    /** 检测状态 */
    @Excel(name = "检测状态")
    private String detectStatus;

    /** 检测员 */
    @Excel(name = "检测员")
    private String inspector;

    /** 检测员ID */
    @Excel(name = "检测员ID")
    private Long inspectorId;

    /** 报告状态 */
    @Excel(name = "报告状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setIsDangerNotice(String isDangerNotice) 
    {
        this.isDangerNotice = isDangerNotice;
    }

    public String getIsDangerNotice() 
    {
        return isDangerNotice;
    }
    public void setIsComplete(String isComplete) 
    {
        this.isComplete = isComplete;
    }

    public String getIsComplete() 
    {
        return isComplete;
    }
    public void setIsTest(String isTest) 
    {
        this.isTest = isTest;
    }

    public String getIsTest() 
    {
        return isTest;
    }
    public void setIsTestReason(String isTestReason) 
    {
        this.isTestReason = isTestReason;
    }

    public String getIsTestReason() 
    {
        return isTestReason;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setDetectData(Date detectData) 
    {
        this.detectData = detectData;
    }

    public Date getDetectData() 
    {
        return detectData;
    }
    public void setDetectStatus(String detectStatus) 
    {
        this.detectStatus = detectStatus;
    }

    public String getDetectStatus() 
    {
        return detectStatus;
    }
    public void setInspector(String inspector) 
    {
        this.inspector = inspector;
    }

    public String getInspector() 
    {
        return inspector;
    }
    public void setInspectorId(Long inspectorId) 
    {
        this.inspectorId = inspectorId;
    }

    public Long getInspectorId() 
    {
        return inspectorId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitId", getUnitId())
            .append("isDangerNotice", getIsDangerNotice())
            .append("isComplete", getIsComplete())
            .append("isTest", getIsTest())
            .append("isTestReason", getIsTestReason())
            .append("type", getType())
            .append("code", getCode())
            .append("detectData", getDetectData())
            .append("detectStatus", getDetectStatus())
            .append("inspector", getInspector())
            .append("inspectorId", getInspectorId())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
