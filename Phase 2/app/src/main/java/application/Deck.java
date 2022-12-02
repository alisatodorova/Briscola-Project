package application;

import java.util.*;

import application.Card.Rank;
import application.Card.Suit;

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

}
