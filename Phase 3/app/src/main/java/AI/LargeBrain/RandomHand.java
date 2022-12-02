package AI.LargeBrain;

import application.Card;
import application.Deck;

import java.util.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RandomHand{
    private static int cheatingCardCounter;
    private static final boolean cheatingOnOpponentCards = true;
    private static final boolean cheatingOnAICards = true;

    public static int cheatingFixed = 0; // 1, 2, 3 cards shall be known to the AI

    public static Card[] randomHand(LinkedList<Card> x, int length, Card[] oppHand){
        LinkedList<Card> cardsLeft = new LinkedList<>(x);
        ArrayList<Card> humanHand = new ArrayList<>();
        cheatingCardCounter = cheatingFixed;
        LinkedList<Card> removedNull = new LinkedList<>();
        for (Card crd : oppHand){
            if (crd != null){
                removedNull.add(crd);
            }
        }
        oppHand = removedNull.toArray(new Card[0]);

        ArrayList<Card> temporalCheating = new ArrayList<>();

//        int[] stolen = new int[length];
        if (cardsLeft.size() < length){
            length = cardsLeft.size();
        }

        for(int i=0;i< length;i++){
            if (cheatingCardCounter > 0){
                Card temporalCard = cheatingAgent(cardsLeft, removedNull.toArray(new Card[0]));
                int counter = 0;
                while ((temporalCheating.contains(temporalCard) || temporalCard == null) && counter < 3){
                    temporalCard = cheatingAgent(cardsLeft, removedNull.toArray(new Card[0]));
                    counter++;
                }
                temporalCheating.add(temporalCard);
                humanHand.add(temporalCard);
//                System.out.println(temporalCheating);
//                cardsLeft.remove(temporalCard);
                cheatingCardCounter--;
            }else{
                int ran=(int)Math.floor(Math.random()*(cardsLeft.size()-1));
                humanHand.add(cardsLeft.get(ran));
//            cardsLeft.remove(ran);
//            stolen[i] = ran;
            }
        }
        // For the ArrayList to Array Conversion it is needed.
        Card[] hand = new Card[length];
        return humanHand.toArray(hand);
    }

    protected static String toString(Card[] currentHand) {
        StringBuilder toStringReturn= new StringBuilder();
        for (int i = 0; i < currentHand.length; i++){
            toStringReturn.append(currentHand[i]).append(" ");
        }
        return toStringReturn.toString();
    }

    public static Card cheatingAgent(LinkedList<Card> cardsLeft, Card[] opponentHand){
        int lenOpp = opponentHand.length;
        for (int i = 0 ; i < opponentHand.length; i++)
            if (opponentHand[i] == null) lenOpp--;
        Random random = new Random();
        if (lenOpp <= 0) {
            for (int i = 0; i < opponentHand.length; i++){
                if (opponentHand[i] != null){
                    return opponentHand[i];
                }
            }
            return null;
        }
        int randomIndex = random.nextInt(lenOpp);
        return opponentHand[randomIndex];
    }
}