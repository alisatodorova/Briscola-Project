package AI.LargeBrain;

import application.Briscola;
import application.Card;
import application.GameLogic;

import java.util.*;

public class CheatingBrain extends application.Player {
    private static final boolean DEBUG = false;


    private static CheatingAgent agent;
    protected Tree tree;
    protected ArrayList<Card> path;
    protected GameLogic gameLogic = new GameLogic();
    protected ArrayList te = new ArrayList();
    protected boolean react;

    /**
     * Constructure of the class player. player has characteristic like points that in start is 0,
     * a current hand is a array of 3 that save the current hand of the player
     *
     * @param game Briscola Game that is being used to know more then just what is allowed in a Human v Human game
     * @param id   Id that will be assign to players depends on if they are first or second player
     */
    public CheatingBrain(Stack<Card> shuffled, int id){

        super("AI", id);
        this.agent = new CheatingAgent(shuffled, id);
    }
    public CheatingBrain(Stack<Card> shuffled){
        super("AI", 1);
        this.agent = new CheatingAgent(shuffled, 1);
    }



    public int oldMiniMax(BigState initialState, Card onFloor){
        ArrayList <TreeNode> sameUtility = new ArrayList<>();
        Random rand = new Random();
        path = new ArrayList<>();
        TreeNode<BigState> root = new TreeNode<>(initialState, null);
        tree = new Tree<TreeNode>(root);
        react = false;
        if(onFloor != null){
            react = true;
            path.add(onFloor);
        }
        root.setUtility(maxValue(root));
//        print(root);
        for (int i = 0; i < root.getChildren().size(); i++) {
            if (root.getChild(i).getUtility() == root.getUtility()) {
                sameUtility.add(root.getChild(i)); //saves all the same moves
            }
        }
        if (sameUtility.size() == 1) {
            return sameUtility.get(0).getAction();
        } else {
            return sameUtility.get(rand.nextInt(sameUtility.size())).getAction();
        }

    }


    public int miniMax(BigState initialState, Card onFloor, Card[] humanHand) {
        int[] count = new int[3];
        for (int i = 0; i < 1; i++) {
            if(initialState.aiTurn){ //generates the random hand
                initialState.setHumanHand(agent.updateOpponentHand(null));
            }else{
                initialState.setHumanHand(agent.getOpponentHand());
            }
            count[oldMiniMax(initialState, onFloor)]++;

        }
        int bestMove = 0;
        for (int k=1; k<count.length; k++){
            if(count[bestMove] < count[k]){
                bestMove = k;
            }
        }

        return bestMove;
    }



    public int maxValue(TreeNode<BigState> node){
        if(node.getState().terminalTest()){
            node.setUtility(node.getState().calUtility(path, react));
            return node.getUtility();
        }
        node.setUtility(Integer.MIN_VALUE);
        for (int a = 0; a < node.getState().getAiHand().length; a++) {
            if (node.getState().getAiHand()[a] != null) {
                node.addChildren(result(a, node.getState())); // change to the Cheating Brain Version
                node.getChild().setAction(a);
                path.add(node.getState().getAiHand()[a]);
                node.setUtility(Math.max(node.getUtility(), minValue(node.getChild())));
                path.remove(path.size()-1);

            }

        }
        return node.getUtility();
    }

    public int minValue(TreeNode<BigState> node ) {
        if (node.getState().terminalTest()) {
            node.setUtility(node.getState().calUtility(path,react));
            return node.getUtility();
        }
        node.setUtility(Integer.MAX_VALUE);
        for(int a = 0 ; a < node.getState().getHumanHand().length; a++ ){
            if (node.getState().getHumanHand()[a] != null) {
                node.addChildren(result(a, node.getState())); // change to Cheating Agent node.getState().result(a)
                node.getChild().setAction(a);
                path.add(node.getState().getHumanHand()[a]);
                node.setUtility(Math.min(node.getUtility(), maxValue(node.getChild())));
                path.remove(path.size()-1);
            }
        }

        return node.getUtility();
    }

    public BigState result(int action, BigState state) {
        BigState newState;
        Card played = new Card();
        Card[] newAiHand = new Card[3];
        System.arraycopy(state.aiHand, 0, newAiHand, 0, state.aiHand.length);
        Card[] newHumanHand = new Card[state.humanHand.length];
        System.arraycopy(state.humanHand, 0, newHumanHand, 0, state.humanHand.length);

        if (state.aiTurn) {
            for (int i = 0; i < state.aiHand.length; i++) {
                if (action == i && !agent.futureAICards.isEmpty()) {
//                    played = aiHand[i];

                    newAiHand[i] = agent.futureAICards.remove(0);
                }
                else if(action == i){
                    newAiHand[i] = null;
                }
            }
        } else {
            for (int j = 0; j < state.humanHand.length; j++) {
//                System.out.println("this is human hand here" + humanHand[0].toString() + humanHand[1].toString() + humanHand[2].toString());
                if (action == j) {
                    if(!agent.futureOpponentCards.isEmpty()){
                        newHumanHand[j] = agent.futureOpponentCards.remove(0);
//                    played = humanHand[j];
                    }
                    else {
                        newHumanHand[j] = null;
                    }
                }
            }
        }
        newState = new BigState((LinkedList<Card>) (state.cardsLeft), newAiHand, newHumanHand, state.highsuit);
        newState.setAITurn(!state.aiTurn);
        return newState;
    }

    private void print(TreeNode<BigState>n) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(n);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            System.out.print(temp.getUtility() + " ->");
            for(int i =0; i <temp.getChildren().size() ; i++ ){
                if (temp.getChild(i) != null) {
                    queue.add(temp.getChild(i));
                }
            }
        }
    }






}
