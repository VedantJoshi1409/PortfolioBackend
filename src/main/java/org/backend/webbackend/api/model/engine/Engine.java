package org.backend.webbackend.api.model.engine;

import java.util.HashMap;

public class Engine {
    public final String PATH = "Bitterfish.jar";
    private HashMap<String, JarOpener> sessions;

    public Engine() {
        sessions = new HashMap<>();
    }

    public void newSession(String sessionId) {
        System.out.println("New session: " + sessionId);
        if (!sessions.containsKey(sessionId)) {
            sessions.put(sessionId, new JarOpener(PATH));
        }
    }

    public void endSession(String sessionId) {
        System.out.println("Session ended: " + sessionId);
        sessions.get(sessionId).kill();
        System.out.println("Process Killed");
        sessions.remove(sessionId);
    }

    public String parseCommand(String command, String sessionId) {
        JarOpener jarOpener = sessions.get(sessionId);

        if (command.equals("uci")) {
            if (jarOpener.sendCommand("uci", "uciok") != null) {
                return "uciok";
            }
        }

        if (command.equals("ucinewgame")) {
            jarOpener.sendCommand("ucinewgame", null);
            return null;
        }

        String[] blocks = command.split(" ");
        if (blocks[0].equals("position")) {
            jarOpener.sendCommand(command, null);
            return null;
        }

        if (blocks[0].equals("go")) {
            String move = jarOpener.sendCommand(command, "bestmove");
            String[] temp = move.split("\n");
            return temp[temp.length-1];
        }
        return null;
    }
}
