package AI.aNN;

import application.Card;

import java.util.LinkedList;
import java.util.List;
/**
    this is ANN agent it make a ANN from saved weights and biases, take the right
     data from ANNData and predict using ANN and return the best action
 */

public class ANNAgent {
    NeuralNetwork nn;
    public ANNAgent(){
         nn = new NeuralNetwork();
    }

    public int playCard(LinkedList<Card> cardsLeft, Card highsuit, Card[] aiHand, Card onFloor){
        ANNData annData = new ANNData();
        annData.makeRound(cardsLeft,highsuit,aiHand,onFloor);
        int[] input = annData.getDeckInt();

        int outputnn = nn.predict(input);
//        int output = annData
        Card cardOutput = annData.getDeckCard().get(outputnn);
//        System.out.println("this is the card " + cardOutput.toString());
//        System.out.println(" this is the hand " + aiHand[0] +" "+  aiHand[1]+ " " + aiHand[2]);
        int output = -1;
        for(int i = 0; i < aiHand.length; i ++){
            if (aiHand[i] == null) continue;
            if(cardOutput.getSuit() == aiHand[i].getSuit() && cardOutput.getRank() == aiHand[i].getRank()){
                output = i ;
            }
        }
        return output;
    }
}
