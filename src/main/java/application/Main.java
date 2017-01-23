package application;
	


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
//			root.setGridlineColor(Color.BLUE);
			root.init(5, 7);
			root.getCell(0, 1).setText("I feel good");
			root.getCell(1, 2).setText("I feel good");
			root.getCell(2, 1).setText("I feel good");
			root.getCell(3, 2).setText("I feel good");
			root.getCell(4, 1).setText("I feel good");
			
			VBox vb= new VBox(root);
			root.merge(2, 3, 1, 2);
			vb.setVgrow(root, Priority.ALWAYS);
			vb.setFillWidth(true);
			vb.setMaxHeight(Double.MAX_VALUE);
			vb.setPrefHeight(400);
			root.setGridlineVisible(false);
			Scene scene = new Scene(vb,400,400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Simple grid");
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
