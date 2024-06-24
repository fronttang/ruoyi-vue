package com.ruoyi.electrical.template.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模板列表对象 detect_template
 * 
 * @author fronttang
 * @date 2024-06-24
 */
public class DetectTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 检测单位 */
    @Excel(name = "检测单位")
    private Long detectId;

    /** 检测单位名称 */
    @Excel(name = "检测单位名称")
    private String detectName;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String name;

    /** 模板类型 */
    @Excel(name = "模板类型")
    private String type;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("detectId", getDetectId())
            .append("detectName", getDetectName())
            .append("name", getName())
            .append("type", getType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
