package org.backend.webbackend.service;

import org.backend.webbackend.api.model.visualizer.Visualizer;
import org.springframework.stereotype.Service;

@Service
public class VisualizerService {
    private Visualizer visualizer;;

    public VisualizerService() {
         visualizer = new Visualizer();
    }

    public int[][] getNeurons(String fen) {
        return visualizer.getNeurons(fen);
    }
}
