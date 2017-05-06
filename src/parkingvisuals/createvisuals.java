package parkingvisuals;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class createvisuals extends JPanel {
	
	private GraphicsPanel _puzzleGraphics;
	private logicclass _puzzleModel = new logicclass();
	
	public createvisuals() {
/**
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new NewGameAction());

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(newGameButton);
**/
		_puzzleGraphics = new GraphicsPanel();

		this.setLayout(new BorderLayout());
		//this.add(controlPanel, BorderLayout.NORTH);
		this.add(_puzzleGraphics, BorderLayout.CENTER);
	}

	class GraphicsPanel extends JPanel implements MouseListener {
		private static final int ROWS = 5;
		private static final int COLS = 5;

		private static final int CELL_SIZE = 80;
		private Font _biggerFont;

		public GraphicsPanel() {
			_biggerFont = new Font("SansSerif", Font.BOLD, CELL_SIZE / 2);
			this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE * ROWS));
			this.setBackground(Color.black);
			this.addMouseListener(this); 
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			ArrayList<String> cars = new ArrayList<String>();
			
			cars.add("0");
			cars.add("16");
			cars.add("8");
			cars.add("20");

			for (int r = 0; r < ROWS; r++) {
				for (int c = 0; c < COLS; c++) {

					int x = c * CELL_SIZE;
					int y = r * CELL_SIZE;
					String text = _puzzleModel.getFace(r, c);
					
					if (text != null) {
						
						for(String temp: cars){
						
							if(text.equals(temp)){
						
								g.setColor(Color.red);					
								g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);						
								g.setColor(Color.black);						
								g.setFont(_biggerFont);						
								g.drawString(text, x + 20, y + (3 * CELL_SIZE) / 4);
								//_puzzleModel.Graph(25);
								//while(text.isEmpty()){
								//_puzzleModel.addEdge(r, c);}
								//_puzzleModel.BFS(0);
								//_puzzleModel.move(r, c + 1);
								break;					
						
							 } else {
								
								g.setColor(Color.gray);
								g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
								g.setColor(Color.black);
								g.setFont(_biggerFont);
								g.drawString(text, x + 20, y + (3 * CELL_SIZE) / 4);
							
						    }
						}
				    }					
				}
			}
		}
		
		

		public void mousePressed(MouseEvent e) {
			
			int col = e.getX() / CELL_SIZE;
			int row = e.getY() / CELL_SIZE;

			if (!_puzzleModel.moveTile(row, col)) {
				
				Toolkit.getDefaultToolkit().beep();
			}

			this.repaint(); 
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
}