// Creating Command Line Runner and playing with JPA in here provides a lot of options.
package com.in28minutes.springboot.first_rest_api.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// We want Spring to manage this so we use @Component.
@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {   // CLR is an interface used to indicate that a bean should run when it is contained within a SpringApplication.

	// Implement logger.
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	// Make use of the UserRepository.
	private UserDetailsRepository repository;
	
	// Utilize constructor injection.
	public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
		super();
		this.repository = repository;
	} 
	
	@Override
	public void run(String... args) throws Exception {
		// Use logger to print the args coming in. Anything written in here will be executed at startup.
		// logger.info(args.toString());
		
		// To see complete details of toString() use below.
		//logger.info(Arrays.toString(args));
		
		// Write the code to insert new users.
		repository.save(new UserDetails("Angi", "Dev"));
		repository.save(new UserDetails("Dan", "Manager"));
		repository.save(new UserDetails("Dan", "Contractor"));
		
		// List all users in the repository. 
		//List<UserDetails> users = repository.findAll();
		
		// Instead of printing all the users. We want to only print users with a certain role. See list of all the findBys.
		List<UserDetails> users = repository.findByRole("Dev");
		
		// Do something with each user like logging them for example.
		users.forEach(user -> logger.info(user.toString()));
	} 

}
