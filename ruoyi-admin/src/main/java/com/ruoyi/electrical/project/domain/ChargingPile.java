package com.ruoyi.electrical.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 充电桩对象 charging_pile
 * 
 * @author ruoyi
 * @date 2024-07-18
 */
public class ChargingPile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 充电站ID */
    @Excel(name = "充电站ID")
    private Long unitId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 自编号 */
    @Excel(name = "自编号")
    private String code;

    /** 轮次 */
    @Excel(name = "轮次")
    private Long rounds;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

    /** 功率 */
    @Excel(name = "功率")
    private String power;

    /** 出厂编号 */
    @Excel(name = "出厂编号")
    private String serialNumber;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setRounds(Long rounds) 
    {
        this.rounds = rounds;
    }

    public Long getRounds() 
    {
        return rounds;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setPower(String power) 
    {
        this.power = power;
    }

    public String getPower() 
    {
        return power;
    }
    public void setSerialNumber(String serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() 
    {
        return serialNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitId", getUnitId())
            .append("name", getName())
            .append("code", getCode())
            .append("rounds", getRounds())
            .append("type", getType())
            .append("brand", getBrand())
            .append("model", getModel())
            .append("power", getPower())
            .append("serialNumber", getSerialNumber())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
