package tetris3;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class TetrisBoard extends Pane {

	public static final int COLUMNS = 10, ROWS = 25;
  //private static final int LEVEL = 10;
  public static int UNITSIZE = 20;
  
  private Tetrimino currentTetrimino, nextTetrimino;
  public int rowIndex = 0, columnIndex = 0;

  private ArrayList<ArrayList<Rectangle>> rows;
  public boolean running;
  public boolean fastDrop = false;
  
  //private AudioClip removeClip, levelupClip, finalClip;
  //private AudioClip musicClip;
  //private AudioClip removeClip;
  
  MediaPlayer BGM1,BGM2,BGM3,Gameoverb,finish,remove,NowBGM;
  Media media_BGM1 = new Media(getClass().getResource("Town7.mp3").toString()); 
  Media media_BGM2 = new Media(getClass().getResource("Town3.mp3").toString()); 
  Media media_BGM3 = new Media(getClass().getResource("Dungeon7.mp3").toString()); 
  Media media_Gameoverb = new Media(getClass().getResource("Gameover.mp3").toString()); 
  Media media_finish = new Media(getClass().getResource("finish.mp3").toString()); 
  Media media_remove = new Media(getClass().getResource("audio/Item.mp3").toString()); 
  
  private final IntegerProperty score;
  
  private int tetriminoCount = 0;
  private double speed = 2.0;
  
  private Pane previewboard;
  private boolean gameover;
  private boolean win;
  public boolean resetb;
  
  private boolean firstwin;
  
  public boolean second = false;
  public boolean third = false;
  
  public Timeline timeline, loop;
  
  Rectangle background;
  
  //Stage stage;
  Game1 gamescene;

  public TetrisBoard(Game1 gamescene)  {
    try {
      /*removeClip = new AudioClip(getClass().getResource("audio/remove.wav").toString());
      levelupClip = new AudioClip(getClass().getResource("audio/levelup.wav").toString());
      finalClip = new AudioClip(getClass().getResource("audio/final.wav").toString());*/
    //musicClip = new AudioClip(getClass().getResource("audio/music.wav").toString());
    //removeClip = new AudioClip(getClass().getResource("audio/Item.wav").toString());
      //NowBGM  =new MediaPlayer(media_BGM1);
      BGM1  =new MediaPlayer(media_BGM1);
      BGM2  =new MediaPlayer(media_BGM2);
      BGM3  =new MediaPlayer(media_BGM3);
      Gameoverb  =new MediaPlayer(media_Gameoverb);
      finish  =new MediaPlayer(media_finish);
      remove  =new MediaPlayer(media_remove);
      
    }catch (Exception ex) {
      ex.toString();
    }

    score = new SimpleIntegerProperty(0);
    
    setGameBoard();
    initTimeline();
   
    this.gamescene = gamescene;
  }


  public void setGameBoard() {
	
    score.setValue(0);

    rows = new ArrayList<ArrayList<Rectangle>>();
    
    for (int i=0; i<ROWS; i++) {
      rows.add(i, setEmptyRow());
    }

    //tetrisboard's background is black
    background = new Rectangle();
    background.setFocusTraversable(true);
    background.setFill(Color.AZURE);
    background.setStroke(Color.GRAY);
    background.setStrokeWidth(2.0);
    background.setTranslateX(0);
    background.setTranslateY(0);
    background.setWidth(COLUMNS * UNITSIZE);
    background.setHeight(ROWS * UNITSIZE);
    background.setEffect(new DropShadow());

    setOnKeyPressed(new EventHandler<KeyEvent>() {
    	
      @Override public void handle(KeyEvent e) {
    	  
        if (running && e.getCode() == KeyCode.J) {
          if (columnIndex >= 1 && isPossibleMove(0, - 1)) {
            columnIndex--;
          }
        }
        
        if (running && e.getCode() == KeyCode.L) {
          if (!(columnIndex >= COLUMNS) && isPossibleMove(0, 1)) {
            columnIndex++;
          }
        }
        
        if (running && e.getCode() == KeyCode.I) {
          currentTetrimino.rotateTetrimino(false);
          if (!isPossibleMove(0, 0)) {
            currentTetrimino.rotateTetrimino(true);
          }
        }
        
        if (running && (e.getCode() == KeyCode.K || e.getCode() == KeyCode.SPACE)) {
          if (!fastDrop) {
            timeline.setRate(10.0);
            fastDrop = true;
          }
        }
             
        //real change the x position
        currentTetrimino.setTranslateX(columnIndex * UNITSIZE);
      }
      
    });

    setOnKeyReleased(new EventHandler<KeyEvent>() {
    	
      @Override public void handle(KeyEvent e) {
        if (fastDrop && (e.getCode() == KeyCode.K )) {
          fastDrop = false;
          timeline.setRate(speed);
        }
        
        //real change the x position
        currentTetrimino.setTranslateX(columnIndex * UNITSIZE);
      }
      
    });

    //directly add the Rectangle background on the tetrisboard (pane)
 
    getChildren().add(background);
    
    
    
    currentTetrimino.num = 7;
    nextTetrimino.num = 7;
    
    //columnIndex start from middle
    columnIndex = COLUMNS / 2;
    currentTetrimino = setTetrimino();
    currentTetrimino.setTranslateX(columnIndex * UNITSIZE);
    currentTetrimino.setTranslateY(rowIndex * UNITSIZE);
    nextTetrimino = setTetrimino();
    
    previewboard = new Pane();
    previewboard.setPrefSize(5 * TetrisBoard.UNITSIZE, 5 * TetrisBoard.UNITSIZE);
    updatePreviewBoard();
   
    getChildren().add(currentTetrimino);
  }
  
  
  
  
  
  public void setGameBoard2() {
		
	    score.setValue(0);

	    rows = new ArrayList<ArrayList<Rectangle>>();
	    
	    for (int i=0; i<ROWS; i++) {
	      rows.add(i, setEmptyRow());
	    }

	    //tetrisboard's background is black
	    background = new Rectangle();
	    background.setFocusTraversable(true);
	    background.setFill(Color.AZURE);
	    background.setStroke(Color.GRAY);
	    background.setStrokeWidth(2.0);
	    background.setTranslateX(0);
	    background.setTranslateY(0);
	    background.setWidth(COLUMNS * UNITSIZE);
	    background.setHeight(ROWS * UNITSIZE);
	    background.setEffect(new DropShadow());

	    setOnKeyPressed(new EventHandler<KeyEvent>() {
	    	
	      @Override public void handle(KeyEvent e) {
	    	  
	        if (running && e.getCode() == KeyCode.J) {
	          if (columnIndex >= 1 && isPossibleMove(0, - 1)) {
	            columnIndex--;
	          }
	        }
	        
	        if (running && e.getCode() == KeyCode.L) {
	          if (!(columnIndex >= COLUMNS) && isPossibleMove(0, 1)) {
	            columnIndex++;
	          }
	        }
	        
	        if (running && e.getCode() == KeyCode.I) {
	          currentTetrimino.rotateTetrimino(false);
	          if (!isPossibleMove(0, 0)) {
	            currentTetrimino.rotateTetrimino(true);
	          }
	        }
	        
	        if (running && (e.getCode() == KeyCode.K || e.getCode() == KeyCode.SPACE)) {
	          if (!fastDrop) {
	            timeline.setRate(10.0);
	            fastDrop = true;
	          }
	        }
	             
	        //real change the x position
	        currentTetrimino.setTranslateX(columnIndex * UNITSIZE);
	      }
	      
	    });

	    setOnKeyReleased(new EventHandler<KeyEvent>() {
	    	
	      @Override public void handle(KeyEvent e) {
	        if (fastDrop && (e.getCode() == KeyCode.K )) {
	          fastDrop = false;
	          timeline.setRate(speed);
	        }
	        
	        //real change the x position
	        currentTetrimino.setTranslateX(columnIndex * UNITSIZE);
	      }
	      
	    });

	    //directly add the Rectangle background on the tetrisboard (pane)
	 
	    getChildren().add(background);
	    
	    
	    currentTetrimino.num = 7;
	    nextTetrimino.num = 7;
	    
	    //columnIndex start from middle
	    columnIndex = COLUMNS / 2;
	    currentTetrimino = setTetrimino();
	    currentTetrimino.setTranslateX(columnIndex * UNITSIZE);
	    currentTetrimino.setTranslateY(rowIndex * UNITSIZE);
	    nextTetrimino = setTetrimino();
	    
	    //previewboard = new Pane();
	    previewboard.setPrefSize(5 * TetrisBoard.UNITSIZE, 5 * TetrisBoard.UNITSIZE);
	    updatePreviewBoard();
	   
	    getChildren().add(currentTetrimino);
	  }
  
  
  
  
  
  
  
  

  public void initTimeline() {
	  
    timeline = new Timeline();
    
    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
    	
      @Override public void handle(ActionEvent event) {
    	  
        if (!isPossibleMove(1, 0)) 
        {
        	
          placeTetrimino();
          
         
          if (rowIndex == 0) {
            setGameOver();
          }     
        
          rowIndex = 0;
          columnIndex = COLUMNS / 2;

 
          getChildren().remove(currentTetrimino);

          currentTetrimino = nextTetrimino;
          
          //set the current block's position
          currentTetrimino.setTranslateX(columnIndex * UNITSIZE);
          currentTetrimino.setTranslateY(rowIndex * UNITSIZE);
          
          //preview board
          nextTetrimino = setTetrimino();
          updatePreviewBoard();

          getChildren().add(currentTetrimino);
          
        } 
       
        else {
          currentTetrimino.setTranslateY(rowIndex++ * UNITSIZE);
        }
      }
    }));

    timeline.setCycleCount(Timeline.INDEFINITE);
    
    gameover = false;
    win = false;
    resetb = false;
    firstwin=true;
    
    second = false;
    third = false;
    
    currentTetrimino.num = 7;
    nextTetrimino.num = 7;
    
    NowBGM = BGM1;
    
  }

  public boolean isRunning() {
    return running;
  }

  private void setGameOver() {
    timeline.stop();
    
    speed = 1.0;
    
    if (timeline != null)
      timeline.setRate(speed);
    
    running = false;

    //finalClip.play();
    /*BGM2.stop();
    BGM1.stop();
    BGM3.stop();*/
    
    NowBGM.stop();
    
    
    //musicClip.stop();
    Gameoverb.stop();
    Gameoverb.play();
  
    final Text text = new Text("Game Over!");
    text.setFont(new Font("Algerian", 55));
    text.setStrokeWidth(1);
    text.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.REFLECT, new Stop(0.0, Color.RED), new Stop(0.5, Color.PLUM), new Stop(1.0, Color.PURPLE)));
    text.setStroke(Color.BLACK);
    text.setX(15);
    text.setY(600);
    getChildren().add(text);

    loop = new Timeline(new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {
      double deltaY = -5;

      @Override
      public void handle(final ActionEvent e) {
        double y = text.getY() + deltaY;
        
        if (y <= 205) {
          loop.stop();
        }
        else {
          text.setY(y);
        }
      }
    }));

    loop.setCycleCount(Timeline.INDEFINITE);
    loop.play();    

    gameover = true;
  }
  
  public void placeTetrimino() {
    ArrayList<Rectangle> rectangles = currentTetrimino.getRectangles();

    for (Rectangle rectangle : rectangles) {
      Bounds bound = currentTetrimino.localToParent(rectangle.getBoundsInLocal());
      int row = (int) (bound.getMinY() / UNITSIZE);
      int column = (int) (bound.getMinX() / UNITSIZE);

      rectangle.setX(0);
      rectangle.setY(0);
      rectangle.setTranslateX(bound.getMinX());
      rectangle.setTranslateY(bound.getMinY());
      getChildren().add(rectangle);
      rows.get(row).set(column, rectangle);
    }
    
    checkRowComplete();
    tetriminoCount++;
    
  }

  public boolean isPossibleMove(int rowAdd, int columnAdd) {
    ArrayList<Rectangle> rectangles = currentTetrimino.getRectangles();

    for (Rectangle rectangle : rectangles) {
      Bounds bound = currentTetrimino.localToParent(rectangle.getBoundsInLocal());
      int row = (int) (bound.getMinY() / UNITSIZE) + rowAdd;
      int column = (int) (bound.getMinX() / UNITSIZE + columnAdd);

      if (row >= ROWS || row < 0 || column < 0 || column >= COLUMNS || getRectangle(row, column) != null) {
        return false;
      }
    }
    return true;
  }

  public void pause() {
    if (running) {
      timeline.pause();
      /*BGM1.pause();
      BGM2.pause();
      BGM3.pause();*/
      
      NowBGM.pause();
      
      running = false;
    } 
    else {
      timeline.play();
      running = true;
    }
  }

  public void play()  {
	  
    if (gameover||win||resetb) {
    	
      getChildren().clear();
      setGameBoard2();
      initTimeline();
    }
    
    timeline.play();
    
    //BGM.setStartTime(Duration.seconds(60));
    //BGM.setStopTime(Duration.seconds(120));
    //BGM1.setCycleCount(MediaPlayer.INDEFINITE);
    //BGM1.play();
    
    NowBGM.setCycleCount(MediaPlayer.INDEFINITE);
    NowBGM.play();
    
    
    //TimeUnit.SECONDS.sleep(5);
    //Gameoverb.stop();
    running = true;
  
  }

  public Rectangle getRectangle(int row, int column) {
    return rows.get(row).size() > column ? rows.get(row).get(column) : null;
  }

  private void checkRowComplete() {
    int numRemoved = 0;

    for (int i = 0; i < ROWS; i++) {
    	
      ArrayList<Rectangle> arrayList = rows.get(i);
      boolean remove = true;

      for (Rectangle rectangle : arrayList) {
        if (null == rectangle) {
          remove = false;
        }
      }
      
      if (remove) {
        removeRow(i);
        numRemoved++;
      }

      int points = 0;
      
      switch (numRemoved) {
        case 0:
          break;
        case 1:
          points = 50 ;
          break;
        case 2:
          points = 100 ;
          break;
        case 3:
          points = 300;
          break;
        case 4:
          points = 500;
          break;
      }

      setScore(score.getValue() + points);
      if(second==false)
    	  secondRound();
      
      
      if(third==false)
    	  thirdRound();
      
      if(firstwin==true)
    	  winCondition();
      
    }
    
  }
  
  public void winCondition()
  {
	  int nowScore =score.getValue();
	  
	  if(nowScore>=3500)
	  {
		  
		  firstwin = false;
		  
		  timeline.stop();
		    
		    speed = 1.0;
		    
		    if (timeline != null)
		      timeline.setRate(speed);
		    
		    running = false;

		    //finalClip.play();
		    /*BGM2.stop();
		    BGM1.stop();
		    BGM3.stop();*/
		    
		    NowBGM.stop();
		    
		    finish.stop();
		    finish.play();
		    
		    //musicClip.stop();
		  
		    final Text text = new Text("You Win!");
		    text.setFont(new Font("Algerian", 55));
		    text.setStrokeWidth(1);
		    text.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.REFLECT, new Stop(0.0, Color.RED), new Stop(0.5, Color.YELLOW), new Stop(1.0, Color.ORANGE)));
		    text.setStroke(Color.BLACK);
		    text.setX(70);
		    text.setY(600);
		    getChildren().add(text);

		    loop = new Timeline(new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {
		      double deltaY = -5;

		      @Override
		      public void handle(final ActionEvent e) {
		        double y = text.getY() + deltaY;
		        
		        if (y <= 205) {
		          loop.stop();
		        }
		        else {
		          text.setY(y);
		        }
		      }
		    }));

		    loop.setCycleCount(Timeline.INDEFINITE);
		    loop.play();    

		    win = true;
	  }
	  
  }
  
  public void secondRound()
  {
	  int nowScore =score.getValue();
	  
	  if(nowScore>=1000)
	  {
		  speed = 3.0;
		 Timeline change = new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(background.fillProperty(),Color.BEIGE)));
		 change.playFromStart();
		 
		 second = true;
		 
		 currentTetrimino.num = 8;
		 nextTetrimino.num = 8;
		 //Tetrimino.first = false;
		 //BGM1.stop();
		 NowBGM.stop();
		 
		 NowBGM = BGM2;
		 
		 NowBGM.setCycleCount(MediaPlayer.INDEFINITE);
		 NowBGM.play();
		 
	  }
	  
  }
  
  
  public void thirdRound()
  {
	  int nowScore =score.getValue();
	  
	  if(nowScore>=2500)
	  {
		  speed = 4.0;
		 Timeline change = new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(background.fillProperty(),Color.LAVENDER)));
		 change.playFromStart();
		 
		 third = true;
		 
		 currentTetrimino.num = 9;
		 nextTetrimino.num = 9;
		 //Tetrimino.first = false;
		 //BGM2.stop();
		 
		 NowBGM.stop();
		 NowBGM = BGM3;
		 
		 NowBGM.setCycleCount(MediaPlayer.INDEFINITE);
		 NowBGM.play();
		 
	  }
	  
  }
  
  
  

  private void removeRow(int index) {
	remove.stop();
    remove.play();
    
    ArrayList<Rectangle> row = rows.get(index);
    //remove rectangle on canvas
    for (Rectangle rectangle : row) {
      if (rectangle != null) {
        getChildren().remove(rectangle);
      }
    }

    for (int i = index; i > 0; i--) {
      rows.set(i, rows.get(i - 1));
      moveRows(rows.get(i));
    }
    
    rows.set(0, setEmptyRow());
    
  }

  //move down=>y+1
  private void moveRows(ArrayList<Rectangle> row) {
    for (Rectangle rectangle : row) {
      if (rectangle != null) {
        rectangle.setTranslateY(rectangle.getTranslateY() + UNITSIZE);
      }
    }
  }

  private ArrayList<Rectangle> setEmptyRow() {
	  
    ArrayList<Rectangle> arrayList = new ArrayList<Rectangle>(COLUMNS);
    
    for (int j = 0; j < COLUMNS; j++) {
      arrayList.add(null);
    }
    return arrayList;
    
  }

  private Tetrimino setTetrimino() {
    Tetrimino newTetrimino = Tetrimino.setTetrimino();
    return newTetrimino;
  }

  public final double getScore() {
    return score.get();
  }

  public final void setScore(int value) {
    score.set(value);
  }

  public IntegerProperty scoreProperty() {
    return score;
  }

  private void updatePreviewBoard() {
    previewboard.getChildren().clear();
    previewboard.getChildren().add(nextTetrimino);
    //System.out.println("***");
  }

  public Pane getPreviewBoard() {
    return previewboard;
  }

  /*private void levelUp() {
    levelupClip.play();
    tetriminoCount = 0;
    speed += .5;
    timeline.setRate(speed);
  }*/
}