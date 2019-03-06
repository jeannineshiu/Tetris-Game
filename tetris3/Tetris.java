package tetris3;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Tetris extends Application {
	public Stage stage;
	public Scene menu,gameScene;
    
  @Override
  public void start(Stage primaryStage) {

		try {
			menu= new Menu(FXMLLoader.load(getClass().getResource("menusb.fxml")),this);
			gameScene = new Game1(FXMLLoader.load(getClass().getResource("gamescene.fxml")),this);
			
			 stage = primaryStage;
			 primaryStage.setTitle("Tetris");
			 primaryStage.setScene(menu);
			 primaryStage.setResizable(false);
			 primaryStage.show();
			 
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
  }

  
  public void toGame() {
		stage.setScene(gameScene);
	}
	public void toMenu() {
		stage.setScene(menu);
		
	}
	public Scene getGameScene() {
		return gameScene;
	}
	public Scene getMenuScene() {
		return menu;
	}
  
  
  public static void main(String[] args) {
    launch(args);
  }

}
