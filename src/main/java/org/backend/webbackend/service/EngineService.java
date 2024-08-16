package org.backend.webbackend.service;

import org.backend.webbackend.api.model.engine.Engine;
import org.springframework.stereotype.Service;

@Service
public class EngineService {
    private Engine engine;

    public EngineService() {
        this.engine = new Engine();
    }

    public String parseCommand(String command, String sessionId) {
        return engine.parseCommand(command, sessionId);
    }

    public void startSession(String sessionId) {
        engine.newSession(sessionId);
    }

    public void endSession(String sessionId) {
        engine.endSession(sessionId);
    }
}
