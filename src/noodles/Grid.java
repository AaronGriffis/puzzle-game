//Aaron Griffis

package noodles;
import java.awt.*;

public abstract class Grid{
   private Noodle[][] noodles;
   private Point[] neighbors;
   
   public Grid(int rows, int cols, Point[] neighbors){
      this.neighbors = neighbors;
      this.initNoodles(rows, cols);
   }
   
   //get methods
   public Noodle getNoodle(int row, int col){return this.noodles[row][col];}
   public Noodle[][] getNoodles(){return this.noodles;}
   public int getRows(){return this.noodles.length;}
   public int getCols(){return this.noodles[0].length;}
   public abstract int getRow(Noodle noodle);
   public abstract int getCol(Noodle noodle);
   public Noodle getNeighbor(Noodle noodle, int dir, boolean wrap){
      Point coords = this.neighbors[dir];
      int row = getNeighborRow(noodle, coords);
      int col = getNeighborCol(noodle, coords);
      int rows = this.noodles.length;
      int cols = this.noodles[0].length;
      if(wrap)
         return this.noodles[(rows + row) % rows][(cols + col) % cols];
      else{
         if(row >= 0 && row < rows && col >= 0 && col < cols)
            return this.noodles[row][col];
         return null;
      }
   }
   public Point[] getNeighbors(){return this.neighbors;}
   protected abstract int getNeighborRow(Noodle noodle, Point coords);
   protected abstract int getNeighborCol(Noodle noodle, Point coords);
   
   //setMethods
   public void setNoodles(Noodle[][] noodles){this.noodles = noodles;}
   public void setNeighbors(Point[] neighbors){this.neighbors = neighbors;}
   
   public void initNoodles(int rows, int cols){
      this.noodles = new Noodle[rows][cols];
      for(int row = 0; row < rows; row++){
         for(int col = 0; col < cols; col++){
            noodles[row][col] = initNoodle(row, col);
         }
      }
   }//end initNoodles
   
   public void resetActiveSides(int sideCount){
      for(Noodle[] row : this.noodles){
         for(Noodle noodle : row){
            noodle.setActiveSides(new boolean[sideCount]);
            noodle.setRotation(0);
         }
      }
   }//end resetActiveSides
   
   public void resetPower(){
      for(Noodle[] row : this.noodles){
         for(Noodle noodle : row){
            noodle.setPowered(false);
         }
      }
   }//end resetPower
   
   public abstract Noodle initNoodle(int row, int col);
}//end Grid class