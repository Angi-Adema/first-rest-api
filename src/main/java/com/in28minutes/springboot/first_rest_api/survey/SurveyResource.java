// We build a new REST API in here that will GET all the details of all the surveys.
package com.in28minutes.springboot.first_rest_api.survey;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
			// Throw correct error message if the Id does not exist.
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return survey;
	}
	
	// Create a GET request to retrieve all the questions from a survey. Use URL: localhost:8080/surveys/Survey1/questions.
	@RequestMapping("/surveys/{surveyId}/questions")
	
	// Since we are not looking for a survey, we want to return a List of all the questions of the survey inquired about back.
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId) {
		
		List<Question> questions = surveyService.retrieveAllSurveyQuestions(surveyId);  // Create the retrieveAllSurveyQuestions method.
		
		// Check if the questions are null.
		if (questions == null)
			// Throw a new error if the questions do not exist.
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return questions;
	}

	// Create a GET request to retrieve a specific survey question. Use URL: localhost:8080/surveys/Survey1/questions/Question1
	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	
	// Since we are not looking for a survey, we want to return a List of all the questions of the survey inquired about back.
	public Question retrieveSpecificSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		
		Question question = surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId);
		
		// Check if the questions are null.
		if (question == null)
			// Throw a new error if the questions do not exist.
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return question;
	}
	
	// Create a POST request to create a new survey question. Map the question to a specific survey Id. Use URL: localhost:8080/surveys/.
	@RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
	
	// How can we send the details of the question? We can send it as part of the request body.
	public void addNewSurveyQuestion(@PathVariable String surveyId, 
			@RequestBody Question question) {   // @RequestBody captures the request body.
		
			// We want to take this question and add it to a specific survey.
			surveyService.addNewSurveyQuestion(surveyId, question);
		
	}
}
