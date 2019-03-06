package tetris3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Game1 extends Scene {
		
	 Tetris m;
	
	 TetrisBoard tetrisboard;
	
	 Pane previewboard;
	 
	 Label score;
	 Rectangle rectangle ;
	 StackPane stackpane;
	 VBox vbox;
	 AnchorPane anchorpane;
	 HBox hbox,vhbox;
	 BorderPane rootPane;
	 
	 Button pause,restart;
	 boolean first=true;
	 
	 
	 

	public Game1(Parent root, Tetris main) {
		super(root);
		m = main;
		
		tetrisboard = new TetrisBoard(this);
		previewboard = tetrisboard.getPreviewBoard();
		
		rectangle = (Rectangle) lookup("#rectangle");
		stackpane = (StackPane) lookup("#stackpane");
		vbox = (VBox) lookup("#vbox");
		anchorpane = (AnchorPane) lookup("#anchorpane");
		vhbox = (HBox) lookup("#vhbox");
		hbox = (HBox) lookup("#hbox");
		rootPane = (BorderPane) lookup("#rootpane");
		score = (Label) lookup("#score");
	
		
		stackpane.getChildren().addAll(previewboard);
		hbox.getChildren().add(tetrisboard);
		
		
	    // Show Score
	    tetrisboard.scoreProperty().addListener(new ChangeListener() {
	      @Override public void changed(ObservableValue observable, Object oldVal, Object newVal) {
	        score.setText(""+newVal);
	      }
	    });
	    
	    /*this.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	      @Override public void handle(KeyEvent e) {
	        if (e.getCode()==KeyCode.ENTER) {
	          if (tetrisboard.isRunning()){
	            tetrisboard.pause();
	          }
	          else {
	            tetrisboard.play();
	          }      
	        }
	      }
	    });*/
	    
	    restart = (Button) lookup("#restart");
	    
	    restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				tetrisboard.resetb = true;
				
				tetrisboard.BGM2.stop();
				tetrisboard.BGM1.stop();
				tetrisboard.BGM3.stop();
				
				
				/*tetrisboard.getChildren().clear();
				tetrisboard.setGameBoard();
				tetrisboard.initTimeline();
				tetrisboard.timeline.play();
				
				
				tetrisboard.BGM1.setCycleCount(MediaPlayer.INDEFINITE);
				tetrisboard.BGM1.play();
				
				tetrisboard.running = true;
				tetrisboard.second = false;*/
				
				///////////////////////////////////
				
				tetrisboard.rowIndex = 0;
				tetrisboard.columnIndex = 10 / 2;
				
				
				
				tetrisboard.timeline.stop();
				tetrisboard.play();
						
			}
				
		});
	    
	    
	    pause = (Button) lookup("#pause");
	    
	    pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (tetrisboard.isRunning()){
		            tetrisboard.pause();
		            pause.setText("play");
		           first=false;	       
		           
		          }
				else if(first==false)
				{
					tetrisboard.play();
					pause.setText("pause");
			        first=true;
				}
			}
		});
	    
	    tetrisboard.play();
	    
	}
	

}
