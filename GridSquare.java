package a1;
import java.awt.Color;
import javax.swing.*;

/*
 * This class is responsible for making a grid of black and white colour to play knight game.
 * 
 * */

public class GridSquare extends JButton
{
	private int xcoord, ycoord;     // location of this square
    
	// constructor takes the x and y coordinates of this square
	public GridSquare( int xcoord, int ycoord)
	{
		super();
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}
	
	// if the decider is even, it chooses black, otherwise white (for 'column+row' will allow a chequerboard effect)
	public void setColor( int decider)
	{
		Color colour = (int) (decider/2.0) == (decider/2.0) ? Color.black : Color.white;
		this.setBackground( colour);
	}
	
	// if the square is black it becomes white, and vice-versa
	public void switchColor(String colour)
	{
//		Color colour = (getBackground() == Color.yellow) ? Color.yellow: Color.yellow;
		if (colour == "yellow") {
			this.setBackground( Color.yellow);
		}
		else {
			this.setBackground( Color.blue);
		}
	}
    
    // simple setters and getters
    public void setXcoord(int value)    { xcoord = value; }
    public void setYcoord(int value)    { ycoord = value; }
    public int getXcoord()              { return xcoord; }
    public int getYcoord()              { return ycoord; }

}
