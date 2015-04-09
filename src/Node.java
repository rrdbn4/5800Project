package code;

import javax.swing.Timer;
import java.awt.event.*;
import java.util.Calendar;
import java.util.*;

public class Node implements ActionListener
{
  int ID;
  String color;
  View inst;
  int x;
  int y;
  Direction state;
  boolean waiting = false;
  boolean allowMultiple = false;
  int acks = 0;
  long requestTimestamp = 0;
  Queue<Node> requestBuffer;
  
  Timer timer;

  public Node(int id, String clr, int xcord, int ycord, Direction st, View view)
  {
    requestBuffer = new LinkedList<Node>();
    ID=id;
    color=clr;
    x=xcord;
    y=ycord;
    state=st;
    inst = view;

    timer = new Timer(200, this);
    timer.setInitialDelay(200);
    timer.setRepeats(true);
    timer.start();
  }

  public void actionPerformed(ActionEvent e)
  {
    this.update();  //for the calls from the timer
  }

  public void request(Node callingNode)
  {
    if(state != Direction.WEST && state != Direction.EAST && !waiting) //not in CS and not waiting to enter
      callingNode.ack();
    if(state == Direction.WEST || state == Direction.EAST)
    {//in CS
    	if (callingNode.state == Direction.NORTH_WEST && state == Direction.WEST && allowMultiple && callingNode.allowMultiple)
    	{
    		callingNode.ack();
    	}
    	if (callingNode.state == Direction.SOUTH_EAST && state == Direction.EAST && allowMultiple && callingNode.allowMultiple)
    	{
    		callingNode.ack();
    	}
      bufferRequest(callingNode);
    }
    if(state != Direction.WEST && state != Direction.EAST && waiting)  //not in CS and waiting to enter
    {
      if(callingNode.requestTimestamp <= this.requestTimestamp)
        callingNode.ack();
      else
        bufferRequest(callingNode);
    }

  }

  public void ack()
  {
    acks++;
    if(acks >= inst.numNodes - 1) //recieved acks from everyone. safe to enter the CS now
    {
      waiting = false; //this allows it to go past the timer check at the beginning of update()
      update();        //acks will be reset to 0 inside update()
    }
  }

  public void bufferRequest(Node callingNode)
  {
    requestBuffer.add(callingNode);
  }

  //retuns true if the node is allowed to enter the CS
  //if the node is not allowed to enter the CS, this preps it to enter the CS and returns false
  public boolean enterCriticalSection()
  {
    if(acks >= inst.numNodes - 1)
      return true;

    waiting = true;
    requestTimestamp = getTimestamp();
    for(int i = 0; i < inst.numNodes; i++)
    {
      if(i != ID) //don't send a request to yourself
        inst.nodes[i].request(this);
    }
    return false;
  }

  //send acks to all nodes in the request buffer
  public void exitCriticalSection()
  {
    int size = requestBuffer.size();
    for(int i = 0; i < size; i++)
      requestBuffer.remove().ack();
  }

  public long getTimestamp()
  {
    Calendar cal = Calendar.getInstance();
    return cal.getTimeInMillis();
  }

  public void update()
  {
    if(waiting)   //waiting to enter CS. skip everything else
      return;     //this is mainly for when the timer tries to call update() during a waiting state

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
      //It wants to enter the critical section here
      if(!enterCriticalSection())
        return;
      else
        acks = 0;
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
          //It wants to enter the critical section here
        if(!enterCriticalSection())
          return;
        else
          acks = 0;
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
      exitCriticalSection();
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
        exitCriticalSection();
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
      //It wants to enter the critical section here
      if(!enterCriticalSection())
        return;
      else
        acks = 0;
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
      //It wants to enter the critical section here
      if(!enterCriticalSection())
        return;
      else
        acks = 0;
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
      exitCriticalSection();
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
      exitCriticalSection();
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
  
  public void setSpeed(int newSpeed)
  {
	  timer.setDelay(250 - newSpeed);
  }
  
  public void setAllowMultiple(boolean allowMultiple)
  {
	  this.allowMultiple = allowMultiple;
  }
 
}
