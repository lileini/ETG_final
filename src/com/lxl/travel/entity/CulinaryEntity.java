package com.lxl.travel.entity;

import java.io.Serializable;

public class CulinaryEntity implements Serializable{
	@Override
	public String toString() {
		return "CulinaryEntity [photos=" + photos + "]";
	}
	private String name, address, phone, avg_price, photos;
	private int all_remarks,very_good_remarks,good_remarks,common_remarks,bad_remarks,very_bad_remarks;
	private String product_rating, environment_rating,service_rating, recommended_products;
	public CulinaryEntity() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAvg_price() {
		return avg_price;
	}
	public void setAvg_price(String avg_price) {
		this.avg_price = avg_price;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public int getAll_remarks() {
		return all_remarks;
	}
	public void setAll_remarks(int all_remarks) {
		this.all_remarks = all_remarks;
	}
	public int getVery_good_remarks() {
		return very_good_remarks;
	}
	public void setVery_good_remarks(int very_good_remarks) {
		this.very_good_remarks = very_good_remarks;
	}
	public int getGood_remarks() {
		return good_remarks;
	}
	public void setGood_remarks(int good_remarks) {
		this.good_remarks = good_remarks;
	}
	public int getCommon_remarks() {
		return common_remarks;
	}
	public void setCommon_remarks(int common_remarks) {
		this.common_remarks = common_remarks;
	}
	public int getBad_remarks() {
		return bad_remarks;
	}
	public void setBad_remarks(int bad_remarks) {
		this.bad_remarks = bad_remarks;
	}
	public int getVery_bad_remarks() {
		return very_bad_remarks;
	}
	public void setVery_bad_remarks(int very_bad_remarks) {
		this.very_bad_remarks = very_bad_remarks;
	}
	public String getProduct_rating() {
		return product_rating;
	}
	public void setProduct_rating(String product_rating) {
		this.product_rating = product_rating;
	}
	public String getEnvironment_rating() {
		return environment_rating;
	}
	public void setEnvironment_rating(String environment_rating) {
		this.environment_rating = environment_rating;
	}
	public String getService_rating() {
		return service_rating;
	}
	public void setService_rating(String service_rating) {
		this.service_rating = service_rating;
	}
	public String getRecommended_products() {
		return recommended_products;
	}
	public void setRecommended_products(String recommended_products) {
		this.recommended_products = recommended_products;
	}
}
