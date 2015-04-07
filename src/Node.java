package code;


import javax.swing.*;

public class Node
{
  int ID;
  String color;
  int x;
  int y;
  Direction state;
  public Node(int id, String clr, int xcord, int ycord, Direction st)
  {
    ID=id;
    color=clr;
    x=xcord;
    y=ycord;
    state=st;
  }
  
  public String getDirection(Direction state)
  {
    String direction ="Unknown";
    if(state == Direction.NORTH)
      direction = "North";
    else if(state == Direction.SOUTH_EAST)
      direction = "South East";
    else if(state == Direction.EAST)
      direction = "East";
    else if(state == Direction.NORTH_EAST)
      direction = "North East";
    else if(state == Direction.SOUTH)
      direction = "South";
    else if(state == Direction.NORTH_WEST)
      direction = "North West";
    else if(state == Direction.WEST)
      direction = "West";
    else if(state == Direction.SOUTH_WEST)
      direction = "South West";
    return direction;
  }
 
}
