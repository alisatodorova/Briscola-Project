package AI.lowBrain;
import application.*;

/**
 * THe State for the 3 lower tier Brains
 * */
public class State {
    /**
     * instance variables
     * */
    Card[] currentHand = new Card[3];
    boolean myTurn;
    private int utility;

    /**
     * Constructor with
     * @param aiStart Is it this Agents turn to start the 'round'
     * @param currentHand the agents hand*/
    public State(boolean aiStart, Card[] currentHand) {
        myTurn = aiStart;
        this.currentHand = currentHand;
    }

    /**
     * safty initializator
     * @param currentHand only takes the hadn as its not our init turn but focus on the Card on the table already*/
    public State(Card[] currentHand) {
        this.currentHand = currentHand;
        this.myTurn = false;
    }

    /**
     * Rturning the currentHand
     * @return currentHand
     */
    public Card[] getCurrentHand() {
        return currentHand;
    }

    /**
     * @return the answer to the question if it is the agents turn*/
    public boolean isMyTurn() {
        return myTurn;
    }

    /**
     * Is the hand Empty
     * @return True o False */
    public boolean isCurrentHandEmpty(){
        if(currentHand[0] == null && currentHand[1] == null && currentHand[2] == null){
            return true;
        }
        return false;
    }

    /**Getting the Utility used by BigBrainPruning
     * @return integer of the Untility*/
    public int getUtility(){
        return utility;
    }
    /**
     * Setting a new Utility for the current State
     * @param u integer representation of the Utility*/
    public void setUtility(int u){
        utility = u;
    }
}
