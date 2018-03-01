//Aaron Griffis

package noodles;
import noodles.hex.*;
import noodles.square.*;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class Noodles implements ActionListener, ChangeListener, ItemListener{
   private Map<JComponent, Integer> colorComponents = new HashMap<>();
   private Border lowered;
   private Font font = new Font(Font.DIALOG, Font.BOLD, 20);
   private Noodle hexIcon, squareIcon;
   private NoodlePanel noodlePanel;
   private JButton regenBtn;
   private JCheckBox wrapCheck;
   private JComboBox<String> themeBox;
   private JFrame frame;
   private JLabel rowLabel, colLabel;
   private JPanel mainPanel, puzzlePanel, shapePanel, sizePanel, wrapPanel;
   private JRadioButton hexBtn, squareBtn;
   private JSpinner rowSpinner, colSpinner;
   
   public Noodles(){
      noodlePanel = new NoodlePanel(new HexPanel(3, 4, ColorTheme.getThemeNames()[2], true));
      initButtons();
      initComboBox();
      initSpinners();
      initLabels();
      initPanels();
      initBorders();
      initTheme();
      initFrame();
   }
   
   public static void main(String [] args){
      SwingUtilities.invokeLater(new Runnable(){
         @Override
         public void run(){
            new Noodles();
         }
      });
   }//end main
   
   public void actionPerformed(ActionEvent e){
      if(e.getActionCommand().equals("regen"))
         noodlePanel.regenerate();
      else if(e.getActionCommand().equals("theme")){
         JComboBox cb = (JComboBox)e.getSource();
         String theme = (String)cb.getSelectedItem();
         noodlePanel.setTheme(theme);
         initTheme();
      }
      else if(e.getActionCommand().equals("wrap")){
         noodlePanel.setWrap(((JCheckBox)e.getSource()).isSelected());
      }
   }//end actionPerformed
   
   public void stateChanged(ChangeEvent e){
      JSpinner source = (JSpinner)e.getSource();
      if(source.equals(rowSpinner))
         noodlePanel.setRows((int)rowSpinner.getValue());
      else if(source.equals(colSpinner)){
         noodlePanel.setCols((int)colSpinner.getValue());
         if(noodlePanel.getStrategy() instanceof HexPanel)
            setWrapCheck(noodlePanel.getCols() % 2 != 0 ? false : true);
      }
   }//end stateChanged
   
   public void itemStateChanged(ItemEvent e){
      JRadioButton source = (JRadioButton)e.getSource();
      if(source.equals(hexBtn) && e.getStateChange() == ItemEvent.SELECTED){
         setWrapCheck(noodlePanel.getCols() % 2 != 0 ? false : true);
         noodlePanel.setStrategy(new HexPanel(noodlePanel.getRows(), noodlePanel.getCols(), noodlePanel.getThemeName(), noodlePanel.getWrap()));
      }
      else if(source.equals(squareBtn) && e.getStateChange() == ItemEvent.SELECTED){
         setWrapCheck(true);
         noodlePanel.setStrategy(new SquarePanel(noodlePanel.getRows(), noodlePanel.getCols(), noodlePanel.getThemeName(), noodlePanel.getWrap()));
      }
   }//end itemStateChanged
   
   public void setWrapCheck(boolean enabled){
      wrapCheck.setEnabled(enabled);
      noodlePanel.setWrap(enabled ? wrapCheck.isSelected() : false);
   }//end setWrapCheck
   
   private JSpinner createSpinner(int init){
      JSpinner spinner = new JSpinner(new SpinnerNumberModel(init, 3, 30, 1));
      colorComponents.put((JComponent)spinner.getEditor().getComponent(0), 0b111);
      spinner.setFont(font);
      spinner.addChangeListener(this);
      return spinner;
   }//end createSpinner
   
   private JLabel createLabel(String text){
      JLabel label = new JLabel(text, JLabel.RIGHT);
      label.setFont(font);
      colorComponents.put(label, 0b100);
      return label;
   }
   
   private JRadioButton createRadioButton(ButtonGroup group, String text, boolean selected){
      JRadioButton button = new JRadioButton(text);
      initButton(button, selected, 0b110);
      group.add(button);
      button.addItemListener(this);
      return button;
   }//end createRadioButton
   
   private void initButton(AbstractButton button, boolean selected, int binaryColors){
      colorComponents.put(button, binaryColors);
      button.setFont(font);
      button.setFocusPainted(false);
      button.setSelected(selected);
   }//end initButton
   
   private void initButtons(){
      regenBtn = new JButton("New Puzzle");
      regenBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
      initButton(regenBtn, false, 0b111);
      regenBtn.setActionCommand("regen");
      regenBtn.addActionListener(this);
      
      ButtonGroup group = new ButtonGroup();
      hexBtn = createRadioButton(group, "Hexagons", true);
      squareBtn = createRadioButton(group, "Squares", false);
      
      wrapCheck = new JCheckBox("Wrap Edges");
      initButton(wrapCheck, noodlePanel.getWrap(), 0b110);
      wrapCheck.setActionCommand("wrap");
      wrapCheck.addActionListener(this);
   }//end initButtons
   
   private void initComboBox(){
      themeBox = new JComboBox<>(ColorTheme.getThemeNames());
      colorComponents.put(themeBox, 0b110);
      themeBox.setFont(font);
      themeBox.setSelectedItem(noodlePanel.getThemeName());
      themeBox.addActionListener(this);
      themeBox.setActionCommand("theme");
   }//end initComboBox
   
   private void initSpinners(){
      rowSpinner = createSpinner(noodlePanel.getRows());
      colSpinner = createSpinner(noodlePanel.getCols());
   }//end initSpinners
   
   private void initLabels(){
      rowLabel = createLabel("Rows:");
      colLabel = createLabel("Columns:");
   }//end initLabels
   
   private void initPanels(){
      shapePanel = new JPanel();
      colorComponents.put(shapePanel, 0b010);
      GroupLayout shapeLayout = new GroupLayout(shapePanel);
      shapePanel.setLayout(shapeLayout);
      shapeLayout.setAutoCreateGaps(true);
      shapeLayout.setAutoCreateContainerGaps(true);
      GroupLayout.ParallelGroup shapeHorizGroup = shapeLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
      shapeHorizGroup.addGroup(shapeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(hexBtn)
         .addComponent(squareBtn));
      shapeLayout.setHorizontalGroup(shapeHorizGroup);
      GroupLayout.SequentialGroup shapeVertGroup = shapeLayout.createSequentialGroup();
      shapeVertGroup.addGroup(shapeLayout.createSequentialGroup()
         .addComponent(hexBtn)
         .addComponent(squareBtn));
      shapeLayout.setVerticalGroup(shapeVertGroup);
      
      sizePanel = new JPanel();
      colorComponents.put(sizePanel, 0b010);
      GroupLayout sizeLayout = new GroupLayout(sizePanel);
      sizePanel.setLayout(sizeLayout);
      sizeLayout.setAutoCreateGaps(true);
      sizeLayout.setAutoCreateContainerGaps(true);
      GroupLayout.ParallelGroup sizeHorizGroup = sizeLayout.createParallelGroup(GroupLayout.Alignment.TRAILING);
      sizeHorizGroup.addGroup(sizeLayout.createSequentialGroup()
            .addComponent(rowLabel)
            .addComponent(rowSpinner))
         .addGroup(sizeLayout.createSequentialGroup()
            .addComponent(colLabel)
            .addComponent(colSpinner));
      sizeLayout.setHorizontalGroup(sizeHorizGroup);
      GroupLayout.SequentialGroup sizeVertGroup = sizeLayout.createSequentialGroup();
      sizeVertGroup.addGroup(sizeLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(rowLabel)
            .addComponent(rowSpinner))
         .addGroup(sizeLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(colLabel)
            .addComponent(colSpinner));
      sizeLayout.setVerticalGroup(sizeVertGroup);
      sizeLayout.linkSize(SwingConstants.HORIZONTAL, rowSpinner, colSpinner);
      
      wrapPanel = new JPanel();
      colorComponents.put(wrapPanel, 0b010);
      wrapPanel.add(wrapCheck);
      
      puzzlePanel = new JPanel(new BorderLayout());
      colorComponents.put(puzzlePanel, 0b010);
      puzzlePanel.add(noodlePanel, BorderLayout.CENTER);
      
      mainPanel = new JPanel();
      GroupLayout mainLayout = new GroupLayout(mainPanel);
      mainPanel.setLayout(mainLayout);
      mainLayout.setAutoCreateGaps(true);
      mainLayout.setAutoCreateContainerGaps(true);
      GroupLayout.SequentialGroup mainHorizGroup = mainLayout.createSequentialGroup();
      mainHorizGroup.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(regenBtn)
            .addComponent(shapePanel)
            .addComponent(sizePanel)
            .addComponent(wrapPanel)
            .addComponent(themeBox))
         .addComponent(puzzlePanel);
      mainLayout.setHorizontalGroup(mainHorizGroup);
      GroupLayout.ParallelGroup mainVertGroup = mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
      mainVertGroup.addGroup(mainLayout.createSequentialGroup()
            .addComponent(regenBtn)
            .addComponent(shapePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(sizePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(wrapPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(themeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
         .addComponent(puzzlePanel);
      mainLayout.setVerticalGroup(mainVertGroup);
      mainLayout.linkSize(SwingConstants.HORIZONTAL, shapePanel, sizePanel, wrapPanel, themeBox, regenBtn);
   }//end initPanels
   
   private void initBorders(){
      lowered = BorderFactory.createLoweredBevelBorder();
      shapePanel.setBorder(lowered);
      sizePanel.setBorder(lowered);
      wrapPanel.setBorder(lowered);
      puzzlePanel.setBorder(new CompoundBorder(lowered, BorderFactory.createEmptyBorder(20, 20, 20, 20)));
   }//end initBorders
   
   private void initTheme(){
      mainPanel.setBackground(noodlePanel.getTheme()[2]);
      noodlePanel.setBackground(noodlePanel.getTheme()[0]);
      Color bg = noodlePanel.getTheme()[0];
      Color fg = noodlePanel.getTheme()[1];
      
      Iterator iter = colorComponents.entrySet().iterator();
      while(iter.hasNext()){
         Map.Entry comp = (Map.Entry)iter.next();
         if(((int)comp.getValue() & 0b010) == 0b010)
            ((JComponent)comp.getKey()).setBackground(((int)comp.getValue() & 0b001) == 0b001 ? fg : bg);
         if(((int)comp.getValue() & 0b100) == 0b100){
            ((JComponent)comp.getKey()).setForeground(((int)comp.getValue() & 0b001) == 0b001 ? bg : fg);
         }
      }
   }//end initTheme
   
   private void initFrame(){
      frame = new JFrame("Noodles");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(mainPanel);
      frame.pack();
      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }//end initFrame
}//end Noodles class