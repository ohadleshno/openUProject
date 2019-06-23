package main.dataStructure.HashTable;

public class HashTable {

    private String[] table;
    private int size;


    /**
     * constructor
     * for every word in the array insert it into the table with the insert function
     * @param words
     * @throws HashTableException
     */
    public HashTable(String[] words) throws HashTableException {
        this.size = words.length * 2;
        this.table = new String[words.length * 2];

        for (String word : words) {
            this.insert(word);
        }
    }

    /**
     * checks if hash table contains key
     * @param key
     * @return true if exists otherwise returns false
     */
    public boolean contains(String key) {
        int j = doubleHash(key, 0);
        int i = 1;
        while (table[j] != null && i != size) {
            if (table[j].equals(key)) {
                return true;
            }
            j = doubleHash(key, i);
            i++;
        }
        return false;
    }


    /**
     * insert key into the HashTable
     * @param key
     * @throws HashTableException if no more space in the HashTable
     */
    public void insert(String key) throws HashTableException {
        int i = 0;

        while (i != size) {
            int hash = doubleHash(key, i);
            if (table[hash] == null) {
                table[hash] = key;
                return;
            }
            i++;
        }

        throw new HashTableException("dont have space");
    }

    /**
     * does hash for the key and for i
     * @param key
     * @param i
     * @return
     */
    private int doubleHash(String key, int i) {
        int hash = (hash(key) + i * hash(key)) % size;
        if (hash < 0) {
            hash = hash + size;
        }
        return hash;
    }

    /**
     * hash the value
     * @param value
     * @return
     */
    private int hash(String value) {
        int h = 0;
        for (char v : value.toCharArray()) {
            h = 31 * h + (v & 0xff);
        }

        return h;
    }

}
