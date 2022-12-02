package AI.ReinforcementLearning;
import java.util.*;

public class QTable {


   public static QState[] states;
   public static ArrayList<QState> states1 = new ArrayList<QState>();
    double[][] qTable;
    ArrayList<ArrayList<Double>> table = new ArrayList<>();
    public final double ALPHA = 0.7;
    public final double GAMMA = 0.99;



    // Initialise the Qtable to 0 as the agent doesn't know anything about the world at first
    public QTable() {
        qTable = new double[2500000][3];

        states = new QState[2500000];
        for(int row = 0; row < qTable.length; row++) {
            for(int col = 0; col < qTable[row].length; col++) {
                qTable[row][col] = 0;
            }
        }
    }

    public double maxQ(QState S) {
        int id = S.getStateID();
        double maxQVALUE = qTable[id][0];
        for(int i = 1; i < 3; i++) {
            if(id >= qTable.length)
            {
                break;
            }

            if(qTable[id][i] > maxQVALUE)
                maxQVALUE = qTable[id][i];
        }
        return maxQVALUE;
    }

    /**
     * Gives the Q value of a given action at a given state
     * @param S
     * @param Action
     * @return Q value
     */
    public double qValue(QState S, int Action) {
        int ID = S.getStateID();
        double qValue = qTable[ID][Action];
        return qValue;
    }

    public double[][] getQTable()
    {
        return qTable;
    }


    public QState[] getStates() {
        return states;
    }
    public ArrayList<QState> getStatesList(){
        return states1;
    }

    public void addState(int index, QState newState){
        states[index] = newState;
        states1.add(newState);
    }

    public void addRow(int index, double[] row){
        qTable[index] = row;
    }

    public double newQ(QState state, int action, double immediateReward, QState nextState) {
        int ID = state.getStateID();
        qTable[ID][action] = ((1 - ALPHA) * qValue(state, action) + ALPHA * (immediateReward + GAMMA * (maxQ(nextState))));
        double updatedValue = qTable[ID][action];
        return updatedValue;


    }

    public boolean isFull() {
        int counter = 0;
        for (double[] doubles : qTable) {
            for (double aDouble : doubles) {
                if (counter > 40){
                    return false;
                }
                if (aDouble == 0 ) {
                    counter++;
                }
            }
        }
        return true;
    }

    public void update (int index, QState currentState, int action, double immediateReward, QState nextState){

        System.out.println("UPDATE");
        qTable[index][action] = ((1-ALPHA)* qValue(currentState,action) + ALPHA*(immediateReward + GAMMA*(maxQ(nextState))));
        //  nextState.stateID--;
        QState.stateIDHolder--;
    }


}