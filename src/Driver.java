package code;

import javax.swing.*;

public class Node
{
  String direction;
  int ID;
  String color;
  int x;
  int y;
  //each state corresponds to a direction
  int state;
  public Node(int id, String clr, int xcord, int ycord, int st)
  {
    ID=id;
	color=clr;
	x=xcord;
	y=ycord;
	state=0;
	state=st;
  }
 
}
