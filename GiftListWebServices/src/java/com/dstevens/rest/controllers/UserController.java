package com.dstevens.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dstevens.users.User;
import com.dstevens.users.UserIdentifier;
import com.dstevens.users.UserRepository;

@RestController
public class UserController {

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser(@RequestParam(value="email", required=false, defaultValue="aaa") String email) {
    	userRepository.createUser(email);
    	return userRepository.findUser(new UserIdentifier(email)).get();
    }
    
    
}