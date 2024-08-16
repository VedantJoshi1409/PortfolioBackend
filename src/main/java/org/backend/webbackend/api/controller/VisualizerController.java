package org.backend.webbackend.api.controller;

import org.backend.webbackend.service.VisualizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisualizerController {
    private VisualizerService visualizerService;

    @Autowired
    public VisualizerController(VisualizerService visualizerService) {
        this.visualizerService = visualizerService;
    }

    @GetMapping("/visualize")
    public int[][] visualize(@RequestParam String fen) {
        return visualizerService.getNeurons(fen);
    }
}
