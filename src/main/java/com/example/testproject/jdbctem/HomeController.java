package com.example.testproject.jdbctem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/123")
    public String home() {
        return "home";
    }


}
