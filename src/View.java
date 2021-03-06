package code;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/*
Robert Dunn, Holly Busken, Matt Lindner
*/
public class View extends JPanel implements ChangeListener, ActionListener
{
	// Array to store/access nodes
  Node[] nodes;
	// The number of nodes in our graph.
  final int numNodes = 4;
  
	// The intersection points of the "track" on which the Nodes "ride"
  int x1, x2, x3, x4;
  int y1, y2, y3; 

	// The height of the Java window
  final int height=600;
	// The width of the Java window
  final int width=800;

	// Array containing the sliders used to adjust the speed of the Nodes
  JSlider[] speedSliders;
	// Array containing the description labels
  JLabel[] sliderLabels;
  
	// GUI element indicating whether or not the program will allow
	// multiple nodes to cross the bridge as long as they are travelling
	// in the same direction.
  JCheckBox allowMultiple;
  
  public View()
  {
		// Defines the size of the Java window
    setPreferredSize(new Dimension(width, height)); 
		// Makes this JPanel not invisible
    setVisible(true);
    validate();

		// Set the initial position of the intersection points
    x1 = getInsets().left + (int)(width * 0.15);
    x2 = getInsets().left + (int)(width * 0.35);
    x3 = getInsets().left + (int)(width * 0.65);
    x4 = getInsets().left + (int)(width * 0.85);
    y1 = getInsets().top + (int)(height * 0.2);
    y2 = getInsets().top + (int)(height * 0.5);
    y3 = getInsets().top + (int)(height * 0.8);
  
    int num = (y3-y1)/3;
    
    //nodes are self-managing and update themselves
    nodes = new Node[numNodes];
    nodes[0] = new Node(0,"red",x1,y1+num,Direction.NORTH, this, 200);
    nodes[1] = new Node(1,"red",x1,y3-num,Direction.NORTH, this, 200);
    nodes[2] = new Node(2,"blue",x4,y1+num,Direction.SOUTH, this, 250);
    nodes[3] = new Node(3,"blue",x4,y3-num,Direction.SOUTH, this, 250); 
		
    speedSliders = new JSlider[numNodes];
    sliderLabels = new JLabel[numNodes];
    for (int sliderID = 0; sliderID < numNodes; sliderID++)
    {
    	JPanel movementContainer = new JPanel();
    	movementContainer.setLayout(new GridLayout(2, 1));
    	
			// Creates a label to indicate which slider belongs to which 
			// Node, and adds it to the container.
    	sliderLabels[sliderID] = new JLabel("Person " + sliderID, SwingConstants.CENTER);
    	sliderLabels[sliderID].setForeground(((sliderID < 2)? Color.red : Color.blue));
    	movementContainer.add(sliderLabels[sliderID]);
    	
			// Creates a slider that controls the speed of a node, 
			// and adds it to the container.
    	speedSliders[sliderID] = new JSlider(10, 230, 200);
    	speedSliders[sliderID].setPreferredSize(new Dimension(120, 50));
    	speedSliders[sliderID].addChangeListener(this);
    	movementContainer.add(speedSliders[sliderID]);
    	
    	add(movementContainer);
    }
    
		// Adds the JCheckbox too the container.
    allowMultiple = new JCheckBox("Allow Multiple People 1-Way", false);
    allowMultiple.addActionListener(this);
    add(allowMultiple);
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    // Draws the left triangle
    g.drawLine(x1, y1, x1, y3);
    g.drawLine(x1, y1, x2, y2);
    g.drawLine(x1, y3, x2, y2);

    // Draws the bridge connecting the two triangles.
    g.drawLine(x2, y2, x3, y2);

    //right triangle
    g.drawLine(x3, y2, x4, y1);
    g.drawLine(x4, y1, x4, y3);
    g.drawLine(x3, y2, x4, y3);
  
    //draw Nodes
    final int diameter = 10;
    final int rad = diameter/2;
    
    g.setColor(Color.red);
    for(int i = 0; i < numNodes / 2; i++)
      g.fillOval(nodes[i].x-rad,nodes[i].y-rad,diameter,diameter);
    g.setColor(Color.blue);
    for(int i = numNodes / 2; i < numNodes; i++)
      g.fillOval(nodes[i].x-rad,nodes[i].y-rad,diameter,diameter);
  }

	// Event listener. When one of the speed sliders is adjusted,
	// this function changes the "speed" of the appropriate node.
  public void stateChanged(ChangeEvent e)
  {
	if (e.getSource() == speedSliders[0])
	{
		nodes[0].setSpeed(speedSliders[0].getValue());
	}
	else if (e.getSource() == speedSliders[1])
	{
		nodes[1].setSpeed(speedSliders[1].getValue());
	}
	else if (e.getSource() == speedSliders[2])
	{
		nodes[2].setSpeed(speedSliders[2].getValue());
	}
	else if (e.getSource() == speedSliders[3])
	{
		nodes[3].setSpeed(speedSliders[3].getValue());
	}
  }

	// Event listener.  When the JCheckbox is selected, this 
	// tells the nodes whether or not they are allowed to cross the bridge
	// in the same direction at the same time.
  public void actionPerformed(ActionEvent e)
  {
	for (int i = 0; i < numNodes; i++)
	{
		nodes[i].setAllowMultiple(allowMultiple.isSelected());
	}
  }
}
