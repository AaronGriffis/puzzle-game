//Aaron Griffis
//code inspired by RedBlobGames
//http://www.redblobgames.com/grids/hexagons/

package noodles.hex;
import noodles.*;
import java.awt.*;

public class HexGrid extends Grid{

   public HexGrid(int rows, int cols){
      super(rows, cols, new Point[]{new Point(1, 0), new Point(0, 1), new Point(-1, 1), new Point(-1, 0), new Point(0, -1), new Point(1, -1)});
   }
   
   //get methods
   public int getRow(Noodle noodle){return ((Hex)noodle).getR() + (((Hex)noodle).getQ()+1)/2;}
   public int getCol(Noodle noodle){return ((Hex)noodle).getQ();}
   public int getNeighborRow(Noodle noodle, Point coords){return (((Hex)noodle).getR() + coords.y) + ((((Hex)noodle).getQ() + coords.x)+1)/2;}
   public int getNeighborCol(Noodle noodle, Point coords){return ((Hex)noodle).getQ() + coords.x;}
   
   public Noodle initNoodle(int row, int col){
      int q = col;
      int r = row - (col+1)/2;
      return new Hex(q, r);
   }
}//end HexGrid class