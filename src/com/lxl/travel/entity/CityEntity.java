package com.lxl.travel.entity;

public class CityEntity {
	private String city;

	public CityEntity() {
		super();
	}

	public CityEntity(String city) {
		super();
		this.city = city;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CityEntity [city=" + city + "]";
	}
	
}
