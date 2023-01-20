package a1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * This class is responsible for doing all the work for playing the knight game
 *  
 * */

public class Window extends JFrame implements ActionListener
{
	// gui components that are contained in this frame:
		
		private JPanel topPanel, bottomPanel;	// top and bottom panels in the main window
		private JLabel topLabel;				// a text label to appear in the top panel
		private JButton topButton;				// a 'reset' button to appear in the top panel
		private GridSquare [][] gridSquares;	// squares to appear in grid formation in the bottom panel
		private int x,y;					// the size of the grid
		private int moves = 0;				// Moves performed by knight
		private int current_x;				// Current value of x where knight is
		private int current_y;				// Current value of y where knight is
		private boolean firstTime = true;   // To make record of first move
		private GridSquare lastSquareVisited;  // Last visited square
		
	/*
	 * This constructor is responsible for creating GUI and adding action listener to buttons
	 * */
	public Window(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		// first create the panels
		topPanel = new JPanel();
		topPanel.setLayout( new FlowLayout());
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridLayout( x, y));
		
		// then create the components for each panel and add them to it
		
		// for the top panel:
		topLabel = new JLabel("Sir Lancelot, visit every square once!");
		topButton = new JButton("New Game");
		topButton.addActionListener( this);			// IMPORTANT! Without this, clicking the button does nothing.
		
		topPanel.add( topLabel);
		topPanel.add ( topButton);
		
		// for the bottom panel:	
		// create the buttons and add them to the grid
		gridSquares = new GridSquare [x][y];
		for ( int column = 0; column < x; column ++)
		{
			for ( int row = 0; row < y; row ++)
			{
				gridSquares [column][row] = new GridSquare( column,row);
				gridSquares [column][row].setSize( 20, 20);
				gridSquares [column][row].setColor( column + row);
				gridSquares [column][row].setOpaque( true);				// without this line and the next the OS' default
				gridSquares [column][row].setBorderPainted( false);		// look & feel will dominate / interfere
																		// (try commenting each out & see what happens)
				
				gridSquares [column][row].addActionListener( this);		// AGAIN, don't forget this line!
				
				bottomPanel.add( gridSquares [column][row]);
			}
		}
		
		// now add the top and bottom panels to the main frame
		getContentPane().setLayout( new BorderLayout());
		getContentPane().add( topPanel, BorderLayout.NORTH);
		getContentPane().add( bottomPanel, BorderLayout.SOUTH);
		pack();
		
		// housekeeping : behaviour
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		setResizable( false);
		setVisible( true);		
		
	}

	/*
	 * This function will tell what to do when buttons are clicked
	 * */
	public void actionPerformed(ActionEvent e) {
		Object selected = e.getSource(); // Getting source of the clicked button
		
		if ( selected instanceof GridSquare) 
		{
			// If button is clicked first time
			if (firstTime) {
				((GridSquare) selected).switchColor("yellow"); // Switch color to yellow
				moves++;  // Increase moves by 1
				topLabel.setText("Moves made: " + moves);  // Change top label 
				current_x = ((GridSquare) selected).getXcoord(); // Change current x and y values
				current_y = ((GridSquare) selected).getYcoord();
				firstTime = false;
				lastSquareVisited = ((GridSquare) selected);  // Storing the last visited square
			}
			else if (!checkAlreadySelectedSquare(selected)) {  // Check if the square is already visited
				if(correctKnightMove(selected)) {  // Check if knight can move on selected square
					((GridSquare) selected).switchColor("yellow");  // Switch color to yellow
					lastSquareVisited.switchColor("blue");  // Switch color to blue
					moves++;  // Increase moves by 1
					topLabel.setText("Moves made: " + moves);  // Change top label 
					current_x = ((GridSquare) selected).getXcoord();  // Change current x and y values
					current_y = ((GridSquare) selected).getYcoord();
					lastSquareVisited = ((GridSquare) selected);  // Storing the last visited square
					// If 25 moves were made, finish the game and change the top label.
					if (moves == 25) {
						topLabel.setText("You did it! ");
						}
					}
				// If illegal square selected, then raise warning
				else {
					topLabel.setText("You cannot go there!   ");
					}
				}
			// If already visited square selected, then raise warning
			else {
				topLabel.setText("Already visited square!   ");
			}
		}
		
		// If the reset button was selected, reset the game. Set all the variable to initial configuration.
		if ( selected.equals( topButton) )
		{
			for ( int column = 0; column < x; column ++)
			{
				for ( int row = 0; row < y; row ++)
				{
					gridSquares [column][row].setColor( column + row);
				}
			}
			moves = 0;
			topLabel.setText("Moves made: " + moves);
			current_x = -1;
			current_y= -1;
			firstTime = true;
			lastSquareVisited= null;
		}
		
	}
	
	// This function will check if the correct square is selected
	public boolean checkCorrectSquare(Object selected) {
		if (((GridSquare) selected).getXcoord() == 1) { 
			return true;
		}
		
		return false;
	}
	// This function will check if the already selected square is selected again
	public boolean checkAlreadySelectedSquare(Object selected) {
		if (((GridSquare) selected).getBackground() == Color.yellow || ((GridSquare) selected).getBackground() == Color.blue) { 
			return true;
		}
		
		return false;
	}
	
	// This function will check if the square selected is a legal move.
	public boolean correctKnightMove(Object selected) {
		int row = Math.abs(current_x - ((GridSquare) selected).getXcoord());
		int col = Math.abs(current_y - ((GridSquare) selected).getYcoord());
		if ((row == 1 && col == 2) || (row == 2 && col == 1)){
			return true;
		}
		
		return false;
	}

}
