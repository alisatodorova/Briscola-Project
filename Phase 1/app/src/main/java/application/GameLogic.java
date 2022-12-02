package application;

public class GameLogic {
    boolean played;

    public GameLogic() {
        this.played = false;
    }

    /**
     * Take the parameters needed to calculate which player win the round
     *
     * @param card1        Card of first player who start the round
     * @param card2        Card of second player who start the round
     * @param highSuit     High suit of the game
     * @param firstPlayer  Who start the round
     * @param secondPlayer Second player who play the round
     * @return Player who win the round
     */
    public Player checkCards(Card card1, Card card2, Card highSuit, Player firstPlayer, Player secondPlayer) {
        if (card2.getSuit() != card1.getSuit()) {

            if (card2.getSuit() != highSuit.getSuit()) {
                // Player 1 wins
                firstPlayer.addPoints(calculatePoints(card1, card2));
                return firstPlayer;
            } else {
                // Player 2 wins
                secondPlayer.addPoints(calculatePoints(card1, card2));
                return secondPlayer;
            }
        } else {
            // check for rank
            if (card2.getRank().getIndex() < card1.getRank().getIndex()) {
                // Player 1 wins
                firstPlayer.addPoints(calculatePoints(card1, card2));
                return firstPlayer;
            } else {
                // Player 2 wins
                secondPlayer.addPoints(calculatePoints(card1, card2));
                return secondPlayer;
            }
        }
    }

    /**
     * Calculate the points you get from cards that were played
     *
     * @param card1 First card
     * @param card2 Second card
     * @return Value of two cards together
     */
    private int calculatePoints(Card card1, Card card2) {
        return card1.getRank().getCardValue() + card2.getRank().getCardValue();
    }

}
