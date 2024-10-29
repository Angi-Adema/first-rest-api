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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

// Launch up entire Spring Boot application using @SpringBootTest and configure to run on a random port so we do not use a hard coded port that might have other apps running on them. (Instead of 8080)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

	// The URL we want to send the request to has this URI as we will store this as
	// a constant. We will fire a request below to this specific Question URL.
	private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

	// Get all questions using a GET request for a generic question.
	private static String GENERIC_QUESTIONS_URL = "/surveys/Survey1/questions";
	
	// Get all surveys.
	private static String GENERIC_SURVEYS_URL = "/surveys";
	
	// Get one specific survey.
	private static String SPECIFIC_SURVEY_URL = "/surveys/Survey1";

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

	// Test to retrieve a specific question.
	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {

		// We want to send a GET request to the above URL.
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

		// Check expected response. This is what we copied from the console log as the
		// response body. Makes the test very complex checking against JSON strings,
		// better to use JsonAssert.
		// String expectedResponse =
		// """
		// {"id":"Question1","description":"Most Popular Cloud Platform
		// Today","options":["AWS","Azure","Google Cloud","Oracle
		// Cloud"],"correctAnswer":"AWS"}

		// """;

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

		// Then check if the returned content type is JSON:
		// [Content-Type:"application/json", there may be several headers with
		// "Content-Type" so we add .get(0). Params expected first then actual.
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Add an assert. We are not comparing all attributes, only those specified
		// above.
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

		// Whenever we write unit tests, asserts are the most important part and we need
		// to identify what to check for in the response.
		// Since the two strings were fairly identical, we added .trim() to the
		// assertEquals to see if there might be an extra space somewhere and adding
		// this made the test pass.
		// assertEquals(expectedResponse.trim(), responseEntity.getBody()); // commented
		// out when we added the JSONAssert above.

		// Get the response body and the headers of the response.
		// System.out.println(responseEntity.getBody());
		// System.out.println(responseEntity.getHeaders());
	}

	// Test for retrieving all questions.
	@Test
	void retrieveAllSurveyQuestions_basicScenario() throws JSONException {

		// We want to send a GET request to the above URL.
		ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);

		// Check expected response. This is what we copied from the browser once got response running the app (localhost:8080/surveys/Survey1/questions. 

		// We would not want to check all these details (id, description, options, correctAnswer), we would just want to check the most important items.
		// Does the response contain three events and do the ids match?
		String expectedResponse = 
				"""
				
						[
						  {
						    "id": "Question1"
						  },
						  {
						    "id": "Question2"
						  },
						  {
						    "id": "Question3"
						  }
						]

				""";

		// First check that the status of the response is a successful 200.
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Then check if the returned content type is JSON:
		// [Content-Type:"application/json", there may be several headers with
		// "Content-Type" so we add .get(0). Params expected first then actual.
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Add an assert. We are not comparing all attributes, only those specified
		// above.
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}
	
	// Test to retrieve all surveys.
	@Test
	void retrieveAllSurveys_basicScenario() throws JSONException {

		// We want to send a GET request to the above URL.
		ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_SURVEYS_URL, String.class);

		// Check expected response. This is what we copied from the browser once got response running the app (localhost:8080/surveys/Survey1/questions. 

		// We would not want to check all these details (id, description, options, correctAnswer), we would just want to check the most important items.
		// Does the response contain three events and do the ids match?
		String expectedResponse = 
				"""
				
						[
						  {
						    "id": "Survey1"
						  }
						]

				""";

		// First check that the status of the response is a successful 200.
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Then check if the returned content type is JSON:
		// [Content-Type:"application/json", there may be several headers with
		// "Content-Type" so we add .get(0). Params expected first then actual.
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Add an assert. We are not comparing all attributes, only those specified
		// above.
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}
	
	// Test to retrieve a specific survey.
	@Test
	void retrieveSurveyById_basicScenario() throws JSONException {

		// We want to send a GET request to the above URL.
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_SURVEY_URL, String.class);

		// Check expected response. This is what we copied from the browser once got response running the app (localhost:8080/surveys/Survey1/questions. 

		// We would not want to check all these details (id, description, options, correctAnswer), we would just want to check the most important items.
		// Does the response contain three events and do the ids match?
		String expectedResponse = 
				"""
					{
						"id": "Survey1"
					}

				""";

		// First check that the status of the response is a successful 200.
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Then check if the returned content type is JSON:
		// [Content-Type:"application/json", there may be several headers with
		// "Content-Type" so we add .get(0). Params expected first then actual.
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Add an assert. We are not comparing all attributes, only those specified
		// above.
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}
	
	// Write test for the POST request of adding a new survey question.
	// URL we are firing a request to: http://localhost:8080/surveys/Survey1/questions
	// Needs to be a POST request.
	// We also need to set the headers: Content-Type application/json  (Headers found in running post request in Talend)
	// Check if we got a successful response. 
	// As well as get the Location: header in Talend (http://localhost:8080/surveys/Survey1/questions/2327916144).
	@Test
	void addNewSurveyQuestion_basicScenario() {
		
		// Response body:
		String requestBody = """
				{
	  				"description": "Your Favorite Language",
	  				"options": [
	    			"Java",
	    			"Python",
	    			"JavaScript",
	    			"Ruby"
	  				],
	  				"correctAnswer": "Java"
				}

		  """;
		// URL we are firing a request to: http://localhost:8080/surveys/Survey1/questions
		// Needs to be a POST request.
		// RequestBody
		
		// We also need to set the headers: Content-Type application/json  (Headers found in running post request in Talend)
		// Configuration for request header.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		// Combine both headers with the request body.
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
		
		// Can use template.postForEntity here for the POST request, however when we have complex things like setting headers etc. it is more convenient to use template.exchange.
		// Fire a POST request to the above URL and make use of this HttpEntity. 
		// First param, we already have the GENERIC_QUESTIONS_URL so we will make use of it here for initial GET request to the POST request, second param is what kind of request, third is httpEntity and
		// fourth is type of response.
		ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);
		
		// Now that we have configured the headers in here, lets do a sysout.
		//System.out.println(responseEntity.getHeaders());
		
		// Write asserts to check if we got a successful response. 
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		
		// As well as get the Location: header in Talend (http://localhost:8080/surveys/Survey1/questions/2327916144).
		
		// Initial response shows the below headers where the test ran on a different port as well as assigned its own id. We write an assert to see if it contains something like this.
		//[Location:"http://localhost:52940/surveys/Survey1/questions/3451927084", Content-Length:"0", Date:"Tue, 29 Oct 2024 19:00:54 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
		//null
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);  // To write POST/DELETE together, we extracted to local variable & called it locationHeader.
		assertTrue(locationHeader.contains("/surveys/Survey1/questions"));
		
		// The test as is written above is causing the test to fail because there are only three question in the survey and this post request is creating a fourth question. We need to negate this side effect
		// by immediately deleting the question after it is created. We need to combine the test for both the POST and the DELETE together.
		
		// Assign a DELETE request to locationHeader.
		template.delete(locationHeader);

	}
}

// Below is the response that we initially got after adding in the first assertEquals in testing the specific question. We removed the <> and compared just the two strings and saw they were fairly identical.

//org.opentest4j.AssertionFailedError: expected: < 

//{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
//{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}>
//> but was: <
//[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Thu, 24 Oct 2024 21:52:46 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]

// When writing tests, we cannot guarantee the order the tests will be run in so we have to be sure each test has no side effects.
