import AI.lowBrain.SimpleBrain;
import application.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBrainTestFirst {
    Card highSuit = new Card(Card.Suit.A, Card.Rank.ACE);
    Card[] currentHand = new Card[3];
    SimpleBrain sim = new SimpleBrain(highSuit);

    //AI is first player: All highsuit
    @Test
    void aiFirstPlayerCardsAllHighsuit() {
        currentHand[0] = new Card(Card.Suit.A, Card.Rank.THREE);
        currentHand[1] = new Card(Card.Suit.A, Card.Rank.KING);
        currentHand[2] = new Card(Card.Suit.A, Card.Rank.JACK);
        assertEquals(0, sim.playFirst(currentHand));
    }

    //AI is first player: All not highshuit
    @Test
    void aiFirstPlayerCardsAllNotHighsuit() {
        currentHand[0] = new Card(Card.Suit.B, Card.Rank.THREE);
        currentHand[1] = new Card(Card.Suit.B, Card.Rank.KING);
        currentHand[2] = new Card(Card.Suit.B, Card.Rank.JACK);
        assertEquals(0, sim.playFirst(currentHand));
    }

    //AI is first player: Half highsuit/non-highsuit
    @Test
    void aiFirstPlayerCardsHalfHighsuitHalfNonHighsuit() {
        currentHand[0] = new Card(Card.Suit.A, Card.Rank.THREE);
        currentHand[1] = new Card(Card.Suit.B, Card.Rank.KING);
        currentHand[2] = new Card(Card.Suit.A, Card.Rank.JACK);
        assertEquals(0, sim.playFirst(currentHand));
    }
}