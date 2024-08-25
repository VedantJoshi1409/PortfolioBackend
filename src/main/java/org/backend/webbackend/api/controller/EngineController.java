package org.backend.webbackend.api.controller;

import jakarta.servlet.http.HttpSession;
import org.backend.webbackend.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EngineController {
    private EngineService engineService;

    @Autowired
    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/engine")
    public String getResponse(HttpSession session, @RequestParam String command) {
        return engineService.parseCommand(command, session.getId());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/start-session")
    public String startSession(HttpSession session) {
        engineService.startSession(session.getId());
        return "Session started with ID: " + session.getId();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/end-session")
    public String endSession(HttpSession session) {
        // End the JAR process and invalidate the session
        engineService.endSession(session.getId());
        session.invalidate();
        return "Session ended";
    }
}
