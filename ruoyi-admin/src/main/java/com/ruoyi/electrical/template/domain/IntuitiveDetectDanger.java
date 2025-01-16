package com.ruoyi.electrical.template.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检测内容隐患对象 intuitive_detect_danger
 * 
 * @author fronttang
 * @date 2024-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
    
    /**
     * 是否重大隐患1是0否
     */
    private Integer important;
}
