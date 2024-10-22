package com.in28minutes.springboot.first_rest_api.helloworld;

import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// Since we are building a web application we add @Controller. Commented out when refactored to @RestController. Hold CTRL + click on @RestController we can see that @Controller and @RestBody are encompassed by this.
//@Controller

// Spring MVC will start to look for a view with the specific name "Hello World" and will not return this string directly back to the browser. To return the string we use @ResponseBody.
//@ResponseBody  Commented out as it is encompassed by @RestController.

// When building a REST API you do not want to map it to a view, you want to return the object back as is. This is why we use @RestController.
@RestController
public class HelloWorldResource {
	// Create a simple string of "Hello World"
	
	// Map this to a URL with @RequestMapping and pass in mapping name. In a REST API, we would need to apply this annotation to all the methods in here. To do this we use @RestController above.
	@RequestMapping("/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	// What if we wanted to return a Bean back to the browser? Must create a new class. Change public String hellowWorld() to line below it.
	@RequestMapping("/hello-world-bean")
	//public String helloWorldBean() {
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
}
