package com.dstevens.rest.controllers;

import java.util.List;
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
@RequestMapping(value="/user")
public class UserController {

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable int id) {
		Optional<User> findUser = userRepository.findUser(new UserIdentifier(id));
		if (findUser.isPresent()) {
			return findUser.get();
		}
		throw new IllegalArgumentException("No user found with id " + id);
	}
	
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestParam("email") String email) {
		return userRepository.createUser(email);
    }
    
    @RequestMapping(value="/{id}/friend", method = RequestMethod.POST)
    @ResponseBody
    public User addFriend(@PathVariable int id, @RequestParam("friendId") int friendId) {
    	Optional<User> user = userRepository.findUser(new UserIdentifier(id));
    	Optional<User> friend = userRepository.findUser(new UserIdentifier(friendId));
    	if(user.isPresent()) {
    		if (friend.isPresent()) {
    			user.get().addFriend(new UserIdentifier(friendId));
    			userRepository.saveUser(user.get());
    		}
    		return user.get();
    	}
    	throw new IllegalArgumentException("No user found with id " + id);
    }
    
	@RequestMapping(value="/{id}/friend", method = RequestMethod.GET)
	@ResponseBody
	public List<UserIdentifier> getFriends(@PathVariable int id) {
		Optional<User> user = userRepository.findUser(new UserIdentifier(id));
		if (user.isPresent()) {
			return user.get().getFriends();
		}
		throw new IllegalArgumentException("No user found with id " + id);
	}
	
	@RequestMapping(value="/{id}/friend/{friendId}", method = RequestMethod.GET)
	@ResponseBody
	public User getFriends(@PathVariable int id, @PathVariable int friendId) {
		Optional<User> user = userRepository.findUser(new UserIdentifier(id));
		if (user.isPresent()) {
			UserIdentifier friendIdentifier = new UserIdentifier(friendId);
			if (user.get().getFriends().contains(friendIdentifier)) {
				Optional<User> friend = userRepository.findUser(friendIdentifier);
				if(friend.isPresent()) {
					return friend.get();
				}
			}
		}
		throw new IllegalArgumentException("No user found with id " + id);
	}
    
    
    
    
}