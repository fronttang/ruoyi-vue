package com.ruoyi.electrical.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目工作人员区域对象 project_worker_area
 * 
 * @author fronttang
 * @date 2024-07-02
 */
public class ProjectWorkerArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 项目工作人员ID */
    @Excel(name = "项目工作人员ID")
    private Long workerId;

    /** 数据权限1查看,2编辑 */
    @Excel(name = "数据权限1查看,2编辑")
    private String type;

    /** 区县 */
    @Excel(name = "区县")
    private String district;

    /** 街道 */
    @Excel(name = "街道")
    private String street;

    /** 社区 */
    @Excel(name = "社区")
    private String community;

    /** 村 */
    @Excel(name = "村")
    private String hamlet;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWorkerId(Long workerId) 
    {
        this.workerId = workerId;
    }

    public Long getWorkerId() 
    {
        return workerId;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setDistrict(String district) 
    {
        this.district = district;
    }

    public String getDistrict() 
    {
        return district;
    }
    public void setStreet(String street) 
    {
        this.street = street;
    }

    public String getStreet() 
    {
        return street;
    }
    public void setCommunity(String community) 
    {
        this.community = community;
    }

    public String getCommunity() 
    {
        return community;
    }
    public void setHamlet(String hamlet) 
    {
        this.hamlet = hamlet;
    }

    public String getHamlet() 
    {
        return hamlet;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workerId", getWorkerId())
            .append("type", getType())
            .append("district", getDistrict())
            .append("street", getStreet())
            .append("community", getCommunity())
            .append("hamlet", getHamlet())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
