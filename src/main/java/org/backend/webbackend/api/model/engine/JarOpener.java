package org.backend.webbackend.api.model.engine;

import java.io.*;

public class JarOpener {
    private Process process;
    private OutputStream processInput;
    private BufferedReader processOutput;

    public JarOpener(String path) {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", path);
        processBuilder.redirectErrorStream(true);
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        processInput = process.getOutputStream();
        processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    public String sendCommand(String input, String end) {
        try {
            processInput.write((input + "\n").getBytes());
            processInput.flush();

            StringBuilder output = new StringBuilder();
            String line;

            if (!(input.equals("ucinewgame") || input.split(" ")[0].equals("position"))) {
                while ((line = processOutput.readLine()) != null) {
                    output.append(line).append("\n");
                    if (line.split(" ")[0].equals(end)) {
                        break;
                    }
                }

                return output.toString().trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void kill() {
        process.destroy();
    }
}
