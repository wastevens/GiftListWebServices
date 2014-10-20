package com.dstevens.rest.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@RequestMapping(value="/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable int id) {
		Optional<User> findUser = userRepository.findUser(new UserIdentifier(id));
		if (findUser.isPresent()) {
			return findUser.get();
		}
		return new User(new UserIdentifier(333), "no user found");
	}
	
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestParam("email") String email) {
		return userRepository.createUser(email);
    }
    
    
}