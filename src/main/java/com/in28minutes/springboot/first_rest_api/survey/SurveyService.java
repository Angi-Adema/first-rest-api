package com.in28minutes.springboot.first_rest_api.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

// This is a component we want Spring to manage so we add @Service.
@Service
public class SurveyService {
	// Here we want a static list of all the survey details we want to have managed.
	// Whenever an operation is performed on the survey we want the details saved
	// back to the static list.
	private static List<Survey> surveys = new ArrayList<>();

	// Initialize with some data.
	static {
		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3", "Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

		surveys.add(survey);
	}

	public List<Survey> retrieveAllSurveys() {
		return surveys;
	}

	
	public Survey retrieveSurveyById(String surveyId) {
		
		Predicate<? super Survey> predicate = 
				// Given a survey, we want to check survey.getId() and confirm if it matches the survey passed in. Must use .equals() to compare strings.
				survey -> survey.getId().equals(surveyId);
				
		// Create a stream to filter surveys by Id and find the first survey Id that matches the passed in value (use findFirst() as the Id may not exist at all. 
		// After enter findFirst() CTRL + 1 to assign to local variable. We use 'Optional' as a survey might be returned or not (see info CTRL + click on Optional).
		Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();
		
		// Check if the returned data is empty and if not, return the survey.
		if(optionalSurvey.isEmpty()) return null;
		
		return optionalSurvey.get();
	}


	public List<Question> retrieveAllSurveyQuestions(String surveyId) {
		
		Survey survey = retrieveSurveyById(surveyId);   // Extract retrieveSurveyById to a local variable and call it survey.
		
		// If the survey does not exist then return null back.
		if (survey == null) return null;
		
		// If not null then return the questions back.
		return survey.getQuestions();  
	}


	public Question retrieveSpecificSurveyQuestion(String surveyId, String questionId) {
		
		// Pull in a list of all the survey questions.
		List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);
		
		// Control statement to handle if there are no questions.
		if (surveyQuestions == null) return null;
		
		// If it is not null then we need to loop through the questions via a stream and filter out the question we are trying to retrieve and return the first matching question Id.
		Optional<Question> optionalQuestion = surveyQuestions.stream().filter(q -> q.getId().equalsIgnoreCase(questionId)).findFirst();
		
		// We check if the optionalQuestion is null and return null. Otherwise return the optionalQuestion.
		if (optionalQuestion.isEmpty()) return null;
		
		return optionalQuestion.get();
	}


	public String addNewSurveyQuestion(String surveyId, Question question) {   // Put this back to String from void to return the Id of the question that is generated.
		
		// Get all the questions from the specific survey.
		List<Question> questions = retrieveAllSurveyQuestions(surveyId);
		
		// Add logic to generate an Id for the question rather than having the user input it. We extract these two lines to a new method then refactor inline for lines 98 and 99.
		//SecureRandom secureRandom = new SecureRandom();
		//String randomId = new BigInteger(32, secureRandom).toString();
		
		// Set the random Id into the question.
		question.setId(generateRandomId());
		
		// Add in the question.
		questions.add(question);
		
		// Return the Id that is generated.
		return question.getId();
	}


	private String generateRandomId() {
		SecureRandom secureRandom = new SecureRandom();
		String randomId = new BigInteger(32, secureRandom).toString();
		return randomId;
	}
}
