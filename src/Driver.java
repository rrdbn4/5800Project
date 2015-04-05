package code;



import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.lang.Thread.*;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.event.*;
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
    add(new View());
    validate();
  	

    // Timer timer=new Timer(200, this);
    // timer.setInitialDelay(0);
    // timer.setRepeats(true);
    // timer.start();
  } 

  public static void main(String[] args)
  {
    new Driver();
  }

}
