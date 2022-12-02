package AI.LargeBrain;

import AI.lowBrain.SimpleBrain;
import AI.lowBrain.State;
import application.Card;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** creates a BigState object
     * @param : list of cards left in the deck
     * @param : current hand of the AI player
     * @param : current hand of the Human player
     * @param : highsuit of the game
     */

public class BigState extends State {
    boolean aiTurn;
    Card highsuit;
    LinkedList<Card> cardsLeft;
    Card[] humanHand;
    Card[] aiHand;
    SimpleBrain briscola;

    public BigState(LinkedList<Card> cardsLeft, Card[] aiHand, Card[] humanHand, Card highsuit) {
        super(true,aiHand);
        this.humanHand = humanHand;
        this.aiHand = aiHand;
        this.aiTurn = true;
        this.highsuit = highsuit;
        this.cardsLeft = cardsLeft;
    }
    
    /** Calculates the utility of the leaf nodes
     * @param path formed by the cards that hve been played from the root node to the leaf node
     * @return : returns the utility value as an integer
     */
    
    public int calUtility(ArrayList<Card> path) {
        this.briscola = new SimpleBrain(highsuit);
        int utility = 0;
        for (int i = 0; i < path.size() / 2 ; i++) {
            utility += briscola.thinkCard(path.get((i * 2)+1), path.get(i * 2));
        }
        return utility;
    }

      /** Tests if the current state is terminal, i.e, if there are no cards left in the hand (no more actions available)
     *  @return : true if the state is terminal, false if not
     */
    public boolean terminalTest(){
        if(aiTurn){
            for(int i =0; i< aiHand.length ;i++ ){
                if(aiHand[i] != null){
                    return false;
                }
            }


        }else{
            for(int j =0; j< humanHand.length ;j++ ){
                if(humanHand[j] != null){
                    return false;
                }
            }
        }
        return true;
    }

    /** checks if it's the AI's turn to play
     */
    public boolean getAITurn(){
        return aiTurn;
    }

    /** sets the curret turn to AI
     * @param aiTurnInput next Turn - is it the AIs
    */
    public void setAITurn(boolean aiTurnInput){
        this.aiTurn = aiTurnInput;
    }
    
    /** returns the resulting state of an action applied to the current State
     * @param action to be applied to the current State
     * @return : the resulting state after applying the action
     */

    public BigState result(int action) {
        BigState newState;
        Card played = new Card();
        Card[] newAiHand = new Card[3];
        System.arraycopy(aiHand, 0, newAiHand, 0, aiHand.length);
        Card[] newHumanHand  = new Card[3];
        System.arraycopy(humanHand, 0 , newHumanHand, 0, humanHand.length);

        if (aiTurn) {
            for (int i = 0; i < aiHand.length; i++) {
                if (action == i) {
                    played = aiHand[i];
                    newAiHand[i] = null;
                }
            }
        } else {
            for (int j = 0; j < humanHand.length; j++) {
//                System.out.println("this is human hand here" + humanHand[0].toString() + humanHand[1].toString() + humanHand[2].toString());
                if (action == j) {
                    played = humanHand[j];
                    newHumanHand[j] = null;

                }
            }
        }
        for (int j = 0; j < cardsLeft.size(); j++) {
             if (played.getRank() == cardsLeft.get(j).getRank() && played.getSuit() == cardsLeft.get(j).getSuit()) {
                cardsLeft.remove(j);
            }
        }
        newState = new BigState((LinkedList<Card>)(cardsLeft), newAiHand, newHumanHand, highsuit);
        newState.setAITurn(!this.aiTurn);
        return newState;
    }
    
    
    /** returns the list of cards left in the deck
     */
    public List<Card> getCardsLeft() {
        return cardsLeft;
    }

    /** returns the current hand of the human player as an array of type card
     */
    public Card[] getHumanHand() {
        return humanHand;
    }

    /** returns the current hand of the AI player as an array of type card
     */
    public Card[] getAiHand() {
        return aiHand;
    }
}
