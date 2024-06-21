package com.ruoyi.electrical.vo;

/**
 * 能用数据字典VO
 */
public class DictVO {

	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DictVO [id=" + id + ", name=" + name + "]";
	}
	
}
