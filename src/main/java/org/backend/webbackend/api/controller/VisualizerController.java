package org.backend.webbackend.api.controller;

import org.backend.webbackend.service.VisualizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
public class VisualizerController {
    private final BlockingQueue<Runnable> requestQueue = new LinkedBlockingQueue<>();
    private final Thread workerThread;

    private VisualizerService visualizerService;

    @Autowired
    public VisualizerController(VisualizerService visualizerService) {
        this.visualizerService = visualizerService;

        workerThread = new Thread(this::processQueue);
        workerThread.start();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/visualize")
    public int[][] visualize(@RequestParam String fen) throws InterruptedException {
        final int[][][] result = {null};

        // Create a task for the queue
        Runnable task = () -> result[0] = visualizerService.getNeurons(fen);

        // Add the task to the queue
        requestQueue.put(task);

        // Wait until the task has been processed
        while (result[0] == null) {
            Thread.sleep(100); // Simple wait, can be improved
        }

        return result[0];
    }

    private void processQueue() {
        try {
            while (true) {
                Runnable task = requestQueue.take();
                task.run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
