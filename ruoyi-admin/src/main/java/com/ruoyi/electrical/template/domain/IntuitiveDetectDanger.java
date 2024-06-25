package com.ruoyi.electrical.template.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检测内容隐患对象 intuitive_detect_danger
 * 
 * @author fronttang
 * @date 2024-06-24
 */
public class IntuitiveDetectDanger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    /** 内容ID */
    @Excel(name = "内容ID")
    private Long dataId;

    /** 隐患等级 */
    @Excel(name = "隐患等级")
    private String level;

    /** 隐患描述 */
    @Excel(name = "隐患描述")
    private String description;

    /** 整改建议 */
    @Excel(name = "整改建议")
    private String suggestions;

    /** 累计方式 */
    @Excel(name = "累计方式")
    private String accMethod;

    /** 扣分数 */
    @Excel(name = "扣分数")
    private Long score;

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
    public void setDataId(Long dataId) 
    {
        this.dataId = dataId;
    }

    public Long getDataId() 
    {
        return dataId;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setSuggestions(String suggestions) 
    {
        this.suggestions = suggestions;
    }

    public String getSuggestions() 
    {
        return suggestions;
    }
    public void setAccMethod(String accMethod) 
    {
        this.accMethod = accMethod;
    }

    public String getAccMethod() 
    {
        return accMethod;
    }
    public void setScore(Long score) 
    {
        this.score = score;
    }

    public Long getScore() 
    {
        return score;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templateId", getTemplateId())
            .append("dataId", getDataId())
            .append("level", getLevel())
            .append("description", getDescription())
            .append("suggestions", getSuggestions())
            .append("accMethod", getAccMethod())
            .append("score", getScore())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
