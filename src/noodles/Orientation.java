//Aaron Griffis

package noodles;

public class Orientation{
   private double[][] forward;
   private double[][] inverse;
   private double startAngle;
   
   public Orientation(double f0, double f1, double f2, double f3, double b0, double b1, double b2, double b3){
      this.forward = new double[][]{{f0, f1}, {f2, f3}};
      this.inverse = new double[][]{{b0, b1}, {b2, b3}};
   }
   
   //get methods
   public double[][] getForward(){return this.forward;}
   public double[][] getInverse(){return this.inverse;}
}//end Orientation class