package blackjack;

public class Card 
{
	//Constants to define what a card is.
	public final static int ACE = 1,
							JACK = 11,
							QUEEN = 12,
							KING = 13;
	
	public final static int SPADES = 1,
							HEARTS = 2,
							DIAMONDS = 3, 
							CLUBS = 4;
	
	private final int suit;
	private final int value;	//Ranges from 1-11
	//++++++++++++++++++++++++++++++++++++++++++++++
	//**********************************************
	public Card(int val, int thesuit)
	{
		value = val;
		suit = thesuit;
	}
	//**********************************************
	public int getSuit()
	{
		return suit;
	}
	//**********************************************
	public int getValue()
	{
		return value;
	}
	//**********************************************
	public String getStringSuit()
	{
		switch(suit)
		{
			case SPADES: return "Spades";
			case HEARTS: return "Hearts";
			case DIAMONDS: return "Diamonds";
			case CLUBS: return "Clubs";
			default: return "ERROR";
		}
	}
	//**********************************************
	public String getStringValue()
	{
		switch(value)
		{
			case 1: return "Ace";
			case 2: return "2";
			case 3: return "3";
			case 4: return "4";
			case 5: return "5";
			case 6: return "6";
			case 7: return "7";
			case 8: return "8";
			case 9: return "9";
			case 10: return "10";
			case 11: return "Jack";
			case 12: return "Queen";
			case 13: return "King";
			default: return "ERROR";
		}
	}
	//******************************************************
	public String toString()
	{
		return getStringValue() + " of " + getStringSuit();
	}
	//******************************************************
	public String toImageName()
	{
		
		StringBuilder stringBuilder = new StringBuilder();
		String imageName = new String();
		
		switch(value)
		{
			case 1: stringBuilder.append("ace"); break;
			case 2: stringBuilder.append("2"); break;
			case 3: stringBuilder.append("3"); break;
			case 4: stringBuilder.append("4"); break;
			case 5: stringBuilder.append("5"); break;
			case 6: stringBuilder.append("6"); break;
			case 7: stringBuilder.append("7"); break;
			case 8: stringBuilder.append("8"); break;
			case 9: stringBuilder.append("9"); break;
			case 10: stringBuilder.append("10"); break;
			case 11: stringBuilder.append("jack"); break;
			case 12: stringBuilder.append("queen"); break;
			case 13: stringBuilder.append("king"); break;
			default: break;
		}
		
		switch(suit)
		{
			case 1: stringBuilder.append("s.jpg"); break;
			case 2: stringBuilder.append("h.jpg"); break;
			case 3: stringBuilder.append("d.jpg"); break;
			case 4: stringBuilder.append("c.jpg"); break;
			default: break;
		}
		
		imageName = stringBuilder.toString();
		
		return imageName;
		
	}
}
