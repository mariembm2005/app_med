package com.example.app_medical.Controller;

import com.example.app_medical.dto.ChatRequestDTO;
import com.example.app_medical.dto.ChatResponseDTO;
import com.example.app_medical.service.ChatbotService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final ChatbotService service;

    public ChatbotController(ChatbotService service) {
        this.service = service;
    }

    @PostMapping("/message")
    public ChatResponseDTO message(@RequestBody ChatRequestDTO req) {
        return service.repondreEtEnregistrer(req);
    }
}
