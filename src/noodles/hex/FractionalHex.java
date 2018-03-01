//Aaron Griffis
//code inspired by RedBlobGames
//http://www.redblobgames.com/grids/hexagons/

package noodles.hex;

public class FractionalHex {
   private double q;
   private double r;
   
   public FractionalHex(double q, double r){
      this.q = q;
      this.r = r;
   }//end EVC
   
   //get methods
   public double getQ(){return this.q;}
   public double getR(){return this.r;}
   public double getS(){return -this.q - this.r;}
   
   public static Hex hexRound(FractionalHex frac){
      int q = (int)(Math.round(frac.getQ()));
      int r = (int)(Math.round(frac.getR()));
      int s = (int)(Math.round(frac.getS()));
      double qDiff = Math.abs(q - frac.getQ());
      double rDiff = Math.abs(r - frac.getR());
      double sDiff = Math.abs(s - frac.getS());
      if(qDiff > rDiff && qDiff > sDiff)
         q = -r - s;
      else if(rDiff > sDiff)
         r = -q - s;
      else
         s = -q - r;
      return new Hex(q, r);
   }//end hexRound
}//end FractionalHex class