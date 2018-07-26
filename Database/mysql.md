1. 在数据库中查询语句速度很慢，如何优化？
    * 建索引
    * 减少表之间的关联
    * 优化sql，尽量让sql很快定位数据，不要让sql做全表查询，应该走索引,把数据 量大的表排在前面 
    * 简化查询字段，没用的字段不要，已经对返回结果的控制，尽量返回少量数据 
    * 尽量用PreparedStatement来查询，不要用Statement
2. SQL查询中in和exists的区别分析
```sql
select * from A where id in (select id from B);

select * from A where exists (select 1 from B where A.id=B.id);
```
对于以上两种情况，in是相当于在遍历比较，exists需要查询数据库，所以说当b表数据量比较大的时候，
exists效率优先in。
IN()只执行一次，它查出B表中的所有id字段并缓存起来。之后，检查A表的id是否与B表中的id相等，如果相等则将A表的记录加入结果集中，直到遍历完A表的所有记录。
它的查询过程类似于以下过程：
```js
List resultSet={};
Array A=(select * from A);
Array B=(select id from B);

for(int i=0;i<A.length;i++) {
  for(int j=0;j<B.length;j++) {
      if(A[i].id==B[j].id) {
        resultSet.add(A[i]);
        break;
      }
  }
}
return resultSet;
```
可以看出，当B表数据较大时不适合使用in()，因为它会B表数据全部遍历一次

例1：A表有10000条记录，B表有1000000条记录，那么最多有可能遍历10000*1000000次，效率很差。
例2：A表有10000条记录，B表有100条记录，那么最多有可能遍历10000*100次，遍历次数大大减少，效率大大提升。
结论：IN()适合B表比A表数据小的情况

EXISTS()语句内部工作原理

exists()会执行A.length次，它并不缓存exists()结果集，因为exists()结果集的内容并不重要，重要的是其内查询语句的结果集空或者非空，空则返回false，非空则返回true。
它的查询过程类似于以下过程：
```js
List resultSet={};
Array A=(select * from A);

for(int i=0;i<A.length;i++) {
   if(exists(A[i].id) {  //执行select 1 from B where B.id=A.id是否有记录返回
       resultSet.add(A[i]);
   }
}
return resultSet;
```
当B表比A表数据大时适合使用exists()，因为它没有那么多遍历操作，只需要再执行一次查询就行。

例1：A表有10000条记录，B表有1000000条记录，那么exists()会执行10000次去判断A表中的id是否与B表中的id相等。
例2：A表有10000条记录，B表有100000000条记录，那么exists()还是执行10000次，因为它只执行A.length次，可见B表数据越多，越适合exists()发挥效果。
例3：A表有10000条记录，B表有100条记录，那么exists()还是执行10000次，还不如使用in()遍历10000*100次，因为in()是在内存里遍历比较，而exists()需要查询数据库，我们都知道查询数据库所消耗的性能更高，而内存比较很快。
结论：EXISTS()适合B表比A表数据大的情况

使用情况分析
当A表数据与B表数据一样大时，in与exists效率差不多，可任选一个使用。

在插入记录前，需要检查这条记录是否已经存在，只有当记录不存在时才执行插入操作，可以通过使用 EXISTS 条件句防止插入重复记录。

EXISTS与IN的使用效率的问题，通常情况下采用exists要比in效率高，因为IN不走索引。但要看实际情况具体使用：

IN适合于外表大而内表小的情况；
EXISTS适合于外表小而内表大的情况。

关于EXISTS：
EXISTS用于检查子查询是否至少会返回一行数据，该子查询实际上并不返回任何数据，而是返回值True或False。

EXISTS 指定一个子查询，检测行的存在。

EXISTS(包括 NOT EXISTS )子句的返回值是一个boolean值。 EXISTS内部有一个子查询语句(SELECT ... FROM...),我将其称为EXIST的内查询语句。其内查询语句返回一个结果集, EXISTS子句根据其内查询语句的结果集空或者非空，返回一个布尔值。

一种通俗的可以理解为：将外查询表的每一行，代入内查询作为检验，如果内查询返回的结果取非空值，则EXISTS子句返回TRUE，这一行行可作为外查询的结果行，否则不能作为结果。

分析器会先看语句的第一个词，当它发现第一个词是SELECT关键字的时候，它会跳到FROM关键字，然后通过FROM关键字找到表名并把表装入内存。接着是找WHERE关键字，如果找不到则返回到SELECT找字段解析，如果找到WHERE，则分析其中的条件，完成后再回到SELECT分析字段。最后形成一张我们要的虚表。

WHERE关键字后面的是条件表达式。条件表达式计算完成后，会有一个返回值，即非0或0，非0即为真(true)，0即为假(false)。同理WHERE后面的条件也有一个返回值，真或假，来确定接下来执不执行SELECT。

分析器先找到关键字SELECT，然后跳到FROM关键字将STUDENT表导入内存，并通过指针找到第一条记录，接着找到WHERE关键字计算它的条件表达式，如果为真那么把这条记录装到一个虚表当中，指针再指向下一条记录。如果为假那么指针直接指向下一条记录，而不进行其它操作。一直检索完整个表，并把检索出来的虚拟表返回给用户。EXISTS是条件表达式的一部分，它也有一个返回值(true或false)。




优化的目的
避免页面访问出现错误，给用户带来不良好的体验。
由于慢查询带来的timeout 慢查询造成锁等待导致timeout
增加数据库的稳定性
本文主要从以下几方面入手
数据库表结构设计
SQL以及索引
数据库表结构设计
尽可能使用小的数据类型，因为小的数据类型更快，占用更少的空间，能用整型就不用字符串，mysql处理int比处理字符串简单。eg:使用整型来存储ip。
字段定义成not null，在mysql中null字段也会占用一个字节。
尽量少用text类型，如果要用可以考虑将该列独立成一张表。
表中要设置独立自增主键，该主键尽量和业务没有任何关联。
表的垂直拆分：就是把原来的一个有很多列的表拆分成多个表，这就解决了表的宽度问题，垂直拆分之后两张表和起来还是一张大表。垂直拆分可以按一下原则进行：
把不常用的字段单独存放到一个表中
把大字段独立存放到一个表中，如text,blod
把经常使用的字段放入一个表中
SQL以及索引
SQL优化

尽量不要使用join查询，尽可能将join查询优化成子查询
limit优化，在大表进行查询的时候有可能会查询全表，可安装一下两点进行优化：
1. 对主键进行orderby操作
select ID, name from user order by id limit 50000,5; ##只扫描5跳数据 2. 复杂操作，子查询返回ID
select * from user where id = (select id from where ....) limit 100000, 10; 3. 记录查询的主键，在下次查询时使用主键过滤
select * from user where id>50003 order by id limit 10000,5

优化关联查询
确保ON和USING子句的列上有索引，需要注意的是在创建关联索引时需要考虑关联顺序，如果A JOIN B时，就不需要在A表的对应的列上创建索引，只需要在B表上的创建相应的索引即可。因为A表上的索引不会执行。

确保GROUP BY和ORDER BY中的表达式只涉及一个表中的列，这样MYSQL才有可能使用索引来优化。

优化UNION：尽量不要使用UNION,MYSQL处理union的方式是把每个查询的结果放到临时表中，然后再把数据返回，很多优化的策略在union中都不能做到很好。如果非要用UNION时，要加上ALL关键字，即UNION ALL, 如果没有ALL关键字，MYSQL会给临时表加上DISTINCT选项，会导致该表的数据做唯一行检查。

索引优化

非独立的列不会使用索引
select * from where id + 1 = 5
前缀索引
如果列很长，通常可以索引开始的部分字符，这样可以有效节约索引空间。
建立索引时尽量使用常用的，字段小的列。
联合索引
在查询条件打乱顺序时还是会引用索引，但是要注意不能缺字段：
#建立索引index(a, b, c) select * from user where c=1 and a=2 and b=3 #会走索引
select * from user where c=1 and b=3 #不会走索引 select * from user where c>1 and a=2 and b=3 #c会走索引,a和b不走索引
select * from user where a=1 and c=3 #a会走索引,b不走
当出现多个索引做相交操作时(AND)，通常来说包含所有相关列时要优于单个列。当出现or操作时，对结果集的合并，排序需要耗费比较大的资源，需要注意，如
select name,dept_name from user where id=1 or dept_id = 1
# 在mysql内部会被优化成
select name,dept_name from user where id=1
union all
select name,dept_name from user where dept_id=1 and id <> 1 
此时出现了union all操作会耗费资源
在选择联合索引时应该把选择性更高的的字段放到索引的前面，这样通过第一个字段就可以过滤大多数不符合条件的数据.

索引选择性是指不重复的索引值和数据表的总记录数的比值，选择性越高查询效率越高，因为选择性越高的索引可以让MYSQL在查询时过滤掉更多的行，唯一索引的选择性是1，此时性能最好。

计算索引选择性

select count(distinct id)/count(*),count(distinct customer_id)/count(*) from user
避免多个范围条件，如
select * from user where money > 10 and age between 18 and 30
此时money可以使用索引，而age不能使用索引 6. 覆盖索引：如果一个索引包含了所要查询的列，此时就成为覆盖索引
7. 排序时利用索引扫描来排序，但要注意的是只有当索引的列顺序和order by子句的顺序完全一样，并且所有列的排序方向也一样时，才能使用索引来排序。如果关联了多张表，则之后order by子句引用的字段全部为第一张表时，才能使用索引来排序。 8. 冗余索引和重复索引：冗余索引是指在相同的列上按照相同的顺序创建相同类型的索引，如已经存在索引(A, B),再创建索引(A)就是冗余索引。 那么如何确定查询冗余索引，可以通过一下语句查询：

select a.table_schema as '数据名',a.table_name as '表名',a.index_name as  '索引1',b.index_name as '索引2',a.column_name as '重复列名' from statistics a join statistics b on a.table_schema = b.table_schema and a.table_name = b.table_name and a.seq_in_index = b.seq_in_index and a.column_name = b.column_name where a.seq_in_index = 1 and a.index_name <> b.index_name

# 或者使用pt-duplicate-key-checker工具进行检查。
及时删除长期未使用的索引。
like关键字使用
# 会只用索引
select * from user where name list 'test_%' 
# 不会使用索引
select * from user where name list '%_test_%' 
EXPLAIN字段解析
id
select识别符，是select的查询序号
select_type
SELECT类型,可以为以下任何一种:
SIMPLE:简单SELECT(不使用UNION或子查询)
PRIMARY:最外面的SELECT
UNION:UNION中的第二个或后面的SELECT语句
DEPENDENT UNION:UNION中的第二个或后面的SELECT语句,取决于外面的查询
UNION RESULT:UNION 的结果
SUBQUERY:子查询中的第一个SELECT
DEPENDENT SUBQUERY:子查询中的第一个SELECT,取决于外面的查询
DERIVED:导出表的SELECT(FROM子句的子查询)

par-titions
如果查询基于分区的话，显示将访问的分区
table
输出的行所引用的表
type
联接类型。下面给出各种联接类型,按照从最佳类型到最坏类型进行排序:
system:表仅有一行(=系统表)。这是const联接类型的一个特例。
const:表最多有一个匹配行,它将在查询开始时被读取。因为仅有一行,在这行的列值可被优化器剩余部分认为是常数。const表很快,因为它们只读取一次!
eq_ref:对于每个来自于前面的表的行组合,从该表中读取一行。这可能是最好的联接类型,除了const类型。
ref:对于每个来自于前面的表的行组合,所有有匹配索引值的行将从这张表中读取。
ref_or_null:该联接类型如同ref,但是添加了MySQL可以专门搜索包含NULL值的行。
index_merge:该联接类型表示使用了索引合并优化方法。
unique_subquery:该类型替换了下面形式的IN子查询的ref: value IN (SELECT primary_key FROM single_table WHERE some_expr) unique_subquery是一个索引查找函数,可以完全替换子查询,效率更高。
index_subquery:该联接类型类似于unique_subquery。可以替换IN子查询,但只适合下列形式的子查询中的非唯一索引: value IN (SELECT key_column FROM single_table WHERE some_expr)
range:只检索给定范围的行,使用一个索引来选择行。
index:该联接类型与ALL相同,除了只有索引树被扫描。这通常比ALL快,因为索引文件通常比数据文件小。
ALL:对于每个来自于先前的表的行组合,进行完整的表扫描，说明查询就需要优化了。
一般来说，得保证查询至少达到range级别，最好能达到ref。

possible_keys
指出MySQL能使用哪个索引在该表中找到行
key
显示MySQL实际决定使用的键(索引)。如果没有选择索引,键是NULL。
key_len
显示MySQL决定使用的键长度。如果键是NULL,则长度为NULL。在不损失精确性的情况下，长度越短越好
ref
显示使用哪个列或常数与key一起从表中选择行。
rows
显示MySQL认为它执行查询时必须检查的行数。多行之间的数据相乘可以估算要处理的行数。
filtered
显示了通过条件过滤出的行数的百分比估计值。
Extra
该列包含MySQL解决查询的详细信息
Distinct:MySQL发现第1个匹配行后,停止为当前的行组合搜索更多的行。
Select tables optimized away MySQL根本没有遍历表或索引就返回数据了，表示已经优化到不能再优化了
Not exists:MySQL能够对查询进行LEFT JOIN优化,发现1个匹配LEFT JOIN标准的行后,不再为前面的的行组合在该表内检查更多的行。
range checked for each record (index map: #):MySQL没有发现好的可以使用的索引,但发现如果来自前面的表的列值已知,可能部分索引可以使用。
Using filesort:MySQL需要额外的一次传递,以找出如何按排序顺序检索行，说明查询就需要优化了。
Using index:从只使用索引树中的信息而不需要进一步搜索读取实际的行来检索表中的列信息。
Using temporary:为了解决查询,MySQL需要创建一个临时表来容纳结果，说明查询就需要优化了。
Using where:WHERE 子句用于限制哪一个行匹配下一个表或发送到客户。
Using sort_union(...), Using union(...), Using intersect(...):这些函数说明如何为index_merge联接类型合并索引扫描。
Using index for group-by:类似于访问表的Using index方式,Using index for group-by表示MySQL发现了一个索引,可以用来查 询GROUP BY或DISTINCT查询的所有列,而不要额外搜索硬盘访问实际的表。
