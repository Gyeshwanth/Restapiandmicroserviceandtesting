package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.model.Country;
import com.test.repositores.CountryRespository;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = ServiceMacktioTests.class)
public class ServiceMacktioTests {

	@Mock
	private CountryRespository respository;
	
	@InjectMocks
	private CountryService countryService;


	@Test
	@Order(1)
	public void test_getAllCountries() {
		
		List<Country> allcountries = new ArrayList<>();
		allcountries.add(new Country(1, "india", "delhi"));
		allcountries.add(new Country(2, "usa", "washington"));
		
		when(respository.findAll()).thenReturn(allcountries);
		
		assertEquals(2,countryService.getAllCountries().size());
	}

	@Test
	@Order(2)
	public void test_getCountryById() {
		
		
		 Country country = new Country(1, "india", "delhi");
		//allcountries.add(new Country(2, "washington", "usa"));
		
		int id=1;
		when(respository.findById(id)).thenReturn(Optional.of(country));
		
		assertEquals(1,countryService.getCountryById(1).getId());
		
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() {
		
		 Country country = new Country(1, "india", "delhi");
		 
		 String name ="india";
		 when(respository.findBycountryName(name)).thenReturn(country);
		 
		 assertEquals("india", countryService.getCountryByName(name).getCountryName());
		
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		 Country country = new Country(1, "india", "delhi");
		 
		when(respository.save(country)).thenReturn(country);
		
		assertEquals(country, countryService.addCountry(country));
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		 Country country = new Country(1, "india", "delhi");
		
		when(respository.save(country)).thenReturn(country);
		assertEquals(country, countryService.updateCountry(country));
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		 Country country = new Country(1, "india", "delhi");
		
	countryService.deleteCountry(country);
		verify(respository,times(1)).delete(country);
		
	}
	
}
