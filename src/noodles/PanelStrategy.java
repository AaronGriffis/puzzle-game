//Aaron Griffis

package noodles;
import noodles.NoodlePanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public abstract class PanelStrategy{
   private int rows, cols, segment;
   private Layout layout;
   private Grid grid;
   private Puzzle puzzle;
   private BasicStroke noodleStroke, baseStroke;
   private String themeName;
   private Color[] theme;
   private Point2D.Double clickPoint, mousePoint;
   private Noodle draggedNoodle;
   
   public PanelStrategy(int rows, int cols, Layout layout, Grid grid, String themeName){
      this.rows = rows;
      this.cols = cols;
      this.segment = -1;
      this.layout = layout;
      this.grid = grid;
      this.baseStroke = new BasicStroke();
      this.setTheme(themeName);
   }
   
   //get methods
   public int getRows(){return this.rows;}
   public int getCols(){return this.cols;}
   public Layout getLayout(){return this.layout;}
   public Grid getGrid(){return this.grid;}
   public Puzzle getPuzzle(){return this.puzzle;}
   public String getThemeName(){return this.themeName;}
   public Color[] getTheme(){return this.theme;}
   public boolean getWrap(){return this.layout.getWrap();}
   
   //set methods
   public void setLayout(Layout layout){this.layout = layout;}
   public void setGrid(Grid grid){this.grid = grid;}
   public void setPuzzle(Puzzle puzzle){this.puzzle = puzzle;}
   public void setNoodleStroke(BasicStroke noodleStroke){this.noodleStroke = noodleStroke;}
   public void setRows(int rows){
      this.rows = rows;
      this.grid.initNoodles(this.rows, this.cols);
      this.regenerate();
   }
   public void setCols(int cols){
      this.cols = cols;
      this.grid.initNoodles(this.rows, this.cols);
      this.regenerate();
   }
   public void setWrap(boolean wrap){
      this.layout.setWrap(wrap);
      this.regenerate();
   }
   public void setTheme(String themeName){
      this.themeName = themeName;
      this.theme = ColorTheme.getTheme(themeName);
   }
   
   public void regenerate(){
      this.puzzle.initPuzzle(this.grid, this.getWrap());
   }//end regenerate
   
   public int getSegment(Point2D.Double p1, Point2D.Double p2){
      int segments = this.layout.getSegments();
      double angle = Math.toDegrees(Math.atan2(p1.getY() - p2.getY(), p1.getX() - p2.getX()));
      angle += (360 / segments) * this.layout.getCornerAngle();
      angle %= 360;
      if(angle < 0)
         angle +=360;
      return (int)((angle / (360 / segments)) % segments);
   }
   
   public void mousePressed(MouseEvent e, NoodlePanel panel){
      if(this.puzzle.isSolved())
         this.puzzle.initPuzzle(this.grid, this.getWrap());
      else{
         Point2D.Double point = new Point2D.Double(e.getX(), e.getY());
         Noodle noodle = this.layout.pixelToNoodle(point);
         int row = this.grid.getRow(noodle);
         int col = this.grid.getCol(noodle);
         if(row >= 0 && row < this.rows && col >= 0 && col < this.cols){
            noodle = this.grid.getNoodle(row, col);
            if(this.layout.getBase(noodle).contains(point)){
               if(e.getButton() == 1){
                  if(this.layout.getNoodleCenter(noodle, .2).contains(point))
                     noodle.rotate(-1);
                  else{
                     this.clickPoint = point;
                     this.draggedNoodle = noodle;
                  }
               }
               else if(e.getButton() == 2)
                  noodle.rotate(-1 * noodle.getRotation());
               else if(e.getButton() == 3)
                  noodle.rotate(1);
            }
            this.puzzle.testPowered(this.grid, this.getWrap());
         }
      }
   }//end mousePressed
   
   public void mouseReleased(){
      if(this.draggedNoodle != null){
         this.segment = -1;
         this.draggedNoodle = null;
         this.clickPoint = null;
         this.mousePoint = null;
      }
      this.puzzle.testPowered(this.grid, this.getWrap());
   }//end mouseReleased
   
   public void mouseDragged(MouseEvent e){
      if(clickPoint != null){
         if(this.segment < 0)
            this.segment = this.getSegment(this.clickPoint, this.layout.noodleToPixel(draggedNoodle));
         this.mousePoint = new Point2D.Double(e.getX(), e.getY());
         int difference = this.getSegment(this.mousePoint, this.layout.noodleToPixel(draggedNoodle)) - this.segment;
         this.draggedNoodle.rotate(difference);
         this.segment += difference;
      }
   }//end mouseDragged
   
   public void paintComponent(Graphics2D g2){
      this.noodleStroke = new BasicStroke(this.layout.getStroke(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
      g2.setStroke(this.baseStroke);
      g2.setColor(this.theme[2]);
      Noodle[][] noodles = this.grid.getNoodles();
      Noodle cur;
      boolean wrap = this.getWrap();
      for(int row = (wrap ? -1 : 0); row < (wrap ? this.rows + 1 : this.rows); row++){
         for(int col = (wrap ? -1 : 0); col < (wrap ? this.cols + 1 : this.cols); col++){
            if(row == -1 || row == this.rows || col == -1 || col == this.cols)
               cur = this.getGhostNoodle(noodles, row, col);
            else
               cur = noodles[row][col];
            this.paintNoodle(g2, cur, row, col);
         }
      }
   }//end paintComponent
   
   public Noodle getGhostNoodle(Noodle[][] noodles, int row, int col){
      if(row == -1 && col == -1 || row == -1 && col == this.cols || row == this.rows && col == this.cols || row == this.rows && col == -1)
         return this.getGhost(row, col);
      Noodle noodle = noodles[(this.rows + row) % this.rows][(this.cols + col) % this.cols];
      return this.getGhost(row, col, noodle.getPowered(), noodle.getActiveSides());
   }//end getGhostNoodle
   
   public abstract Noodle getGhost(int row, int col);
   public abstract Noodle getGhost(int row, int col, boolean powered, boolean[] activeSides);
   
   public void paintNoodle(Graphics2D g2, Noodle noodle, int row, int col){
      g2.fill(this.layout.getBase(noodle));
      g2.setColor(noodle.getPowered() ? this.theme[0] : this.theme[1]);
      g2.setStroke(this.noodleStroke);
      boolean[] activeSides = noodle.getActiveSides();
      for(int i = 0; i < activeSides.length; i++){
         if(activeSides[i]){
            g2.draw(this.layout.getNoodleSide(noodle, i));
         }
      }
      g2.fill(this.layout.getNoodleCenter(noodle, .2));
      if(this.drawSource(noodle, row, col)){
         g2.setColor(this.theme[2]);
         g2.fill(this.layout.getNoodleCenter(noodle, -.1));
      }
      if(row == -1 || row == rows || col == -1 || col == cols){
         g2.setColor(new Color(this.theme[0].getRed(), this.theme[0].getGreen(), this.theme[0].getBlue(), 150));
         g2.fill(this.layout.getBase(noodle));
      }
      g2.setColor(this.theme[2]);
   }//end paintNoodle
   
   public boolean drawSource(Noodle noodle, int row, int col){
      if(noodle.equals(this.puzzle.getSource()))
         return true;
      if(row == this.grid.getRow(this.puzzle.getSource()) && (col == -1 && this.grid.getCol(puzzle.getSource()) == this.grid.getNoodles()[0].length-1 || col == this.grid.getNoodles()[0].length && this.grid.getCol(puzzle.getSource()) == 0))
         return true;
      if(col == this.grid.getCol(this.puzzle.getSource()) && (row == -1 && this.grid.getRow(puzzle.getSource()) == this.grid.getNoodles().length-1 || row == this.grid.getNoodles().length && this.grid.getRow(puzzle.getSource()) == 0))
         return true;
      return false;
   }
   
   public void setCustomSize(Dimension newSize){
      Dimension oldSize = this.getPreferredSize(this.getWrap());
      double newRatio = newSize.getWidth() / newSize.getHeight();
      double oldRatio = oldSize.getWidth() / oldSize.getHeight();
      
      if(newRatio < oldRatio){
         this.layout.setSize(getCustomWidth(newSize.getWidth(), this.getWrap()));
         this.layout.setMarginX(0);
         this.layout.setMarginY((newSize.getHeight() - this.getPreferredSize(this.getWrap()).getHeight()) / 2);
      }
      else{
         this.layout.setSize(getCustomHeight(newSize.getHeight(), this.getWrap()));
         this.layout.setMarginX((newSize.getWidth() - this.getPreferredSize(this.getWrap()).getWidth()) / 2);
         this.layout.setMarginY(0);
      }
   }//end setCustomSize
   public abstract double getCustomWidth(double newWidth, boolean wrap);
   public abstract double getCustomHeight(double newHeight, boolean wrap);
   public abstract Dimension getPreferredSize(boolean wrap);
}//end PanelStrategy class