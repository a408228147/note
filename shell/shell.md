
   ### 题目： 输入一个文件或目录，是目录就打印首层文件名，是文件就是打印文件内容，还判断了文件是否为空是否可写，可写就进行写，是否覆盖文件进行写。
   
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
   