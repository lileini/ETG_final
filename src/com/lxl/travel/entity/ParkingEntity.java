package com.lxl.travel.entity;

import java.io.Serializable;

public class ParkingEntity implements Serializable{
	/** 白天停车价格*/
	private String BTTCJG;
	/**地址*/
	private String CCDZ;
	/** 空车位*/
	private String KCW;
	/** 名称*/
	private String CCMC;
	/** 是否开放(0：不开放；1：开放；)*/
	private String SFKF;
	/**停车场图片*/
	private String CCTP;
	public ParkingEntity(String bTTCJG, String cCDZ, String kCW, String cCMC,
			String sFKF, String cCTP) {
		super();
		BTTCJG = bTTCJG;
		CCDZ = cCDZ;
		KCW = kCW;
		CCMC = cCMC;
		SFKF = sFKF;
		CCTP = cCTP;
	}
	public ParkingEntity() {
		super();
	}
	public String getBTTCJG() {
		return BTTCJG;
	}
	public void setBTTCJG(String bTTCJG) {
		BTTCJG = bTTCJG;
	}
	public String getCCDZ() {
		return CCDZ;
	}
	public void setCCDZ(String cCDZ) {
		CCDZ = cCDZ;
	}
	public String getKCW() {
		return KCW;
	}
	public void setKCW(String kCW) {
		KCW = kCW;
	}
	public String getCCMC() {
		return CCMC;
	}
	public void setCCMC(String cCMC) {
		CCMC = cCMC;
	}
	public String getSFKF() {
		return SFKF;
	}
	public void setSFKF(String sFKF) {
		SFKF = sFKF;
	}
	public String getCCTP() {
		return CCTP;
	}
	public void setCCTP(String cCTP) {
		CCTP = cCTP;
	}
	
}
