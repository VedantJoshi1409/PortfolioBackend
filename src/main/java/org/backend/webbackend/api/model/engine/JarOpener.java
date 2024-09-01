package org.backend.webbackend.api.model.engine;

import java.io.*;

import static org.backend.webbackend.utility.Timestamp.printWithTimestamp;

public class JarOpener {
    private Process process;
    private final OutputStream processInput;
    private final BufferedReader processOutput;

    private long lastCommand;
    public String sessionId;

    public JarOpener(String path, String sessionId) {
        this.sessionId = sessionId;
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", path);
        processBuilder.redirectErrorStream(true);
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        processInput = process.getOutputStream();
        processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        lastCommand = System.currentTimeMillis();
    }

    public String sendCommand(String command, String expectedResult) {
        refresh();

        try {
            printWithTimestamp("Sending command: " + command);
            return sendCommand(processInput, processOutput, command, expectedResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sendCommand(OutputStream processInput, BufferedReader processOutput, String command, String expectedResponse) throws IOException {
        processInput.write((command + "\n").getBytes());
        processInput.flush();
        if (expectedResponse != null) {
            return waitForResponse(processOutput, expectedResponse);
        }
        return null;
    }

    private static String waitForResponse(BufferedReader processOutput, String expectedResponse) throws IOException {
        String line;
        String output = "";
        while ((line = processOutput.readLine()) != null) {
            System.out.println(line);
            output += line + "\n";
            if (line.contains(expectedResponse)) {
                break;
            }
        }
        return output;
    }

    public void kill() {
        process.destroy();
    }

    public void refresh() {
        System.out.println();
        printWithTimestamp("Refreshed " + sessionId);
        lastCommand = System.currentTimeMillis();
    }

    public boolean sessionCheck() {
        long time = System.currentTimeMillis() - lastCommand;
        printWithTimestamp(sessionId + ": Last command was " + time + " ms ago");
        return time > 75000;
    }
}
