//Aaron Griffis

package noodles;
import java.awt.Shape;
import java.awt.geom.*;

public abstract class Layout{
   public static final double PADDINGRATIO = 0.05;
   public static final double NOODLERATIO = 0.375;
   private double size, marginX, marginY, innerScale, outerScale, cornerAngle, edgeAngle;
   private boolean wrap;
   private int segments;
   
   public Layout(double innerScale, double outerScale, double cornerAngle, double edgeAngle, boolean wrap, int segments){
      this.size = 27;
      this.marginX = 0;
      this.marginY = 0;
      this.innerScale = innerScale;
      this.outerScale = outerScale;
      this.cornerAngle = cornerAngle;
      this.edgeAngle = edgeAngle;
      this.wrap = wrap;
      this.segments = segments;
   }
   
   //get methods
   public double getSize(){return this.size;}
   public double getPadding(){return this.size * PADDINGRATIO;}
   public double getMarginX(){return this.marginX;}
   public double getMarginY(){return this.marginY;}
   public double getCornerAngle(){return this.cornerAngle;}
   public boolean getWrap(){return this.wrap;}
   public int getSegments(){return this.segments;}
   
   //set methods
   public void setSize(double size){this.size = (size >= 1 ? size : 1);}
   public void setMarginX(double marginX){this.marginX = marginX;}
   public void setMarginY(double marginY){this.marginY = marginY;}
   public void setWrap(boolean wrap){this.wrap = wrap;}
   public void setSegments(int segments){this.segments = segments;}
   
   public Shape getBase(Noodle noodle){
      Point2D.Double[] corners = this.getCorners(this.cornerAngle, noodle, this.outerScale);
      Path2D.Double path = new Path2D.Double();
      path.moveTo(corners[0].getX(), corners[0].getY());
      for(int i = 1; i < segments; i++){
         path.lineTo(corners[i].getX(), corners[i].getY());
      }
      path.lineTo(corners[0].getX(), corners[0].getY());
      return path;
   }//end getBase
   
   private Point2D.Double[] getCorners(double startAngle, Noodle noodle, double scale){
      Point2D.Double[] corners = new Point2D.Double[this.segments];
      Point2D.Double center = noodleToPixel(noodle);
      for(int i = 0; i < this.segments; i++){
          Point2D.Double offset = cornerOffset(startAngle, i, scale);
          corners[i] = new Point2D.Double(center.getX() + offset.getX(), center.getY() + offset.getY());
      }
      return corners;
   }//end getCorners
   
   public Shape getNoodleSide(Noodle noodle, int side){
      Point2D.Double[] edges = this.getCorners(this.edgeAngle, noodle, this.innerScale);
      Point2D.Double center = noodleToPixel(noodle);
      Line2D.Double edge = new Line2D.Double(center, edges[side]);
      return edge;
   }//end getNoodleSide
   
   private Point2D.Double cornerOffset(double startAngle, int corner, double scale){
      double angle = 2 * Math.PI * (corner + startAngle) / segments;
      return new Point2D.Double(this.size * scale * Math.cos(angle), this.size * scale * Math.sin(angle));
   }//end hexCornerOffset
   
   public abstract Point2D.Double getOrigin(boolean wrap);
   public abstract int getStroke();
   public abstract Shape getNoodleCenter(Noodle noodle, double scale);
   public abstract Point2D.Double noodleToPixel(Noodle noodle);
   public abstract Noodle pixelToNoodle(Point2D.Double point);
}//end Layout class