package application;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import application.Card.Rank;
import application.Card.Suit;

/**
 * this class make a shuffled deck of cards for the game
 */
public class Deck {
    Stack<Card> shuffled;
    List<Card> sorted;
    boolean i = true;

    public Deck() {
        createDeck();

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
    public Stack<Card> createDeck() {
        sorted = new LinkedList<Card>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                sorted.add(new Card(suit, rank));
            }

        }
        return shuffle(sorted);

    }

}
