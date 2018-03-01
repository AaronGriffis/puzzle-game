//Aaron Griffis

package noodles.square;
import noodles.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class SquarePanel extends PanelStrategy{
   
   public SquarePanel(int rows, int cols, String themeName, boolean wrap){
      super(rows, cols, new SquareLayout(wrap), new SquareGrid(rows, cols), themeName);
      super.setPuzzle(new SquarePuzzle(super.getGrid(), wrap));
   }
   
   public Noodle getGhost(int row, int col){
      return new Square(row, col, false, new boolean[]{false, false, false, false});
   }//end getGhostNoodle
   
   public Noodle getGhost(int row, int col, boolean powered, boolean[] activeSides){
      return new Square(row, col, powered, activeSides);
   }//end getGhostNoodle
   
   public double getCustomWidth(double newWidth, boolean wrap){
      double size = newWidth / ((super.getCols() + (wrap ? 2 : 0)) * 2);
      return size / (Layout.PADDINGRATIO + 1);
   }//end getCustomWidth
   
   public double getCustomHeight(double newHeight, boolean wrap){
      double size = newHeight / ((super.getRows() + (wrap ? 2 : 0)) * 2);
      return size / (Layout.PADDINGRATIO + 1);
   }//end getCustomHeight
   
   public Dimension getPreferredSize(boolean wrap){
      double square = (super.getLayout().getSize() + super.getLayout().getPadding()) * 2;
      int x = (int)(square * (super.getCols() + (wrap ? 2 : 0)));
      int y = (int)(square * (super.getRows() + (wrap ? 2 : 0)));
      return new Dimension(x, y);
   }//end getPreferredSize
}//end SquarePanel