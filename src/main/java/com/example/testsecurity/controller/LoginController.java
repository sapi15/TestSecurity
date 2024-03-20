package com.example.testsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {


	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/page.do")
	public String loginP() {
		
		return "login";
	}
	
	

	
}
