// First unit test. Unit test files end in 'Test' and integration test files end in 'IT'.
package com.in28minutes.springboot.first_rest_api.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// We only want to launch up a web context with the specific resource we are trying to test. (SurveyResource) We also only want to mock out the specific method we wish to test.
// This is easily done using Spring's @WebMvcTest framework.
@WebMvcTest(controllers = SurveyResource.class)
// Since we have brought in Spring security, we want to disable Spring security for the unit test as we do not want security in here at all since we are only testing SurveyService. Use @AutoConfigureMockMvc
@AutoConfigureMockMvc(addFilters = false)   // Disable all the filters in here.
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
		
		// To do a .perform, we must have a RequestBuilder. CTRL + 1 to add to local variable (see it above).
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		// Confirm result being returned.
		//System.out.println(mvcResult.getResponse().getContentAsString());
		
		// Use the assert to confirm results.
		assertEquals(404, mvcResult.getResponse().getStatus());
		
		// Status comes back as 404 because in SurveyResource.java we are using a mock of surveyService but we did not set a value for specific method call. (Question question) Error handling says to respond 404.
		//System.out.println(mvcResult.getResponse().getStatus());  // Coming in as 404
		
		// We do not want to test for a 404 scenario though. Need to make the method Mock -> surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId) become a specific bean.
		
	}
	
	@Test
	// Grab name of method from SurveyResource.java
	void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(org.springframework.http.MediaType.APPLICATION_JSON);
		
		// This is the question we want to see returned in JSON format.
		// We want to return a specific bean back. In this example, Question1. Because this is a unit test, ServeyService.java will not be launched up at all so we copy and paste it here.
		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		
		// We want to mock this method: Mock -> surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId)
		// When the method inside of when below is called, we want to return this question back. Then we can check the response to be sure Question1 is the one coming back using the when method above.
		when(surveyService.retrieveSpecificSurveyQuestion("Survey1", "Question1")).thenReturn(question);
		
		// To do a .perform, we must have a RequestBuilder. CTRL + 1 to add to local variable (see it above).
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse = """
				{
					"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
					"correctAnswer":"AWS"
				}
				
				""";
		
		// Confirm result being returned in the JSON format.
		//{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
		//System.out.println(mvcResult.getResponse().getContentAsString());
		
		// Status comes back as 404 because in SurveyResource.java we are using a mock of surveyService but we did not set a value for specific method call. (Question question) Error handling says to respond 404.
		//System.out.println(mvcResult.getResponse().getStatus()); // Coming in as 200 
		
		// Use the assert to confirm results.
		assertEquals(200, mvcResult.getResponse().getStatus());
		
		// To also check the content of the response, we use JsonAssert.
		JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
		
		
	}

}
