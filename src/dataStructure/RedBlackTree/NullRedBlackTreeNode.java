package dataStructure.RedBlackTree;

public class NullRedBlackTreeNode extends RedBlackTreeNode {
    public NullRedBlackTreeNode() {
        this.key = -1;
        this.color = Color.BLACK;
    }

    @Override
    public RedBlackTreeNode getLeftSon() {
        return new NullRedBlackTreeNode();
    }

    @Override
    public RedBlackTreeNode getRightSon() {
        return new NullRedBlackTreeNode();
    }
}
