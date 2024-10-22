package com.in28minutes.springboot.first_rest_api.survey;

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
}
