package com.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.test.Controller.CountryController;
import com.test.model.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = ControllerMacktioTests.class)
public class ControllerMacktioTests {

	@Mock
 private CountryService countryService;
	
	@InjectMocks
	private CountryController controller;
	
	
	
	@Test
	@Order(1)
	public void test_getAllCountry() {
		
		List<Country> allcountries = new ArrayList<>();
		allcountries.add(new Country(1, "india", "delhi"));
		allcountries.add(new Country(2, "usa", "washington"));
		
		when(countryService.getAllCountries()).thenReturn(allcountries); //mocking 
	
		assertEquals(2, controller.getAllCountry().getBody().size());
		assertEquals(HttpStatus.FOUND, controller.getAllCountry().getStatusCode());
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() {

		Country country = new Country(1, "india", "delhi");
		Integer id=1;
		when(countryService.getCountryById(id)).thenReturn(country);
		
		assertEquals(1,controller.getCountryById(1).getBody().getId());
		assertEquals(HttpStatus.FOUND, controller.getCountryById(1).getStatusCode());
			
}
	
	@Test
	@Order(3)
	public void test_getCountryBycountryName() {
		Country country = new Country(1, "india", "delhi");
		String name="india";
		when(countryService.getCountryByName(name)).thenReturn(country);
		
		assertEquals("india", controller.getCountryBycountryName(name).getBody().getCountryName());
		assertEquals(HttpStatus.FOUND, controller.getCountryBycountryName("india").getStatusCode());		
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		Country country = new Country(2, "japan", "Tokoyo");
		
		when(countryService.addCountry(country)).thenReturn(country);
		assertEquals(country, controller.addCountry(country).getBody());
		assertEquals(HttpStatus.CREATED, controller.addCountry(country).getStatusCode());
	}
	@Test
	@Order(5)
	public void test_updateCountry() {
		Country country = new Country(2, "uk", "londan");
		
		Integer id =2;
		when(countryService.getCountryById(id)).thenReturn(country);
		when(countryService.updateCountry(country)).thenReturn(country);
		
		assertEquals(2,controller.upatedCountry(id, country).getBody().getId());
		assertEquals("uk", controller.upatedCountry(id, country).getBody().getCountryName());
		assertEquals("londan", controller.upatedCountry(id, country).getBody().getCountryCaptial());
		assertEquals(country, controller.upatedCountry(id,country).getBody());
		assertEquals(HttpStatus.OK, controller.upatedCountry(id,country).getStatusCode());		
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		Country country = new Country(2, "uk", "londan");
		
		Integer id=2;
		when(countryService.getCountryById(id)).thenReturn(country);
		//verify(countryService,times(1)).deleteCountry(country);
		
		assertEquals(HttpStatus.OK, controller.deleteCountryById(id).getStatusCode());
		
		
		
		
	}
	
	
}