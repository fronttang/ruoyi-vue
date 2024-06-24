package com.ruoyi.electrical.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 直观检测表内容对象 intuitive_detect_data
 * 
 * @author fronttang
 * @date 2024-06-23
 */
public class IntuitiveDetectDangerVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    private String code;
    
    private String name;
    
    private String type;
    
    /** 类型 */
    private String data_type;

    /** 一级编号 */
    private String firstCode;

    /** 一级编号内容 */
    private String firstContent;

    /** 二级编号 */
    private String secondaryCode;

    /** 二级编号内容 */
    private String secondaryContent;

    /** 权重 */
    private Long weights;

    /** 输出格式 */
    private String output;
    
    /** 隐患等级 */
    private String level;

    /** 隐患描述 */
    private String description;

    /** 整改建议 */
    private String suggestions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public String getFirstContent() {
		return firstContent;
	}

	public void setFirstContent(String firstContent) {
		this.firstContent = firstContent;
	}

	public String getSecondaryCode() {
		return secondaryCode;
	}

	public void setSecondaryCode(String secondaryCode) {
		this.secondaryCode = secondaryCode;
	}

	public String getSecondaryContent() {
		return secondaryContent;
	}

	public void setSecondaryContent(String secondaryContent) {
		this.secondaryContent = secondaryContent;
	}

	public Long getWeights() {
		return weights;
	}

	public void setWeights(Long weights) {
		this.weights = weights;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	@Override
	public String toString() {
		return "IntuitiveDetectDangerVo [id=" + id + ", code=" + code + ", name=" + name + ", type=" + type
				+ ", data_type=" + data_type + ", firstCode=" + firstCode + ", firstContent=" + firstContent
				+ ", secondaryCode=" + secondaryCode + ", secondaryContent=" + secondaryContent + ", weights=" + weights
				+ ", output=" + output + ", level=" + level + ", description=" + description + ", suggestions="
				+ suggestions + "]";
	}
    
}
