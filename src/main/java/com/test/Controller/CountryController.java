package com.test.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.CountryService;
import com.test.model.AddResponse;
import com.test.model.Country;

@RestController
public class CountryController {

	@Autowired
	private CountryService countryService;
	
	@GetMapping("/getcountries")
	public ResponseEntity<List<Country>> getAllCountry(){
		try {
		 List<Country> allCountries = countryService.getAllCountries();
		 return new ResponseEntity<List<Country>>(allCountries, HttpStatus.FOUND);
	}
		catch (Exception e) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		}
	
	@GetMapping("/getcountries/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable(value = "id")int id) {
	try {
	Country country = countryService.getCountryById(id);
	return new ResponseEntity<Country>(country,HttpStatus.FOUND);
	}
	catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	}
	
	@GetMapping("/getcountries/countryName")
	public ResponseEntity<Country> getCountryBycountryName(@RequestParam(value = "countryName") String countryName) {
		try {
		 Country country = countryService.getCountryByName(countryName);
		 return new ResponseEntity<Country>(country, HttpStatus.FOUND);
	}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addcountry")
	public ResponseEntity<Country> addCountry(@RequestBody Country country) {
		try { 
		Country Country = countryService.addCountry(country);
		return new ResponseEntity<Country>(country, HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		}
	}
	
	@PutMapping("/updatecountry/{id}")
	public ResponseEntity<Country> upatedCountry(@PathVariable (value = "id") Integer id ,@RequestBody Country country) {
		
		try {
		Country co = countryService.getCountryById(id);
		co.setCountryCaptial(country.getCountryCaptial());
		co.setCountryName(country.getCountryName());
		Country updateCountry = countryService.updateCountry(co);
		return new ResponseEntity<Country>(updateCountry,HttpStatus.OK);
		}
		
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

		}
		
	}
	
	@DeleteMapping("/deletecountry/{id}")
	public  ResponseEntity<Country> deleteCountryById(@PathVariable (value = "id")Integer id) {
		Country country =null;
		try {
		 country= countryService.getCountryById(id);
		 countryService.deleteCountry(country);
		
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
		return new ResponseEntity<Country>(country,HttpStatus.OK);
	}
	
	
}
