//Aaron Griffis

package noodles;

import java.util.Random;
import java.util.ArrayList;

public abstract class Puzzle{
   private Random rand;
   private int[] dirs;
   private int maxSides;
   private Noodle source;
   private boolean solved;
   
   public Puzzle(Grid grid, int[] dirs, int maxSides, boolean wrap){
      this.rand = new Random();
      this.dirs = dirs;
      this.maxSides = maxSides;
      this.solved = false;
      this.initPuzzle(grid, wrap);
   }
   
   //get methods
   public Noodle getSource(){return this.source;}
   public boolean isSolved(){return this.solved;}
   
   public void initPuzzle(Grid grid, boolean wrap){
      int rows = grid.getRows();
      int cols = grid.getCols();
      int sideCount = this.dirs.length;
      grid.resetActiveSides(sideCount);
      ArrayList<Noodle> activeNoodles = new ArrayList<>();
      this.source = grid.getNoodle(rand.nextInt(rows), rand.nextInt(cols));
      activeNoodles.add(this.source);
      
      while(!activeNoodles.isEmpty()){
         Noodle cur = activeNoodles.get(rand.nextInt(activeNoodles.size()));
         boolean valid = false;
         this.shuffleDirections();
         for(int i = 0; i < sideCount && !valid; i++){
            int dir = this.dirs[i];
            Noodle neighbor = grid.getNeighbor(cur, dir, wrap);
            if(neighbor != null && !neighbor.isVisited() && cur.getActiveCount() < maxSides && neighbor.getActiveCount() < maxSides){
               activeNoodles.add(neighbor);
               cur.setActive(dir, true);
               neighbor.setActive((dir + sideCount/2) % sideCount, true);
               valid = true;
            }
         }
         if(!valid)
            activeNoodles.remove(cur);
      }
      this.shuffleNoodles(grid.getNoodles());
      this.testPowered(grid, wrap);
   }//end initPuzzle
   
   public void testPowered(Grid grid, boolean wrap){
      int rows = grid.getRows();
      int cols = grid.getCols();
      int sideCount = this.dirs.length;
      int totalPowered = 0;
      grid.resetPower();
      ArrayList<Noodle> poweredNoodles = new ArrayList<>();
      this.source.setPowered(true);
      totalPowered++;
      poweredNoodles.add(this.source);
      
      while(!poweredNoodles.isEmpty()){
         Noodle cur = poweredNoodles.get(poweredNoodles.size() - 1);
         boolean valid = false;
         boolean[] activeSides = cur.getActiveSides();
         for(int i = 0; i < sideCount && !valid; i++){
            if(activeSides[i]){
               Noodle neighbor = grid.getNeighbor(cur, i, wrap);
               if(neighbor != null && !neighbor.getPowered() && neighbor.getActiveSides()[(i + sideCount/2) % sideCount]){
                  neighbor.setPowered(true);
                  totalPowered++;
                  poweredNoodles.add(neighbor);
                  valid = true;
               }
            }
         }
         if(!valid)
            poweredNoodles.remove(cur);
      }
      if(totalPowered == rows * cols)
         this.solved = true;
      else
         this.solved = false;
   }//end testPowered
   
   protected void shuffleDirections(){
      for(int i = 0; i < this.dirs.length; i++){
         int randIndex = this.rand.nextInt(this.dirs.length);
         int temp = this.dirs[i];
         this.dirs[i] = this.dirs[randIndex];
         this.dirs[randIndex] = temp;         
      }
   }//end shuffleDirections
   
   protected void shuffleNoodles(Noodle[][] noodles){
      for(Noodle[] row : noodles){
         for(Noodle noodle : row){
            noodle.rotate(this.rand.nextInt(dirs.length));
         }
      }
   }//end shuffleNoodles
}//end Puzzle class