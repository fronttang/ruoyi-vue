package com.ruoyi.electrical.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检测仪器对象 detect_device
 * 
 * @author fronttang
 * @date 2024-06-19
 */
public class DetectDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 检测单位 */
    private Long detectId;

    /** 检测单位名称 */
    @Excel(name = "检测单位名称")
    private String detectName;

    /** 仪器编号 */
    @Excel(name = "仪器编号")
    private String deviceId;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 仪器名称 */
    @Excel(name = "仪器名称")
    private String name;

    /** 校准日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "校准日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date calibrationDate;

    /** 是否过期1过期，0未过期 */
    private String isExpired;

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
    public void setDeviceId(String deviceId) 
    {
        this.deviceId = deviceId;
    }

    public String getDeviceId() 
    {
        return deviceId;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCalibrationDate(Date calibrationDate) 
    {
        this.calibrationDate = calibrationDate;
    }

    public Date getCalibrationDate() 
    {
        return calibrationDate;
    }
    public void setIsExpired(String isExpired) 
    {
        this.isExpired = isExpired;
    }

    public String getIsExpired() 
    {
        return isExpired;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("detectId", getDetectId())
            .append("detectName", getDetectName())
            .append("deviceId", getDeviceId())
            .append("type", getType())
            .append("name", getName())
            .append("calibrationDate", getCalibrationDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isExpired", getIsExpired())
            .toString();
    }
}
