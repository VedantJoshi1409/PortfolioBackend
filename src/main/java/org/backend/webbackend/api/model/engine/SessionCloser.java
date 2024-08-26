package org.backend.webbackend.api.model.engine;

import org.backend.webbackend.api.controller.EngineController;

public class SessionCloser extends Thread {
    private Engine engine;
    private long checkTime;

    public SessionCloser(Engine engine, long checkTime) {
        this.engine = engine;
        this.checkTime = checkTime;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(checkTime);
            } catch (InterruptedException e) {}

            System.out.println("\nSession Check: " + engine.sessions.size() + " session(s)");
            for (JarOpener session : engine.sessions.values()) {
                if (session.sessionCheck()) {
                    EngineController.invalidateSession(session.sessionId);
                    engine.endSession(session.sessionId);
                }
            }
            System.out.println("\n");
        }
    }
}
