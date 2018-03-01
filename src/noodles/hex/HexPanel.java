//Aaron Griffis

package noodles.hex;
import noodles.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class HexPanel extends PanelStrategy{
   
   public HexPanel(int rows, int cols, String themeName, boolean wrap){
      super(rows, cols, new HexLayout(wrap), new HexGrid(rows, cols), themeName);
      super.setPuzzle(new HexPuzzle(super.getGrid(), wrap));
   }
   
   public Noodle getGhost(int row, int col){
      return new Hex(col, row - (col+1)/2, false, new boolean[]{false, false, false, false, false, false});
   }//end getGhostNoodle
   
   public Noodle getGhost(int row, int col, boolean powered, boolean[] activeSides){
      return new Hex(col, row - (col+1)/2, powered, activeSides);
   }//end getGhostNoodle
   
   public double getCustomWidth(double newWidth, boolean wrap){
      double size = newWidth / ((((3/4d) * (super.getCols() + (wrap ? 1 : -1))) + 1) * 2);
      return size / (Layout.PADDINGRATIO + 1);
   }//end getCustomWidth
   
   public double getCustomHeight(double newHeight, boolean wrap){
      double size = newHeight / (Math.sqrt(3) * (super.getRows() + ((wrap ? 5 : 1)/2d)));
      return size / (Layout.PADDINGRATIO + 1);
   }//end getCustomHeight
   
   public Dimension getPreferredSize(boolean wrap){
      double width = (super.getLayout().getSize() + super.getLayout().getPadding()) * 2;
      double height = (Math.sqrt(3)/2d) * width;
      int x = (int)(width * (((3/4d) * (super.getCols() + (wrap ? 1 : -1))) + 1));
      int y = (int)((height * (super.getRows() + (wrap ? 2 : 0))) + (height / 2));
      return new Dimension(x, y);
   }//end getPreferredSize
}//end HexPanel class