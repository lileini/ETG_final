//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : UserEntity.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//



package com.lxl.travel.entity;

import java.io.Serializable;
import java.util.Date;


public class UserEntity implements Serializable{
	private String username,md5password,nickname,gender;
	private Date lastLoginTime, regTime;
	public UserEntity(String username, String md5password, String nickname,
			String gender, Date lastLoginTime, Date regTime) {
		super();
		this.username = username;
		this.md5password = md5password;
		this.nickname = nickname;
		this.gender = gender;
		this.lastLoginTime = lastLoginTime;
		this.regTime = regTime;
	}
	public UserEntity() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMd5password() {
		return md5password;
	}
	public void setMd5password(String md5password) {
		this.md5password = md5password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	@Override
	public String toString() {
		return "UserEntity [username=" + username + ", md5password="
				+ md5password + ", nickname=" + nickname + ", gender=" + gender
				+ ", lastLoginTime=" + lastLoginTime + ", regTime=" + regTime
				+ "]";
	}
	
	
}
