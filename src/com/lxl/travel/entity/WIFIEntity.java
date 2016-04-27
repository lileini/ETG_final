package com.lxl.travel.entity;

import java.io.Serializable;

public class WIFIEntity implements Serializable{
	private String name,intro,address,distance;

	public WIFIEntity() {
		super();
	}

	public WIFIEntity(String name, String intro, String address, String distance) {
		super();
		this.name = name;
		this.intro = intro;
		this.address = address;
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
}
