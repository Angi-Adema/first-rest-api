package com.in28minutes.springboot.first_rest_api.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

// Even if there are additional spaces in the JSON response compared to the expected, the JSONAssert will ignore things like this and will still pass the test.
class JsonAssertTest {

	@Test
	void jsonAssert_learningBasics() throws JSONException {
		
		// In the exptected response you can configure the important attributes. Don't compare date fields or time stamps with the actualResponse because this may continually be changing.
		String expectedResponse = 
				"""
				 {
					 "id":"Question1",
					 "description":"Most Popular Cloud Platform Today",
					 "correctAnswer":"AWS"
				 }
				 
				""";
		
		// In this actualResponse, the id field is not matching the expected response so the test will run and tell you specifically what is not matching. Will tell you specifically the heading the 
		// difference occurred in.
		String actualResponse = 
				"""
				 {"id":"Question1",
				 "description":"Most Popular Cloud Platform Today",
				 "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
				 "correctAnswer":"AWS"}
				 
				""";
		
		// Have to add throws JSONException to the method name or we will get a JSONAssert error. If we want to compare the expectedResponse and actualResponse with the exact same items to compare use 
		// 'true'. Otherwise if you only want to compare a couple attributes in the expectedResponse against the actualResponse that has more attributes, we use 'false'.
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
	}

}

// "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
