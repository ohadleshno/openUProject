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

    /**
     * @return all nodes in the tree
     */
    public List<RedBlackTreeNode> getAllNodes() {
        List<RedBlackTreeNode> nodes = new LinkedList<>();
        addAllNodes(root, nodes);
        return nodes;
    }

    /**
     * delete node from the tree
     * @param nodeToDelete to delete
     * @return
     */
    public RedBlackTreeNode delete(RedBlackTreeNode nodeToDelete) {
        RedBlackTreeNode successor = (nodeToDelete.getLeftSon() instanceof NullRedBlackTreeNode || nodeToDelete.getRightSon() instanceof NullRedBlackTreeNode) ? nodeToDelete :  treePredecessor(nodeToDelete);
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
        RedBlackTreeNode redBlackTreeNode = new RedBlackTreeNode(value, this.treeSize);
        RedBlackTreeNode nullRedBlackTreeNode = new NullRedBlackTreeNode();
        RedBlackTreeNode root = this.root;
        while (!(root instanceof NullRedBlackTreeNode)) {
            nullRedBlackTreeNode = root;
            root = (redBlackTreeNode.getKey() < root.getKey()) ? root.getLeftSon() : root.getRightSon();
        }

        redBlackTreeNode.setParent(nullRedBlackTreeNode);

        if (nullRedBlackTreeNode instanceof NullRedBlackTreeNode) {
            this.root = redBlackTreeNode;
        } else {
            if (redBlackTreeNode.getKey() < nullRedBlackTreeNode.getKey()) {
                nullRedBlackTreeNode.setLeftSon(redBlackTreeNode);
            } else {
                nullRedBlackTreeNode.setRightSon(redBlackTreeNode);
            }
        }

        redBlackTreeNode.setLeftSon(new NullRedBlackTreeNode());
        redBlackTreeNode.setRightSon(new NullRedBlackTreeNode());
        redBlackTreeNode.setColor(Color.RED);
        insertFixUp(redBlackTreeNode);
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
     * @param redBlackTreeNode the node that roots the tree.
     */
    private void printTree(RedBlackTreeNode redBlackTreeNode) {
        if (redBlackTreeNode instanceof NullRedBlackTreeNode) {
            return;
        }

        printTree(redBlackTreeNode.getLeftSon());
        System.out.println(redBlackTreeNode.getValue());
        printTree(redBlackTreeNode.getRightSon());
    }


    /**
     * rotate left the node in the tree
     * @param redBlackTreeNode
     */
    private void leftRotate(RedBlackTreeNode redBlackTreeNode) {
        RedBlackTreeNode rightSon = redBlackTreeNode.getRightSon();
        redBlackTreeNode.setRightSon(rightSon.getLeftSon());
        if (!(rightSon.getLeftSon() instanceof NullRedBlackTreeNode)) {
            rightSon.getLeftSon().setParent(redBlackTreeNode);
        }
        rightSon.setParent(redBlackTreeNode.getParent());

        switchParentConnection(redBlackTreeNode, rightSon);

        rightSon.setLeftSon(redBlackTreeNode);
        redBlackTreeNode.setParent(rightSon);
    }

    /**
     * rotate right the node in the tree
     * @param redBlackTreeNode
     */
    private void rightRotate(RedBlackTreeNode redBlackTreeNode) {
        RedBlackTreeNode leftSon = redBlackTreeNode.getLeftSon();
        redBlackTreeNode.setLeftSon(leftSon.getRightSon());
        if (!(leftSon.getRightSon() instanceof NullRedBlackTreeNode)) {
            leftSon.getRightSon().setParent(redBlackTreeNode);
        }

        leftSon.setParent(redBlackTreeNode.getParent());

        switchParentConnection(redBlackTreeNode, leftSon);
        leftSon.setRightSon(redBlackTreeNode);
        redBlackTreeNode.setParent(leftSon);

    }

    /**
     * switches parent of x connection to x to be y
     *
     * @param redBlackTreeNode node
     * @param node node
     */
    private void switchParentConnection(RedBlackTreeNode redBlackTreeNode, RedBlackTreeNode node) {
        if (redBlackTreeNode.getParent() instanceof NullRedBlackTreeNode) {
            this.root = node;
        } else {
            if (isLeftSon(redBlackTreeNode)) {
                redBlackTreeNode.getParent().setLeftSon(node);
            } else {
                redBlackTreeNode.getParent().setRightSon(node);
            }
        }
    }

    /**
     * fix the tree to be in the standards of RB tree after insertion
     * @param redBlackTreeNode
     */
    private void insertFixUp(RedBlackTreeNode redBlackTreeNode) {
        while (redBlackTreeNode.getParent().getColor() == Color.RED) {
            if (redBlackTreeNode.getParent() == redBlackTreeNode.getParent().getParent().getLeftSon()) {
                RedBlackTreeNode rightSon = redBlackTreeNode.getParent().getParent().getRightSon();
                if (rightSon.getColor() == Color.RED) {
                    redBlackTreeNode.getParent().setColor(Color.BLACK);
                    rightSon.setColor(Color.BLACK);
                    redBlackTreeNode.getParent().getParent().setColor(Color.RED);
                    redBlackTreeNode = redBlackTreeNode.getParent().getParent();
                } else {
                    if (isRightSon(redBlackTreeNode)) {
                        redBlackTreeNode = redBlackTreeNode.getParent();
                        leftRotate(redBlackTreeNode);
                    }
                    redBlackTreeNode.getParent().setColor(Color.BLACK);
                    redBlackTreeNode.getParent().getParent().setColor(Color.RED);
                    rightRotate(redBlackTreeNode.getParent().getParent());
                }
            } else {
                RedBlackTreeNode leftSon = redBlackTreeNode.getParent().getParent().getLeftSon();
                if (leftSon.getColor() == Color.RED) {
                    redBlackTreeNode.getParent().setColor(Color.BLACK);
                    leftSon.setColor(Color.BLACK);
                    redBlackTreeNode.getParent().getParent().setColor(Color.RED);
                    redBlackTreeNode = redBlackTreeNode.getParent().getParent();
                } else {
                    if (isLeftSon(redBlackTreeNode)) {
                        redBlackTreeNode = redBlackTreeNode.getParent();
                        rightRotate(redBlackTreeNode);
                    }
                    redBlackTreeNode.getParent().setColor(Color.BLACK);
                    redBlackTreeNode.getParent().getParent().setColor(Color.RED);
                    leftRotate(redBlackTreeNode.getParent().getParent());
                }
            }
        }

        root.setColor(Color.BLACK);
    }

    /**
     * fix the tree to be in the standards of RB tree after deletion
     * @param redBlackTreeNode
     */
    private void deleteFixUp(RedBlackTreeNode redBlackTreeNode) {
        while (redBlackTreeNode != root && redBlackTreeNode.getColor() == Color.BLACK) {
            if (isLeftSon(redBlackTreeNode)) {
                RedBlackTreeNode rightSon = redBlackTreeNode.getParent().getRightSon();
                //case 1
                if (rightSon.getColor() == Color.RED) {
                    rightSon.setColor(Color.BLACK);
                    redBlackTreeNode.getParent().setColor(Color.RED);
                    leftRotate(redBlackTreeNode.getParent());
                    rightSon = redBlackTreeNode.getParent().getRightSon();
                }
                if (rightSon.getLeftSon().getColor() == Color.BLACK && rightSon.getRightSon().getColor() == Color.BLACK) {
                    rightSon.setColor(Color.RED);
                    redBlackTreeNode = redBlackTreeNode.getParent();
                } else {
                    if (rightSon.getRightSon().getColor() == Color.BLACK) {
                        rightSon.getLeftSon().setColor(Color.BLACK);
                        rightSon.setColor(Color.RED);
                        rightRotate(rightSon);
                        rightSon = redBlackTreeNode.getParent().getRightSon();
                    }
                    rightSon.setColor(redBlackTreeNode.getParent().getColor());
                    redBlackTreeNode.getParent().setColor(Color.BLACK);
                    rightSon.getRightSon().setColor(Color.BLACK);
                    leftRotate(redBlackTreeNode.getParent());
                    redBlackTreeNode = root;
                }
            } else {
                RedBlackTreeNode leftSon = redBlackTreeNode.getParent().getLeftSon();
                //case 1
                if (leftSon.getColor() == Color.RED) {
                    leftSon.setColor(Color.BLACK);
                    redBlackTreeNode.getParent().setColor(Color.RED);
                    rightRotate(redBlackTreeNode.getParent());
                    leftSon = redBlackTreeNode.getParent().getLeftSon();
                }
                if (leftSon.getRightSon().getColor() == Color.BLACK && leftSon.getLeftSon().getColor() == Color.BLACK) {
                    leftSon.setColor(Color.RED);
                    redBlackTreeNode = redBlackTreeNode.getParent();
                } else {
                    if (leftSon.getLeftSon().getColor() == Color.BLACK) {
                        leftSon.getRightSon().setColor(Color.BLACK);
                        leftSon.setColor(Color.RED);
                        leftRotate(leftSon);
                        leftSon = redBlackTreeNode.getParent().getLeftSon();
                    }
                    leftSon.setColor(redBlackTreeNode.getParent().getColor());
                    redBlackTreeNode.getParent().setColor(Color.BLACK);
                    leftSon.getLeftSon().setColor(Color.BLACK);
                    rightRotate(redBlackTreeNode.getParent());
                    redBlackTreeNode = root;
                }
            }
        }
        redBlackTreeNode.setColor(Color.BLACK);
    }

    /**
     * checks if the node is rightSon of his father
     * @param redBlackTreeNode
     * @return
     */
    private boolean isRightSon(RedBlackTreeNode redBlackTreeNode) {
        return redBlackTreeNode == redBlackTreeNode.getParent().getRightSon();
    }

    /**
     * checks if the node is left son of his father
     * @param redBlackTreeNode
     * @return
     */
    private boolean isLeftSon(RedBlackTreeNode redBlackTreeNode) {
        return redBlackTreeNode == redBlackTreeNode.getParent().getLeftSon();
    }

    /**
     * finds the node predecessor in the tree
     * @param redBlackTreeNode
     * @return
     */
    private RedBlackTreeNode treePredecessor(RedBlackTreeNode redBlackTreeNode) {
        if (redBlackTreeNode.getLeftSon() != null) {
            return treeMax(redBlackTreeNode.getLeftSon());
        }
        RedBlackTreeNode parent = redBlackTreeNode.getParent();
        while (!(parent instanceof NullRedBlackTreeNode) && redBlackTreeNode == parent.getLeftSon()) {
            redBlackTreeNode = parent;
            parent = parent.getParent();
        }

        return parent;
    }


    /**
     * find the max element in the tree
     * @param redBlackTreeNode
     * @return
     */
    private RedBlackTreeNode treeMax(RedBlackTreeNode redBlackTreeNode) {
        if (!(redBlackTreeNode.getRightSon() instanceof NullRedBlackTreeNode)) {
            return treeMax(redBlackTreeNode.getRightSon());
        } else {
            return redBlackTreeNode;
        }
    }

    /**
     * recursive function that adds all nodes sons to the list
     * @param node
     * @param listOfNodes
     */
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


