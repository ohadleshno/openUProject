package dataStructure.RedBlackTree;

public class RedBlackTreeNode {

    protected int key;
    protected Color color;
    private String value;
    private RedBlackTreeNode leftSon, rightSon, parent;

    public RedBlackTreeNode() {
        super();
    }

    /**
     * constructor
     */

    public RedBlackTreeNode(String value, int key) {
        this.value = value;
        this.key = key;
        this.color = Color.RED;
        leftSon = new NullRedBlackTreeNode();
        rightSon = new NullRedBlackTreeNode();
        parent = new NullRedBlackTreeNode();
    }

    public RedBlackTreeNode(String value, int key,Color color) {
        this.value = value;
        this.key = key;
        this.color = color;
        leftSon = new NullRedBlackTreeNode();
        rightSon = new NullRedBlackTreeNode();
        parent = new NullRedBlackTreeNode();
    }

    /**
     * getters
     */

    public int getKey() {
        return key;
    }

    public Color getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    public RedBlackTreeNode getLeftSon() {
        return leftSon;
    }

    public RedBlackTreeNode getRightSon() {
        return rightSon;
    }

    public RedBlackTreeNode getParent() {
        return parent;
    }


    /**
     * setters
     */

    public void setLeftSon(RedBlackTreeNode leftSon) {
        this.leftSon = leftSon;
    }

    public void setRightSon(RedBlackTreeNode rightSon) {
        this.rightSon = rightSon;
    }

    public void setParent(RedBlackTreeNode parent) {
        this.parent = parent;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
