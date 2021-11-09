package com.reyco.testShiro.domain;

public class Permission {
	private Integer id;
	private String name;
	private String percode;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPercode() {
		return percode;
	}
	public void setPercode(String percode) {
		this.percode = percode;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", percode=" + percode + "]";
	}
}
