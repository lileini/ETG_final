package com.lxl.travel.entity;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class TrainEntity implements Serializable {
	public String end_time;
	public String start_station;
	public String start_station_type;
	public String start_time;
	public String run_distance;
	public String end_station_type;
	public String train_type;
	public String train_no;
	public String run_time;
	public String end_station;
	public List<Price_list> price_list;
	

	/**
	 * @return the price_list
	 */
	public List<Price_list> getPrice_list() {
		return price_list;
	}

	/**
	 * @param price_list the price_list to set
	 */
	public void setPrice_list(List<Price_list> price_list) {
		this.price_list = price_list;
	}

	public TrainEntity() {
		super();
	}

	public TrainEntity(String end_time, String start_station,
			String start_station_type, String start_time, String run_distance,
			String end_station_type, String train_type, String train_no,
			String run_time, String end_station, List<Price_list> price_list) {
		super();
		this.end_time = end_time;
		this.start_station = start_station;
		this.start_station_type = start_station_type;
		this.start_time = start_time;
		this.run_distance = run_distance;
		this.end_station_type = end_station_type;
		this.train_type = train_type;
		this.train_no = train_no;
		this.run_time = run_time;
		this.end_station = end_station;
		this.price_list = price_list;
	}

	public class Price_list implements Serializable {
		public String price_type;
		public String price;
		
		public Price_list(String price_type, String price) {
			super();
			this.price_type = price_type;
			this.price = price;
		}

		public void setPrice_type(String price_type) {
			this.price_type = price_type;
		}

		public String getPrice_type() {
			return this.price_type;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getPrice() {
			return this.price;
		}

	}

	public void setStart_station(String start_station) {
		this.start_station = start_station;
	}

	public String getStart_station() {
		return this.start_station;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getEnd_time() {
		return this.end_time;
	}

	public void setStart_station_type(String start_station_type) {
		this.start_station_type = start_station_type;
	}

	public String getStart_station_type() {
		return this.start_station_type;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getStart_time() {
		return this.start_time;
	}

	public void setEnd_station_type(String end_station_type) {
		this.end_station_type = end_station_type;
	}

	public String getEnd_station_type() {
		return this.end_station_type;
	}

	public void setRun_distance(String run_distance) {
		this.run_distance = run_distance;
	}

	public String getRun_distance() {
		return this.run_distance;
	}

	public void setTrain_type(String train_type) {
		this.train_type = train_type;
	}

	public String getTrain_type() {
		return this.train_type;
	}

	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}

	public String getTrain_no() {
		return this.train_no;
	}

	public void setEnd_station(String end_station) {
		this.end_station = end_station;
	}

	public String getEnd_station() {
		return this.end_station;
	}

	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}

	public String getRun_time() {
		return this.run_time;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TrainEntity [end_time=" + end_time + ", start_station="
				+ start_station + ", start_station_type=" + start_station_type
				+ ", start_time=" + start_time + ", run_distance="
				+ run_distance + ", end_station_type=" + end_station_type
				+ ", train_type=" + train_type + ", train_no=" + train_no
				+ ", run_time=" + run_time + ", end_station=" + end_station
				+ ", price_list=" + price_list + "]";
	}

}