package com.in28minutes.springboot.first_rest_api.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// We want this to be an entity that is managed by JPA so we use @Entity
@Entity
public class UserDetails {  // We want to add users, delete users, etc.

	// Store a few user details.
	// To have JPA generate the id for us we add @Id and @Generated. Spring uses this to generate the USER_DETAILS table.
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String role;
	
	// To populate the User details in UserDetailsCommandLineRunner.java, we must have an empty constructor.
	public UserDetails() {
		
	}
	
	// Generate a constructor to initialize the fields.
	public UserDetails(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}

	// Generate getter methods.
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	// Generate the toString() method.
	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", name=" + name + ", role=" + role + "]";
	}
	

}
