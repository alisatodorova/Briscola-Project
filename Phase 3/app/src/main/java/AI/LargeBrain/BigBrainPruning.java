package AI.LargeBrain;
import AI.lowBrain.Brain;
import AI.lowBrain.State;
import application.Card;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BigBrainPruning extends application.Player {
    private static final boolean DEBUG = true;
    Tree tree;
    ArrayList<Card> path;
    boolean react;
    private final static int determinizations = 30;

    public BigBrainPruning() {
        super("AI", 1);
    }
    public BigBrainPruning(int id){
        super("AI",id);
    }

    public int alphaBetaPruning(BigState initialState, Card onFloor) {
//    public int alphaBeta(BigState initialState, Card onFloor) {
        ArrayList <TreeNode> sameUtility = new ArrayList<>();
        Random rand = new Random();
        path = new ArrayList<>();
        TreeNode<BigState> root = new TreeNode<>(initialState, null);
        tree = new Tree<TreeNode>(root);
         react= false;
        if(onFloor != null){
            react = true;
            path.add(onFloor);
        }
        root.setUtility(maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE));
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

    /**
     * Alpha-beta search with Multi-Determinization
     *
     * @param initialState the initial state
     * @param onFloor      the human's current hand
     * @return the best action
     */
    public int alphaBeta(BigState initialState, Card onFloor, Card[] humanHand) {
        if(initialState.getRound() == 20 ){
            for(int i = 0 ; i < initialState.getAiHand().length; i ++){
                if(initialState.getAiHand()[i] != null){
                    return i;
                }
            }

        }

        int[] count = new int[3];
        for (int i = 0; i < determinizations; i++) {
            if(initialState.aiTurn){
                initialState.setHumanHand(RandomHand.randomHand(initialState.getCardsLeft(),3,humanHand));
            }else{
                initialState.setHumanHand(RandomHand.randomHand(initialState.getCardsLeft(),4, humanHand));
            }
            count[alphaBetaPruning(initialState, onFloor)]++;
        }

        // return the best move/action (ie the most frequent move)
        int bestMove = 0;
        for (int k=1; k<count.length; k++){
            if(count[bestMove] < count[k]){
                bestMove = k;
            }
        }

        return bestMove;
    }


    public int maxValue(TreeNode<BigState> node, int alpha, int beta) {
        if (node.getState().terminalTest()) {
            node.setUtility(node.getState().calUtility(path, react));
            return node.getUtility();
        }
        node.setUtility(Integer.MIN_VALUE);
        for (int a = 0; a < node.getState().getAiHand().length; a++) {
            if (node.getState().getAiHand()[a] != null) {
                node.addChildren(node.getState().result(a));
                node.getChild().setAction(a);
                path.add(node.getState().getAiHand()[a]);
                node.setUtility(Math.max(node.getUtility(), minValue(node.getChild(), alpha, beta)));
                path.remove(path.size()-1);
                if (node.getUtility() >= beta) {
                    return node.getUtility();
                }
                alpha = Math.max(alpha, node.getUtility());
            }
        }
        return node.getUtility();
    }

    public int minValue(TreeNode<BigState> node, int alpha, int beta) {
        if (node.getState().terminalTest()) {
            node.setUtility(node.getState().calUtility(path,react));
            return node.getUtility();
        }
        node.setUtility(Integer.MAX_VALUE);
        for (int a = 0; a < node.getState().getHumanHand().length; a++) {
            if (node.getState().getHumanHand()[a] != null) {
                node.addChildren(node.getState().result(a));
                node.getChild().setAction(a);
                path.add(node.getState().getHumanHand()[a]);
                node.setUtility(Math.min(node.getUtility(), maxValue(node.getChild(), alpha, beta)));
                path.remove(path.size()-1);
                if (node.getUtility() <= alpha) {
                    return node.getUtility();
                }
                beta = Math.min(node.getUtility(), beta);
            }
        }
        return node.getUtility();
    }


    private void print(TreeNode<BigState> n) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(n);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            if (DEBUG) System.out.print(temp.getUtility() + " ->");
            for (int i = 0; i < temp.getChildren().size(); i++) {
                if (temp.getChild(i) != null) {
                    queue.add(temp.getChild(i));
                }
            }
        }
    }

}