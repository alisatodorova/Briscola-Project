package AI.LargeBrain;

import AI.lowBrain.SimpleBrain;
import application.Card;
import application.Player;

import java.util.ArrayList;

public class TreeNode<BigState> {
    public BigState bigState;
    public TreeNode<BigState> parent;
    public ArrayList<TreeNode<BigState>> children;
    public int utility;

    public TreeNode(BigState s, TreeNode<BigState> p) {
        this.bigState = s;
        parent = p;
        children = new ArrayList<>();
    }

    public void setUtility(int utility){
        this.utility = utility;
    }

    public void setState(BigState s) {
        bigState = s;
    }

    public BigState getState() {
        return this.bigState;
    }

    public TreeNode<BigState> getParent() {
        return parent;
    }



    public boolean hasChild() {
        int count = 0;
        for (TreeNode child : children) {
            if (child != null && child.getState() != null) {
                count++;
            }
        }
        return count != 0;
    }

    public ArrayList<TreeNode<BigState>> getChildren() {
        return children;
    }

    public void addChildren(BigState s) {
        children.add(new TreeNode<BigState>(s,  this));

    }

    public TreeNode getChild() {
            return(children.get(children.size() - 1));
    }

    public TreeNode getChild(int index) {
        return children.get(index);
    }



    public int getUtility() {
        return this.utility;
    }


}
