package com.in28minutes.springboot.first_rest_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// The entire Spring context is launched up using @SpringBootTest.
@SpringBootTest
class FirstRestApiApplicationTests {

	@Test
	void contextLoads() {
	}

}

// - Find which specific method in SurveyResource.java you want to write a test for. We start a test for the GET request to find a specific question.
// - We call the URL localhost:8080/surveys/Survey1/questions/Question1 and see the Question1 in the browser but run a test to check if that response is what is coming back.
// - To launch this URL, we have to launch the entire Spring context. We will launch up the Spring context, fire a request to the URL in the line above and check if the response coming back matches 
//   what we see in the browser.
