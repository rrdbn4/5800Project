package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
Robert Dunn, Holly Busken, Matt Lindner
*/
public class Driver extends JFrame
{

  public Driver() 
  {
    super("Ricart and Agrawala");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800,600); 
    setVisible(true);
    
    validate();
    repaint();
  } 

  public static void main(String[] args)
  {
    new Driver();

  }

  public void paint(Graphics g)
  {
    super.paint(g);
    g.drawOval(100, 100, 100, 100);
  }
}