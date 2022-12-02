package AI.LargeBrain;

import application.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class CheatingAgent {

    private LinkedList<Card> cardsLeft;
    private ArrayList<Card> oddCards;
    private ArrayList<Card> evenCards;
    private Card[] opponentHand;
    private Card[] aiHand;
    protected ArrayList<Card> futureOpponentCards;
    protected ArrayList<Card> futureAICards;
    protected ArrayList<Card> futureOpponentCardsFixed;
    protected ArrayList<Card> futureAICardsFixed;
    private Card highSuit;
    private static final boolean cheatOnOpponentsCards = true;
    private static final boolean cheatOnAICards = true;
    private int playerID;
    public static int roundCount = 20;
    public static double roundProbs = 1;

    public CheatingAgent(Stack<Card> cardsLeft, int id){ // the passed CardsLeft is
        this.cardsLeft = new LinkedList<>(cardsLeft);
        this.highSuit = cardsLeft.get(33);
        oddCards = new ArrayList<>();
        evenCards = new ArrayList<>();
        opponentHand= opponentHandStart();
        futureOpponentCards = new ArrayList<>();
        futureAICards = new ArrayList<>();
        playerID = id;
        cheat();
    }

//    public CheatingAgent(LinkedList<Card> cardsLeft, int id){ // the passed CardsLeft is
//        this.cardsLeft = new LinkedList<>(cardsLeft);
//        this.highSuit = cardsLeft.get(33);
//        oddCards = new ArrayList<>();
//        evenCards = new ArrayList<>();
//        opponentHand= opponentHandStart();
//        futureOpponentCards = new ArrayList<>();
//        futureAICards = new ArrayList<>();
//        playerID = id;
//        cheat();
//    }

    /*33 cards left on deck after cards for round have been dealt
    PLayer 1 will get the odds
    PLayer 2 will get the evens + the HS
     */

    public void cheat(){
        // if ID == 1
            // do it in normal order
            // Odd is opponent
        // else
            // reverse order
            cheatingOddCards();
            cheatingEvenCards();
        if (cheatOnOpponentsCards){
            if (playerID ==2){
                futureOpponentCards = (ArrayList<Card>) evenCards.clone();
            }
            else{
                futureOpponentCards = (ArrayList<Card>) oddCards.clone();
            }
        }
        if (cheatOnAICards){
            if (playerID == 2){
                futureAICards = (ArrayList<Card>) oddCards.clone();
            }
            else{
                futureOpponentCards = (ArrayList<Card>) evenCards.clone();
            }
        }
        futureOpponentCardsFixed = (ArrayList<Card>) futureOpponentCards.clone();
        futureAICardsFixed = (ArrayList<Card>) futureAICards.clone();
    }

    public void cheatingOddCards(){
        for (int i=32; i > 0; i-=2) {
            oddCards.add(cardsLeft.get(i));
        }

    }

    public void cheatingEvenCards(){
        for (int i=31; i >= -1; i-=2) {
            if (i <= 0){
                evenCards.add(highSuit);
            }
            else{
                evenCards.add(highSuit);
            }
        }
    }

    protected void nextPayouts(){
        if(futureAICards.size() > 0) {
            updateAiHand(null);
        }
        if(futureOpponentCards.size() > 0) {
            updateOpponentHand(null);
        }

    }

    public void opponentPlayed(Card card){
        futureOpponentCards.remove(card);
//        futureOpponentCardsFixed.remove(card);
    }

    public void aiPlayed (Card card){
        futureAICards.remove(card);
        futureAICardsFixed.remove(card);
    }

    public Card[] updateOpponentHand(Card played){
        handUpdate(played, opponentHand, futureOpponentCards);
        return opponentHand;
    }

    public Card[] updateAiHand(Card played) {
        handUpdate(played, aiHand, futureAICards);
        return aiHand;
    }

    public void handUpdate(Card played, Card[] hand, ArrayList<Card> futureCards){
        for (int j = 0; j < 3; j++) {
            if (hand[j] == played) {
                if(futureCards.size() > 0) {
                    Card nextCard = futureCards.get(0);
                    int counter = 1;
                    while (Arrays.asList(hand).contains(nextCard)) {
                        if (futureCards.size() > counter)
                            nextCard = futureCards.get(counter++);
                        else {
                            hand[j] = null;
                            break;
                        }
                    }
                    hand[j] = nextCard;
                }
                else
                    hand[j] = null;
            }
        }
    }

    public Card[] opponentHandStart(){
        return new Card[] {cardsLeft.get(38), cardsLeft.get(36), cardsLeft.get(34)};
    }


    public Card[] aiHandStart(){
        aiHand = new Card[] {cardsLeft.get(39), cardsLeft.get(37), cardsLeft.get(35)};
        return aiHand;
    }

    public Card[] getOpponentHand(){
        return opponentHand;
    }

    public Card[] getAiHand(){
        return aiHand;
    }

//    public CheatingAgent clone(){
//        CheatingAgent newAgent = new CheatingAgent(cardsLeft, playerID);
//        return newAgent;
//    }

}


