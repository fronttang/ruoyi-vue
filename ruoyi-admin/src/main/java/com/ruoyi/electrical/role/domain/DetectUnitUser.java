package com.ruoyi.electrical.role.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 账号对象
 * 
 * @author fronttang
 * @date 2024-06-18
 */
public class DetectUnitUser extends BaseEntity
{
    /**
	 * 
	 */
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
    
    private Long projectId;
    
    private String projectName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDetectId() {
		return detectId;
	}

	public void setDetectId(Long detectId) {
		this.detectId = detectId;
	}

	public String getDetectName() {
		return detectName;
	}

	public void setDetectName(String detectName) {
		this.detectName = detectName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getHamlet() {
		return hamlet;
	}

	public void setHamlet(String hamlet) {
		this.hamlet = hamlet;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "DetectUnitUser [id=" + id + ", detectId=" + detectId + ", detectName=" + detectName + ", account="
				+ account + ", name=" + name + ", password=" + password + ", status=" + status + ", type=" + type
				+ ", recorder=" + recorder + ", district=" + district + ", street=" + street + ", community="
				+ community + ", hamlet=" + hamlet + ", projectId=" + projectId + ", projectName=" + projectName + "]";
	}
    
}
