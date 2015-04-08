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
public class View extends JPanel
{
  Node redA;
  Node redB;
  Node blueA;
  Node blueB;
  int x1, x2, x3, x4;
  int y1, y2, y3;  
  final int height=600;
  final int width=800;

  boolean firstTime = true;
  public View()
  {
    setPreferredSize(new Dimension(width, height)); 
    setVisible(true);
    validate();

    x1 = getInsets().left + (int)(width * 0.15);
    x2 = getInsets().left + (int)(width * 0.35);
    x3 = getInsets().left + (int)(width * 0.65);
    x4 = getInsets().left + (int)(width * 0.85);
    y1 = getInsets().top + (int)(height * 0.2);
    y2 = getInsets().top + (int)(height * 0.5);
    y3 = getInsets().top + (int)(height * 0.8);
  
  
    int num = (y3-y1)/3;
    
    //nodes are self-managing and update themselves
    redA=new Node(1,"red",x1,y1+num,Direction.NORTH, this);
    redB=new Node(2,"red",x1,y3-num,Direction.NORTH, this);
    blueA=new Node(3,"blue",x4,y1+num,Direction.SOUTH, this);
    blueB=new Node(4,"blue",x4,y3-num,Direction.SOUTH, this); 
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

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
  
    //draw Nodes
    final int diameter=10;
    final int rad=diameter/2;
    g.setColor(Color.red);
    g.fillOval(redA.x-rad,redA.y-rad,diameter,diameter);
    g.fillOval(redB.x-rad,redB.y-rad,diameter,diameter);
    g.setColor(Color.blue);
    g.fillOval(blueA.x-rad,blueA.y-rad,diameter,diameter);
    g.fillOval(blueB.x-rad,blueB.y-rad,diameter,diameter);
  }
}
