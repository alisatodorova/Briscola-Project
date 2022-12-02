/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import AI.AIStart;
import AI.ReinforcementLearning.TestRL;
import AI.aNN.NeuralNetwork;
import AI.aNN.TrainANN;
import AI.lowBrain.RandBrain;
import AI.lowBrain.SimpleBrain;
import application.*;
import gui.Main;

public class App {
    private final static boolean gui = true;
    private final static boolean humanVHuman = false;
    private final static boolean gameSimulator = true;
    public final static boolean trainRL = false;
    public final static boolean trainANN = false;
    public final static boolean toCSV = false;
    public final static boolean experimenting = false;
    public final static boolean fixedDeck = false;

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
        else if (trainANN){
            TrainANN.main(args);
            // GameSimTrain.main(args,fixedDeck);
            // new GameSimTrain();
        }
        else if(trainRL){
            TestRL.main(args);
        }
        else if (experimenting){
            GameSimAll.main(args, fixedDeck);
        }
        else if (gameSimulator && toCSV) {
            GameSimulatorCSV.main(args, fixedDeck);
        }
        else if (gameSimulator) {
            GameSimulator.main(args, fixedDeck);
        }
        else
        {
            AIStart.main(args);
        }

    }
}