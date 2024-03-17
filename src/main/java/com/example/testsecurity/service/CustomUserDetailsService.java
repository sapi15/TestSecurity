package com.example.testsecurity.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.testsecurity.dto.CustomUserDetails;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;

/**
 *
 * UserDetailsService
 * Spring Security에서 유저의 정보를 가져오는 인터페이스.
 * Spring Security에서 유저의 정보를 불러오기 위해서 구현해야하는 인터페이스 이다.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{


//	@Resource
//	private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

	@Autowired
	private UserRepository userRepository;



    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userData =  userRepository.findByUsername(username);

		if(userData != null) {
			return new CustomUserDetails(userData);
		}

		return null;
	}



}
