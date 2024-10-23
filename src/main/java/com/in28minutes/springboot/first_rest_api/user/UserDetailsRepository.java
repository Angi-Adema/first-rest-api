package com.in28minutes.springboot.first_rest_api.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{  // Pass in UserDetails and the type of the id field.
	
	// Search users by role.
	List<UserDetails> findByRole(String role);

}
