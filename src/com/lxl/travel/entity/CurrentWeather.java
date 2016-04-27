package com.lxl.travel.entity;

/**封装当前天气状况(可代替当天天气)*/
public class CurrentWeather {
	/**当前温度*/
	private String temp;
	/**风向*/
	private String windDirection;
	/**风力*/
	private String windStrength;
	/**当前湿度*/
	private String humidity;
	/**更新时间*/
	private String updateTime;
	
	public CurrentWeather(String temp, String windDirection,
			String windStrength, String humidity, String updateTime) {
		super();
		this.temp = temp;
		this.windDirection = windDirection;
		this.windStrength = windStrength;
		this.humidity = humidity;
		this.updateTime = updateTime;
	}

	public CurrentWeather() {
		super();
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindStrength() {
		return windStrength;
	}

	public void setWindStrength(String windStrength) {
		this.windStrength = windStrength;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "CurrentWeather [temp=" + temp + ", windDirection="
				+ windDirection + ", windStrength=" + windStrength
				+ ", humidity=" + humidity + ", updateTime=" + updateTime + "]";
	}
	
	
}
