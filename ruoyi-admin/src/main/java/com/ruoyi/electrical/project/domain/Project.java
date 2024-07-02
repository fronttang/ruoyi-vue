package com.ruoyi.electrical.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目对象 project
 * 
 * @author fronttang
 * @date 2024-06-18
 */
public class Project extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String type;

    /** 检测单位 */
    @Excel(name = "检测单位")
    private Long detectId;

    /** 检测单位名称 */
    @Excel(name = "检测单位名称")
    private String detectName;

    /** 入户率要求 */
    @Excel(name = "入户率要求")
    private Long householdRate;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

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
    public void setHouseholdRate(Long householdRate) 
    {
        this.householdRate = householdRate;
    }

    public Long getHouseholdRate() 
    {
        return householdRate;
    }
    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("type", getType())
            .append("detectId", getDetectId())
            .append("detectName", getDetectName())
            .append("householdRate", getHouseholdRate())
            .append("templateId", getTemplateId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
