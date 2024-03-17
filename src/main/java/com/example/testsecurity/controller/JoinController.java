package com.example.testsecurity.controller;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.commons.lang3.StringUtils;

@Controller
public class JoinController {
	
	@Autowired
	private JoinService joinService;



	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public String joinp() {
		
		return "join";
	}

	/**
	 * 회원 가입.
	 * @param joinDTO
	 * @return
	 */
	
	@PostMapping("/joinProc")
	public String joinProcess(JoinDTO joinDTO) {

		System.out.println("joinDTO->"+ StringUtils.join(joinDTO));
		System.out.println("joinDTO->"+ joinDTO.getUsername());
		System.out.println("joinDTO->"+ joinDTO.getPassword());

		joinService.joinProcess(joinDTO);
		
		return "redirect:/login";
	}
	
	
	

}
