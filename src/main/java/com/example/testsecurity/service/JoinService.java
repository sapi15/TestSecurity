package com.example.testsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;

@Service
public class JoinService {
	
	@Autowired										// 필드 주입 방식. (이것보단 생성자 주입방식 권장.)
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	/**
	 * 회원 가입 처리.
	 * @param joinDTO
	 */
	public void joinProcess(JoinDTO joinDTO) {

		/**
		 * 회원가입 처리전, 유저 중복 체크. (중복 검증)
		 */
//		boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
//		if(isUser) {
//			return;
//		}
		
		
		/**
		 * DTO(form에서 보낸 데이터) -> Entity(DB에서 사용하는 데이터)로 값 세팅.
		 */
		UserEntity data = new UserEntity();
		data.setUsername(joinDTO.getUsername());
		data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));		// 암호화. 비밀번호는 필수적으로 암호화 처리를 해줘야 한다.
		data.setRole("ROLE_ADMIN");													// 접두어인 ROLE에 role 값을 붙이면 된다. ex) ROLE_ADMIN, ROLE_USER ...
		
		System.out.println("data->"+data);

		userRepository.save(data);
	}

}
