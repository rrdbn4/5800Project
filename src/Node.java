package code;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Node implements ActionListener
{
  int ID;
  String color;
  View inst;
  int x;
  int y;
  Direction state;
  public Node(int id, String clr, int xcord, int ycord, Direction st, View view)
  {
    ID=id;
    color=clr;
    x=xcord;
    y=ycord;
    state=st;
    inst = view;

    Timer timer = new Timer(200, this);
    timer.setInitialDelay(200);
    timer.setRepeats(true);
    timer.start();
  }

  public void actionPerformed(ActionEvent e)
  {
    this.update();
  }

  public void update()
  {
    int updown=15;
    int diagonal=20;
    if(state == Direction.NORTH)
    {
      //Going North on track
      if(x==inst.x1 && y==inst.y1)
      {      
        state = Direction.SOUTH_EAST;
        //slope = y2-y1 / x2-x1
        int xdiff=inst.x2-inst.x1;
        int ydiff=inst.y2-inst.y1;

        x=x+(xdiff/diagonal);
        y=y+(ydiff/diagonal);
      }
      else
      {
        y=y-updown;
        if(y<inst.y1)
        {
          x=inst.x1;
          y=inst.y1;
          state=Direction.SOUTH_EAST;
        }
      }
    }
  else if(state==Direction.SOUTH_EAST)
  {
    //Going South East on track
    if(x==inst.x2 && y==inst.y2)
    {
      //Entering the bridge going East
      state=Direction.EAST;
      x=x+updown;
    }
    else
    {
      int xdiff=inst.x2-inst.x1;
      int ydiff=inst.y2-inst.y1;    
      x=x+(xdiff/diagonal);
      y=y+(ydiff/diagonal);  
      if(x>inst.x2 || y>inst.y2)
      {
        x=inst.x2;
        y=inst.y2;
        state=Direction.EAST;
      } 
    }
  }
  else if(state==Direction.EAST)  //in the critical section
  {
    //Moving East on the bridge 
    if(x==inst.x3 && y==inst.y2)
    {
      //Leaving the bridge to go North East
      state=Direction.NORTH_EAST;
      int xdiff=inst.x4-inst.x3;
      int ydiff=inst.y1-inst.y2;    
      x=x+(xdiff/diagonal);
      y=y+(ydiff/diagonal);   
    }
    else
    {
      x=x+updown;
      if(x>inst.x3)
      {
        x=inst.x3;
        y=inst.y2;
        state=Direction.NORTH_EAST;
      }
    }
  }
  else if(state==Direction.NORTH_EAST)
  {
    //Moving North East on track
    if(x==inst.x4 && y==inst.y1)
    {
      state=Direction.SOUTH;
    y=y+updown;
    }
    else
    {
      int xdiff=inst.x4-inst.x3;
      int ydiff=inst.y1-inst.y2;    
      x=x+(xdiff/diagonal);
      y=y+(ydiff/diagonal); 
      if(x>inst.x4 || y<inst.y1)
      {
      x=inst.x4;
      y=inst.y1;
      state=Direction.SOUTH;
      } 
    }
  }
  else if(state==Direction.SOUTH)
  {
    //Moving South on track
    if(x==inst.x4 && y==inst.y3)
    {
      state=Direction.NORTH_WEST;
      int xdiff=inst.x3-inst.x4;
      int ydiff=inst.y2-inst.y3;    
      x=x+(xdiff/diagonal);
      y=y+(ydiff/diagonal);    
    }
    else
    {
      y=y+updown;
    if(y>inst.y3)
    {
      x=inst.x4;
    y=inst.y3;
    state=Direction.NORTH_WEST;
    }
    }
  }
  else if(state==Direction.NORTH_WEST)
  {
    //Moving North West on track
    if(x==inst.x3 && y==inst.y2)
    {
      //Now entering the bridge to go West
      state=Direction.WEST;
      x=x-updown;
    }
    else
    {
    int xdiff=inst.x3-inst.x4;
    int ydiff=inst.y2-inst.y3;    
    x=x+(xdiff/diagonal);
    y=y+(ydiff/diagonal);
    if(x<inst.x3 || y<inst.y2)
    {
    x=inst.x3;
    y=inst.y2;
    state=Direction.WEST;
    } 
    }
  }
  else if(state==Direction.WEST)  // in the critical section
  {
    //Moving West on the Bridge
    if(x==inst.x2 && y==inst.y2)
    {
      //Leaving Bridge to go South West
      state=Direction.SOUTH_WEST;
    int xdiff=inst.x1-inst.x2;
    int ydiff=inst.y3-inst.y2;    
    x=x+(xdiff/diagonal);
    y=y+(ydiff/diagonal);     
    }
    else
    {
      x=x-updown;
    if(x<inst.x2)
    {
      x=inst.x2;
    y=inst.y2;
    state=Direction.SOUTH_WEST;
    }
    }
  }
  else if(state==Direction.SOUTH_WEST)
  {
    //Moving South West on track
    if(x==inst.x1 && y==inst.y3)
    {
      state=Direction.NORTH;
      y=y-updown;
    }
    else
    {
      int xdiff=inst.x1-inst.x2;
      int ydiff=inst.y3-inst.y2;    
      x=x+(xdiff/diagonal);
      y=y+(ydiff/diagonal); 
      if(x<inst.x1 || y > inst.y3)
    {
      x=inst.x1;
    y=inst.y3;
    state=Direction.NORTH;
    }
    }
    
  }

  inst.repaint();
  
  }
 
}
