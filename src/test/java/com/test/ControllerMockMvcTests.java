package com.test;

import static org.assertj.core.api.Assertions.contentOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.Controller.CountryController;
import com.test.model.Country;

@TestMethodOrder(value = OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest(classes =ControllerMockMvcTests.class)
@ComponentScan(basePackages = "com.test")
public class ControllerMockMvcTests {

	@Autowired
	private	MockMvc mockMvc;
	

	@Mock
	private CountryService countryService;
	
  @InjectMocks
  private CountryController controller;

  @BeforeEach
  public void setUp()
  {    mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
  }
  
  @Test
  @Order(1)
  public void test_getAllCountry() throws Exception {
	  
	  List<Country> allcountries = new ArrayList<>();
		allcountries.add(new Country(1, "india", "delhi"));
		allcountries.add(new Country(2, "usa", "washington"));
	  
	  when(countryService.getAllCountries()).thenReturn(allcountries);
	  
	 
	  MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/getcountries");
	  ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
	  MvcResult result = perform.andReturn();
	  MockHttpServletResponse response = result.getResponse();
	  int status = response.getStatus();
	  assertEquals(status, 302);
	  
  
	  mockMvc.perform(get("/getcountries"))
	  .andExpect(status().isFound())
      .andDo(print());

  }
  
  @Test
  @Order(2)
  public void test_getCountrybyID() throws Exception{
   
	  Country country = new Country(1, "india", "delhi");
      int countryID=1;
     when(countryService.getCountryById(countryID)).thenReturn(country);
     
       mockMvc.perform(get("/getcountries/{id}",countryID))
       .andExpect(status().isFound())
      .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("india"))
      .andExpect(MockMvcResultMatchers.jsonPath(".countryCaptial").value("delhi"))
       .andDo(print());
                        
  }

  @Test
  @Order(3)
   public void test_getCountrybyName() throws Exception{
	  
	  Country country = new Country(1, "india", "delhi");
       String countryName= "india";
      
       when(countryService.getCountryByName(countryName)).thenReturn(country);
      
      mockMvc.perform(get("/getcountries/countryName").param("countryName", countryName))
       .andExpect(status().isFound())
       .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
       .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("india"))
       .andExpect(MockMvcResultMatchers.jsonPath(".countryCaptial").value("delhi"))
       .andDo(print());
  }
  
  
  @Test
  @Order(4)
  public void test_addCountry() throws Exception {
	  
	 Country country = new Country(3, "Germany", "Berlin");
	
	 ObjectMapper mapper = new ObjectMapper();
	 String jsoncountry = mapper.writeValueAsString(country);
	 
	 when(countryService.addCountry(country)).thenReturn(country);
	 
	 mockMvc.perform(post("/addcountry")
			 .content(jsoncountry)
			 .contentType(MediaType.APPLICATION_JSON))
	         .andExpect(status().isCreated())
	         .andDo(print());
	       	  
  }
  
  @Test
  @Order(5)
  public void test_updateCountry() throws Exception {
	
	  Country country = new Country(3, "UK", "London");
	  Integer countryID=3;

	  when(countryService.getCountryById(countryID)).thenReturn(country);
	  when(countryService.updateCountry(country)).thenReturn(country);
	  
	  ObjectMapper mapper = new ObjectMapper();
	  String jsonbody = mapper.writeValueAsString(country);
	 
	  mockMvc.perform(put("/updatecountry/{id}",countryID)
			  .content(jsonbody)
			  .contentType(MediaType.APPLICATION_JSON))
	          .andExpect(status().isOk())
	          .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("UK"))
	        .andExpect(MockMvcResultMatchers.jsonPath(".countryCaptial").value("London"))		  
	          .andDo(print());  
  }
  
 @Test
 @Order(6)
public void test_deleteCountry() throws Exception {
	 Country country = new Country(3, "UK", "London");
	  Integer countryID=3;
	 
	  when(countryService.getCountryById(countryID)).thenReturn(country);
	  mockMvc.perform(delete("/deletecountry/{id}",countryID))
	  .andExpect(status().isOk()).andDo(print());
	 
 }

}
