package com.lxl.travel.entity;

import java.io.Serializable;

public class ParkingEntity implements Serializable{
	/** ����ͣ���۸�*/
	private String BTTCJG;
	/**��ַ*/
	private String CCDZ;
	/** �ճ�λ*/
	private String KCW;
	/** ����*/
	private String CCMC;
	/** �Ƿ񿪷�(0�������ţ�1�����ţ�)*/
	private String SFKF;
	/**ͣ����ͼƬ*/
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
