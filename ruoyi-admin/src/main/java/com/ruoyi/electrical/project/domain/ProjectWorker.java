package com.ruoyi.electrical.project.domain;

import java.util.Arrays;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目工作人员对象 project_worker
 * 
 * @author fronttang
 * @date 2024-07-02
 */
public class ProjectWorker extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 项目ID */
	@Excel(name = "项目ID")
	private Long projectId;

	/** 检测单位ID */
	@Excel(name = "检测单位ID")
	private Long detectId;

	private String detectName;

	/** 用户ID */
	@Excel(name = "用户ID")
	private Long userId;

	private String nickName;

	private String userName;

	/** 绑定类型 */
	@Excel(name = "绑定类型")
	private String bindType;

	private String account;

	private Long[] userIds;

	private String[] viewAreas;

	private String[] editAreas;

	public String[] getViewAreas() {
		return viewAreas;
	}

	public void setViewAreas(String[] viewAreas) {
		this.viewAreas = viewAreas;
	}

	public String[] getEditAreas() {
		return editAreas;
	}

	public void setEditAreas(String[] editAreas) {
		this.editAreas = editAreas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}

	@Override
	public String toString() {
		return "ProjectWorker [id=" + id + ", projectId=" + projectId + ", detectId=" + detectId + ", detectName="
				+ detectName + ", userId=" + userId + ", nickName=" + nickName + ", userName=" + userName
				+ ", bindType=" + bindType + ", account=" + account + ", userIds=" + Arrays.toString(userIds) + "]";
	}

}
