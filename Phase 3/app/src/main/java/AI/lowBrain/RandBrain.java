package AI.lowBrain;

import application.Card;

/**
 * Base Line Agent
 * for having a version that is definitly stupid
 */
public class RandBrain implements Brain{
    /**
     * Gets the same parameters as all the other 'brain's
     * uses only
     * @param whereAmI (the State of the Lower tier brains wiht currentHand and aiTurn)
     * @param onFloor gets the card that the agents opponent has layed out on the table already.
     * @return the integer that is needed to process the playing of a card at an index.
     * */
    public int whatToPlay(State whereAmI, Card onFloor){
        int index = (int)Math.floor(Math.random() * 3);
        while (whereAmI.getCurrentHand()[index] == null){
            index = (int)Math.floor(Math.random() * 3);
        }
        return index;
    }

}
