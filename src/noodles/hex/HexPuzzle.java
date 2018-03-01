//Aaron Griffis

package noodles.hex;
import noodles.*;

public class HexPuzzle extends Puzzle{

   public HexPuzzle(Grid grid, boolean wrap){
      super(grid, new int[]{0, 1, 2, 3, 4, 5}, 4, wrap);
   }
}//end HexPuzzle class