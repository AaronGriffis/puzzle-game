//Aaron Griffis

package noodles.square;
import noodles.Noodle;

public class Square extends Noodle{
   private int row;
   private int col;
   
   public Square(int row, int col){
      super(new boolean[4]);
      this.row = row;
      this.col = col;
   }
   
   public Square(int row, int col, boolean powered, boolean[] activeSides){
      super(powered, activeSides);
      this.row = row;
      this.col = col;
   }
   
   //get methods
   public int getRow(){return this.row;}
   public int getCol(){return this.col;}
}//end Square class