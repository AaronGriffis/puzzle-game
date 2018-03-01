//Aaron Griffis

package noodles;

public abstract class Noodle{
   private boolean powered;
   private boolean[] activeSides;
   private int rotation;
   
   public Noodle(boolean[] activeSides){
      this.powered = false;
      this.activeSides = activeSides;
      this.rotation = 0;
   }
   
   public Noodle(boolean powered, boolean[] activeSides){
      this.powered = powered;
      this.activeSides = activeSides;
   }
   
   //get methods
   public boolean getPowered(){return this.powered;}
   public boolean[] getActiveSides(){return this.activeSides;}
   public int getRotation(){return this.rotation;}
   public int getActiveCount(){
      int count = 0;
      for(boolean bool : this.activeSides){
         if(bool)
            count++;
      }
      return count;
   }
   
   //set methods
   public void setPowered(boolean powered){this.powered = powered;}
   public void setActive(int dir, boolean active){this.activeSides[dir] = active;}
   public void setActiveSides(boolean[] activeSides){this.activeSides = activeSides;}
   public void setRotation(int rotation){this.rotation = rotation;}
   
   public boolean isVisited(){
      boolean visited = false;
      for(boolean bool : this.activeSides){
         if(bool)
            visited = true;
      }
      return visited;
   }//end isVisited
   
   public void rotate(int amount){
      int size = this.activeSides.length;
      amount %= size;
      if(amount < 0)
         amount += size;
      for(int i = 0; i < amount; i++){
         boolean[] temp = new boolean[size];
         for(int j = 0; j < size; j++)
            temp[j] = this.activeSides[((j + size) - 1) % size];
         this.activeSides = temp;
      }
      this.rotation += amount;
   }//end rotate
}//end Noodle class