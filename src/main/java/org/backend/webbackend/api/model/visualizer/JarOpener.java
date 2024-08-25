package org.backend.webbackend.api.model.visualizer;

import java.io.*;

import static org.backend.webbackend.utility.Timestamp.printWithTimestamp;

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

    public int[][] getNeurons(String input) {
        try {
            System.out.println();
            printWithTimestamp("Sending Command: " + input);
            processInput.write((input + "\n").getBytes());
            processInput.flush();
            printWithTimestamp("Command Sent: " + input);
            System.out.println();

            StringBuilder output = new StringBuilder();
            String line;
            String[] blocks;
            int[][] out = new int[14][32];

            while (!(line = processOutput.readLine()).split(" ")[0].equals("fc_0:")) {
            }

            for (int i = 0; i < out.length; i++) {
                if (i != 0) {
                    line = processOutput.readLine();
                }

                output.append(line).append("\n");
                blocks = line.split(" ");

//                printWithTimestamp(line);

                int counter = 0;
                for (String block : blocks) {
                    try {
                        int temp = Integer.parseInt(block);
                        out[i][counter] = temp;
                        counter++;
                    } catch (NumberFormatException e) {
                    }
                }
            }
            System.out.println();
            printWithTimestamp("Data received:");
            System.out.println("\n"+output+"\n");

            return out;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
