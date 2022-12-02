package AI.lowBrain;

import application.Card;

/**
 * Java class for the BaseLine agent
 */
public class BaseLineBrain implements Brain {
    Card highSuit;
    

    /**
     * Constructor for the BaseLine agent
     * @param highSuit the high suit
     */
    public BaseLineBrain(Card highSuit){
        this.highSuit = highSuit;
    }

    /**
     * Determines what the agent should play 
     * based on the agent's current card and the human's current hand
     * @param whereAmI the agent's current hand
     * @param onFloor the human's current hand
     * @return card to play
     */
    public int whatToPlay (State whereAmI, Card onFloor)
    {
        for (int i = 0; i < whereAmI.getCurrentHand().length; i++)
        {
            if (whereAmI.getCurrentHand()[i] == null)
            {
                continue;
            }
            // If the agent has a High Suit Card, it plays that card
            else if (whereAmI.getCurrentHand()[i].getSuit() == highSuit.getSuit())
            {
                return i;
            }
            // The agent is the second player to play:
            // If the agent has a card with the same suit as the human has just played,
            // then play that card
            else if (onFloor != null && whereAmI.getCurrentHand()[i].getSuit() == onFloor.getSuit())
            {
                return i;
            }
            // If the agent is the first player to play,
            // then the agent plays a card with no points.
            else if (onFloor == null && whereAmI.getCurrentHand()[i].getRank().getIndex() < 5
                    && whereAmI.getCurrentHand()[i].getSuit() != highSuit.getSuit())
            {
                return i;
            }
        }
        // Else:
        // Generate 3 cards at random and play one of them
        int index =  (int)Math.floor(Math.random() * 3);

        while (whereAmI.getCurrentHand()[index] == null){
            index = (int)Math.floor(Math.random() * 3);
        }

        return index;
    }
}
