package com.example.testsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping("/user")
    public ModelAndView managerP() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user");

        return mav;
    }
}
