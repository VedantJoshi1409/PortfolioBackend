package org.backend.webbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebBackendApplication {

	public static void main(String[] args) {
//		Engine engine = new Engine("Bitterfish.jar");
//		System.out.println(engine.parseCommand("uci"));
//		engine.parseCommand("ucinewgame");
//		engine.parseCommand("pos startpos");

//		Visualizer visualizer = new Visualizer();
//		int[][] temp = visualizer.getNeurons("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Kkq - 0 1");

		SpringApplication.run(WebBackendApplication.class, args);
	}
}
