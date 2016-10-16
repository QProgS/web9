package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Main {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(){
        return new ModelAndView("redirect:/articles");
    }

}
