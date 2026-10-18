package com.example.app_medical.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SecureController {

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "Hello " + authentication.getName() + ", you are authenticated!";
    }
}