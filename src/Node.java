package code;


import javax.swing.*;

public class Node
{
  String direction;
  int ID;
  String color;
  int x;
  int y;
  int state;
  public Node(int id, String clr, int xcord, int ycord, int st)
  {
    ID=id;
    color=clr;
    x=xcord;
    y=ycord;
    state=st;
  }
  
  public String getDirection(int state)
  {
    String direction ="Unknown";
    if(state ==1)
	  direction = "North";
	else if(state ==2)
	  direction = "South";
	else if(state==3)
	  direction = "South East";
	else if(state==4)
	  direction = "North East";
	else if(state ==5)
	  direction = "South";
	else if(state ==6)
	  direction = "North West";
	else if(state==7)
	  direction = "West";
	else if(state ==8)
	  direction = "South West";
	return direction;
  }
 
}
