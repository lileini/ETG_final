package com.lxl.travel.entity;

/**封装未来天气状况*/
public class FurtherWeather {
	/**温度*/
	private String temp;
	/**天气状况*/
	private String weather;
	/**风向+风力*/
	private String wind;
	/**星期*/
	private String week;
	/**日期*/
	private String date;
	
	public FurtherWeather(String temp, String weather, String wind,
			String week, String date) {
		super();
		this.temp = temp;
		this.weather = weather;
		this.wind = wind;
		this.week = week;
		this.date = date;
	}

	public FurtherWeather() {
		super();
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "FurtherWeather [temp=" + temp + ", weather=" + weather
				+ ", wind=" + wind + ", week=" + week + ", date=" + date + "]";
	}
	
	
}
