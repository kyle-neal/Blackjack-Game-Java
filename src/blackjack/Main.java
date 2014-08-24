package blackjack;

import javax.swing.JOptionPane;

public class Main 
{
	static BlackJackGUI frame;
	static Deck deck;
	static int numberOfPlayers;
	static blackJackHand[] players;
	static blackJackHand dealer;

	
	public static void main(String[] args) throws InterruptedException
	{
		//Set up GUI
		frame = new BlackJackGUI();
		getPlayers();
		
		//Play game until user quits.
		int userResponse;
		do
		{
			Thread.sleep(100);		//Prevents race condition for score boards.
			startGame();
			
			userResponse = JOptionPane.showConfirmDialog(null,
					"Would you like to play again?",
					"",
					JOptionPane.YES_NO_OPTION);
		}while(userResponse == JOptionPane.YES_OPTION);	
		
		frame.dispose();
	}
	
	static void startGame()
	{
		//Start game.
		dealer = new blackJackHand();
		players = new blackJackHand[numberOfPlayers];
		for(int i = 0; i < numberOfPlayers; i++)
		{
			players[i] = new blackJackHand();
		}
		
		//Start a deck
		deck = new Deck();
		deck.shuffle();
		
		//Deal Cards
		dealer.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard());
		
		for(int i = 0; i < numberOfPlayers; i++)
		{
			players[i].addCard(deck.dealCard());
			players[i].addCard(deck.dealCard());
		}
		
		//Here we will update the GUI
		updateScoreBoard();
		
		//Check for a dealer blackjack. If true, dealer wins.
		if(dealer.getBlackJackValue() == 21)	
		{
			JOptionPane.showMessageDialog(null,
					"Dealer has a blackjack!",
					"",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//Go through the players.
		for(int i = 0; i < numberOfPlayers; i++)
		{
			disableOtherPlayers(i);
			playerTurn(i);
		}
		
		//Dealers turn
		while(dealer.getBlackJackValue() <= 16)
		{
			dealer.addCard(deck.dealCard());
		}
		updateScoreBoard(true);
		
		//Compare final scores with dealer's hand
		checkFinalScores();
	}//startGame()
	
	static void getPlayers()
	{
		Object[] players = {"1", "2", "3", "4"};
		
		String numPlayers = (String)JOptionPane.showInputDialog(frame,
																"How many players?",
																"Enter Number of Players",
																JOptionPane.PLAIN_MESSAGE,
																null,
																players,
																"1");
		
		numberOfPlayers = Integer.parseInt(numPlayers);
	}//getPlayers()

	static void playerTurn(int player)
	{
		System.out.println("Player " + player + "'s value: " + players[player].getBlackJackValue());
		while(true)
		{
			if(frame.playerPanels[player].hitIsClicked())
			{
				players[player].addCard(deck.dealCard());
				int playersScore = players[player].getBlackJackValue();
				frame.playerPanels[player].updateScore(playersScore, players[player]);
				System.out.println("Player " + player + "'s value: " + players[player].getBlackJackValue());
				if(players[player].checkForBust())
				{
					disableAllPlayers();
					JOptionPane.showMessageDialog(null,
							"You busted!",
							"",
							JOptionPane.ERROR_MESSAGE);
					frame.playerPanels[player].resetButtonFlags();
					return;
				}
				frame.playerPanels[player].resetButtonFlags();	//Reset for next iteration.
			}
			
			
			if(frame.playerPanels[player].stayIsClicked())
			{
				frame.playerPanels[player].resetButtonFlags();
				return;
			}
		}
		
	}
	
	static void disableOtherPlayers(int playerPlaying)
	{
		switch(playerPlaying)
		{
			case 0: frame.playerPanels[0].enableButtons();
					frame.playerPanels[1].disableButtons();
					frame.playerPanels[2].disableButtons();
					frame.playerPanels[3].disableButtons();
					break;
			case 1: frame.playerPanels[0].disableButtons();
					frame.playerPanels[1].enableButtons();
					frame.playerPanels[2].disableButtons();
					frame.playerPanels[3].disableButtons();
					break;
			case 2: frame.playerPanels[0].disableButtons();
					frame.playerPanels[1].disableButtons();
					frame.playerPanels[2].enableButtons();
					frame.playerPanels[3].disableButtons();
					break;
			case 3: frame.playerPanels[0].disableButtons();
					frame.playerPanels[1].disableButtons();
					frame.playerPanels[2].disableButtons();
					frame.playerPanels[3].enableButtons();
					break;
					
			default: break;//No-op
		}
	}

	static void disableAllPlayers()
	{
		frame.playerPanels[0].disableButtons();
		frame.playerPanels[1].disableButtons();
		frame.playerPanels[2].disableButtons();
		frame.playerPanels[3].disableButtons();
	}

	static void updateScoreBoard()
	{
		for(int i = 0; i < numberOfPlayers; i++)
		{
			frame.playerPanels[i].updateScore(players[i].getBlackJackValue(), players[i]);
		}
		
		frame.house.updateScore(dealer.getBlackJackValue(), dealer, true);
	}

	static void updateScoreBoard(boolean dealerPlaying)
	{
		if(dealerPlaying)
		{
			for(int i = 0; i < numberOfPlayers; i++)
			{
				frame.playerPanels[i].updateScore(players[i].getBlackJackValue(), players[i]);
			}
			
			frame.house.updateScore(dealer.getBlackJackValue(), dealer, false);
		}
		
	}
	
	static void checkFinalScores()
	{
		String[] wins = new String[numberOfPlayers];
			
		for(int i = 0; i < numberOfPlayers; i++)
		{
			if(players[i].getBlackJackValue() >= dealer.getBlackJackValue() && players[i].getBlackJackValue() <= 21)
			{
				if(players[i].getBlackJackValue() == dealer.getBlackJackValue())
					wins[i] = "Player " + (i+1) + " pushed.\n";
				else
					wins[i] = "Player " + (i+1) + " wins!\n";
			}
			else
			{
				if(players[i].getBlackJackValue() <= 21 && dealer.getBlackJackValue() > 21)
					wins[i] = "Player " + (i+1) + " wins!\n";
				else
					wins[i] = "Player " + (i+1) + " lost.\n";
			}
		}
		
		JOptionPane.showMessageDialog(null,
				wins,
				"Score Report",
				JOptionPane.INFORMATION_MESSAGE);	
	}
}


