package AI.ReinforcementLearning;
import application.Card;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QState {
    boolean aiTurn;
    Card highsuit;
    Card[] aiHand;
    Card opponentCard;
    int stateID;
    //public ArrayList<QState> states = new ArrayList<>();
    protected static int stateIDHolder = 0;



    public QState(boolean aiTurn, Card highsuit, Card[] aiHand, Card opponentCard) {
        this.aiHand = aiHand;
        this.aiTurn = aiTurn;
        this.highsuit = highsuit;
        this.opponentCard = opponentCard;
        // checkDuplicate(this);
        setStateID();;
    }


    public void setStateID() {
        stateID = stateIDHolder;
       // System.out.println("STATE ID Constructor " + stateIDHolder);
        stateIDHolder++;

    }



    public Card getOpponentCard() {
        return opponentCard;
    }

    public Card[] getAIHand() {
        return aiHand;
    }

    public boolean ifAITurn() {
        return aiTurn;
    }

    public int getStateID() {
        return stateID;
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append(aiTurn);
        output.append(" ");
        output.append((aiHand[0] == null) ? "null" : aiHand[0]);
        output.append(" ");
        output.append((aiHand[1] == null) ? "null" : aiHand[1]);
        output.append(" ");
        output.append((aiHand[2] == null) ? "null" : aiHand[2]);
        output.append(" ");
        output.append((opponentCard == null) ? "null" : opponentCard);
        output.append(" ");
        output.append((highsuit == null) ? "null" : highsuit);
        output.append(" ");
        return output.toString();
//        return aiTurn + " " + aiHand[0] + " " + aiHand[1] + " " + aiHand[2] + " " + opponentCard + " " + highsuit + "";
    }


   /* public int checkDuplicate(QState S ) {
        for (int i = 0; i < TrainQ.states.length; i++) {
            if (S.getAIHand().equals(TrainQ.states[i].getAIHand()) && S.getOpponentCard() == TrainQ.states[i].getOpponentCard() && S.highsuit == TrainQ.states[i].highsuit) {
                return i;
            }
            else {
                stateID = stateIDHolder;
                TrainQ.states[stateID] = S;
                System.out.println("STATE ID Constructor " + stateIDHolder);
                stateIDHolder++;
                return -1;
            }
        }
        return -1;
    }
    */
}