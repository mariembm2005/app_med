package com.example.app_medical.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
int x = 0;



    @GetMapping("/")
    public String home(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }

        return "home";
    }

    @GetMapping("/shelves")
    public String shelves() {
        return "shelves";
    }

    @GetMapping("/discuss")
    public String discuss() {
        return "discuss";
    }

    @GetMapping("/discover")
    public String discover() {
        return "discover";
    }

    @GetMapping("/trends")
    public String trends() {
        return "trends";
    }


    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
