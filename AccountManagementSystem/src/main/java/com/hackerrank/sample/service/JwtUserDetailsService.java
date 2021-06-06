package com.hackerrank.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hackerrank.sample.exception.AuthenticationException;
import com.hackerrank.sample.jwt.model.SecurityUser;
import com.hackerrank.sample.model.User;
import com.hackerrank.sample.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new AuthenticationException("User not found with username: " + username);
		}
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return new SecurityUser(user);
	}
}