# Stream 简介

 ## 特点
 1. 函数式
 2. lazy
 3. once 只能使用1次
 4. Short-circuiting
 5. parallel

## 操作
1. Intermediate 
    * map(mapToint,flatmap)
    * filter
    * distinct
    * peek
    * skip
    * parallel

2. Terminal
    * forEach
    * forEachOrdered
    * toArray
    * reduce
    * collect
    * min
    * max
    * count
    * iterator
3. Short-circuiting
    * anyMatch
    * findFirst
    * findAny
    * limit
    