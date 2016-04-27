package com.lxl.travel.entity;

/**��װδ������״��*/
public class FurtherWeather {
	/**�¶�*/
	private String temp;
	/**����״��*/
	private String weather;
	/**����+����*/
	private String wind;
	/**����*/
	private String week;
	/**����*/
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
