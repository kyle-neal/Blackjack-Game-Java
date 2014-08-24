//This class represents a hand by using a vector to store card objects.

package blackjack;

import java.util.Vector;

public class blackJackHand 
{
	private Vector hand;
	
	public blackJackHand()
	{
		hand = new Vector();
	}

	
	public void clearHand()
	{
		hand.removeAllElements();
	}
	
	public void addCard(Card c)
	{
		if(c != null)
			hand.addElement(c);
		else
			System.out.println("ERROR: Tried to add a null Card");
	}
	
	public int getCardCount()
	{
		return hand.size();
	}
	
	public int getBlackJackValue()
	{
		int value = 0;
		boolean haveAce = false;
		
		for(int i = 0; i < hand.size(); i++)
		{
			if(((Card) hand.elementAt(i)).getValue() > 10)		//For King, Queen, Jack
				value += 10;
			else if(((Card) hand.elementAt(i)).getValue() == 1)		//Ace
			{
				value += 1;
				haveAce = true;
			}
			else
			{
				value += ((Card) hand.elementAt(i)).getValue();
			}
		}
		
		//Here I check to see if I have an ace, and if making that ace an 11
		//would improve score then I add 10 to the value.
		if(haveAce == true && (value+10) <= 21)	
			value += 10;
		
		return value;
	}
	
	public boolean checkForBust()
	{
		if(getBlackJackValue() > 21)
			return true;
		else
			return false;
	}

	public String[] returnCardImageNames()
	{
		//This function retrieves image name representations from a Card object.
		//Returns a string array of image names for the given hand.
		
		String[] names = new String[hand.size()];
		
		for(int i = 0; i < hand.size(); i++)
		{
			Card card = (Card)hand.elementAt(i);
			names[i] = card.toImageName();
		}
		
		return names;
	}

}
