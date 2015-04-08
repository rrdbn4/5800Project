package code;

import javax.swing.*;

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
    setResizable(false);
    add(new View());
    validate();
  } 

  public static void main(String[] args)
  {
    new Driver();
  }
}
