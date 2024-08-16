package org.backend.webbackend.api.model.visualizer;

public class Visualizer {
    public final String PATH = "NNUEVisualization.jar";
    private final JarOpener jarOpener;

    public Visualizer() {
        jarOpener = new JarOpener(PATH);
    }

    public int[][] getNeurons(String fen) {
        return jarOpener.getNeurons(fen);
    }
}
