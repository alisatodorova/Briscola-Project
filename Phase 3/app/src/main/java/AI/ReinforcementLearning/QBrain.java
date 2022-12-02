package AI.ReinforcementLearning;

import application.Briscola;
import application.GameLogic;

public class QBrain {
    Briscola game;
    QTable qTable;

    //We might need the game logic but I am unsure
    GameLogic gameLogic = new GameLogic();


    public QBrain(Briscola game) {
        qTable = new QTable();
        /*Call a Training instace - readIn the QTable that is fron training ...*/
        this.game = game;

    }


     /*------ Method 1
    we probably need a method to
     */


    /* ----- Method 2
    we probably need a method to go checking the Q Table
     */


    /*


     */
}



