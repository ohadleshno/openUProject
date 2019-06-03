import dataStructure.HashTable.HashTable;
import dataStructure.HashTable.HashTableException;
import dataStructure.RedBlackTree.RedBlackTree;
import dataStructure.RedBlackTree.RedBlackTreeNode;
import file.FileUtil;

import java.io.IOException;
import java.util.*;

public class Main {

    private static final String DICTIONARY_FILE_PATH = "/Users/ohadleshno/IdeaProjects/openUproject/src/file/dict.txt";
    private static final String TEXT_FILE_FILE_PATH = "/Users/ohadleshno/IdeaProjects/openUproject/src/file/textFile.txt";
    private static final String MTEXT_FILE_FILE_PATH = "/Users/ohadleshno/IdeaProjects/openUproject/src/file/myTextFile.txt";


    public static void main(String[] args) throws IOException, HashTableException {
        String[] dictionaryWords = FileUtil.getAllWordsFromFiles(DICTIONARY_FILE_PATH);
        HashTable dictionary = new HashTable(dictionaryWords);
        String[] wordsFromTextFile = FileUtil.getAllWordsFromFiles(MTEXT_FILE_FILE_PATH);
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