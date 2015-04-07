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
public class View extends JPanel implements ActionListener
{
  Node redA;
  Node redB;
  Node blueA;
  Node blueB;
  int x1, x2, x3, x4;
  int y1, y2, y3;  

  boolean firstTime = true;
  public View()
  {
    setPreferredSize(new Dimension(800,600)); 
    setVisible(true);
    validate();    
    

    Timer timer = new Timer(200, this);
    timer.setInitialDelay(0);
    timer.setRepeats(true);
    timer.start();
  }

  public void actionPerformed(ActionEvent e)
  {
    repaint();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if(firstTime)
    {
	  /*
      x1 = getInsets().left + (int)(getWidth() * 0.15);
      x2 = getInsets().left + (int)(getWidth() * 0.35);
      x3 = getInsets().left + (int)(getWidth() * 0.65);
      x4 = getInsets().left + (int)(getWidth() * 0.85);
      y1 = getInsets().top + (int)(getHeight() * 0.2);
      y2 = getInsets().top + (int)(getHeight() * 0.5);
      y3 = getInsets().top + (int)(getHeight() * 0.8);
      */
	  
	  final int height=600;
	  final int width=800;
	  
	  x1 = getInsets().left + (int)(width * 0.15);
      x2 = getInsets().left + (int)(width * 0.35);
      x3 = getInsets().left + (int)(width * 0.65);
      x4 = getInsets().left + (int)(width * 0.85);
      y1 = getInsets().top + (int)(height * 0.2);
      y2 = getInsets().top + (int)(height * 0.5);
      y3 = getInsets().top + (int)(height * 0.8);
	  
	  
      int num = (y3-y1)/3;
      
      redA=new Node(1,"red",x1,y1+num,Direction.NORTH);
      redB=new Node(2,"red",x1,y3-num,Direction.NORTH);
      blueA=new Node(3,"blue",x4,y1+num,Direction.SOUTH);
      blueB=new Node(4,"blue",x4,y3-num,Direction.SOUTH); 

      firstTime = false;
    }


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
  
    //Update nodes
    updateNode(redA);
    updateNode(redB);
    updateNode(blueA);
    updateNode(blueB);  
  
    //draw Nodes
    int diameter=10;
    int rad=diameter/2;
    g.setColor(Color.red);
    g.fillOval(redA.x-rad,redA.y-rad,diameter,diameter);
    g.fillOval(redB.x-rad,redB.y-rad,diameter,diameter);
    g.setColor(Color.blue);
    g.fillOval(blueA.x-rad,blueA.y-rad,diameter,diameter);
    g.fillOval(blueB.x-rad,blueB.y-rad,diameter,diameter);
  }
  
  public void updateNode(Node node)
  {
    int updown=15;
    int diagonal=20;
    if(node.state == Direction.NORTH)
    {
      //Going North on track
      if(node.x==x1 && node.y==y1)
      {      
        node.state = Direction.SOUTH_EAST;
        //slope = y2-y1 / x2-x1
        int xdiff=x2-x1;
        int ydiff=y2-y1;

        node.x=node.x+(xdiff/diagonal);
        node.y=node.y+(ydiff/diagonal);
      }
      else
      {
        node.y=node.y-updown;
        if(node.y<y1)
        {
    	    node.x=x1;
          node.y=y1;
          node.state=Direction.SOUTH_EAST;
        }
      }
    }
  else if(node.state==Direction.SOUTH_EAST)
  {
    //Going South East on track
    if(node.x==x2 && node.y==y2)
    {
      //Entering the bridge going East
      node.state=Direction.EAST;
      node.x=node.x+updown;
    }
    else
    {
      int xdiff=x2-x1;
      int ydiff=y2-y1;    
      node.x=node.x+(xdiff/diagonal);
      node.y=node.y+(ydiff/diagonal);  
      if(node.x>x2 || node.y>y2)
      {
        node.x=x2;
        node.y=y2;
        node.state=Direction.EAST;
      }	
    }
  }
  else if(node.state==Direction.EAST)
  {
    //Moving East on the bridge 
    if(node.x==x3 && node.y==y2)
    {
      //Leaving the bridge to go North East
      node.state=Direction.NORTH_EAST;
      int xdiff=x4-x3;
      int ydiff=y1-y2;    
      node.x=node.x+(xdiff/diagonal);
      node.y=node.y+(ydiff/diagonal);   
    }
    else
    {
      node.x=node.x+updown;
      if(node.x>x3)
      {
        node.x=x3;
        node.y=y2;
        node.state=Direction.NORTH_EAST;
      }
    }
  }
  else if(node.state==Direction.NORTH_EAST)
  {
    //Moving North East on track
    if(node.x==x4 && node.y==y1)
    {
      node.state=Direction.SOUTH;
    node.y=node.y+updown;
    }
    else
    {
      int xdiff=x4-x3;
      int ydiff=y1-y2;    
      node.x=node.x+(xdiff/diagonal);
      node.y=node.y+(ydiff/diagonal); 
      if(node.x>x4 || node.y<y1)
      {
	    node.x=x4;
	    node.y=y1;
	    node.state=Direction.SOUTH;
      }	
    }
  }
  else if(node.state==Direction.SOUTH)
  {
    //Moving South on track
    if(node.x==x4 && node.y==y3)
    {
      node.state=Direction.NORTH_WEST;
      int xdiff=x3-x4;
      int ydiff=y2-y3;    
      node.x=node.x+(xdiff/diagonal);
      node.y=node.y+(ydiff/diagonal);    
    }
    else
    {
      node.y=node.y+updown;
	  if(node.y>y3)
	  {
	    node.x=x4;
		node.y=y3;
		node.state=Direction.NORTH_WEST;
	  }
    }
  }
  else if(node.state==Direction.NORTH_WEST)
  {
    //Moving North West on track
    if(node.x==x3 && node.y==y2)
    {
      //Now entering the bridge to go West
      node.state=Direction.WEST;
      node.x=node.x-updown;
    }
    else
    {
    int xdiff=x3-x4;
    int ydiff=y2-y3;    
    node.x=node.x+(xdiff/diagonal);
    node.y=node.y+(ydiff/diagonal);
    if(node.x<x3 || node.y<y2)
    {
	  node.x=x3;
	  node.y=y2;
	  node.state=Direction.WEST;
    }	
    }
  }
  else if(node.state==Direction.WEST)
  {
    //Moving West on the Bridge
    if(node.x==x2 && node.y==y2)
    {
      //Leaving Bridge to go South West
      node.state=Direction.SOUTH_WEST;
    int xdiff=x1-x2;
    int ydiff=y3-y2;    
    node.x=node.x+(xdiff/diagonal);
    node.y=node.y+(ydiff/diagonal);     
    }
    else
    {
      node.x=node.x-updown;
	  if(node.x<x2)
	  {
	    node.x=x2;
		node.y=y2;
		node.state=Direction.SOUTH_WEST;
	  }
    }
  }
  else if(node.state==Direction.SOUTH_WEST)
  {
    //Moving South West on track
    if(node.x==x1 && node.y==y3)
    {
      node.state=Direction.NORTH;
      node.y=node.y-updown;
    }
    else
    {
      int xdiff=x1-x2;
      int ydiff=y3-y2;    
      node.x=node.x+(xdiff/diagonal);
      node.y=node.y+(ydiff/diagonal); 
      if(node.x<x1 || node.y > y3)
	  {
	    node.x=x1;
		node.y=y3;
		node.state=Direction.NORTH;
	  }
    }
    
  }
  
  }
}
