package application;

import java.util.*;

import application.Card.Rank;
import application.Card.Suit;
import application.GameSimulator;
import org.checkerframework.checker.units.qual.C;

/**
 * this class make a shuffled deck of cards for the game
 */
public class Deck {
    public Stack<Card> shuffled;
    public LinkedList<Card> sorted;
    boolean i = true;

    public Deck() {
        createDeck();
        createShufleDeck();
    }

    /**
     * Take unshuffle deck of cards and shuffle it
     *
     * @param unshuffled deck of cards
     * @return Shuffled deck of cards
     */
    public Stack<Card> shuffle(List<Card> unshuffled) {
        if (i) {
            shuffled = new Stack<Card>();
            for (int i = 40; i > 0; i--) {
                int shuffleCards = (int) (Math.random() * i);
                shuffled.push(unshuffled.get(shuffleCards));

                unshuffled.remove(shuffleCards);
            }
            i = false;
        }
        return shuffled;


    }

    /**
     * Make an unshuffled deck of cards and shuffle it
     *
     * @return shuffled deck of cards
     */
    public List<Card> createDeck() {
        sorted = new LinkedList<Card>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                sorted.add(new Card(suit, rank));
            }

        }
        return sorted;

    }

    public Stack<Card> createShufleDeck(){
        LinkedList<Card> cardsLeft = new LinkedList<>();
        cardsLeft = (LinkedList<Card>) sorted.clone();
        if (GameSimulator.fixedDeck){
            return createFixedDeck();
        }
//        System.out.println(Arrays.toString(sorted.toArray()));
//        System.out.println(Arrays.toString(cardsLeft.toArray()));
        return shuffle(cardsLeft);
    }

    public static LinkedList<Card> cardsLeftUpdate (LinkedList<Card> cardsLeft, Card played){
        cardsLeft.remove(played);
        return cardsLeft;
    }
    public static LinkedList<Card> cardsLeftUpdateHand (LinkedList<Card> cardsLeft, Card[] played){
        for (int k = 0; k < played.length; k++) {
            cardsLeft.remove(played[k]);
        }
        return cardsLeft;
    }

    private Stack<Card> createFixedDeck(){
        shuffled = new Stack<Card>();
        shuffled.push(sorted.get(4));
        shuffled.push(sorted.get(13));
        shuffled.push(sorted.get(30));
        shuffled.push(sorted.get(23));
        shuffled.push(sorted.get(0));
        shuffled.push(sorted.get(25));
        shuffled.push(sorted.get(1));
        shuffled.push(sorted.get(39));
        shuffled.push(sorted.get(24));
        shuffled.push(sorted.get(3));
        shuffled.push(sorted.get(10));
        shuffled.push(sorted.get(27));
        shuffled.push(sorted.get(36));
        shuffled.push(sorted.get(31));
        shuffled.push(sorted.get(8));
        shuffled.push(sorted.get(19));
        shuffled.push(sorted.get(34));
        shuffled.push(sorted.get(16));
        shuffled.push(sorted.get(5));
        shuffled.push(sorted.get(11));
        shuffled.push(sorted.get(32));
        shuffled.push(sorted.get(22));
        shuffled.push(sorted.get(6));
        shuffled.push(sorted.get(21));
        shuffled.push(sorted.get(12));
        shuffled.push(sorted.get(33));
        shuffled.push(sorted.get(38));
        shuffled.push(sorted.get(2));
        shuffled.push(sorted.get(14));
        shuffled.push(sorted.get(29));
        shuffled.push(sorted.get(9));
        shuffled.push(sorted.get(15));
        shuffled.push(sorted.get(7));
        shuffled.push(sorted.get(20));
        shuffled.push(sorted.get(18));
        shuffled.push(sorted.get(37));
        shuffled.push(sorted.get(26));
        shuffled.push(sorted.get(17));
        shuffled.push(sorted.get(28));
        shuffled.push(sorted.get(25));
        return shuffled;
    }

}

















