####1.Arraylist和Linklist的区别
____
- ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。
- 对于随机访问get和set，ArrayList觉得优于LinkedList，因为LinkedList要移动指针。
- 对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据。
(在查改方面数组可以通过索引迅速确定位置，而链表只能依次寻找，所以数组有着无法匹敌的优势。而在增删方面，数组每次插入元素都要将该元素后面的所有元素向后挪动一位，删除则将该元素之后所有元素向前移动一位。而链表在插入元素的时候，只需将该元素前驱的指针指向本身，将本身的指针指向后驱，删除则将该元素的前驱指向该元素的后驱。)
   </br></br>
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
</br></br>
####3.hashmap和hashtable的区别
- 两者最主要的区别在于Hashtable是线程安全，而HashMap则非线程安全。
- HashMap可以使用null作为key，不过建议还是尽量避免这样使用。HashMap以null作为key时，总是存储在table数组的第一个节点上。而Hashtable则不允许null作为key。
- HashMap继承了AbstractMap，HashTable继承Dictionary抽象类，两者均实现Map接口。


## 题目

  #### 从access.log中统计数据
     1. 对healthcheck.html的请求不计入统计
     2. 输出请求总量，以及GET和POST分别的总量
     3. 输出请求最频繁的10个接口及其次数，按次数降序
     4. 输出每个小时有多少分钟请求数超过400次， 比如12点有30分钟每分钟超过了400次，11点有35分钟每分钟超过了400次 table
     
 
 ```java
 public class homework {
  Map<String, Integer> urlMap = new HashMap<>();
     Table<String, String, Integer> timesTable = HashBasedTable.create();
 
     public void one(String path) throws IOException {
         File file = new File(path);
         BufferedReader reader = null;
         //匹配时间
         Pattern date = Pattern.compile("\\d{4}:\\d{2}:\\d{2}:\\d{2}");
         //匹配接口
         Pattern p1 = Pattern.compile("GET (.*?) HTTP");
         try {
             reader = new BufferedReader(new FileReader(file));
             String line = null;
             List<model> list = new ArrayList<model>();
             while ((line = reader.readLine()) != null) {
                 model model = new model();
                 Splitter S = Splitter.onPattern("\\d{4}:\\d{2}:\\d{2}:\\d{2}");
                 //截取访问时间
                 Matcher datem = date.matcher(line);
                 if (datem.find()) {
                     String[] d = datem.group().split(":");
                     model.setRequestMin(d[2]);
                     model.setRequestHour(d[1]);
                 }
                 //截取访问方式
                 if (line.contains("GET")) {
                     model.setRequestWay("GET");
                     //截取访问路径
                     Matcher m1 = p1.matcher(line);
                     m1.find();
                     String i = m1.group();
                     String[] l = i.split(" ");
                     if (l[1].contains("?")) {
                         String[] temp = l[1].split("\\?");
                         model.setRequestUrl(temp[0]);
                     } else {
                         model.setRequestUrl(l[1]);
                     }
                 } else {
                     model.setRequestWay("POST");
                     //截取访问路径
                     Matcher m1 = p1.matcher(line);
                     m1.find();
                     String i = m1.group();
                     String[] l = i.split(" ");
                     model.setRequestUrl(l[1]);
                 }
                 list.add(model);
             }
 
             //除去healthcheck.html
             list = list.stream().filter(t -> !t.getRequestUrl().equals("/healthcheck.html")).collect(Collectors.toList());
             System.out.println("请求总量：" + list.stream().count());
             System.out.println("GET总量：" + list.stream().filter(t -> t.getRequestWay().equals("GET")).count());
             System.out.println("POST总量：" + list.stream().filter(t -> t.getRequestWay().equals("POST")).count());
 
             //统计访问每个URL的次数
             list.stream().forEach(t -> countUrl(t.getRequestUrl()));
             Map<String, Integer> finalMap = new LinkedHashMap<>();
             urlMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
             //输出前十的
             finalMap.entrySet().stream().limit(10).forEach(e -> System.out.println("接口名称：" + e.getKey() + "    访问次数：   " + e.getValue()));
 
             //统计每小时访问次数
             list.stream().forEach(t -> countMin(t));
             /**
              * 计算每小时访问次数>400的个数
              */
             for (Table.Cell<String, String, Integer> cell : timesTable.cellSet()) {
                 if (cell.getValue() > 400)
                     System.out.println(cell.getRowKey() + "点" + cell.getColumnKey() + "分" + "访问：" + cell.getValue() + "次");
             }
 
         } catch (IOException e) {
 
         } finally {
             reader.close();
         }
     }
 
     /**
      * 计算相同接口的访问次数
      */
     public void countUrl(String url) {
         if (urlMap.containsKey(url)) {
             int count = urlMap.get(url);
             urlMap.put(url, count + 1);
         } else {
             urlMap.put(url, 1);
         }
     }
 
     /**
      * 计算每分钟的访问次数
      */
     public void countMin(model date) {
         if (timesTable.contains(date.getRequestHour(), date.getRequestMin())) {
             int count = timesTable.get(date.getRequestHour(), date.getRequestMin());
             timesTable.put(date.getRequestHour(), date.getRequestMin(), count + 1);
         } else {
             timesTable.put(date.getRequestHour(), date.getRequestMin(), 1);
         }
     }
     
      public static void main(String[] args) throws IOException {
             homework t = new homework();
             String path = homework.class.getResource("/").getPath() + "access.log";//获取资源相对路径
             t.one(path);
         }
}
```


```java
          /**
               * @Description: 有五个学生，每个学生有三门课的成绩，
               * 从“成绩单.txt”中读取以上数据（包括学号、姓名、三门课成绩），计算出每门课的平均成绩,
               * 将原有的数据和计算出的平均分数存放在磁盘文件"stud"中。
               * @Author: by haoqiang.zheng
               * @CreateDate: 16/07/2018 12:02 PM
               */
     public class MainAction {
         public static void main(String[] args) throws IOException {
             String path = MainAction.class.getResource("/").getPath() + "成绩单.txt";//获取资源相对路径
             String file = AvgScore.class.getResource("/").getPath() + "stud.txt";
             System.out.println(path);
             System.out.println(file);
             AvgScore as = new AvgScore();
             as.readFile(path);
         }
     }
     
// 学号,姓名,语文成绩,数学成绩,英语成绩
// 10001,xiaoming,90,89,77
// 10002,xiaowang,88,97,93
// 10003,xiaoli,60,67,62
// 10004,xiaohong,91,75,80
// 10005,xiaozhao,62,55,45
// 各科,平均成绩,78.2,76.6,71.4

public class AvgScore {
    /**
     * @Description: 读取文件
     * @Author: by haoqiang.zheng
     * @CreateDate: 16/07/2018 1:52 PM
     */
    final Logger logger = Logger.getLogger(MainAction.class);

    public void readFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        Table<String, String, Table> infoTable = HashBasedTable.create();
        int i = 0;
        while ((line = reader.readLine()) != null) {
            String[] info = line.split(",");
            Table<String, String, String> scoreTable = HashBasedTable.create();
            scoreTable.put(info[2], info[3], info[4]);
            infoTable.put(info[0], info[1], scoreTable);
        }
        handleInfo(infoTable);
    }

    /**
     * @Description: 解析数据求平均值
     * @Author: by haoqiang.zheng
     * @CreateDate: 16/07/2018 1:52 PM
     */
    private void handleInfo(Table infoTable) {
        int chinese = 0;
        int math = 0;
        int english = 0;
        int i = 0;
        for (Object cell : infoTable.cellSet()) {
            Table.Cell<String, String, Table> infocell = (Table.Cell<String, String, Table>) cell;
            for (Object scoreinfocell : infocell.getValue().cellSet()) {
                Table.Cell<String, String, String> scoreinfo = (Table.Cell<String, String, String>) scoreinfocell;
                if(i!=0){
                chinese += Integer.parseInt(scoreinfo.getColumnKey());
                math += Integer.parseInt(scoreinfo.getRowKey());
                english += Integer.parseInt(scoreinfo.getValue());}
                i++;
            }
        }
        Table<String, String, String> avgtable = HashBasedTable.create();
        avgtable.put(String.valueOf(Float.valueOf((float)math /(float) 5)), String.valueOf(Float.valueOf((float)chinese /(float) 5)), String.valueOf(Float.valueOf((float)english / (float)5)));
        infoTable.put("各科", "平均成绩", avgtable);
        infoWrite(infoTable);
    }

    /**
     * @Description: 输入文本
     * @Author: by haoqiang.zheng
     * @CreateDate: 16/07/2018 3:05 PM
     */
    private void infoWrite(Table infoTable) {
        BufferedWriter out = null;
        String file = AvgScore.class.getResource("/").getPath() + "stud.txt";
        System.out.println(file);
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            int english = 0;
            for (Object cell : infoTable.cellSet()) {
                Table.Cell<String, String, Table> infocell = (Table.Cell<String, String, Table>) cell;
                for (Object scoreinfocell : infocell.getValue().cellSet()) {
                    Table.Cell<String, String, String> scoreinfo = (Table.Cell<String, String, String>) scoreinfocell;
                    out.write(infocell.getRowKey() + "," + infocell.getColumnKey() + "," + scoreinfo.getRowKey() + "," + scoreinfo.getColumnKey() + "," + scoreinfo.getValue());
                }
                out.write("\n");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}

```
            
   