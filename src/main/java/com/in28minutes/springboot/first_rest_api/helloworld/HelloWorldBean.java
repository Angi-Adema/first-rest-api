package com.in28minutes.springboot.first_rest_api.helloworld;

public class HelloWorldBean {
	
	private String message;

	public HelloWorldBean(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [message=" + message + "]";
	}
	
	
}

// The message "Hello World" is printed to the browser because we have a String message in here that is pulling the message from the Bean in the parent class and returning it to the browser.

