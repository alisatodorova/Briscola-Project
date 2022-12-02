package AI.lowBrain;
import application.*;

import java.util.Arrays;

/**
     SimpleBrain represents the basic AI
     It is a simple Minimax agent that calculates and compares the utility values of the current hand,
     for the current round
 */
public class SimpleBrain implements Brain{

    private static final boolean DEBUG = false;
    public Card highSuit;

    /**
     * Minimax is aware of the the high suit for the game
     * @param highSuit the high suit
     */
    public SimpleBrain(Card highSuit){
        this.highSuit = highSuit;
    }

    /**
     * Get's the same parameters as all the other brains
     * @param whereAmI the State with the agents hand and the
     * @param table card played by opponent
     * @return the integer that is needed to process the playing of a card at an index.
     */
    public int whatToPlay(State whereAmI, Card table ){
        if(whereAmI.isMyTurn()){ // if it's AI turn
            return playFirst(whereAmI.getCurrentHand());
        }else{ //if Player played and AI need to response
            return playSecond(whereAmI.getCurrentHand(), table);
        }
    }

    /**
     * When the AI is playing the first card of the round
     * @param myHand AI's current hand
     * @return the integer that is needed to process the playing of a card at an index.
     */
    public int playFirst(Card[] myHand){
        Card bestCard = new Card();
        // The Max Card at index 0 could be null and then the verification breaks.
        // so I initialize the max card to null
        Card max = null;
        // have an itterator over the hand - as if the hand is empty the game will be ended anyhow.
        int itterator = 0;
        // while loop as it is something different for once  than the 'usual' for loop
        while (max == null && itterator < myHand.length)
        {
            // setting the max card to the card on the hand at the itterator
            max = myHand[itterator];
            // increasign the itterator
            itterator++;
        }

        int maxIndex = itterator;
        Card[] suitComparison = new Card[3];
        int j = 0;
        for(int i = 0; i < myHand.length; i++)
        {
            if (myHand[i] == null){
                continue;
            }

            if(myHand[i].getRank().getIndex() > max.getRank().getIndex()) {
                max = myHand[i];
            }

            if(myHand[i].getSuit() == highSuit.getSuit())
            {
                suitComparison[j] = myHand[i];
               j++;
            }
        }

        if(suitComparison[0] != null) {
            if (DEBUG) System.out.println("Suit Comparison : This array contains all suits the AI has " + suitComparison.toString());
            Card maxHighSuit = suitComparison[0];
            for (int i = 0; i < suitComparison.length; i++) {
                if (suitComparison[i] != null) {
                    if (suitComparison[i].getRank().getIndex() > maxHighSuit.getRank().getIndex()) {
                        maxHighSuit = suitComparison[i];
                    }
                }
            }
            bestCard = maxHighSuit;
            for (int k = 0; k < myHand.length; k++) {
                if (myHand[k] == null){
                    continue;
                }

                if ((bestCard.getRank().getIndex() == myHand[k].getRank().getIndex()) && (bestCard.getSuit() == myHand[k].getSuit())) {
                    maxIndex = k;
                }
            }

            if (DEBUG) System.out.println("THE CARD THAT IS BEING PLAYED IS" + bestCard.toString());
            return maxIndex;
        }

        bestCard = max;


        for(int i = 0; i < myHand.length; i++)
        {
            if (myHand[i] == null){
                continue;
            }

            if((bestCard.getRank().getIndex() == myHand[i].getRank().getIndex()) && (bestCard.getSuit() == myHand[i].getSuit()))
            {
                maxIndex = i;
            }
        }

        if (DEBUG) System.out.println("THE CARD THAT IS BEING PLAYED IS" + bestCard.toString());

        return maxIndex;
    }

    /**
     * When the AI is playing the second card of the round (responding to card on table)
     * @param myHand AI's hand
     * @param table card played by the opponent
     * @return When the AI is playing the first card of the round
     */
    public int playSecond(Card[] myHand, Card table){
        int[] desicion = think(myHand,table);
        int index;
        int max = desicion[0];
        index = 0;
        System.out.println(Arrays.toString(desicion));
        for(int i = 1; i < desicion.length; i++){
            if (max < desicion[i]){
                max = desicion[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * Creates an array with utility values for current hand
     * @param AIhand current hand
     * @param table card on the table
     * @return
     */
    public int[] think(Card[] AIhand, Card table){
        int[] decisionArray = new int[3];
        for(int i = 0; i < AIhand.length; i++){
            if (AIhand[i] == null){
                continue;
            }
            decisionArray[i] = thinkCard(AIhand[i], table);
        }
        return decisionArray;
    }

    /**
     * Calculating the utility value for a card
     * @param handCard card in hand that can be played
     * @param table card played by opponent
     * @return utility value as an integer
     */
    public int thinkCard (Card handCard, Card table){
        if (handCard.getSuit() != table.getSuit()) {
            if (handCard.getSuit() != highSuit.getSuit()) {
                // Current Card can be greater than the
                if (handCard.getRank().getIndex() == 8 || handCard.getRank().getIndex() == 9){
                    return -(handCard.getRank().getIndex() + handCard.getRank().getCardValue());
                }
                else {
                    return -(handCard.getRank().getIndex());
                }
            } else {
                // Player 2 wins
                if (handCard.getRank().getIndex() == 8  || handCard.getRank().getIndex() == 9){
                    return handCard.getRank().getIndex() + handCard.getRank().getCardValue() + 10;
                }
                else {
                    return handCard.getRank().getIndex() + 10;
                }
            }
        } else {
            // check for rank
            if ((table.getRank().getIndex() < handCard.getRank().getIndex())) {
                // Player 1 wins
                return positiveHighSuit(handCard);
            } else {
                // Player 2 wins
                return negativeHighSuit(handCard);
            }
        }
    }

    /**
     * When the AI is loosing the round, 10 is substracted from the utility when card is a high suit
     * If not high suit, nothing happens
     * @param handCard card played by AI
     * @return  utility as integer  (adapted in case of high suit played)
     */
    private int negativeHighSuit (Card handCard){
        if (handCard.getSuit() != highSuit.getSuit()){
            return -handCard.getRank().getIndex();
        }
        else {
            return (-handCard.getRank().getIndex() -10);
        }
    }

    /**
     * When AI is winning the round with the given card, 10 points are added to the utility if card is high suit
     * If card played is not high suit nothing happens to utility
     * @param handCard card played by AI
     * @return utility as integer  (adapted in case of high suit played)
     */
    private int positiveHighSuit (Card handCard){
        if (handCard.getSuit() != highSuit.getSuit()){
            return handCard.getRank().getIndex();
        }
        else {
            return (handCard.getRank().getIndex() + 10);
        }
    }
}
