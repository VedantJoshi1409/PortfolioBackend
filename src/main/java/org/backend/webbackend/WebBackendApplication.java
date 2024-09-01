package org.backend.webbackend;

import org.backend.webbackend.api.model.engine.Engine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebBackendApplication {

	public static void main(String[] args) {
//		Visualizer visualizer = new Visualizer();
//		int[][] temp = visualizer.getNeurons("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Kkq - 0 1");

		SpringApplication.run(WebBackendApplication.class, args);
	}
}
