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
    

    Timer timer=new Timer(200, this);
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
      x1 = getInsets().left + (int)(getWidth() * 0.15);
      x2 = getInsets().left + (int)(getWidth() * 0.35);
      x3 = getInsets().left + (int)(getWidth() * 0.65);
      x4 = getInsets().left + (int)(getWidth() * 0.85);
      y1 = getInsets().top + (int)(getHeight() * 0.2);
      y2 = getInsets().top + (int)(getHeight() * 0.5);
      y3 = getInsets().top + (int)(getHeight() * 0.8);
    
      int num = (y3-y1)/3;
      
      redA=new Node(1,"red",x1,y1+num,1);
      redB=new Node(2,"red",x1,y3-num,1);
      blueA=new Node(3,"blue",x4,y1+num,5);
      blueB=new Node(4,"blue",x4,y3-num,5); 

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
    if(node.state==1)
  {
    if(node.x==x1 && node.y==y1)
    {
      node.state=2;
    //slope = y2-y1 / x2-x1
    int xdiff=x2-x1;
    int ydiff=y2-y1;
    node.x=node.x+(xdiff/20);
    node.y=node.y+(ydiff/20);
    }
    else
    {
      node.y=node.y-15;
    }
  }
  else if(node.state==2)
  {
    if(node.x==x2 && node.y==y2)
    {
      //Entering the bridge going East
      node.state=3;
    node.x=node.x+15;
    }
    else
    {
    int xdiff=x2-x1;
    int ydiff=y2-y1;    
    node.x=node.x+(xdiff/20);
    node.y=node.y+(ydiff/20);   
    }
  }
  else if(node.state==3)
  {
    //Moving East on the bridge 
      if(node.x==x3 && node.y==y2)
    {
      //Leaving the bridge to go North East
      node.state=4;
    int xdiff=x4-x3;
    int ydiff=y1-y2;    
    node.x=node.x+(xdiff/20);
    node.y=node.y+(ydiff/20);   
    }
    else
      node.x=node.x+15;
  }
  else if(node.state==4)
  {
    if(node.x==x4 && node.y==y1)
    {
      node.state=5;
    node.y=node.y+15;
    }
    else
    {
    int xdiff=x4-x3;
    int ydiff=y1-y2;    
    node.x=node.x+(xdiff/20);
    node.y=node.y+(ydiff/20);   
    }
  }
  else if(node.state==5)
  {
    if(node.x==x4 && node.y==y3)
    {
      node.state=6;
    int xdiff=x3-x4;
    int ydiff=y2-y3;    
    node.x=node.x+(xdiff/20);
    node.y=node.y+(ydiff/20);    
    }
    else
    {
      node.y=node.y+15;
    }
  }
  else if(node.state==6)
  {
    if(node.x==x3 && node.y==y2)
    {
      //Now entering the bridge to go West
      node.state=7;
    node.x=node.x-15;
    }
    else
    {
    int xdiff=x3-x4;
    int ydiff=y2-y3;    
    node.x=node.x+(xdiff/20);
    node.y=node.y+(ydiff/20);   
    }
  }
  else if(node.state==7)
  {
    //Moving West on the Bridge
    if(node.x==x2 && node.y==y2)
    {
      //Leaving Bridge to go South West
      node.state=8;
    int xdiff=x1-x2;
    int ydiff=y3-y2;    
    node.x=node.x+(xdiff/20);
    node.y=node.y+(ydiff/20);     
    }
    else
    {
      node.x=node.x-15;
    }
  }
  else if(node.state==8)
  {
    if(node.x==x1 && node.y==y3)
    {
      node.state=1;
    node.y=node.y-15;
    }
    else
    {
    int xdiff=x1-x2;
    int ydiff=y3-y2;    
    node.x=node.x+(xdiff/20);
    node.y=node.y+(ydiff/20);     
    }
    
  }
  
  }
}
