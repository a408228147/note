## 一、作业
### 题目:
   * work1：编写10个线程，第一个线程从1加到10，第二个线程从11加到20……第十个线程从91加到100，最后再把十个线程结果相加。
   * work2：有五个学生，每个学生有三门课的成绩，从“成绩单.txt”中读取以上数据（包括学号、姓名、三门课成绩），计算出每门课的平均成绩,将原有的数据和计算出的平均分数存放在磁盘文件"stud"中。
### git地址：
   * eaxm1：
     * http://gitlab.corp.bianlifeng.com/campus2018-dev/homework/tree/team8/team8/haoqiang.zheng/exam2
   * eaxm2：
     * http://gitlab.corp.bianlifeng.com/campus2018-dev/homework/tree/team8/team8/haoqiang.zheng/exam3
## 二、学习内容

   1. awk
    * 内置变量表
      * $0 当前记录（作为单个变量）可以说是存储文本的内容 awk '{print  $0}' awk.txt
      * $1~$n 当前记录的第n个字段，字段间由FS分格
      * FS 输入字段分隔符 默认是空格 awk 'BEGIN{FS=" ";OFS=":"}{print $1,$3,$4,$5,$6,$7}' awk.txt       
      * NF 当前记录中的字段个数，也表示最后一个字段
      * NR 已经读出对的记录数，默认从1开始,打印行号 awk '{print  NR,$2}' awk.txt
      * RS 记录分隔符
    * awk的模式
      * 空模式
      * 关系运算符模式
      * 正则表达式模式
      * BEGIN和END模式
    2. 预习React



    