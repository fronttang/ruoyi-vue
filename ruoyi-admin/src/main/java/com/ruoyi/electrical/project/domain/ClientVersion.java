package com.ruoyi.electrical.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 版本更新对象 client_version
 * 
 * @author fronttang
 * @date 2024-07-31
 */
public class ClientVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 客户端 */
    @Excel(name = "客户端")
    private String client;

    /** 版本名称 */
    @Excel(name = "版本名称")
    private String name;

    /** 版本代码 */
    @Excel(name = "版本代码")
    private String version;

    /** 强制更新1是0否 */
    @Excel(name = "强制更新1是0否")
    private String forced;

    /** 更新内容 */
    @Excel(name = "更新内容")
    private String content;

    /** 下载地址 */
    @Excel(name = "下载地址")
    private String downloadUrl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClient(String client) 
    {
        this.client = client;
    }

    public String getClient() 
    {
        return client;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }
    public void setForced(String forced) 
    {
        this.forced = forced;
    }

    public String getForced() 
    {
        return forced;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setDownloadUrl(String downloadUrl) 
    {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() 
    {
        return downloadUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("client", getClient())
            .append("name", getName())
            .append("version", getVersion())
            .append("forced", getForced())
            .append("content", getContent())
            .append("downloadUrl", getDownloadUrl())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
