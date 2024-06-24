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
    
    private Long templateId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 代号 */
    @Excel(name = "代号")
    private String code;
    
    public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

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
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }

	@Override
	public String toString() {
		return "IntuitiveDetect [id=" + id + ", templateId=" + templateId + ", name=" + name + ", type=" + type
				+ ", code=" + code + "]";
	}

   
}
