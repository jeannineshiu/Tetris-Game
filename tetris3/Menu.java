package tetris3;


import tetris3.Tetris;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class Menu extends Scene{
	Button start,exit;
	Tetris m;
	
	public Menu(Parent root,Tetris main) {
		super(root);
		m = main;
		start = (Button) lookup("#start");
		start.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				m.toGame();
			}
		});
		exit = (Button) lookup("#exit");
		exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Platform.exit();
			}
		});
		
	}
}
