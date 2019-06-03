package dataStructure.RedBlackTree;

import java.util.LinkedList;
import java.util.List;

public class RedBlackTree {
    private RedBlackTreeNode root;
    private int treeSize = 0;

    /**
     * Construct the tree.
     */
    public RedBlackTree(String[] values) {
        this.root = new RedBlackTreeNode(values[0], 0, Color.BLACK);
        for (int i = 1; i < values.length; i++) {
            this.treeSize += 1;
            insert(values[i]);
        }
    }

    public RedBlackTreeNode delete(RedBlackTreeNode nodeToDelete) {
        RedBlackTreeNode successor = (nodeToDelete.getLeftSon() instanceof NullRedBlackTreeNode || nodeToDelete.getRightSon() instanceof NullRedBlackTreeNode) ? nodeToDelete :  treePredccessor(nodeToDelete);
        RedBlackTreeNode successorSon = (!(successor.getLeftSon() instanceof NullRedBlackTreeNode)) ? successor.getLeftSon() : successor.getRightSon();
        successorSon.setParent(successor.getParent());
        if (successor.getParent() instanceof NullRedBlackTreeNode) {
            root = successorSon;
        } else {
            if (isLeftSon(successor)) {
                successor.getParent().setLeftSon(successorSon);
            } else {
                successor.getParent().setRightSon(successorSon);
            }
        }

        if (successor != nodeToDelete) {
            nodeToDelete.setKey(successor.getKey());
            nodeToDelete.setValue(successor.getValue());
        }

        if (successor.getColor() == Color.BLACK) {
            deleteFixUp(successorSon);
        }

        this.treeSize -= 1;
        return successor;
    }

    /**
     * Insert into the tree value.
     */
    public void insert(String value) {
        RedBlackTreeNode z = new RedBlackTreeNode(value, this.treeSize);
        RedBlackTreeNode y = new NullRedBlackTreeNode();
        RedBlackTreeNode x = this.root;
        while (!(x instanceof NullRedBlackTreeNode)) {
            y = x;
            x = (z.getKey() < x.getKey()) ? x.getLeftSon() : x.getRightSon();
        }

        z.setParent(y);

        if (y instanceof NullRedBlackTreeNode) {
            this.root = z;
        } else {
            if (z.getKey() < y.getKey()) {
                y.setLeftSon(z);
            } else {
                y.setRightSon(z);
            }
        }

        z.setLeftSon(new NullRedBlackTreeNode());
        z.setRightSon(new NullRedBlackTreeNode());
        z.setColor(Color.RED);
        insertFixUp(z);
    }


    /**
     * Print all items.
     */
    public void printTree() {
        printTree(root);
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param t the node that roots the tree.
     */
    private void printTree(RedBlackTreeNode t) {
        if (t instanceof NullRedBlackTreeNode) {
            return;
        }

        printTree(t.getLeftSon());
        System.out.println(t.getValue());
        printTree(t.getRightSon());
    }


    private void leftRotate(RedBlackTreeNode x) {
        RedBlackTreeNode y = x.getRightSon();
        x.setRightSon(y.getLeftSon());
        if (!(y.getLeftSon() instanceof NullRedBlackTreeNode)) {
            y.getLeftSon().setParent(x);
        }
        y.setParent(x.getParent());

        switchParentConnection(x, y);

        y.setLeftSon(x);
        x.setParent(y);
    }

    private void rightRotate(RedBlackTreeNode x) {
        RedBlackTreeNode y = x.getLeftSon();
        x.setLeftSon(y.getRightSon());
        if (!(y.getRightSon() instanceof NullRedBlackTreeNode)) {
            y.getRightSon().setParent(x);
        }

        y.setParent(x.getParent());

        switchParentConnection(x, y);
        y.setRightSon(x);
        x.setParent(y);

    }

    /**
     * switches parent of x connection to x to be y
     *
     * @param x node
     * @param y node
     */
    private void switchParentConnection(RedBlackTreeNode x, RedBlackTreeNode y) {
        if (x.getParent() instanceof NullRedBlackTreeNode) {
            this.root = y;
        } else {
            if (isLeftSon(x)) {
                x.getParent().setLeftSon(y);
            } else {
                x.getParent().setRightSon(y);
            }
        }
    }

    private void insertFixUp(RedBlackTreeNode z) {
        while (z.getParent().getColor() == Color.RED) {
            if (z.getParent() == z.getParent().getParent().getLeftSon()) {
                RedBlackTreeNode y = z.getParent().getParent().getRightSon();
                if (y.getColor() == Color.RED) {
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (isRightSon(z)) {
                        z = z.getParent();
                        leftRotate(z);
                    }
                    z.getParent().setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    rightRotate(z.getParent().getParent());
                }
            } else {
                RedBlackTreeNode y = z.getParent().getParent().getLeftSon();
                if (y.getColor() == Color.RED) {
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (isLeftSon(z)) {
                        z = z.getParent();
                        rightRotate(z);
                    }
                    z.getParent().setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    leftRotate(z.getParent().getParent());
                }
            }
        }

        root.setColor(Color.BLACK);
    }

    private boolean isRightSon(RedBlackTreeNode z) {
        return z == z.getParent().getRightSon();
    }

    private void deleteFixUp(RedBlackTreeNode x) {
        while (x != root && x.getColor() == Color.BLACK) {
            if (isLeftSon(x)) {
                RedBlackTreeNode w = x.getParent().getRightSon();
                //case 1
                if (w.getColor() == Color.RED) {
                    w.setColor(Color.BLACK);
                    x.getParent().setColor(Color.RED);
                    leftRotate(x.getParent());
                    w = x.getParent().getRightSon();
                }
                if (w.getLeftSon().getColor() == Color.BLACK && w.getRightSon().getColor() == Color.BLACK) {
                    w.setColor(Color.RED);
                    x = x.getParent();
                } else {
                    if (w.getRightSon().getColor() == Color.BLACK) {
                        w.getLeftSon().setColor(Color.BLACK);
                        w.setColor(Color.RED);
                        rightRotate(w);
                        w = x.getParent().getRightSon();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(Color.BLACK);
                    w.getRightSon().setColor(Color.BLACK);
                    leftRotate(x.getParent());
                    x = root;
                }
            } else {
                RedBlackTreeNode w = x.getParent().getLeftSon();
                //case 1
                if (w.getColor() == Color.RED) {
                    w.setColor(Color.BLACK);
                    x.getParent().setColor(Color.RED);
                    rightRotate(x.getParent());
                    w = x.getParent().getLeftSon();
                }
                if (w.getRightSon().getColor() == Color.BLACK && w.getLeftSon().getColor() == Color.BLACK) {
                    w.setColor(Color.RED);
                    x = x.getParent();
                } else {
                    if (w.getLeftSon().getColor() == Color.BLACK) {
                        w.getRightSon().setColor(Color.BLACK);
                        w.setColor(Color.RED);
                        leftRotate(w);
                        w = x.getParent().getLeftSon();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(Color.BLACK);
                    w.getLeftSon().setColor(Color.BLACK);
                    rightRotate(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(Color.BLACK);
    }

    private boolean isLeftSon(RedBlackTreeNode x) {
        return x == x.getParent().getLeftSon();
    }

    private RedBlackTreeNode treePredccessor(RedBlackTreeNode x) {
        if (x.getLeftSon() != null) {
            return treeMax(x.getLeftSon());
        }
        RedBlackTreeNode y = x.getParent();
        while (!(y instanceof NullRedBlackTreeNode) && x == y.getLeftSon()) {
            x = y;
            y = y.getParent();
        }

        return y;
    }


    private RedBlackTreeNode treeMax(RedBlackTreeNode x) {
        if (!(x.getRightSon() instanceof NullRedBlackTreeNode)) {
            return treeMax(x.getRightSon());
        } else {
            return x;
        }
    }

    private RedBlackTreeNode treeSuccessor(RedBlackTreeNode x) {
        if (!(x.getRightSon() instanceof NullRedBlackTreeNode)) {
            return treeMinimum(x.getRightSon());
        }
        RedBlackTreeNode y = x.getParent();
        while (!(y instanceof NullRedBlackTreeNode) && x == y.getRightSon()) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    private RedBlackTreeNode treeMinimum(RedBlackTreeNode x) {
        while (!(x.getLeftSon() instanceof NullRedBlackTreeNode)) {
            x = x.getLeftSon();
        }
        return x;
    }

    public List<RedBlackTreeNode> getAllNodes() {
        List<RedBlackTreeNode> nodes = new LinkedList<>();
        addAllNodes(root, nodes);
        return nodes;
    }

    private void addAllNodes(RedBlackTreeNode node, List<RedBlackTreeNode> listOfNodes) {
        if (node != null) {
            RedBlackTreeNode rightSon = node.getRightSon();
            RedBlackTreeNode leftSon = node.getLeftSon();
            if (!(leftSon instanceof NullRedBlackTreeNode)) {
                addAllNodes(leftSon, listOfNodes);
            }
            listOfNodes.add(node);
            if (!(rightSon instanceof NullRedBlackTreeNode)) {
                addAllNodes(rightSon, listOfNodes);
            }
        }
    }
}


