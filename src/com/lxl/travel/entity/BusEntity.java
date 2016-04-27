package com.lxl.travel.entity;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class BusEntity implements Serializable {
	public String arrive;
	public String price;
	public String start;
	public String date;

	public BusEntity() {
		super();
	}

	public BusEntity(String arrive, String price, String start, String date) {
		super();
		this.arrive = arrive;
		this.price = price;
		this.start = start;
		this.date = date;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getArrive() {
		return this.arrive;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return this.price;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStart() {
		return this.start;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return this.date;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BusEntity [arrive=" + arrive + ", price=" + price + ", start="
				+ start + ", date=" + date + "]";
	}

	
}