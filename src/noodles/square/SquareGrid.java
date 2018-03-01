//Aaron Griffis

package noodles.square;
import noodles.*;
import java.awt.*;

public class SquareGrid extends Grid{

   public SquareGrid(int rows, int cols){
      super(rows, cols, new Point[]{new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)});
   }
   
   //get methods
   public int getRow(Noodle noodle){return ((Square)noodle).getRow();}
   public int getCol(Noodle noodle){return ((Square)noodle).getCol();}
   public int getNeighborRow(Noodle noodle, Point coords){return ((Square)noodle).getRow() + coords.y;}
   public int getNeighborCol(Noodle noodle, Point coords){return ((Square)noodle).getCol() + coords.x;}
   
   public Noodle initNoodle(int row, int col){
      return new Square(row, col);
   }
}//end SquareGrid class