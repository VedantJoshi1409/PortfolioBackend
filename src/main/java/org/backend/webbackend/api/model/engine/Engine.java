package org.backend.webbackend.api.model.engine;

import java.util.HashMap;

import static org.backend.webbackend.utility.Timestamp.printWithTimestamp;

public class Engine {
    public final String PATH = "Bitterfish.jar";
    public HashMap<String, JarOpener> sessions;
    SessionCloser sessionCloser;

    public Engine() {
        sessions = new HashMap<>();
        sessionCloser = new SessionCloser(this, 15000);
        sessionCloser.start();
    }

    public void newSession(String sessionId) {
        printWithTimestamp("New session: " + sessionId);
        if (!sessions.containsKey(sessionId)) {
            sessions.put(sessionId, new JarOpener(PATH, sessionId));
        }
        printWithTimestamp("Process Created: " + (sessions.get(sessionId) != null));
    }

    public void endSession(String sessionId) {
        printWithTimestamp("Session ended: " + sessionId);
        sessions.get(sessionId).kill();
        printWithTimestamp("Process Killed");
        sessions.remove(sessionId);
    }

    public String parseCommand(String command, String sessionId) {
        JarOpener jarOpener = sessions.get(sessionId);
        String[] blocks = command.split(" ");

        switch (blocks[0]) {
            case "refresh" -> jarOpener.refresh();
            case "uci" -> {
                if (jarOpener.sendCommand("uci", "uciok") != null) {
                    return "uciok";
                }
            }
            case "ucinewgame" -> {
                return jarOpener.sendCommand("ucinewgame", null);
            }
            case "position" -> {
                return jarOpener.sendCommand(command, null);
            }
            case "moves" -> {
                return jarOpener.sendCommand(command, "movelist");
            }
            case "go" -> {
                String move = jarOpener.sendCommand(command, "bestmove");
                String[] temp = move.split("\n");
                return temp[temp.length - 2] + "\n" + temp[temp.length - 1];
            }
        }

        return null;
    }
}
