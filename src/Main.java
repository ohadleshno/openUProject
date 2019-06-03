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
        String[] dictionaryWords = FileUtil.createHashMapDict(DICTIONARY_FILE_PATH);
        HashTable dictionary = new HashTable(dictionaryWords);
        String[] wordsFromTextFile = FileUtil.createHashMapDict(MTEXT_FILE_FILE_PATH);
        RedBlackTree tree = new RedBlackTree(wordsFromTextFile);
        removeWordsInTree(dictionary, tree);
        System.out.println("The following words doesn't exist: ");
        tree.printTree();
    }

    private static TreeSet<String> createTree(String[] textWords) {
        TreeSet<String> words = new TreeSet<>();

        for (String textWord : textWords) {
            words.add(textWord);
        }

        return words;
    }

    private static void removeWordsInTree(HashTable dictionary, RedBlackTree tree) {
        List<RedBlackTreeNode> allNodes = tree.getAllNodes();
        for (RedBlackTreeNode node : allNodes) {
            if (dictionary.contains(node.getValue().toLowerCase())) {
                System.out.println(node.getValue()+ " : key -" + node.getKey());
                tree.delete(node);
            }
        }
    }
}