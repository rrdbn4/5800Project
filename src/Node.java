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
  public Node(int id, String clr, int xcord, int ycord)
  {
    ID=id;
	  color=clr;
	  x=xcord;
	  y=ycord;
	  state=0;
  }
 
}
