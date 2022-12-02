package AI.aNN;

import application.Card;
import java.util.ArrayList;
import java.util.LinkedList;
/**
Get data ready for ANN, change state to numberical values
 */
public class ANNData {
    // if unkown is 0 - if in my hand 1 - if played -1 - highsuit 2
    // if the HighSuit has been played it will have -2 -> thus math.abs(2) will get highsuit
    int[] deckInt;
    int[] output;
    ArrayList<Card> deckCard;

    public ANNData(){
        initDeck();
    }
    public ANNData(LinkedList<Card> cardsLeft, Card highsuit, Card[] aiHand, Card onFloor, Card output){
        initDeck();
        setCardsLeft(cardsLeft);
        setHighSuit(highsuit);
        setAiHand(aiHand);
        if(onFloor != null)
            setOnFloor(onFloor);
        setOutPut(output);
    }

    public void makeRound(LinkedList<Card> cardsLeft, Card highsuit, Card[] aiHand, Card onFloor){
        initDeck();
        setCardsLeft(cardsLeft);
        setHighSuit(highsuit);
        setAiHand(aiHand);
        if(onFloor != null)
            setOnFloor(onFloor);
    }



    private void setOnFloor(Card card){
        deckInt[indexOf(card)] = 2;

    }


    private void initDeck(){
        deckCard = new ArrayList<>();
        deckInt = new int[40];
        output = new int[40];
//        output[0] = -100;
        int index = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deckInt[index] = 0;
                output[index] = 0;
                deckCard.add(new Card(suit, rank));
                index ++;
            }
        }

    }

//    private void playCard (Card card){
//        deckInt[indexOf(card)] = -1;
//    }

    private void setAiHand (Card[] x){
        for(Card card : x){
            if(card != null)
                deckInt[indexOf(card)] = 1;
        }

    }
    private void setHighSuit (Card card){
        deckInt[indexOf(card)] = -2;
    }

    private void setCardsLeft(LinkedList<Card> cardsLeft){
        for(Card card: deckCard){
            if(!contains(card,cardsLeft)){
                deckInt[indexOf(card)] = -1;
            }
        }
    }
    private void setOutPut(Card card){
        output[cardToInt(card)] =  1 ;
    }
    public int[] getOutput(){
        return output;
    }

    public int[] getDeckInt(){
        return deckInt;
    }


    private Card intToCard(int intPlayed){
        return deckCard.get(intPlayed);

    }
    protected int cardToInt(Card card){
        return indexOf(card);
    }

    public ArrayList<Card> getDeckCard() {
        return deckCard;
    }




    private int indexOf(Card o) {
        for (int i = 0; i < deckCard.size(); i++) {
            if(deckCard.get(i).getRank() == o.getRank() && deckCard.get(i).getSuit() == o.getSuit()){
                return i;
            }
        }
        return -1;
    }

    private boolean contains(Card card, LinkedList<Card> cardsLeft){
        for (int i = 0; i < cardsLeft.size(); i++) {
            if(cardsLeft.get(i).getRank() == card.getRank() && cardsLeft.get(i).getSuit() == card.getSuit()){
                return true;
            }
        }
        return false;
    }




}
