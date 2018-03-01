//Aaron Griffis
//code inspired by RedBlobGames
//http://www.redblobgames.com/grids/hexagons/

package noodles.hex;
import noodles.*;
import java.awt.Shape;
import java.awt.geom.*;

public class HexLayout extends Layout{
   public static final Orientation FLAT = new Orientation(3/2d, 0, Math.sqrt(3)/2d, Math.sqrt(3), 2/3d, 0, -1/3d, Math.sqrt(3)/3d);
   public static final Orientation POINTY = new Orientation(Math.sqrt(3), Math.sqrt(3)/2d, 0, 3/2d, Math.sqrt(3)/3d, -1/3d, 0, 2/3d);
   
   public HexLayout(boolean wrap){
      super(Math.sqrt(3)/2, 1, 0, 0.5d, wrap, 6);
   }
   
   public Point2D.Double getOrigin(boolean wrap){
      double xOrigin = (wrap ? (3/4d * (super.getSize() + super.getPadding()) * 2) : 0) + super.getSize() + super.getPadding() + super.getMarginX();
      double yOrigin = (wrap ? 4 : 2) * ((super.getSize() + super.getPadding()) * Math.sqrt(3)/2d) + super.getMarginY();
      return new Point2D.Double(xOrigin, yOrigin);
   }//end getOrigin
   
   public int getStroke(){
      return super.getSize() * Layout.NOODLERATIO > 0 ? (int)(super.getSize() * Layout.NOODLERATIO) : 1;
   }//end getStroke
   
   public Shape getNoodleCenter(Noodle noodle, double scale){
      Point2D.Double center = noodleToPixel(noodle);
      double size = super.getSize() * (Layout.NOODLERATIO + scale);
      return new Ellipse2D.Double(center.getX() - size/2, center.getY() - size/2, size, size);
   }//end getNoodleCenter
   
   public Point2D.Double noodleToPixel(Noodle noodle){
      Hex hex = (Hex)noodle;
      double[][] matrix = HexLayout.FLAT.getForward();
      double x = (matrix[0][0] * hex.getQ() + matrix[0][1] * hex.getR()) * (super.getSize() + super.getPadding());
      double y = (matrix[1][0] * hex.getQ() + matrix[1][1] * hex.getR()) * (super.getSize() + super.getPadding());
      return new Point2D.Double(x + this.getOrigin(super.getWrap()).getX(), y + this.getOrigin(super.getWrap()).getY());
   }//end noodleToPixel
      
   public Noodle pixelToNoodle(Point2D.Double point){
      double x = (point.getX() - this.getOrigin(super.getWrap()).getX()) / (super.getSize() + super.getPadding());
      double y = (point.getY() - this.getOrigin(super.getWrap()).getY()) / (super.getSize() + super.getPadding());
      double[][] matrix = this.FLAT.getInverse();
      double q = (matrix[0][0] * x + matrix[0][1] * y);
      double r = (matrix[1][0] * x + matrix[1][1] * y);
      return FractionalHex.hexRound(new FractionalHex(q, r));
   }//end pixelToNoodle
}//end HexLayout class