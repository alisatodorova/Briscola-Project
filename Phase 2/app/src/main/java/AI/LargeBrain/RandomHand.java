package AI.LargeBrain;

import application.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RandomHand{
    public static Card[] randomHand(LinkedList<Card> cardsLeft ){
        ArrayList<Card> humanHand = new ArrayList<>();
        //System.out.println(Arrays.toString(cardsLeft.toArray()));
        //Card[] humanHand = new Card[3];
        int length = 3;
        int[] stolen = new int[3];
        if (cardsLeft.size() < 3){
            length = cardsLeft.size();
        }

        for(int i=0;i< length;i++){
            int ran=(int)Math.floor(Math.random()*(cardsLeft.size()-1));
            humanHand.add(cardsLeft.get(ran));
            cardsLeft.remove(ran);
            stolen[i] = ran;
        }
        // For the ArrayList to Array Conversion it is needed.
        Card[] hand = new Card[length];
        return humanHand.toArray(hand);
    }
}