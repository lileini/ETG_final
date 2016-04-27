package com.lxl.travel.entity;

/**��װ��ǰ����״��(�ɴ��浱������)*/
public class CurrentWeather {
	/**��ǰ�¶�*/
	private String temp;
	/**����*/
	private String windDirection;
	/**����*/
	private String windStrength;
	/**��ǰʪ��*/
	private String humidity;
	/**����ʱ��*/
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
