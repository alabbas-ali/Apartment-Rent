package edu.elearning.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.elearning.model.User;
import edu.elearning.service.UserService;
import edu.elearning.util.CustomErrorType;

@RestController
@RequestMapping("/account")
public class AccountController {
	public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private UserService userService;

	// request method to create a new account by a guest
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User newUser) {
		if (userService.find(newUser.getUsername()) != null) {
			logger.error("username Already exist " + newUser.getUsername());

			ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(
					new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
					HttpStatus.CONFLICT);
			return responseEntity;
		}
		newUser.setRole("USER");

		return new ResponseEntity<User>(userService.save(newUser), HttpStatus.CREATED);
	}

	// this is the login api/service
	@RequestMapping("/login")
	public Principal user(Principal principal) {
		logger.info("user logged " + principal);
		return principal;
	}

	@GetMapping(path = "/ping")
	public ResponseEntity<Map<String, String>> ping() {
		Map<String, String> map = new HashMap<>();
		map.put("ping", "pong");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}