package com.ruoyi.electrical.report.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报告日志对象 owner_unit_report_log
 * 
 * @author fronttang
 * @date 2024-07-15
 */
public class OwnerUnitReportLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志ID */
    private Long id;

    /** 报告ID */
    @Excel(name = "报告ID")
    private Long reportId;

    /** 操作员 */
    @Excel(name = "操作员")
    private String operator;

    /** 操作员ID */
    @Excel(name = "操作员ID")
    private Long operatorId;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String operationType;

    /** 操作员角色 */
    @Excel(name = "操作员角色")
    private String operatorRole;

    /** 操作图片 */
    @Excel(name = "操作图片")
    private String operationPic;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setReportId(Long reportId) 
    {
        this.reportId = reportId;
    }

    public Long getReportId() 
    {
        return reportId;
    }
    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }
    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }
    public void setOperationType(String operationType) 
    {
        this.operationType = operationType;
    }

    public String getOperationType() 
    {
        return operationType;
    }
    public void setOperatorRole(String operatorRole) 
    {
        this.operatorRole = operatorRole;
    }

    public String getOperatorRole() 
    {
        return operatorRole;
    }
    public void setOperationPic(String operationPic) 
    {
        this.operationPic = operationPic;
    }

    public String getOperationPic() 
    {
        return operationPic;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reportId", getReportId())
            .append("operator", getOperator())
            .append("operatorId", getOperatorId())
            .append("operationType", getOperationType())
            .append("operatorRole", getOperatorRole())
            .append("operationPic", getOperationPic())
            .append("content", getContent())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
