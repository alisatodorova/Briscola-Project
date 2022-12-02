import application.Card;
import application.GameLogic;
import application.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    Player one = new Player("AlisaTheGreat", 1);
    Player two = new Player("ParandTheBest", 2);
    Card highSuit = new Card(Card.Suit.A, Card.Rank.ACE);
    Card c1 = new Card(Card.Suit.A, Card.Rank.JACK);
    Card c2 = new Card(Card.Suit.A, Card.Rank.THREE);
    Card c3 = new Card(Card.Suit.C, Card.Rank.TWO);
    Card c4 = new Card(Card.Suit.B, Card.Rank.KING);
    Card c5 = new Card(Card.Suit.B, Card.Rank.SEVEN);
    GameLogic game = new GameLogic();

    @Test
    void p1PlayHighSuit(){
        assertEquals(one, game.checkCards(c1,c5,highSuit, one, two));
    }
    @Test
    void bothPlayHighSuit(){
        assertEquals(two, game.checkCards(c1,c2,highSuit, one, two));
    }

    @Test
    void noHighSuitP1notEqualToP2(){
    assertEquals(one, game.checkCards(c3, c4, highSuit, one, two));
    }

    @Test
    void noHighSuitP1EqualToP2() {
        assertEquals(two, game.checkCards(c5, c4, highSuit, one, two));
    }
}