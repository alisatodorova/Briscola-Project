package application;

/**
 * this class make each card of the deck
 */

public class Card {

    /**
     * the 4 Suits
     */
    public enum Suit {
        A, B, C, D
    }

    /**
     * the rank of the card with an card value for counting the points and
     * the index for figuring out which card can trump another in the same suit
     */
    public enum Rank {
        TWO(0, 0), FOUR(0, 1), FIVE(0, 2), SIX(0, 3),
        SEVEN(0, 4), JACK(2, 5), QUEEN(3, 6), KING(4, 7),
        THREE(10, 8), ACE(11, 9);

        final int cardValue;
        final int index;

        /**
         * Initial the value of card and its index
         *
         * @param cardValue Value of card
         * @param index     Index of card
         */
        Rank(int cardValue, int index) {
            this.cardValue = cardValue;
            this.index = index;
        }

        /**
         * @return Value of the card
         */
        public int getCardValue() {
            return this.cardValue;
        }

        /**
         * @return Index of the card
         */
        public int getIndex() {
            return this.index;
        }
    }

    ;

    private final Suit suit;
    private final Rank rank;

    /**
     * Initial the card with its rank and suit
     *
     * @param suit Suit of the card
     * @param rank Rank of the card
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * empty constructor
     */
    public Card() {
        // for player Hand
        // KEEP EMPTY
        suit = null;
        rank = null;
    }

    /**
     * @return The rank of the card
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * @return the suit of the card
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * @return the string value of card
     */
    public String toString() {
        return getSuit().toString() + getRank().toString();
    }

}
