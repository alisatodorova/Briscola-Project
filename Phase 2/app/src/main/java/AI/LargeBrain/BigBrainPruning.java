package AI.LargeBrain;
import AI.lowBrain.Brain;
import AI.lowBrain.State;
import application.Card;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Java class for the Advanced agent
 * with Alpha-beta pruning
 */
public class BigBrainPruning extends application.Player {
    private static final boolean DEBUG = false;
    Tree tree;
    ArrayList<Card> path;

    public BigBrainPruning() {
        super("AI", 1);
    }
    public BigBrainPruning(int id){
        super("AI",id);
    }

    /**
     * Alpha-beta search
     * @param initialState the initial state
     * @param onFloor the human's current hand
     * @return an action
     */
    public int alphaBeta(BigState initialState, Card onFloor) {
        path = new ArrayList<>();
        TreeNode<BigState> root = new TreeNode<>(initialState, null);
        tree = new Tree<TreeNode>(root);
        if(onFloor != null){
            path.add(onFloor);
        }
        root.setUtility(maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE));
        print(root);
        for(int i = 0 ; i < root.getChildren().size(); i++){
            if(root.getChild(i).getUtility() == root.getUtility()){
                return i;
            }
        }


        return -1;
    }

     /**
     * Calculates the max value for Max player
     * @param node a tree node
     * @param alpha alpha, the value of the best choice found so far for MAX
     * @param beta beta, the value of the best choice found so far for MIN
     * @return the utility value for Max player
     */
    public int maxValue(TreeNode<BigState> node, int alpha, int beta) {
        if (node.getState().terminalTest()) {
            node.setUtility(node.getState().calUtility(path));
            return node.getUtility();
        }
        node.setUtility(Integer.MIN_VALUE);
        for (int a = 0; a < node.getState().getAiHand().length; a++) {
            if (node.getState().getAiHand()[a] != null) {
                node.addChildren(node.getState().result(a));
                path.add(node.getState().getAiHand()[a]);
                node.setUtility(Math.max(node.getUtility(), minValue(node.getChild(), alpha, beta)));
                if (node.getUtility() >= beta) {
                    return node.getUtility();
                }
                alpha = Math.max(alpha, node.getUtility());
            }
        }
        return node.getUtility();
    }

     /**
     * Calculates the min value for Min player
     * @param node a tree node
     * @param alpha alpha, the value of the best choice found so far for MAX
     * @param beta beta, the value of the best choice found so far for MIN
     * @return the utility value for Min player
     */
    public int minValue(TreeNode<BigState> node, int alpha, int beta) {
        if (node.getState().terminalTest()) {
            node.setUtility(node.getState().calUtility(path));
            return node.getUtility();
        }
        node.setUtility(Integer.MAX_VALUE);
        for (int a = 0; a < node.getState().getHumanHand().length; a++) {
            if (node.getState().getHumanHand()[a] != null) {
                node.addChildren(node.getState().result(a));
                path.add(node.getState().getHumanHand()[a]);
                node.setUtility(Math.min(node.getUtility(), maxValue(node.getChild(), alpha, beta)));
                if (node.getUtility() <= alpha) {
                    return node.getUtility();
                }
                beta = Math.min(node.getUtility(), beta);
            }
        }
        return node.getUtility();
    }


    /**
     * Prints the MiniMax with Alpha-Beta tree
     * @param n a tree node
     */
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
