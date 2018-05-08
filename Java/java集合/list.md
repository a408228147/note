####1.Arraylist和Linklist的区别
____
- ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。
- 对于随机访问get和set，ArrayList觉得优于LinkedList，因为LinkedList要移动指针。
- 对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据。
(在查改方面数组可以通过索引迅速确定位置，而链表只能依次寻找，所以数组有着无法匹敌的优势。而在增删方面，数组每次插入元素都要将该元素后面的所有元素向后挪动一位，删除则将该元素之后所有元素向前移动一位。而链表在插入元素的时候，只需将该元素前驱的指针指向本身，将本身的指针指向后驱，删除则将该元素的前驱指向该元素的后驱。)
  </br>
####2.hashmap的实现
____
- HashMap实际上是一个“链表散列”的数据结构，即数组和链表的结合体,HashMap底层就是一个数组结构，数组中的每一项又是一个链表。当新建一个HashMap的时候，就会初始化一个数组。
<pre><code>
public V put(K key, V value) {
    // HashMap允许存放null键和null值。
    // 当key为null时，调用putForNullKey方法，将value放置在数组第一个位置。
    if (key == null)
        return putForNullKey(value);
    // 根据key的keyCode重新计算hash值。
    int hash = hash(key.hashCode());
    // 搜索指定hash值在对应table中的索引。
    int i = indexFor(hash, table.length);
    // 如果 i 索引处的 Entry 不为 null，通过循环不断遍历 e 元素的下一个元素。
    for (Entry<K,V> e = table[i]; e != null; e = e.next) {
        Object k;
        if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }
    // 如果i索引处的Entry为null，表明此处还没有Entry。
    modCount++;
    // 将key、value添加到i索引处。
    addEntry(hash, key, value, i);
    return null;
}
</code></pre>
- 当我们往HashMap中put元素的时候，先根据key的hashCode重新计算hash值，根据hash值得到这个元素在数组中的位置（即下标）， 如果数组该位置上已经存放有其他元素了，那么在这个位置上的元素将以链表的形式存放，新加入的放在链头，最先加入的放在链尾。如果数组该位置上没有元素，就直接将该元素放到此数组中的该位置上。
- HashMap底层数组的长度总是 2 的 n 次方，这是HashMap在速度上的优化。
####2.hashmap和hashtable的区别
- 两者最主要的区别在于Hashtable是线程安全，而HashMap则非线程安全。
- HashMap可以使用null作为key，不过建议还是尽量避免这样使用。HashMap以null作为key时，总是存储在table数组的第一个节点上。而Hashtable则不允许null作为key。
- HashMap继承了AbstractMap，HashTable继承Dictionary抽象类，两者均实现Map接口。
