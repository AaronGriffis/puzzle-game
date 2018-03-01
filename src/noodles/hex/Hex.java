//Aaron Griffis
//code inspired by RedBlobGames
//http://www.redblobgames.com/grids/hexagons/

package noodles.hex;
import noodles.Noodle;

public class Hex extends Noodle{
   private int q;
   private int r;
   
   public Hex(int q, int r){
      super(new boolean[6]);
      this.q = q;
      this.r = r;
   }
   
   public Hex(int q, int r, boolean powered, boolean[] activeSides){
      super(powered, activeSides);
      this.q = q;
      this.r = r;
   }
   
   //get methods
   public int getQ(){return this.q;}
   public int getR(){return this.r;}
   public int getS(){return -this.q - this.r;}
}//end Hex class