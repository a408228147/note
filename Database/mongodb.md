## 常用命令

1. 使用数据库(如果没有的话就自动创建数据库)
    * use test
2. 查看数据库
    * show dbs
3. 插入数据

    1. db.test.insert({“name”:”aaa”})
        * 注意：batchinsert()方式在3.2之后的版本似乎已废除
    
    2. 方法二
    ```js
       j = { name : “mongo” };
       t = { x : 3 }; 
       // 把对象保存到集合 hurl 中: 
       db.test.save(j);
    ```
### 查找
 
1. 查找指定的值
    * db.test.find({name:”Bob”})