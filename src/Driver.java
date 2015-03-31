package code;



import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.lang.Thread.*;
/*
Robert Dunn, Holly Busken, Matt Lindner
*/
public class Driver extends JFrame implements ActionListener
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
	
	redA=new Node(1,"red",x1,y1+num,1);
	redB=new Node(2,"red",x1,y3-num,1);
	blueA=new Node(3,"blue",x4,y1+num,5);
	blueB=new Node(4,"blue",x4,y3-num,5);	
	repaint();
	
	int x=500;
	while(x!=0)
	{
      Timer timer=new Timer(1000, this);
	  timer.setInitialDelay(10);
	  timer.start();
	  x=x-1;
	}
  } 

  public static void main(String[] args)
  {
    new Driver();

  }
  
  public void actionPerformed(ActionEvent e)
  {
    repaint();
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
	
	//Update nodes
	
	updateNode(redA);	
	
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
    long step=1/(y3-y1);
	System.out.println(step);
    if(node.state==1)
	{
	  if(node.x==x1 && node.y==y1)
	  {
	    node.state=2;
		//slope = y2-y1 / x2-x1
		int xdiff=x2-x1;
		int ydiff=y2-y1;
		node.x=node.x+xdiff;
		node.y=node.y+ydiff;
	  }
	  else
	    node.y=node.y-1;
	}
	else if(node.state==2)
	{
	  if(node.x==x2 && node.y==y2)
	  {
	    node.state=3;
		node.x=node.x+1;
	  }	
	}
	else if(node.state==3)
	{
      if(node.x==x3 && node.y==y2)
	  {
	    node.state=4;
	  }
	  else
	    node.x=node.x+1;
	}
  }
}
