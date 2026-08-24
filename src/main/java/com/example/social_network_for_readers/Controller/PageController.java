package com.example.social_network_for_readers.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
int x = 0;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("x", this.x);

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
