package com.ruoyi.electrical.template.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 直观检测表内容对象 intuitive_detect_data
 * 
 * @author fronttang
 * @date 2024-06-24
 */
public class IntuitiveDetectData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    /** 检测表标题 */
    @Excel(name = "检测表标题")
    private Long detectTitle;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 业主单元类型 */
    @Excel(name = "业主单元类型")
    private String unitType;

    /** 模块类型（一级，二级） */
    @Excel(name = "模块类型", readConverterExp = "一级，二级")
    private String moduleType;

    /** 检测板块 */
    @Excel(name = "检测板块")
    private String detectModule;

    /** 上级模块ID */
    @Excel(name = "上级模块ID")
    private Long parentId;

    /** 一级编号 */
    @Excel(name = "一级编号")
    private String firstCode;

    /** 一级编号内容 */
    @Excel(name = "一级编号内容")
    private String firstContent;

    /** 二级编号 */
    @Excel(name = "二级编号")
    private String secondaryCode;

    /** 二级编号内容 */
    @Excel(name = "二级编号内容")
    private String secondaryContent;

    /** 权重 */
    @Excel(name = "权重")
    private Long weights;

    /** 输出格式 */
    @Excel(name = "输出格式")
    private String output;

    /** 最高扣分数 */
    @Excel(name = "最高扣分数")
    private Long maxScore;

    /** 1展示，2计分 */
    @Excel(name = "1展示，2计分")
    private String view;
    
    /** 展示模块上级 */
    @Excel(name = "展示模块上级")
    private Long viewParentId;
    
    private List<IntuitiveDetectDanger> dangers;
    
    public List<IntuitiveDetectDanger> getDangers() {
		return dangers;
	}

	public void setDangers(List<IntuitiveDetectDanger> dangers) {
		this.dangers = dangers;
	}

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
    public void setDetectTitle(Long detectTitle) 
    {
        this.detectTitle = detectTitle;
    }

    public Long getDetectTitle() 
    {
        return detectTitle;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setUnitType(String unitType) 
    {
        this.unitType = unitType;
    }

    public String getUnitType() 
    {
        return unitType;
    }
    public void setModuleType(String moduleType) 
    {
        this.moduleType = moduleType;
    }

    public String getModuleType() 
    {
        return moduleType;
    }
    public void setDetectModule(String detectModule) 
    {
        this.detectModule = detectModule;
    }

    public String getDetectModule() 
    {
        return detectModule;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setFirstCode(String firstCode) 
    {
        this.firstCode = firstCode;
    }

    public String getFirstCode() 
    {
        return firstCode;
    }
    public void setFirstContent(String firstContent) 
    {
        this.firstContent = firstContent;
    }

    public String getFirstContent() 
    {
        return firstContent;
    }
    public void setSecondaryCode(String secondaryCode) 
    {
        this.secondaryCode = secondaryCode;
    }

    public String getSecondaryCode() 
    {
        return secondaryCode;
    }
    public void setSecondaryContent(String secondaryContent) 
    {
        this.secondaryContent = secondaryContent;
    }

    public String getSecondaryContent() 
    {
        return secondaryContent;
    }
    public void setWeights(Long weights) 
    {
        this.weights = weights;
    }

    public Long getWeights() 
    {
        return weights;
    }
    public void setOutput(String output) 
    {
        this.output = output;
    }

    public String getOutput() 
    {
        return output;
    }
    public void setMaxScore(Long maxScore) 
    {
        this.maxScore = maxScore;
    }

    public Long getMaxScore() 
    {
        return maxScore;
    }
    public void setView(String view) 
    {
        this.view = view;
    }

    public String getView() 
    {
        return view;
    }
    
    public Long getViewParentId() {
		return viewParentId;
	}

	public void setViewParentId(Long viewParentId) {
		this.viewParentId = viewParentId;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templateId", getTemplateId())
            .append("detectTitle", getDetectTitle())
            .append("type", getType())
            .append("unitType", getUnitType())
            .append("moduleType", getModuleType())
            .append("detectModule", getDetectModule())
            .append("parentId", getParentId())
            .append("firstCode", getFirstCode())
            .append("firstContent", getFirstContent())
            .append("secondaryCode", getSecondaryCode())
            .append("secondaryContent", getSecondaryContent())
            .append("weights", getWeights())
            .append("output", getOutput())
            .append("maxScore", getMaxScore())
            .append("view", getView())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
