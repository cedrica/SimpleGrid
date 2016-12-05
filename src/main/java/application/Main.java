package application;
	


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simplegrid.SimpleGrid;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			SimpleGrid root = new SimpleGrid(400,400);
			root.setGridlineColor(Color.BLUE);
			root.init(5, 2);
			root.getCell(1, 1).setText("Ich fülle mich gut");
			VBox vb= new VBox(root);
			vb.setVgrow(root, Priority.ALWAYS);
			vb.setFillWidth(true);
			vb.setMaxHeight(Double.MAX_VALUE);
			vb.setPrefHeight(400);
			Scene scene = new Scene(vb,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
