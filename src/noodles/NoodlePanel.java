//Aaron Griffis

package noodles;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NoodlePanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener{
   private PanelStrategy strategy;
   private Graphics2D g2;
   
   public NoodlePanel(PanelStrategy strategy){
      this.strategy = strategy;
      addMouseListener(this);
      addMouseMotionListener(this);
      addComponentListener(this);
   }
   
   //get methods
   public PanelStrategy getStrategy(){return this.strategy;}
   public int getRows(){return this.strategy.getRows();}
   public int getCols(){return this.strategy.getCols();}
   public boolean getWrap(){return this.strategy.getWrap();}
   public String getThemeName(){return this.strategy.getThemeName();}
   public Color[] getTheme(){return this.strategy.getTheme();}
   
   //set methods
   public void setRows(int rows){
      this.strategy.setRows(rows);
      this.resetView();
   }
   public void setCols(int cols){
      this.strategy.setCols(cols);
      this.resetView();
   }
   public void setStrategy(PanelStrategy strategy){
      this.strategy = strategy;
      this.resetView();
   }
   public void setWrap(boolean wrap){
      this.strategy.setWrap(wrap);
      this.resetView();
   }
   public void setTheme(String theme){
      this.strategy.setTheme(theme);
      this.repaint();
   }
   
   public void regenerate(){
      this.strategy.regenerate();
      this.repaint();
   }//end regenerate
   
   public void resetView(){
      this.strategy.setCustomSize(this.getSize());
      this.revalidate();
      this.repaint();
   }//end resetView
   
   public void mouseClicked(MouseEvent e){}
   public void mouseEntered(MouseEvent e){}
   public void mouseExited(MouseEvent e){}
   public void mouseMoved(MouseEvent e){}
   public void mousePressed(MouseEvent e){
      this.strategy.mousePressed(e, this);
      this.repaint();
   }
   public void mouseReleased(MouseEvent e){
      this.strategy.mouseReleased();
      this.repaint();
   }
   public void mouseDragged(MouseEvent e){
      this.strategy.mouseDragged(e);
      this.repaint();
   }
   
   public void componentResized(ComponentEvent e){
      this.strategy.setCustomSize(e.getComponent().getSize());
      this.repaint();
   }
   public void componentHidden(ComponentEvent e){}
   public void componentMoved(ComponentEvent e){}
   public void componentShown(ComponentEvent e){}
   
   protected void paintComponent(Graphics g){
      this.g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      super.paintComponent(g2);
      strategy.paintComponent(g2);
   }//end paintComponent
   
   public Dimension getPreferredSize(){
      return this.strategy.getPreferredSize(this.strategy.getWrap());
   }//end getPreferredSize
}//end NoodlePanel class