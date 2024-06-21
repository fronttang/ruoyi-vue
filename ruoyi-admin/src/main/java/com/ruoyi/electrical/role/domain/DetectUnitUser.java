package com.ruoyi.electrical.role.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检测单位账号对象 detect_unit_user
 * 
 * @author fronttang
 * @date 2024-06-18
 */
public class DetectUnitUser extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 检测单位ID */
    @Excel(name = "检测单位ID")
    private Long detectId;

    /** 检测单位 */
    @Excel(name = "检测单位")
    private String detectName;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 1.管理员，2员工 */
    private String type;

    /** 记录员 */
    private String recorder;

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
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setRecorder(String recorder) 
    {
        this.recorder = recorder;
    }

    public String getRecorder() 
    {
        return recorder;
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
            .append("detectId", getDetectId())
            .append("detectName", getDetectName())
            .append("account", getAccount())
            .append("name", getName())
            .append("password", getPassword())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("type", getType())
            .append("recorder", getRecorder())
            .append("district", getDistrict())
            .append("street", getStreet())
            .append("community", getCommunity())
            .append("hamlet", getHamlet())
            .toString();
    }
}
