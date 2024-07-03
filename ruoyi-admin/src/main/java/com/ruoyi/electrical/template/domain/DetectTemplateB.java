package com.ruoyi.electrical.template.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模板B检测关联对象 detect_template_b
 * 
 * @author fronttang
 * @date 2024-07-03
 */
public class DetectTemplateB extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    /** B类表ID */
    @Excel(name = "B类表ID")
    private String bid;

    /** 类型1检测员端显示2初检报告显示 */
    @Excel(name = "类型1检测员端显示2初检报告显示")
    private String type;

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
    public void setBid(String bid) 
    {
        this.bid = bid;
    }

    public String getBid() 
    {
        return bid;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templateId", getTemplateId())
            .append("bid", getBid())
            .append("type", getType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
