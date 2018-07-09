## 一、摸底考试
  1.题目
   * 将附件 StringUtils.java 放到项目的资源文件夹下，然后统计该文件中：
     
     * 有效代码行数（不包括空行和只有注释的行）
     
     * 有效代码行中，每个英文字符出现的次数（忽略大小写）
  2.git地址
  * http://gitlab.corp.bianlifeng.com/campus2018-dev/homework/blob/team8/team8/haoqiang.zheng/exam1/src/main/java/func/count.java

## 二、学习内容
  ### 1.常用git命令
   * git init  某个项目开始用git管理
   * git clone 克隆项目
   * git status 文件当前状态
   * git add 开始跟踪文件，并处于暂存状态
   * git commit -m "xxx" 提交到本地仓库
   * git rm 移除文件 
   * git rm -f 删除已经修改过的文件并且放入暂存区的
   * git rm --cached filename 远程库中删除但保留在当前工作目录中
   * git mv 重命名文件
   * git commit --amend 撤销操作修改最后一次提交
   * git fetch 抓取远程仓库中本地仓库没有的。
   * git bush 提交到远程仓库
   * git branch -r -1d origin/branch-name  删除远程分支
      * push origin :branch-name
   * git branch 查看分支
   * git branch xxx 新建分支
   * git checkout xx 切换分支
   * git checkout -b xx 创建并切换分支
   * git merge 合并分支
   ### 2.巩固shell命令
   
  题目： 输入一个文件或目录，是目录就打印首层文件名，是文件就是打印文件内容，还判断了文件是否为空是否可写，可写就进行写，是否覆盖文件进行写。
   
   ## 代码： 
   ```
    #! /bin/bash 
    echo "Enter the name of file: \root"
    read filename
    if [ -e $filename ]
    then
    if [ -d $filename ]
    then
    for file in $filename
    do
    if [ -d $file ]
    then
    echo "$file is  a dirctory"
    for newfile in $file/*
    do
    if [ -d $newfile ]
    then	
    echo "$newfile is a dirctory"
    else
    echo "$newfile is a file"
    fi
    done
    fi
    done
    else
    if [ -f $filename ]
    then
    echo "is a file"	
    if [ -s $filename ]
    then
    echo "found,is no empty"
    if [ -w $filename ]
    then	
    echo " $filename is a file"
    SAVEIFS=$IFS  
    IFS=$(echo -en "\n")  #  -n不换行输出　-e处理特殊字符
    :<<!
    \a 发出警告声；
    \b 删除前一个字符；
    \c 最后不加上换行符号；
    \f 换行但光标仍旧停留在原来的位置；
    \n 换行且光标移至行首；
    \r 光标移至行首，但不换行；
    \t 插入tab；
    \v 与\f相同；
    \\ 插入\字符；
    \nnn 插入nnn（八进制）所代表的ASCII字符；
    !
    for line in $(cat $filename)  
    do  
      echo  $line;  	
    done  
    IFS=$SAVEIFS	
    echo "$filename type some text data. press ctrl+d to quit"	
    read -p "if cover file  yes or no" ask  #找了很久的错误，，才发现。我在这里多写了$..导致不能进行比较
    if [[ $ask  == "yes" ]]
    then
    echo "cover file"
    cat > $filename
    else
    cat >> $filename
    fi
    echo "see file"	
    SAVEIFS=$IFS  
    IFS=$(echo -en "\n")  
    for line in $(cat $filename)  
    do  
      echo  $line;  	
    done  
    IFS=$SAVEIFS
    else
    echo "$filename do not have write permissions"	
    fi
    else
    echo "is empty"
    if [ -w $filename ]
    then
    echo "type some text data. press ctrl+d to quit"
    cat >> $filname
    else
    echo "$filename do not have write permissions"
    fi
    fi
    else
    echo "not is a file"	
    fi
    fi	
    else
    echo "not found"
    fi
   ```
   