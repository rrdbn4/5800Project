package code;

import javax.swing.Timer;
import java.awt.event.*;
import java.util.Calendar;
import java.util.*;

/*
Robert Dunn, Holly Busken, Matt Lindner
*/
// The Node class contains the functionality of each node represented on the map
// Each node is self-reliant and talks to all the other nodes
public class Node implements ActionListener
{
  // a unique identifier
  int ID;
  // the color of the node
  String color;
  // the reference to the graphical view. It contains information relevant to the function of the node
  View inst;
  // position y
  int x;
  // position y
  int y;
  // the current direction state of the node
  Direction state;
  // is this node waiting to enter the bridge?
  boolean waiting = false;
  // boolean for allowing multiple on the bridge (in the same direction) at the same time
  boolean allowMultiple = false;
  // the number of acknowledgements received back
  int acks = 0;
  // the timestamp of the request
  long requestTimestamp = 0;
  // the waiting queue for all the nodes that are waiting for a response back from this node
  Queue<Node> requestBuffer;
  // the self-calling movement update timer
  Timer timer;                       

  // Parameterized constructor
  // all parameters are pretty self-explanatory
  public Node(int id, String clr, int xcord, int ycord, Direction st, View view, int initialDelay)
  {
    requestBuffer = new LinkedList<Node>();
    ID=id;
    color=clr;
    x=xcord;
    y=ycord;
    state=st;
    inst = view;

    timer = new Timer(initialDelay, this);
    timer.setRepeats(true);
    timer.start();
  }

  // callback method for the timer that updates the node
  public void actionPerformed(ActionEvent e)
  {
    this.update();
  }

  // This is the method for other nodes to call when they are requesting to enter the bridge
  // This node will send back an acknowledgement to the calling node when this node deems it safe to enter the bridge
  // requests from the calling node will either be acknowledged immediately or queued for an acknowledgement later
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
    	else if (callingNode.state == Direction.SOUTH_EAST && state == Direction.EAST && allowMultiple && callingNode.allowMultiple)
    	{
    		callingNode.ack();
    	}
      else
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

  // This is the method other nodes call when they send an acknowledgement back to this node
  // When an ack is received, acks increments, and if there are as many acks as there are other nodes, then it is safe to enter the bridge
  public void ack()
  {
    acks++;
    if(acks >= inst.numNodes - 1) //recieved acks from everyone. safe to enter the CS now
    {
      waiting = false; //this allows it to go past the timer check at the beginning of update()
      update();        //acks will be reset to 0 inside update()
      return;
    }
  }

  // add an incoming request to the request queue for acknowledgement later
  public void bufferRequest(Node callingNode)
  {
    requestBuffer.add(callingNode);
  }

  
  // retuns true if the node is allowed to enter the CS
  // if the node is not allowed to enter the CS, this preps it to enter the CS and returns false
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

  // send acks to all nodes in the request buffer to tell them you left the CS
  public void exitCriticalSection()
  {
    int size = requestBuffer.size();
    for(int i = 0; i < size; i++)
      requestBuffer.remove().ack();
  }

  // get a timestamp for the requests
  public long getTimestamp()
  {
    Calendar cal = Calendar.getInstance();
    return cal.getTimeInMillis();
  }

  // update the position of the node
  // take the necessary precautions when entering and leaving the critical session
  // this method is called by the node's timer
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
  
  // set the timer interval for the update() method
  public void setSpeed(int newSpeed)
  {
	  timer.setDelay(250 - newSpeed);
  }
  
  // set whether or not to allow multiple nodes on the bridge at the same time in the same direction
  public void setAllowMultiple(boolean allowMultiple)
  {
	  this.allowMultiple = allowMultiple;
  }
 
}
