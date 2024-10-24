package com.in28minutes.springboot.first_rest_api.user;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

// You can configure the path segment under which this resource is to be exported. Using @RepositoryRestResource(path).
public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails, Long>{  // Pass in UserDetails and the type of the id field. Visit localhost:8080 and see the links based on UserDetails.
	
	// Search users by role.
	List<UserDetails> findByRole(String role);

}

// If you visit localhost:8080/userDetailses, it will print in JSON format all the users with their info and URL info.
// localhost:8080/userDetailses?sort=name will sort all the users by name. Change name to role to sort by role.
// If you want each page to contain only one user, use localhost:8080/userDetailses?size=1 will also provide links to the other users.
// Open Talend in the browser to add, update, delete or get the details of the users. We do not have to code the logic to do this since it uses Spring REST.
