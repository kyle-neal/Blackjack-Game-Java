/*
 * Programmer: Kyle Neal
 * Date:	   27 January 2014
 * Info:	   This program is a simulation of the famous blackjack game.
 */
package blackjack;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BlackJackGUI extends JFrame
{
	//Variables+++++++++++++++++++++++++++
	//These panels are public so they can be disabled when not playing.
	PlayerPanel[] playerPanels;
	HousePanel house;
	//++++++++++++++++++++++++++++++++++++
	
	//**********************************************************************
	public class PlayerPanel extends JPanel
	{
		//This panel will hold a player's area. Contains HIT and STAY buttons
		//as well as the cards.
		
		//Here are the public changeable items
		public JButton hitButton;
		public JButton stayButton;
		private CardPanel cardpanel;		//Area where cards and score will be drawn.
		
		public boolean hitIsClicked;
		public boolean stayIsClicked;
		
		public PlayerPanel()
		{
			setLayout(new BorderLayout());
			
			//Card Panel is where the cards will go.
			cardpanel = new CardPanel();	
			add(cardpanel, BorderLayout.CENTER);
			
			//Button Panel is where the buttons will go.
			JPanel buttonPanel = new JPanel();	
			buttonPanel.setLayout(new GridLayout(1, 2));
			buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			
		    hitButton = new JButton("HIT");
		    stayButton = new JButton("STAY");
	
		    HITListener hitAction = new HITListener();
		    hitButton.addActionListener(hitAction);
		    STAYListener stayAction = new STAYListener();
		    stayButton.addActionListener(stayAction);
			buttonPanel.add(hitButton);
			buttonPanel.add(stayButton);
	
			add(buttonPanel,BorderLayout.SOUTH);
		}//PlayerPanel()
		
		public void disableButtons()
		{
			hitButton.setEnabled(false);
			stayButton.setEnabled(false);
		}
		
		public void enableButtons()
		{
			hitButton.setEnabled(true);
			stayButton.setEnabled(true);
		}
		
		public void resetButtonFlags()
		{
			hitIsClicked = false;
			stayIsClicked = false;
		}
		
		public boolean hitIsClicked()
		{
			return hitIsClicked;
		}
		
		public boolean stayIsClicked()
		{
			return stayIsClicked;
		}
	
		public void updateScore(int score, blackJackHand h)
		{
			cardpanel.updateScore(score, h);
		}		

		class HITListener implements ActionListener
		{
			//Action listener for hit button. NOTE THESE WERE ORIGINALLY OUTSIDE OF PLAYER PANEL
			
			public void actionPerformed(ActionEvent e)
			{
				hitIsClicked = true;
			}
		}
		class STAYListener implements ActionListener
		{
			//Action Listener for stay button.
			
			public void actionPerformed(ActionEvent e)
			{
				stayIsClicked = true;
			}
		}
	}//PlayerPanel
	
	public class HousePanel extends JPanel
	{
		private CardPanel cardpanel;
		
		public HousePanel()
		{
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createTitledBorder("House"));
			cardpanel = new CardPanel();
			add(cardpanel, BorderLayout.CENTER);
			
		}
		
		public void updateScore(int score, blackJackHand h, boolean dealer) 
		{
			cardpanel.updateScore(score, h, dealer);
		}
	}
	//**********************************************************************
	public class CardPanel extends JPanel
	{
		//This panel is where the cards will go.
		
		public void updateScore(int score, blackJackHand h) 
		{			
			Graphics gfx = getGraphics();
			paintComponent(gfx);
	
				Integer scoreStr = score;
				if(score > 21)
					gfx.drawString("BUST", 125,10);
				else	
					gfx.drawString("Score: " + scoreStr.toString(), 125,10);
				
				updateCards(h);
		}
		
		public void updateScore(int score, blackJackHand h, boolean dealer) 
		{			
			//This is the function to update a house's GUI.
			//If dealer is passed as true, this means that the players are still
			//playing and only one card should be showing with no score. If dealer
			//is passed as true then we can show everything.
			
			Graphics gfx = getGraphics();
			paintComponent(gfx);
	
			if(dealer)
			{
				updateCards(h, true);	//We pass true to let update cards know
										//to only show one card.
			}
			else
			{
				Integer scoreStr = score;
				if(score > 21)
					gfx.drawString("BUST", 125,10);
				else	
					gfx.drawString("Score: " + scoreStr.toString(), 125,10);
				
				updateCards(h);
			}
		}
		
		private void updateCards(blackJackHand h) 
		{
			Graphics gfx = getGraphics();
			
			String[] imageNames = new String[h.getCardCount()];
			imageNames = h.returnCardImageNames();
			
			//Draw cards.
			Image[] imageArray =  new Image[h.getCardCount()];
			for(int i = 0; i < h.getCardCount(); i++)
			{	
				try 
				{
					imageArray[i] = ImageIO.read(new File("src/blackjack/"+imageNames[i]));
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			
			for(int i = 0; i < h.getCardCount(); i ++)
			{
				gfx.drawImage(imageArray[i], 50+(i*25), 50+(i*25), this);
			}
		}
		
		private void updateCards(blackJackHand h, boolean dealer) 
		{
			Graphics gfx = getGraphics();
			
			String[] imageNames = new String[h.getCardCount()];
			imageNames = h.returnCardImageNames();
			imageNames[1] = "back.jpg";
			
			//Draw cards.
			Image[] imageArray =  new Image[h.getCardCount()];
			for(int i = 0; i < 2; i++)
			{	
				try 
				{
					imageArray[i] = ImageIO.read(new File("src/blackjack/"+imageNames[i]));
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			
			for(int i = 0; i < h.getCardCount(); i ++)
			{
				gfx.drawImage(imageArray[i], 50+(i*25), 50+(i*25), this);
			}
		}
		protected void paintComponent(Graphics gfx)
		{
			super.paintComponent(gfx);
		}
	}
	//**********************************************************************

	public BlackJackGUI()
	{
		//This is the main frame, now we set up all the panels and items that
		//go inside of the frame
		
		//Set frame properties
		setLayout(new GridLayout(2, 1));
		
		//Player panels~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		JPanel mainPlayerPanel = new JPanel();
		mainPlayerPanel.setSize(900, 300);
		mainPlayerPanel.setLayout(new GridLayout(1, 4));
		
		playerPanels = new PlayerPanel[4];
		playerPanels[0] = new PlayerPanel();
		playerPanels[0].setBorder(BorderFactory.createTitledBorder("Player 1"));
		playerPanels[1] = new PlayerPanel();
		playerPanels[1].setBorder(BorderFactory.createTitledBorder("Player 2"));
		playerPanels[2] = new PlayerPanel();
		playerPanels[2].setBorder(BorderFactory.createTitledBorder("Player 3"));
		playerPanels[3] = new PlayerPanel();
		playerPanels[3].setBorder(BorderFactory.createTitledBorder("Player 4"));
		mainPlayerPanel.add(playerPanels[0]);
		mainPlayerPanel.add(playerPanels[1]);
		mainPlayerPanel.add(playerPanels[2]);
		mainPlayerPanel.add(playerPanels[3]);

		//Player panels~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		//House panels~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		JPanel housePanel = new JPanel();
		housePanel.setLayout(new GridLayout(1, 3));
		housePanel.add(new JPanel());	//BLANK
		
		house = new HousePanel();
		housePanel.add(house);
		
		housePanel.add(new JPanel());	//BLANK
		//House panels~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		//Add components to frame.
		add(housePanel, BorderLayout.NORTH);
		add(mainPlayerPanel, BorderLayout.SOUTH);
			
		createFrame(this);
	}//BlackJackGUI()

	private static void createFrame(JFrame frame)
	{
		//Set up GUI~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		frame.setSize(1300, 1000);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}//createFrame()
	//**********************************************************************
}//BlackJackGUI