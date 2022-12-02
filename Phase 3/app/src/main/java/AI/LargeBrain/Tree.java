package AI.LargeBrain;

public class Tree<TreeNode> {

  public TreeNode root;

  public Tree(TreeNode root) {
      this.root = root;
  }

  public TreeNode getRoot() {
    return root;
  }

  public void setRoot(TreeNode n)
  {
    root = n;
  }


}
