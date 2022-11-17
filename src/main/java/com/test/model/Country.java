package com.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country")
public class Country {

	@Id
	private Integer id;
	@Column(name = "country_name")
	private String countryName;
	@Column(name="country_captial")
	private String CountryCaptial;
	
	
	public Integer getId() {
		return id;
	}
	public Country() {
		// TODO Auto-generated constructor stub
	}
	public Country(Integer id, String countryName, String countryCaptial) {
		super();
		this.id = id;
		this.countryName = countryName;
		CountryCaptial = countryCaptial;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCaptial() {
		return CountryCaptial;
	}
	public void setCountryCaptial(String countryCaptial) {
		CountryCaptial = countryCaptial;
	}
	
}
