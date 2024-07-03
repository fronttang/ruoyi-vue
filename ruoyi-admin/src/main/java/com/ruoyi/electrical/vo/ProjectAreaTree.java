package com.ruoyi.electrical.vo;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.electrical.project.domain.ProjectArea;

public class ProjectAreaTree {

	private String id;

	private String label;

	private List<ProjectAreaTree> children = new ArrayList<ProjectAreaTree>();

	private ProjectArea area;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<ProjectAreaTree> getChildren() {
		return children;
	}

	public void setChildren(List<ProjectAreaTree> children) {
		this.children = children;
	}

	public ProjectArea getArea() {
		return area;
	}

	public void setArea(ProjectArea area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "ProjectAreaTree [id=" + id + ", label=" + label + ", children=" + children + ", area=" + area + "]";
	}

}
