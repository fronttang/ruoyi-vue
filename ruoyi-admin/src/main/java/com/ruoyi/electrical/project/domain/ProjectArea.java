package com.ruoyi.electrical.project.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目区域对象 project_area
 * 
 * @author fronttang
 * @date 2024-06-19
 */
public class ProjectArea extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 项目ID */
	private Long projectId;

	/** 项目名称 */
	@Excel(name = "项目名称")
	private String projectName;

	/** 区县 */
	@Excel(name = "区县")
	private String district;

	private String districtName;

	/** 街道 */
	@Excel(name = "街道")
	private String street;

	private String streetName;

	/** 社区 */
	@Excel(name = "社区")
	private String community;

	private String communityName;

	/** 村 */
	@Excel(name = "村")
	private String hamlet;

	private String hamletName;

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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getHamlet() {
		return hamlet;
	}

	public void setHamlet(String hamlet) {
		this.hamlet = hamlet;
	}

	public String getHamletName() {
		return hamletName;
	}

	public void setHamletName(String hamletName) {
		this.hamletName = hamletName;
	}

	@Override
	public String toString() {
		return "ProjectArea [id=" + id + ", projectId=" + projectId + ", projectName=" + projectName + ", district="
				+ district + ", districtName=" + districtName + ", street=" + street + ", streetName=" + streetName
				+ ", community=" + community + ", communityName=" + communityName + ", hamlet=" + hamlet
				+ ", hamletName=" + hamletName + "]";
	}

}
