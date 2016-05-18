package com.lxl.travel.entity;

import java.io.Serializable;

public class FlightEntity implements Serializable {
	public String DepCity;
	public String ArrCity;
	public String Airline;
	public String FlightNum;
	public String OnTimeRate;
	public String FlightDate;
	public String DepTime;
	public String ArrTime;
	public String DepTerminal;
	public String ArrTerminal;
	

	public FlightEntity() {
		super();
	}

	public FlightEntity(String depCity, String arrCity, String airline,
			String flightNum, String onTimeRate, String flightDate,
			String depTime, String arrTime, String depTerminal,
			String arrTerminal) {
		super();
		DepCity = depCity;
		ArrCity = arrCity;
		Airline = airline;
		FlightNum = flightNum;
		OnTimeRate = onTimeRate;
		FlightDate = flightDate;
		DepTime = depTime;
		ArrTime = arrTime;
		DepTerminal = depTerminal;
		ArrTerminal = arrTerminal;
	}


	/**
	 * @return the depCity
	 */
	public String getDepCity() {
		return DepCity;
	}


	/**
	 * @param depCity the depCity to set
	 */
	public void setDepCity(String depCity) {
		DepCity = depCity;
	}


	/**
	 * @return the arrCity
	 */
	public String getArrCity() {
		return ArrCity;
	}


	/**
	 * @param arrCity the arrCity to set
	 */
	public void setArrCity(String arrCity) {
		ArrCity = arrCity;
	}


	/**
	 * @return the airline
	 */
	public String getAirline() {
		return Airline;
	}


	/**
	 * @param airline the airline to set
	 */
	public void setAirline(String airline) {
		Airline = airline;
	}


	/**
	 * @return the flightNum
	 */
	public String getFlightNum() {
		return FlightNum;
	}


	/**
	 * @param flightNum the flightNum to set
	 */
	public void setFlightNum(String flightNum) {
		FlightNum = flightNum;
	}


	/**
	 * @return the onTimeRate
	 */
	public String getOnTimeRate() {
		return OnTimeRate;
	}


	/**
	 * @param onTimeRate the onTimeRate to set
	 */
	public void setOnTimeRate(String onTimeRate) {
		OnTimeRate = onTimeRate;
	}


	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return FlightDate;
	}


	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		FlightDate = flightDate;
	}


	/**
	 * @return the depTime
	 */
	public String getDepTime() {
		return DepTime;
	}


	/**
	 * @param depTime the depTime to set
	 */
	public void setDepTime(String depTime) {
		DepTime = depTime;
	}


	/**
	 * @return the arrTime
	 */
	public String getArrTime() {
		return ArrTime;
	}


	/**
	 * @param arrTime the arrTime to set
	 */
	public void setArrTime(String arrTime) {
		ArrTime = arrTime;
	}


	/**
	 * @return the depTerminal
	 */
	public String getDepTerminal() {
		return DepTerminal;
	}


	/**
	 * @param depTerminal the depTerminal to set
	 */
	public void setDepTerminal(String depTerminal) {
		DepTerminal = depTerminal;
	}


	/**
	 * @return the arrTerminal
	 */
	public String getArrTerminal() {
		return ArrTerminal;
	}


	/**
	 * @param arrTerminal the arrTerminal to set
	 */
	public void setArrTerminal(String arrTerminal) {
		ArrTerminal = arrTerminal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[航空公司=" + Airline + ", 航班号" + FlightNum
				+ ", 准确率=" + OnTimeRate + ", FlightDate=" + FlightDate
				+ ", DepTime=" + DepTime + ", ArrTime=" + ArrTime
				+ ", DepTerminal=" + DepTerminal + ", ArrTerminal="
				+ ArrTerminal + "]";
	}
	
	
}