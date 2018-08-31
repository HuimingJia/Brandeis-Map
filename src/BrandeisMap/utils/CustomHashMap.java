package BrandeisMap.utils;

public class CustomHashMap<K, V> {
    static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K key, V value, Entry<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K,V>[] table;
    private int capacity= 10;

    public CustomHashMap(){
        table = new Entry[capacity];
    }

    private int hash(K key){
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V data){
        if(key == null) return;
        int hash = hash(key);
        Entry<K,V> newEntry = new Entry<K,V>(key, data, null);

        //if table location does not contain any entry, store entry there.
        if(table[hash] == null){
            table[hash] = newEntry;
        } else {
            Entry<K,V> pre = null;
            Entry<K,V> cur = table[hash];

            while (cur != null){ //we have reached last entry of bucket.
                if (cur.key.equals(key)) {
                    if (pre == null){
                        newEntry.next=cur.next;
                        table[hash]=newEntry;
                        return;
                    }
                    else{
                        newEntry.next=cur.next;
                        pre.next=newEntry;
                        return;
                    }
                }
                pre=cur;
                cur = cur.next;
            }
            pre.next = newEntry;
        }
    }

    public V get(K key) {
        int hash = hash(key);
        if(table[hash] == null){
            return null;
        } else {
            Entry<K,V> temp = table[hash];
            while (temp!= null) {
                if (temp.key.equals(key))
                    return temp.value;
                temp = temp.next;
            }
            return null;
        }
    }
}
