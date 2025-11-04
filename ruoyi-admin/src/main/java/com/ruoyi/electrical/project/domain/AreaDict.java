package com.ruoyi.electrical.project.domain;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 区域字典对象 sys_dict_data
 * 
 * @author fronttang
 * @date 2024-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AreaDict extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    private Long dictCode;

    /** 字典排序 */
    private Long dictSort;

    /** 名称 */
    @Excel(name = "名称")
    private String dictLabel;

    /** CODE */
    @Excel(name = "CODE")
    private String dictValue;

    /** 类型 */
    @Excel(name = "类型")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    private String cssClass;

    /** 表格回显样式 */
    private String listClass = "default";

    /** 是否默认（Y是 N否） */
    private String isDefault;

    /** 状态（0正常 1停用） */
    private String status;
    
    private Long projectId;

    private List<AreaDict> sub = new ArrayList<>();
}
