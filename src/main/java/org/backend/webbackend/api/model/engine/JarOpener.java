package org.backend.webbackend.api.model.engine;

import java.io.*;

public class JarOpener {
    private Process process;
    private final OutputStream processInput;
    private final BufferedReader processOutput;

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
            System.out.println("\nSending command: " + input);
            processInput.write((input + "\n").getBytes());
            processInput.flush();
            System.out.println("Command sent: " + input+"\n");

            StringBuilder output = new StringBuilder();
            String line;

            if (!(input.equals("ucinewgame") || input.split(" ")[0].equals("position"))) {
                while ((line = processOutput.readLine()) != null) {
                    output.append(line).append("\n");
                    if (line.split(" ")[0].equals(end)) {
                        break;
                    }
                }
                System.out.println("\nData received:\n\n" + output + "\n");

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
