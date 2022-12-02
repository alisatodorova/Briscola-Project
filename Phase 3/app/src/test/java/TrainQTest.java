import AI.ReinforcementLearning.QState;
import AI.lowBrain.SimpleBrain;
import application.Card;
import AI.ReinforcementLearning.TrainQ;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


    public class TrainQTest {
        Card highSuit = new Card(Card.Suit.A, Card.Rank.ACE);
        Card[] currentHand = new Card[3];

        Card[] aiHand = new Card[3];

        TrainQ sim = new TrainQ();


        @Test
        void notEqualCards() {
            currentHand[0] = new Card(Card.Suit.B, Card.Rank.JACK);//this card should be chosen
            currentHand[1] = new Card(Card.Suit.C, Card.Rank.THREE);
            assertEquals(false, sim.equalCards(currentHand[0], currentHand[1]));
        }

        @Test
        void equalCards() {
            currentHand[0] = new Card(Card.Suit.B, Card.Rank.JACK);//this card should be chosen
            currentHand[1] = new Card(Card.Suit.C, Card.Rank.THREE);
            currentHand[2] = new Card(Card.Suit.B, Card.Rank.JACK);
            assertEquals(true, sim.equalCards(currentHand[0], currentHand[2]));
        }

        @Test
        void nullEqualCards() {
            currentHand[0] = null;//this card should be chosen
            currentHand[1] = new Card(Card.Suit.C, Card.Rank.THREE);
            currentHand[2] = new Card(Card.Suit.B, Card.Rank.JACK);
            assertEquals(false, sim.equalCards(currentHand[0], currentHand[2]));
        }

        @Test
        void EqualStates() {
            Card[] aiHand = new Card[3];
            aiHand[0] = new Card(Card.Suit.B, Card.Rank.JACK);
            aiHand[1] = new Card(Card.Suit.C, Card.Rank.THREE);
            aiHand[2] = new Card(Card.Suit.A, Card.Rank.FIVE);
            Card opponentCard = new Card(Card.Suit.A, Card.Rank.KING);


            Card[] aiHand2 = new Card[3];
            aiHand2[0] = new Card(Card.Suit.B, Card.Rank.TWO);
            aiHand2[1] = new Card(Card.Suit.D, Card.Rank.THREE);
            aiHand2[2] = new Card(Card.Suit.A, Card.Rank.FIVE);
            Card opponentCard2 = new Card(Card.Suit.A, Card.Rank.KING);

            Card[] aiHand3 = new Card[3];
            aiHand3[2] = new Card(Card.Suit.B, Card.Rank.TWO);
            aiHand3[0] = new Card(Card.Suit.D, Card.Rank.THREE);
            aiHand3[1] = new Card(Card.Suit.A, Card.Rank.FIVE);
            Card opponentCard3 = new Card(Card.Suit.A, Card.Rank.KING);

            QState s1 = new QState(false,highSuit,aiHand,opponentCard);
            QState s2 = new QState(false,highSuit,aiHand2,opponentCard2);
            QState s3 = new QState(false,highSuit,aiHand3,opponentCard3);

            QState [] q = new QState[2];
            q[0] = s1;
            q[1] = s2;

            assertEquals(1, sim.foundAtRowFinal(q,s3));



        }



    }


