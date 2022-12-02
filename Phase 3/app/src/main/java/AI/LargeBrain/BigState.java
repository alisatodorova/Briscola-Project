package AI.LargeBrain;

import AI.lowBrain.SimpleBrain;
import AI.lowBrain.State;
import application.Card;
import application.GameLogic;
import application.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BigState extends State {
    boolean aiTurn;
    Card highsuit;
    LinkedList<Card> cardsLeft;
    Card[] humanHand;
    Card[] aiHand;
    GameLogic briscola;
    int round;

    public int getRound() {
        return round;
    }

    public void setHumanHand(Card[] humanHand) {
        this.humanHand = humanHand;
    }

    public BigState(LinkedList<Card> cardsLeft, Card[] aiHand, Card[] humanHand, Card highsuit) {
        super(true, aiHand);
        this.humanHand = humanHand;
        this.aiHand = aiHand;
        this.aiTurn = true;
        this.highsuit = highsuit;
        this.cardsLeft = cardsLeft;
    }

    public BigState(LinkedList<Card> cardsLeft, Card[] aiHand, Card highsuit) {
        super(true,aiHand);
        this.humanHand = humanHand;
        this.aiHand = aiHand;
        this.aiTurn = true;
        this.highsuit = highsuit;
        this.cardsLeft = cardsLeft;

    }

    public BigState(LinkedList<Card> cardsLeft, Card[] aiHand, Card highsuit, int round) {
        super(true,aiHand);
        this.aiHand = aiHand;
        this.aiTurn = true;
        this.highsuit = highsuit;
        this.cardsLeft = cardsLeft;
        this.round = round;

    }



    public int calUtility(ArrayList<Card> x, boolean react) {
//        System.out.println("this is the first path" + x);
        List<Card> path = (ArrayList<Card>) x.clone();
        if(path.size()%2 != 0 ){
            path.remove(path.size()-1);
        }
        this.briscola = new GameLogic();
        int utility = 0;
        Player max = new Player("MAX", 1);
        Player min = new Player("MIN", 2);

        Card card1 = path.get(0);
        Card card2 = path.get(1);
        Player next;
        if (react) {
            next = briscola.checkCards(card1, card2, highsuit, min, max);
            path = path.subList(2, path.size());
            while(!path.isEmpty()) {
                if (next.getId() == min.getId() && (path.size() >= 2)) {
                    card1 = path.get(0);
                    card2 = path.get(1);
                    next = briscola.checkCards(card1, card2, highsuit, min, max);
                } else if(next.getId() == max.getId() && (path.size() >= 2)) {
                    card1 = path.get(1);
                    card2 = path.get(0);
                    next = briscola.checkCards(card1, card2, highsuit, max, min);
                }else{
                    System.out.println("NOOOOOOOOOOOOOO");
                }
                path = path.subList(2, path.size());
            }
        }
        else{
            next = briscola.checkCards(card1, card2, highsuit, max, min);
            path = path.subList(2, path.size());
            while(!path.isEmpty()) {
                if (next.getId() == min.getId() && (path.size() >= 2)) {
                    card1 = path.get(1);
                    card2 = path.get(0);
                    next = briscola.checkCards(card1, card2, highsuit, min, max);
                } else if(next.getId() == max.getId() && (path.size() >= 2)) {
                    card1 = path.get(0);
                    card2 = path.get(1);
                    next = briscola.checkCards(card1, card2, highsuit, max, min);
                }else{
                    System.out.println("NOOOOOOOOOOOOOO");
                }
                path = path.subList(2, path.size());
            }
        }
//        path = path.subList(2, path.size());



//        while(!path.isEmpty()) {
//            if(react){
//
//            }else{
//                while(!path.isEmpty()) {
//                    if (next.getId() == min.getId() && (path.size() >= 2)) {
//                        card1 = path.get(1);
//                        card2 = path.get(0);
//                        next = briscola.checkCards(card1, card2, highsuit, min, max);
//                    } else if(next.getId() == max.getId() && (path.size() >= 2)) {
//                        card1 = path.get(0);
//                        card2 = path.get(1);
//                        next = briscola.checkCards(card1, card2, highsuit, max, min);
//                    }else{
//                        System.out.println("NOOOOOOOOOOOOOO");
//                    }
//                    path = path.subList(2, path.size());
//                }
//
//            }
//
//        }
        utility = max.getPoints() - min.getPoints();
//        System.out.println("this is utility" + utility);
        return utility;
    }

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

    public boolean getAITurn(){
        return aiTurn;
    }

    public void setAITurn(boolean aiTurnInput){
        this.aiTurn = aiTurnInput;
    }

    public BigState result(int action) {
        BigState newState;
        Card played = new Card();
        Card[] newAiHand = new Card[3];
        System.arraycopy(aiHand, 0, newAiHand, 0, aiHand.length);
        Card[] newHumanHand  = new Card[humanHand.length];
        System.arraycopy(humanHand, 0 , newHumanHand, 0, humanHand.length);

        if (aiTurn) {
            for (int i = 0; i < aiHand.length; i++) {
                if (action == i) {
//                    played = aiHand[i];
                    newAiHand[i] = null;
                }
            }
        } else {
            for (int j = 0; j < humanHand.length; j++) {
//                System.out.println("this is human hand here" + humanHand[0].toString() + humanHand[1].toString() + humanHand[2].toString());
                if (action == j) {
//                    played = humanHand[j];
                    newHumanHand[j] = null;

                }
            }
        }
//        for (int j = 0; j < cardsLeft.size(); j++) {
//             if (played.getRank() == cardsLeft.get(j).getRank() && played.getSuit() == cardsLeft.get(j).getSuit()) {
//                cardsLeft.remove(j);
//            }
//        }
        newState = new BigState((LinkedList<Card>)(cardsLeft), newAiHand, newHumanHand, highsuit);
        newState.setAITurn(!this.aiTurn);
        return newState;
    }

    public LinkedList<Card> getCardsLeft() {
        return cardsLeft;
    }

    public Card[] getHumanHand() {
        return humanHand;
    }

    public Card[] getAiHand() {
        return aiHand;
    }

    /**
     * Calculate the points for Max and Min player.
     *
     * @param card1        Card of first player who starts the round
     * @param card2        Card of second player who starts the round
     * @return Value of two cards together for Max and Min, respectively
     */
    private int calculateCorrectPoints(Card card1, Card card2, boolean maxWon){
        if(maxWon) {
            return card1.getRank().getCardValue() + card2.getRank().getCardValue();
        }
        else {
            return -(card1.getRank().getCardValue() + card2.getRank().getCardValue());
        }
    }


}
