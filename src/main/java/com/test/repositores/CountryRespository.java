package com.test.repositores;



import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Country;

public interface CountryRespository extends JpaRepository<Country, Integer> {

	Country findBycountryName(String countryName);

	 
	

}
