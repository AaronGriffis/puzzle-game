//Aaron Griffis

package noodles.square;
import noodles.*;
import java.awt.Shape;
import java.awt.geom.*;

public class SquareLayout extends Layout{

   public SquareLayout(boolean wrap){
      super(1, Math.sqrt(2), 0.5d, 0, wrap, 4);
   }
   
   public Point2D.Double getOrigin(boolean wrap){
      double xOrigin = (wrap ? ((super.getSize() + super.getPadding()) * 2) : 0) + super.getSize() + super.getPadding() + super.getMarginX();
      double yOrigin = (wrap ? ((super.getSize() + super.getPadding()) * 2) : 0) + super.getSize() + super.getPadding() + super.getMarginY();
      return new Point2D.Double(xOrigin, yOrigin);
   }//end getOrigin
   
   public int getStroke(){
      return super.getSize() * 1.5 * Layout.NOODLERATIO > 0 ? (int)(super.getSize() * 1.5 * Layout.NOODLERATIO) : 1;
   }//end getStroke
   
   public Shape getNoodleCenter(Noodle noodle, double scale){
      Point2D.Double center = this.noodleToPixel(noodle);
      double size = super.getSize() * 1.5 * (Layout.NOODLERATIO + scale);
      double round = size - this.getStroke();
      return new RoundRectangle2D.Double(center.getX() - size/2, center.getY() - size/2, size, size, round, round);
   }//end getNoodleCenter
   
   public Point2D.Double noodleToPixel(Noodle noodle){
      Square square = (Square)noodle;
      double x = square.getCol() * ((super.getSize() + super.getPadding()) * 2);
      double y = square.getRow() * ((super.getSize() + super.getPadding()) * 2);
      return new Point2D.Double(x + this.getOrigin(super.getWrap()).getX(), y + this.getOrigin(super.getWrap()).getY());
   }//end noodleToPixel
   
   public Noodle pixelToNoodle(Point2D.Double point){
      double x = (point.getX() - this.getOrigin(super.getWrap()).getX()) / ((super.getSize() + super.getPadding()) * 2);
      double y = (point.getY() - this.getOrigin(super.getWrap()).getY()) / ((super.getSize() + super.getPadding()) * 2);
      int row = (int)(Math.round(y));
      int col = (int)(Math.round(x));
      return new Square(row, col);
   }//end pixelToNoodle
}//end SquareLayout class