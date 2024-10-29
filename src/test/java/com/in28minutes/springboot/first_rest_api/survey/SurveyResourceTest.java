// First unit test. Unit test files end in 'Test' and integration test files end in 'IT'.
package com.in28minutes.springboot.first_rest_api.survey;

import java.awt.PageAttributes.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// We only want to launch up a web context with the specific resource we are trying to test. (SurveyResource) We also only want to mock out the specific method we wish to test.
// This is easily done using Spring's @WebMvcTest framework.
@WebMvcTest(controllers = SurveyResource.class)
class SurveyResourceTest {

	// We are mocking the method surveyService.retrieveSpecificSurveyQuestion method since we are calling that method.
	// Then launch up only the Spring context with SurveyResource and fire request to the specific URL "http://localhost:8080/surveys/Survey1/questions/Question1" with the type GET.
	
	// Since SurveyService contains a dependency of surveyService, we cannot launch it up alone. We need to mock it out using @MockBean.
	@MockBean
	private SurveyService surveyService;
	
	// We need to first fire a request to the application we have launched up.
	// This is autocreated and available as a Bean so we use @Autowired.
	@Autowired
	private MockMvc mockMvc;
	
	// Create a constant for the request URL.
	private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";
	
	@Test
	// Grab name of method from SurveyResource.java
	void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
		
		// Before we go to mockMvd, we must have a RequestBuilder. We need to send a request to this specific URL: http://localhost:8080/surveys/Survey1/questions/Question1
		// We want a JSON response back so we add in .accept().
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(org.springframework.http.MediaType.APPLICATION_JSON);
		
		// To do a .perform, we must have a RequestBuilder. CTRL + 1 to add to local variable.
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		// Confirm result being returned.
		System.out.println(mvcResult.getResponse().getContentAsString());
		
		// Status comes back as 404 because in SurveyResource.java we are using a mock of surveyService but we did not set a value for specific method call. (Question question) Error handling says to respond 404.
		System.out.println(mvcResult.getResponse().getStatus());  
		
		// We do not want to test for a 404 scenario though. Need to make the method Mock -> surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId) become a specific bean.
		
	}

}
