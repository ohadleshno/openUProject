package main;

import main.dataStructure.HashTable.HashTable;
import main.dataStructure.HashTable.HashTableException;
import main.dataStructure.RedBlackTree.RedBlackTree;
import main.file.FileUtil;
import main.dataStructure.RedBlackTree.RedBlackTreeNode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Main {

    private static final String DICTIONARY_FILE_PATH = "dict.txt";
    private static final String TEXT_FILE_FILE_PATH = "textFile.txt";
    private static final String MY_TEXT_FILE_FILE_PATH = "myTextFile.txt";


    public static void main(String[] args) throws IOException, HashTableException, URISyntaxException {
        String[] dictionaryWords = FileUtil.getAllWordsFromFiles(DICTIONARY_FILE_PATH);
        HashTable dictionary = new HashTable(dictionaryWords);
        String[] wordsFromTextFile = FileUtil.getAllWordsFromFiles(TEXT_FILE_FILE_PATH);
        RedBlackTree tree = new RedBlackTree(wordsFromTextFile);
        removeWordsInTree(dictionary, tree);
        System.out.println("The following words doesn't exist: ");
        tree.printTree();
    }

    /**
     * removes nodes in tree where there value is in the dictionary
     * @param dictionary
     * @param tree
     */
    private static void removeWordsInTree(HashTable dictionary, RedBlackTree tree) {
        List<RedBlackTreeNode> allNodes = tree.getAllNodes();
        for (RedBlackTreeNode node : allNodes) {
            if (dictionary.contains(node.getValue().toLowerCase())) {
                tree.delete(node);
            }
        }
    }
}