package org.backend.webbackend.api.controller;

import jakarta.servlet.http.HttpSession;
import org.backend.webbackend.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class EngineController {
    static ArrayList<HttpSession> sessions;
    private EngineService engineService;

    @Autowired
    public EngineController(EngineService engineService) {
        this.engineService = engineService;
        sessions = new ArrayList<>();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/engine")
    public String getResponse(@RequestParam String session, @RequestParam String command) {
        return engineService.parseCommand(command, session);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/start-session")
    public String startSession(HttpSession session) {
        sessions.add(session);
        engineService.startSession(session.getId());
        return "Session started with ID: " + session.getId();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/end-session")
    public String endSession(@RequestParam String session) {
        // End the JAR process and invalidate the session
        engineService.endSession(session);
        invalidateSession(session);
        return "Session ended";
    }

    public static boolean invalidateSession(String sessionId) {
        for (HttpSession session : sessions) {
            if (session.getId().equals(sessionId)) {
                session.invalidate();
                sessions.remove(session);
                return true;
            }
        }
        return false;
    }
}
