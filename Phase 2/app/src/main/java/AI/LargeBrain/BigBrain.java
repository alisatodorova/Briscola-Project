package AI.LargeBrain;
import application.Card;
import application.GameLogic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * The class BigBrain represents the minimax algorithm assumes that both players play the best
 * possible card for them each round. In other words, Max player will always choose
 * the card that will maximize its win score ( method : maxValue), while Min player will always choose the
 * card that will minimize its loss ( method : minValue). Both values determine the utility of the agent for
 * each possible state thus the best card to play.
 *
 */
public class BigBrain extends application.Player{

    private static final boolean DEBUG = false;
    Tree tree;
    ArrayList<Card> path;
    GameLogic game = new GameLogic();

    public BigBrain(int id){
        super("AI",id);
    }
    public BigBrain(){
        super("AI", 1);
    }


    /**
     * This method takes the BigState, the initial state and the Card, then returns the utility
     * @param initialState, BigState, Card
     * @return utility
     */
    public int miniMax(BigState initialState, Card onFloor){
        path = new ArrayList<>();
        TreeNode<BigState> root = new TreeNode<>(initialState, null);
        tree = new Tree<TreeNode>(root);
        if(onFloor != null){
            path.add(onFloor);
        }
        root.setUtility(maxValue(root));

//        print(root);
        for(int i = 0 ; i < root.getChildren().size(); i++){
            if(root.getChild(i).getUtility() == root.getUtility()){
                return i;
            }
        }
        return -1;
    }



    /**
     * The following method takes the Tree, alpha and beta and returns the maximum value the agent can play
     * @param TreeNode<BigState>,alpha, beta
     * @return value maximizing the chance the agent wins given the available utilities
     */
    public int maxValue(TreeNode<BigState> node){
//        ArrayList<Card> check = new ArrayList<>();
//        if(path.size()>1){
//            check.add(path.get(path.size() - 1));
//            check.add(path.get(path.size()- 2));
//        }else{
//            check.add(new Card(Card.Suit.A, Card.Rank.FIVE));
//            check.add(new Card(Card.Suit.A, Card.Rank.THREE));
//        }
//        if(node.getState().terminalTest() || node.getState().calUtility(check) < 0){
        if(node.getState().terminalTest()){
            node.setUtility(node.getState().calUtility(path));
            return node.getUtility();
        }
        node.setUtility(Integer.MIN_VALUE);
        for (int a = 0; a < node.getState().getAiHand().length; a++) {
            if (node.getState().getAiHand()[a] != null) {
                node.addChildren(node.getState().result(a));
                path.add(node.getState().getAiHand()[a]);
                node.setUtility(Math.max(node.getUtility(), minValue(node.getChild())));

            }

        }
        return node.getUtility();
    }

    /**
     * The following method takes the Tree, alpha and beta and returns the value that minimizes the chance
     * of the player winning against the agent
     * @param TreeNode<BigState>,alpha,beta
     * @return value minimizing the chance the player wins given the available utilities
     */
    public int minValue(TreeNode<BigState> node) {
        if (node.getState().terminalTest()) {
            node.setUtility(node.getState().calUtility(path));
            return node.getUtility();
        }
        node.setUtility(Integer.MAX_VALUE);
        for(int a = 0 ; a < node.getState().getHumanHand().length; a++ ){
            if (node.getState().getHumanHand()[a] != null) {
                node.addChildren(node.getState().result(a));
                path.add(node.getState().getHumanHand()[a]);
                node.setUtility(Math.min(node.getUtility(), maxValue(node.getChild())));
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
            for(int i =0; i <temp.getChildren().size() ; i++ ){
                if (temp.getChild(i) != null) {
                    queue.add(temp.getChild(i));
                }
            }
        }
    }
}
