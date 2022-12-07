package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.test.model.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = ControllerIntegrationTests.class)
public class ControllerIntegrationTests {

	public String getUrl(String url) {

		return "http://localhost:7410/" + url;
	}

	@Test
	@Order(1)
	public void test_getAllCountriesIntergationTest() throws JSONException {

		String Expected = "[\r\n" + "    {\r\n" + "        \"id\": 1,\r\n" + "        \"countryName\": \"india\",\r\n"
				+ "        \"countryCaptial\": \"delhi\"\r\n" + "    },\r\n" + "    {\r\n" + "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"london\",\r\n" + "        \"countryCaptial\": \"uk\"\r\n" + "    }\r\n"
				+ "]";

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl("/getcountries"), String.class);

		System.out.println(responseEntity.getBody());
		System.out.println(responseEntity.getStatusCode());

		JSONAssert.assertEquals(Expected, responseEntity.getBody(), false);
	}

	@Test
	@Order(2)
	public void test_getCountryById() throws JSONException {

		String Expected = "{\r\n" + "    \"id\": 1,\r\n" + "    \"countryName\": \"india\",\r\n"
				+ "    \"countryCaptial\": \"delhi\"\r\n" + "}";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl("/getcountries/1"), String.class);

		System.out.println(responseEntity.getBody());
		System.out.println(responseEntity.getStatusCode());

		JSONAssert.assertEquals(Expected, responseEntity.getBody(), false);

	}

	@Test
	@Order(3)
	public void test_getCountryByName() throws JSONException {

		String Expected = "{\r\n" + "    \"id\": 1,\r\n" + "    \"countryName\": \"india\",\r\n"
				+ "    \"countryCaptial\": \"delhi\"\r\n" + "}";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity(getUrl("/getcountries/countryName?countryName=india"), String.class);

		System.out.println(responseEntity.getBody());
		System.out.println(responseEntity.getStatusCode());

		JSONAssert.assertEquals(Expected, responseEntity.getBody(), false);

	}

	@Test
	@Order(4)
	public void test_addCountry() throws JSONException {

		String expected = "{\r\n" + "    \"id\": 3,\r\n" + "    \"countryName\": \"usa\",\r\n"
				+ "    \"countryCaptial\": \"washington\"\r\n" + "}";

		Country country = new Country(3, "usa", "washington");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<Country> httpEntity = new HttpEntity<Country>(country, headers);

		ResponseEntity<String> exchange = restTemplate.exchange(getUrl("/addcountry"), HttpMethod.POST, httpEntity,
				String.class);
		System.out.println(exchange.getBody());
		JSONAssert.assertEquals(expected, exchange.getBody(), false);

	}

	@Test
	@Order(5)
	public void test_updateCountryIntegrationTest() throws JSONException {

		Country country = new Country(3, "japan", "tokyo");

		 String Expected="{\r\n"
		 	   		+ "    \"id\": 3,\r\n"
		 	   		+ "    \"countryName\": \"japan\",\r\n"
		 	   		+ "    \"countryCaptial\": \"tokyo\"\r\n"
		 	   		+ "}";  
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		ResponseEntity<String> response = restTemplate.exchange(getUrl("updatecountry/3"), HttpMethod.PUT, request,
				String.class);
		System.out.println(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(Expected, response.getBody(), false);

	}

	@Test
	@Order (6)
	public void test_deleteCountryIntegrationTest() throws JSONException
	{
	     Country country= new Country(3, "japan", "tokyo");
	     
	     String Expected="{\r\n"
	 	   		+ "    \"id\": 3,\r\n"
	 	   		+ "    \"countryName\": \"japan\",\r\n"
	 	   		+ "    \"countryCaptial\": \"tokyo\"\r\n"
	 	   		+ "}";    
	 	   
	     
	   RestTemplate restTemplate =new RestTemplate();
	   
	   HttpHeaders headers=new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Country> request = new HttpEntity<Country>(country ,headers);
	    ResponseEntity<String> response=restTemplate.exchange(getUrl("/deletecountry/3") ,HttpMethod.DELETE,request,String.class);
	    
	    System.out.println(response.getBody());
	    
	     assertEquals(HttpStatus.OK ,response. getStatusCode());
	    JSONAssert.assertEquals(Expected, response.getBody(), false);

	//Directly delete 
	    //  restTemplate.delete(getUrl("/deletecountry/3"));
	}	
	
}