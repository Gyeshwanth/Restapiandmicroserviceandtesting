package com.test;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.AddResponse;
import com.test.model.Country;
import com.test.repositores.CountryRespository;

@Service
public class CountryService {

	@Autowired
	private CountryRespository respository;
	
	
	public List<Country> getAllCountries()
	{
		return respository.findAll();
	}
	
	public Country getCountryById(Integer id) {
		return respository.findById(id).get();
		
		
	}
	
	public Country addCountry(Country country) {
		
		country.setId(getMaxId());
		respository.save(country);
		return country;
		
	}
	
	public int getMaxId() {
	return	respository.findAll().size()+1;
	}
	
	public Country getCountryByName(String countryName) {
		return respository.findBycountryName(countryName);
	}
	
	public Country updateCountry(Country country) {
		
		// Country country = respository.findById(id).get();
		return respository.save(country);
	}
	
	public void deleteCountry(Country country) {
	
		// respository.deleteById(id);
		 respository.delete(country);
//		 AddResponse ar = new AddResponse();
//		 ar.setMsg("Successfully delete country.......");
//		 ar.setId(id);
//		return ar;
		
}
}
