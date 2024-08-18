package org.backend.webbackend.api.model.engine;

import java.io.*;

import static org.backend.webbackend.utility.Timestamp.printWithTimestamp;

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
            System.out.println();
            printWithTimestamp("Sending command: " + input);
            processInput.write((input + "\n").getBytes());
            processInput.flush();
            printWithTimestamp("Command sent: " + input);
            System.out.println();

            StringBuilder output = new StringBuilder();
            String line;

            if (!(input.equals("ucinewgame") || input.split(" ")[0].equals("position"))) {
                while ((line = processOutput.readLine()) != null) {
                    output.append(line).append("\n");
                    if (line.split(" ")[0].equals(end)) {
                        break;
                    }
                }
                System.out.println();
                printWithTimestamp("Data received:");
                System.out.println("\n" + output + "\n");

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
