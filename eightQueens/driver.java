package eightQueens;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class driver {

	public static void main (String args[]) 
	{
		
		JFrame frame = new JFrame("Eight Queens");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new GUI());
		frame.pack();
		frame.setVisible(true);
		
		eightQueens game = new eightQueens();

		for (int i = 0; i<10; i++)
		{
			game.exhaustive();
			game.showBoard();
			game.reset();

			game.bruteForce();
			game.showBoard();
			game.reset();

			game.heuristic();
			game.showBoard();
			game.reset();
		}

		game.getTimes();


	}
}