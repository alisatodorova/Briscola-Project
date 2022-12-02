import AI.lowBrain.SimpleBrain;
import application.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBrainTestSecond {
    Card highSuit = new Card(Card.Suit.A, Card.Rank.ACE);
    Card[] currentHand = new Card[3];
    SimpleBrain sim = new SimpleBrain(highSuit);
    Card humanCard = new Card(Card.Suit.B, Card.Rank.QUEEN);

    //AI is second player: All cards loose
    @Test
    void aiSecondPlayerAllCardsLoose() {
        currentHand[0] = new Card(Card.Suit.B, Card.Rank.JACK);//this card should be chosen
        currentHand[1] = new Card(Card.Suit.C, Card.Rank.THREE);
        currentHand[2] = new Card(Card.Suit.D, Card.Rank.ACE);
        assertEquals(0, sim.playSecond(currentHand, humanCard));
    }

    //AI is second player: All cards win
    @Test
    void aiSecondPlayerAllCardsWin() {
        currentHand[0] = new Card(Card.Suit.A, Card.Rank.TWO);
        currentHand[1] = new Card(Card.Suit.B, Card.Rank.KING);
        currentHand[2] = new Card(Card.Suit.A, Card.Rank.THREE);//this card need be chosen
        assertEquals(2, sim.playSecond(currentHand, humanCard));
    }

    //AI is second player: Half win Half loose
    @Test
    public void aiSecondPlayerHalfWinHalfLoose() {
        currentHand[0] = new Card(Card.Suit.C, Card.Rank.THREE);
        currentHand[1] = new Card(Card.Suit.A, Card.Rank.TWO);//this card need be chosen
        currentHand[2] = new Card(Card.Suit.D, Card.Rank.ACE);
        assertEquals(1, sim.playSecond(currentHand, humanCard));
    }

}