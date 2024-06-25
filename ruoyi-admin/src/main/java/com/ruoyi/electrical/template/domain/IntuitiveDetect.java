package com.ruoyi.electrical.template.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 直观检测标题对象 intuitive_detect
 * 
 * @author fronttang
 * @date 2024-06-19
 */
public class IntuitiveDetect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    /** 检测表名称 */
    @Excel(name = "检测表名称")
    private String name;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 代号 */
    @Excel(name = "代号")
    private String code;

    /** 业主单元类型 */
    @Excel(name = "业主单元类型")
    private String unitType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
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
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setUnitType(String unitType) 
    {
        this.unitType = unitType;
    }

    public String getUnitType() 
    {
        return unitType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templateId", getTemplateId())
            .append("name", getName())
            .append("type", getType())
            .append("code", getCode())
            .append("unitType", getUnitType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }}
