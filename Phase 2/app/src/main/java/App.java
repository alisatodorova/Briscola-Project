/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import AI.AIStart;
import application.BriscolaStart;
import gui.Main;
import gui.TwoPlayers;
import application.BriscolaStart;
public class App {
    private final static boolean gui = true;
    private final static boolean humanVHuman = false;

    /**
     * Starting the Application
     * @param args
     */
    public static void main(String[] args) {
        if (gui)
        {
            Main.main(args);
        }
        else if (humanVHuman)
        {
            BriscolaStart.main(args);
        }
        else
        {
            AIStart.main(args);
        }

    }
}
