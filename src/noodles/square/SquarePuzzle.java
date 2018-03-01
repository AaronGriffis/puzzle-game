//Aaron Griffis

package noodles.square;
import noodles.*;

public class SquarePuzzle extends Puzzle{

   public SquarePuzzle(Grid grid, boolean wrap){
      super(grid, new int[]{0, 1, 2, 3}, 3, wrap);
   }
}//end SquarePuzzle