package org.backend.webbackend.api.model.engine;

import org.backend.webbackend.api.controller.EngineController;

import static org.backend.webbackend.utility.Timestamp.printWithTimestamp;

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

            if (!engine.sessions.isEmpty()) {
                System.out.println();
                printWithTimestamp("Session Check: " + engine.sessions.size() + " session(s)");
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
}
