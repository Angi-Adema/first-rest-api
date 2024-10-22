// We build a new REST API in here that will GET all the details of all the surveys.
package com.in28minutes.springboot.first_rest_api.survey;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// Since this is a REST API we add @RestController.
@RestController
public class SurveyResource {
	
	// What do we want to do? We want to make use of the SurveyService.
	private SurveyService surveyService;
	
	// We are using contructor injection with Spring so we create a constructor.
	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}

	// We want to build a /surveys and when this URI is visited we want to return all the surveys back. 
	// retrieveAllSurveys() does not exist yet, we create the method within SurveyService.java.
	// We get an error when visiting localhost:8080/surveys because we have not mapped this to the URL. Use @RequestMapping and pass in the URI.
	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys() {
		return surveyService.retrieveAllSurveys();
	}
	
	// Create a GET request to view a specific survey from id. Use URL: localhost:8080/surveys/Survey1
	@RequestMapping("/surveys/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable String surveyId) {
		// When we enter a survey id that does not exist, we are still getting a status 200. To fix this, we extract to local variable.
		//return surveyService.retrieveSurveyById(surveyId);
		
		Survey survey = surveyService.retrieveSurveyById(surveyId);
		
		// Check if survey is null.
		if (survey == null)
			// Throw a new response to this exception.
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return survey;
	}


}
