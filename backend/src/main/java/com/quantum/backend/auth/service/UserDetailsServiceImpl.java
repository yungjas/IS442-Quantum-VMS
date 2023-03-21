package com.quantum.backend.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quantum.backend.model.User;
import com.quantum.backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	//email will be used instead of username
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// User user = userRepository.findByUsername(username)
		// 		.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return UserDetailsImpl.build(user);
	}
}
