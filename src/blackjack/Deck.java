//Class Deck holds Card Objects.

package blackjack;

public class Deck 
{
	private Card[] deck;
	private int cardsUsed;
	
	public Deck()
	{
		//Create a deck of cards
		deck =  new Card[52];
		
		//For all suits and values
		int cardsCreated = 0;
		for(int suit = 1; suit <= 4; suit++)
		{
			for(int card = 1; card <= 13; card++)
			{
				deck[cardsCreated] = new Card(card, suit);
				cardsCreated++;
			}
		}
		cardsUsed = 0;	
	}
	
	public void shuffle()
	{
		for(int i = 51; i > 0; i--)
		{
			int rand = (int)(Math.random()*(i+1));
			Card temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardsUsed = 0;
	}
	
	public void printDeck()
	{
		for(int i = 0; i < 52; i++)
		{
			System.out.println(deck[i].toString());
		}	
	}
	
	public void showHowManyCardsUsed()
	{
		System.out.println("Cards used: " + cardsUsed);
	}
	
	public Card dealCard()
	{
		if(cardsUsed == 52)
			shuffle();
		cardsUsed++;
		
		return deck[cardsUsed - 1];
	}

}
