package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
Robert Dunn, Holly Busken, Matt Lindner
*/
public class Driver extends JFrame
{
  Node redA;
  Node redB;
  Node blueA;
  Node blueB;
  int x1, x2, x3, x4;
  int y1, y2, y3;  
  public Driver() 
  {
    super("Ricart and Agrawala");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800,600); 
    setVisible(true);
    validate();
    repaint();
	
	x1 = getInsets().left + (int)(getWidth() * 0.15);
    x2 = getInsets().left + (int)(getWidth() * 0.35);
    x3 = getInsets().left + (int)(getWidth() * 0.65);
    x4 = getInsets().left + (int)(getWidth() * 0.85);
    y1 = getInsets().top + (int)(getHeight() * 0.2);
    y2 = getInsets().top + (int)(getHeight() * 0.5);
    y3 = getInsets().top + (int)(getHeight() * 0.8);
	
	int num = (y3-y1)/3;
	
	redA=new Node(1,"red",x1,y1+num);
	redB=new Node(2,"red",x1,y3-num);
	blueA=new Node(3,"blue",x4,y1+num);
	blueB=new Node(4,"blue",x4,y3-num);
	
	
	repaint();
  } 

  public static void main(String[] args)
  {
    new Driver();
  }

  public void paint(Graphics g)
  {
    super.paint(g);

    //left triangle
    g.drawLine(x1, y1, x1, y3);
    g.drawLine(x1, y1, x2, y2);
    g.drawLine(x1, y3, x2, y2);

    //bridge
    g.drawLine(x2, y2, x3, y2);

    //right triangle
    g.drawLine(x3, y2, x4, y1);
    g.drawLine(x4, y1, x4, y3);
    g.drawLine(x3, y2, x4, y3);
	
	int diameter=10;
	int rad=diameter/2;
	g.setColor(Color.red);
	g.fillOval(redA.x-rad,redA.y-rad,diameter,diameter);
	g.fillOval(redB.x-rad,redB.y-rad,diameter,diameter);
	g.setColor(Color.blue);
	g.fillOval(blueA.x-rad,blueA.y-rad,diameter,diameter);
	g.fillOval(blueB.x-rad,blueB.y-rad,diameter,diameter);
	
	//draw nodes
  }
}
