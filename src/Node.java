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
  
  public String getDirection(int state);
  {
    if(state ==1)
	  return "North";
	else if(state ==2)
	  return "South";
	else if(state==3)
	  return "South East";
	else if(state==4)
	  return "North East";
	else if(state ==5)
	  return "South";
	else if(state ==6)
	  return "North West";
	else if(state==7)
	  return "West";
	else if(state ==8)
	  return "South West";
	else
	  return "Unknown";
  }
 
}

