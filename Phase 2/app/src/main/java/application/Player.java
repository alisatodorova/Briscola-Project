package application;

/**
 * this class make players.
 */


public class Player {
    public String name;
    private int points;
    private int id;

    public Card[] currentHand;
    private int nullElement;

    /**
     * Constructure of the class player. player has characteristic like points that in start is 0, a current hand is a array of 3 that save the current hand of the player
     *
     * @param name Name of the player that player give us
     * @param id   Id that will be assign to players depends on if they are first or second player
     */
    public Player(String name, int id) {
        this.name = name;
        this.points = 0;
        currentHand = new Card[3];
        this.id = id;
    }

    /**
     * when player try to play a card this method is called.
     *
     * @param choiceFromHand index of currant hand
     * @return Card that were played
     */
    public Card playCard(int choiceFromHand) {
        Card play;
        play = currentHand[choiceFromHand];
        currentHand[choiceFromHand] = null;
        nullElement = choiceFromHand;
        return play;
    }

    /**
     * Fill the empty place in current hand array
     *
     * @param card need to be add to the hand
     */
    public void setNullCard(Card card) {
        currentHand[nullElement] = card;
    }

    /**
     * this method is called at start of the game and add the first 3 cards to hand.
     * takes a card and put it in null place in current hand
     *
     * @param card Card need to be added to hand
     */
    public void setCard(Card card) {
        if (currentHand[0] == null) {
            currentHand[0] = card;
        } else if (currentHand[1] == null) {
            currentHand[1] = card;
        } else {
            currentHand[2] = card;
        }
    }

    /**
     * add points to the points player already has
     *
     * @param pointsToBeAdded point need to be added
     */
    public void addPoints(int pointsToBeAdded) {
        points += pointsToBeAdded;
    }

    /**
     * @return the points of the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return value of points in string
     */
    public String getPointsToString() {
        return String.valueOf(points);
    }

    /**
     * @return thr name of player
     */
    public String getName() {
        return name;
    }

    /**
     * set the name
     *
     * @param name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the current hand of the player
     */
    public Card[] getHand() {
        return currentHand;
    }

    /**
     * @return the id of the player
     */
    public int getId() {
        return id;
    }


    /**
     * Setting the ID only needed in case we use BigBrainPruning AI in the Top of the GUI
     * @param id - id to be updated to.
     * */
    public void setId (int id){
        this.id = id;
    }

    /**
     * check if the hand is empty
     *
     * @return boolean true if it is false if its not
     */
    public boolean isHandEmpty() {
        if (currentHand[0] == null && currentHand[1] == null && currentHand == null) {
            return true;
        }
        return false;
    }

    /**
     * @return current hand in String
     */
    public String toString() {
        return currentHand[0].toString() + " " + currentHand[1].toString() + " " + currentHand[2].toString();
    }
}
