// First integration test.
package com.in28minutes.springboot.first_rest_api.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

// Launch up entire Spring Boot application using @SpringBootTest and configure to run on a random port so we do not use a hard coded port that might have other apps running on them. (Instead of 8080)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {
	
	// The URL we want to send the request to has this URI as we will store this as
	// a constant. We will fire a request below to this specific Question URL.
	private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
	
	@Autowired
	private TestRestTemplate template;

	// We want to test the GET method for finding a specific question.
	// Where do we want to fire a request to? This specific URL:
	// http://localhost:RANDOM_PORT/surveys/Survey1/questions/Question1

	// We want to check if the response matches this:
	String str = """

						{
			  "id": "Question1",
			  "description": "Most Popular Cloud Platform Today",
			  "options": [
			    "AWS",
			    "Azure",
			    "Google Cloud",
			    "Oracle Cloud"
			  ],
			  "correctAnswer": "AWS"
			}

						"""; // Triple quotes is any text block you want where you can format how you want
								// and work with any piece of text. Called "text block".

	// If you want to fire a REST API request we make use of a REST template. Use
	// this to fire the specific request to a URL and get a response back.
	// TestRestTemplate already supplies the beginning part of the URL
	// (localhost:RANDOM_PORT) we just need to add in the URI we have created
	// (/surveys/Survey1/questions/Question1)
	// To make use of this, we add in @Autowired
	

	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {

		// We want to send a GET request to the above URL.
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);
		
		// Check expected response. This is what we copied from the console log as the response body. Makes the test very complex checking against JSON strings, better to use JsonAssert.
		//String expectedResponse = 
				//"""
				 //{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
				 
				//""";
		
		// Add our own expected response different from the actual response.
		String expectedResponse = 
				"""
				 {
					 "id":"Question1",
					 "description":"Most Popular Cloud Platform Today",
					 "correctAnswer":"AWS"
				 }
				 
				""";
		
		// First check that the status of the response is a successful 200.
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		
		// Then check if the returned content type is JSON: [Content-Type:"application/json", there may be several headers with "Content-Type" so we add .get(0). Params expected first then actual.
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
		
		// Add an assert. We are not comparing all attributes, only those specified above.
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

		// Whenever we write unit tests, asserts are the most important part and we need to identify what to check for in the response.
		// Since the two strings were fairly identical, we added .trim() to the assertEquals to see if there might be an extra space somewhere and adding this made the test pass.
		//assertEquals(expectedResponse.trim(), responseEntity.getBody());   // commented out when we added the JSONAssert above.
		
		// Get the response body and the headers of the response.
		//System.out.println(responseEntity.getBody());
		//System.out.println(responseEntity.getHeaders());
	}
}

// Below is the response that we initially got after adding in the first assertEquals in testing the specific question. We removed the <> and compared just the two strings and saw they were fairly identical.

//org.opentest4j.AssertionFailedError: expected: < 

//{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
//{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}>
//> but was: <
//[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Thu, 24 Oct 2024 21:52:46 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]







