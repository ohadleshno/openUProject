package dataStructure.HashTable;

public class HashTable {

    private String[] table;
    private int size;


    public HashTable(String[] words) throws HashTableException {
        this.size = words.length * 2;
        this.table = new String[words.length * 2];

        for (String word : words) {
            this.insert(word);
        }
    }

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

    private int doubleHash(String key, int i) {
        int hash = (hash(key) + i * hash(key)) % size;
        if (hash < 0) {
            hash = hash + size;
        }
        return hash;
    }

    private int hash(String value) {
        int h = 0;
        for (char v : value.toCharArray()) {
            h = 31 * h + (v & 0xff);
        }

        return h;
    }

}
