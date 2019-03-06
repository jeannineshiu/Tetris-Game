package tetris3;

import javafx.scene.Group;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Random;

public class Tetrimino extends Group {
	
	public static  int  num=7;
	
	public static boolean first;


  private final ArrayList<Rectangle> tetriminos;
  private int rotateX = 0;
  private int rotateY = 0;

  public Tetrimino(int rotateX, int rotateY, Rectangle... rectangles) {
	  
    this.rotateX = rotateX;
    this.rotateY = rotateY;
    
    tetriminos = new ArrayList<Rectangle>();

    for (int i = 0; i < rectangles.length; i++) {
      Rectangle rectangle = rectangles[i];
      tetriminos.add(rectangle);
    }
    
    getChildren().addAll(rectangles);
	
    first = true;
    
   
    
  }

  public static Rectangle getRectangle(int x, int y, Color color) {
    Rectangle rectangle = new Rectangle();
    rectangle.setX(x * TetrisBoard.UNITSIZE);
    rectangle.setY(y * TetrisBoard.UNITSIZE);
    rectangle.setArcWidth(20);
    rectangle.setArcHeight(20);
    rectangle.setWidth(TetrisBoard.UNITSIZE);
    rectangle.setHeight(TetrisBoard.UNITSIZE);
    rectangle.setFill(color);
    rectangle.setEffect(new InnerShadow(5, color.invert().deriveColor(1.0, 1.0, 1.0, 0.5)));
    
    return rectangle;
  }

  public ArrayList<Rectangle> getRectangles() {
    return tetriminos;
  }
  
  public static Tetrimino setTetrimino() {
	  

    int nextInt = new Random(System.currentTimeMillis()).nextInt(num);

    Tetrimino tetrimino = null;

    switch (nextInt) {
      case 0:
        tetrimino = new I();
        break;
      case 1:
        tetrimino = new J();
        break;
      case 2:
        tetrimino = new L();
        break;
      case 3:
        tetrimino = new O();
        break;
      case 4:
        tetrimino = new S();
        break;
      case 5:
        tetrimino = new T();
        break;
      case 6:
        tetrimino = new Z();
        break;            
      case 7:
        tetrimino = new cross();
        break;
        
      case 8:
        tetrimino = new H();
        break;
    }

    return tetrimino;
  }

  public void rotateTetrimino(boolean flag) {
    getTransforms().add(new Rotate(flag ? -90 : 90, getLayoutX() + (rotateX * TetrisBoard.UNITSIZE), getLayoutY() + (rotateY * TetrisBoard.UNITSIZE)));
  }
  
  public static class cross extends Tetrimino {
	    public cross() {
	      super(1, 1, getRectangle(0, 1, Color.rgb(255, 192, 203, 1.0)), getRectangle(1, 1, Color.rgb(255, 192, 203, 1.0)), getRectangle(2, 1, Color.rgb(255, 192, 203, 1.0)), getRectangle(1, 0, Color.rgb(255, 192, 203, 1.0)), getRectangle(1, 2, Color.rgb(255, 192, 203, 1.0)));
	      this.setTranslateX(40);
	      this.setTranslateY(30);
	    }
}
  
  public static class H extends Tetrimino {
	    public H() {
	      super(1, 1, getRectangle(0, 0, Color.rgb(176, 48, 96, 1.0)), getRectangle(1, 1, Color.rgb(176, 48, 96, 1.0)), getRectangle(2, 0, Color.rgb(176, 48, 96, 1.0)), getRectangle(0, 2, Color.rgb(176, 48, 96, 1.0)), getRectangle(2, 2, Color.rgb(176, 48, 96, 1.0)), getRectangle(0, 1, Color.rgb(176, 48, 96, 1.0)), getRectangle(2, 1, Color.rgb(176, 48, 96, 1.0)));
	      this.setTranslateX(40);
	      this.setTranslateY(30);
	    }
}
  
  
  
  public static class Bomb extends Tetrimino {
	    public Bomb() {
	      super(1, 1, getRectangle(0, 0, Color.rgb(0, 0, 0, 1.0)), getRectangle(1, 1, Color.rgb(0, 0, 0, 1.0)), getRectangle(2, 0, Color.rgb(0, 0, 0, 1.0)), getRectangle(0, 2, Color.rgb(0, 0, 0, 1.0)), getRectangle(2, 2, Color.rgb(0, 0, 0, 1.0)));
	      this.setTranslateX(40);
	      this.setTranslateY(30);
	    }
}

  public static class I extends Tetrimino {
    public I() {
      super(0, 2, getRectangle(0, 0, Color.rgb(0, 240, 240, 1.0)), getRectangle(0, 1, Color.rgb(0, 240, 240, 1.0)), getRectangle(0, 2, Color.rgb(0, 240, 240, 1.0)), getRectangle(0, 3, Color.rgb(0, 240, 240, 1.0)));
      this.setTranslateX(70);
      this.setTranslateY(10);
    }
  }

  public static class J extends Tetrimino {
    public J() {
      super(1, 1, getRectangle(1, 0, Color.rgb(0, 0, 240, 1.0)), getRectangle(1, 1, Color.rgb(0, 0, 240, 1.0)), getRectangle(1, 2, Color.rgb(0, 0, 240, 1.0)), getRectangle(0, 2, Color.rgb(0, 0, 240, 1.0)));
      this.setTranslateX(50);
      this.setTranslateY(20);
    }
  }

  public static class L extends Tetrimino {
    public L() {
      super(1, 1, getRectangle(0, 0, Color.rgb(240, 160, 0, 1.0)), getRectangle(0, 1, Color.rgb(240, 160, 0, 1.0)), getRectangle(0, 2, Color.rgb(240, 160, 0, 1.0)), getRectangle(1, 2, Color.rgb(240, 160, 0, 1.0)));
      this.setTranslateX(50);
      this.setTranslateY(30);
    }
  }

  public static class O extends Tetrimino {
    public O() {
      super(1, 1, getRectangle(0, 0, Color.rgb(240, 240, 0, 1.0)), getRectangle(0, 1, Color.rgb(240, 240, 0, 1.0)), getRectangle(1, 0, Color.rgb(240, 240, 0, 1.0)), getRectangle(1, 1, Color.rgb(240, 240, 0, 1.0)));
      this.setTranslateX(50);
      this.setTranslateY(30);
    }
  }

  public static class S extends Tetrimino {
    public S() {
      super(1, 1, getRectangle(1, 0, Color.rgb(0, 240, 0, 1.0)), getRectangle(2, 0, Color.rgb(0, 240, 0, 1.0)), getRectangle(0, 1, Color.rgb(0, 240, 0, 1.0)), getRectangle(1, 1, Color.rgb(0, 240, 0, 1.0)));
      this.setTranslateX(40);
      this.setTranslateY(30);
    }
  }

  public static class T extends Tetrimino {
    public T() {
      super(1, 1, getRectangle(0, 0, Color.rgb(160, 0, 240, 1.0)), getRectangle(0, 1, Color.rgb(160, 0, 240, 1.0)), getRectangle(0, 2, Color.rgb(160, 0, 240, 1.0)), getRectangle(1, 1, Color.rgb(160, 0, 240, 1.0)));
      this.setTranslateX(40);
      this.setTranslateY(30);
    }
  }

  public static class Z extends Tetrimino {
    public Z() {
      super(1, 1, getRectangle(0, 0, Color.rgb(240, 0, 0, 1.0)), getRectangle(1, 0, Color.rgb(240, 0, 0, 1.0)), getRectangle(1, 1, Color.rgb(240, 0, 0, 1.0)), getRectangle(2, 1, Color.rgb(240, 0, 0, 1.0)));
      this.setTranslateX(40);
      this.setTranslateY(30);
    }
  }
}
