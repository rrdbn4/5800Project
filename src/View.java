package code;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

/*
Robert Dunn, Holly Busken, Matt Lindner
*/
public class View extends JPanel implements ChangeListener
{
  Node[] nodes;
  final int numNodes = 4;

  int x1, x2, x3, x4;
  int y1, y2, y3; 

  final int height=600;
  final int width=800;

  JSlider speed1, speed2, speed3, speed4;
  
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
    nodes = new Node[numNodes];
    nodes[0] = new Node(0,"red",x1,y1+num,Direction.NORTH, this);
    nodes[1] = new Node(1,"red",x1,y3-num,Direction.NORTH, this);
    nodes[2] = new Node(2,"blue",x4,y1+num,Direction.SOUTH, this);
    nodes[3] = new Node(3,"blue",x4,y3-num,Direction.SOUTH, this); 
    
    speed1 = new JSlider(10, 500, 200);
    speed1.setPreferredSize(new Dimension(150, 80));
    speed1.addChangeListener(this);
    speed2 = new JSlider(10, 500, 200);
    speed2.setPreferredSize(new Dimension(150, 80));
    speed2.addChangeListener(this);
    speed3 = new JSlider(10, 500, 200);
    speed3.setPreferredSize(new Dimension(150, 80));
    speed3.addChangeListener(this);
    speed4 = new JSlider(10, 500, 200);
    speed4.setPreferredSize(new Dimension(150, 80));
    speed4.addChangeListener(this);
    add(speed1);
    add(speed2);
    add(speed3);
    add(speed4);
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
    final int diameter = 10;
    final int rad = diameter/2;
    
    g.setColor(Color.red);
    for(int i = 0; i < numNodes / 2; i++)
      g.fillOval(nodes[i].x-rad,nodes[i].y-rad,diameter,diameter);
    g.setColor(Color.blue);
    for(int i = numNodes / 2; i < numNodes; i++)
      g.fillOval(nodes[i].x-rad,nodes[i].y-rad,diameter,diameter);
  }

  public void stateChanged(ChangeEvent e)
  {
	if (e.getSource() == speed1)
	{
		nodes[1].setSpeed(speed1.getValue());
	}
	else if (e.getSource() == speed2)
	{
		nodes[2].setSpeed(speed2.getValue());
	}
	else if (e.getSource() == speed3)
	{
		nodes[3].setSpeed(speed3.getValue());
	}
	else if (e.getSource() == speed4)
	{
		nodes[4].setSpeed(speed4.getValue());
	}
  }
}
