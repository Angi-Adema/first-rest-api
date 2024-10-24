package com.in28minutes.springboot.first_rest_api.survey;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

// Launch up entire Spring Boot application using @SpringBootTest
@SpringBootTest
public class SurveyResourceIT {

	// We want to test the GET method for finding a specific question.
	// Where do we want to fire a request to? This specific URL: http://localhost:8080/surveys/Survey1/questions/Question1
	
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

						""";   // Triple quotes is any text block you want where you can format how you want and work with any piece of text. Called "text block".
	
	// If you want to fire a REST API request we make use of a REST template. Use this to fire the specific request to a URL.
	TestRestTemplate template;

	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() {

	}
}
