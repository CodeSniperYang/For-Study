# SQL优化

基于Linux下的MYSQL

原因：性能低、执行时间太长、等待时间太长、SQL语句欠佳（连接查询）、索引失效、服务器参数设置不合理（缓冲、线程数）

编写过程：
		select dinstinct  ..from  ..join ..on ..where ..group by ...having ..order by ..limit ..
解析过程：			
		from .. on.. join ..where ..group by ....having ...select dinstinct ..order by limit ...
[参考网址](https://www.cnblogs.com/annsshadow/p/5037667.html)	

## 索引

index是帮助MYSQL高效获取数据的数据结构。默认是B树
**优点**：
1.提高查询效率
2.降低CPU的使用率。
**缺点**：
1.索引本身很大，需要空间来存储。可以存放在内存or硬盘，通常是硬盘。
2.索引不是所有情况都使用。对于：少量数据，频繁更新的字段，很少使用的字段 不太使用。
3.索引会降低增删改的效率。因为是B树，会改变树的结构。

**分类**：
主键索引：不能为NULL  主键默认加索引 
唯一索引：不能重复，主键索引可以看成是唯一索引。 可以为NULL
单值索引：单列，一个表可以都多个单值索引
复合索引：多个列构成的索引 

**创建索引：**

tb表包含dept和name两个字段
方式一:  create 索引类型 index 索引名 on 表名(字段)

```mysql
--单值索引:
create index dept_index on tb(dept);
--唯一索引:
create unique index dept_index on tb(dept);
--复合索引：
create index dept_name_index on tb(dept,name);
```

方式二：alter table 表名 add 索引类型 index 索引名 (字段名)

```mysql
--单值索引：
alter table tb add index dept_index(dept);
--唯一索引：
alter table tb add unique index name_index(name);
--复合索引：
alter table tb add unique index dept_name_index(dept,name);
```

**删除索引：**

```mysql
--drop index 所以名 on 表名;
drop index name_index on tb;
```

**查询索引：**

```mysql
show index from 表名 ;
shou index from 表名 \G
--注意 ; 和 \G的区别
```

**explain ：**
可以模拟SQL优化器执行SQL语句，从而让开发人员知道自己编写的SQL情况
举例：explain select * from tb;
执行这一条语句之后，会出现一个表格，参数：

```
 id 					 ：编号				
 select_type   ：查询类型
 table 				 ：表
 type  			 	 ：类型
 possible_keys ：预测用到的索引 
 key  				 ：实际使用的索引
 key_len			 ：实际使用索引的长度     
 ref  				 ：表之间的引用
 rows 				 ：通过索引查询到的数据量 
 Extra    		 ：额外的信息

id：
id值有相同，又有不同： id值越大越优先；id值相同，从上往下 顺序执行
表的执行顺序  因数量的个数改变而改变的原因

select_type: 查询类型
PRIMARY：    包含子查询SQL中的 主查询 （最外层）
SUBQUERY：   包含子查询SQL中的 子查询 （非最外层）
simple：     简单查询（不包含子查询、union）
derived：    衍生查询(使用到了临时表)

type:索引类型、类型
级别：system>const>eq_ref>ref>range>index>all，(达到ref就已经很好了)
要对type进行优化的前提：有索引
其中：system,const只是理想情况；实际能达到 ref>range

possible_keys ：可能用到的索引，是一种预测，不准。
如果 possible_key/key是NULL，则说明没用索引

key ：实际使用到的索引

key_len ：索引的长度 ;
作用：用于判断复合索引是否被完全使用  （a,b,c）。

ref : 注意与type中的ref值区分。
作用： 指明当前表所 参照的 字段。
select ....where a.c = b.x ;(其中b.x可以是常量，const)

rows: 被索引优化查询的 数据个数 (实际通过索引而查询到的 数据个数)
explain select * from course c,teacher t  where c.tid = t.tid
and t.tname = 'tz' ;

Extra：
(i).using filesort ： 性能消耗大；需要“额外”的一次排序。
常见于 order by 语句中。
对于单索引，如果排序和查找是同一个字段，则不会出现using filesort；
如果排序和查找不是同一个字段，则会出现using filesort;
复合索引：不能跨列（最佳左前缀）
举例：text02表中有a1,a2,a3三个字段，建立复合索引
alter table test02 add index idx_a1_a2_a3 (a1,a2,a3) ;
explain select * from test02 where a1='' order by a3 ; 
																				--using filesort
explain select * from test02 where a2='' order by a3 ; 
																				--using filesort
explain select * from test02 where a1='' order by a2 ;
																				--NULL
explain select * from test02 where a2='' order by a1 ; 
																				--using filesort
--小结：where和order by按照复合索引的顺序使用，不要跨列或无序使用。
(ii). using temporary:性能损耗大，用到了临时表。
一般出现在group by 语句中。
(iii). using index :性能提升; 索引覆盖（覆盖索引）。
原因：不读取原文件，只从索引文件中获取数据 （不需要回表查询）
只要使用到的列 全部都在索引中，就是索引覆盖using index
如果用到了索引覆盖(using index时)，会对 possible_keys和key造成影响：
(iV).using where （需要回表查询）
```

## 优化案例

单表优化、两表优化、三表优化

### (1)单表优化

```mysql
-- 创建表book 字段：bid name authorid publicid typeid
create table book
(
	bid int(4) primary key,
	name varchar(20) not null,
	authorid int(4) not null,
	publicid int(4) not null,
	typeid int(4) not null 
);       
-- 插入数据
insert into book values(1,'tjava',1,1,2) ;
insert into book values(2,'tc',2,1,2) ;
insert into book values(3,'wx',3,2,1) ;
insert into book values(4,'math',4,2,3) ;	
```

查询authorid=1且 typeid为2或3的	bid
```mysql
explain 
select bid from book 
where typeid in(2,3) and authorid=1  
order by typeid desc;  
```

优化：

```mysql
-- 加索引
alter table book add index idx_bta (bid,typeid,authorid);
-- 索引一旦进行 升级优化，需要将之前废弃的索引删掉，防止干扰。
drop index idx_bta on book;   -- 下面是升级
```

根据SQL实际解析的顺序，调整索引的顺序：

```mysql
alter table book add index idx_tab (typeid,authorid,bid);
```

虽然可以回表查询bid，但是将bid放到索引中 可以提升使用using index ;
再次优化（之前是index级别）：思路。因为范围查询in有时会实现，因此交换 索引的顺序，将typeid in(2,3) 放到最后。

```mysql
drop index idx_tab on book; -- 删除之前的index
alter table book add index idx_atb (authorid,typeid,bid); -- 升级
explain 
select bid from book 
where  authorid=1 and  typeid in(2,3) 
order by typeid desc ;
```

**小结：**	
a.最佳做前缀，保持索引的定义和使用的顺序一致性  
b.索引需要逐步优化  
c.将含In的范围查询 放到where条件的最后，防止失效。

## 两表优化

```mysql
-- 创建表teacher2 字段：tid cid
create table teacher2
(
	tid int(4) primary key,
	cid int(4) not null
);
insert into teacher2 values(1,2);
insert into teacher2 values(2,1);
insert into teacher2 values(3,3);
-- 创建表course2 字段：cid cname
create table course2
(
	cid int(4) ,
	cname varchar(20)
);
insert into course2 values(1,'java');
insert into course2 values(2,'python');
insert into course2 values(3,'kotlin');
```

左连接：

```mysql
explain 
select * from teacher2 t 
left outer join course2 c
on t.cid=c.cid 
where c.cname='java';  -- 查询课程名为java的课程信息
```

索引往哪张表加？   -小表驱动大表  
索引建立经常使用的字段上 （本题 t.cid=c.cid可知，t.cid字段使用频繁，因此给该字段加索引） 
一般情况对于左外连接，给左表加索引；右外连接，给右表加索引
 对于双层循环来说：一般建议 将数据小的循环 放外层；数据大的循环放内存。

	--当编写 ..on t.cid=c.cid 时，将数据量小的表 放左边（假设此时t表数据量小）
	alter table teacher2 add index index_teacher2_cid(cid) ;
	alter table course2 add index index_course2_cname(cname);

Using join buffer:extra中的一个选项，作用：Mysql引擎使用了 连接缓存。

## 三表优化

a.小表驱动大表  
b.索引建立在经常查询的字段上

```mysql
-- 建立表test03 字段：a1 a2 a3 a4 
create table test03
(
  a1 int(4) not null,
  a2 int(4) not null,
  a3 int(4) not null,
  a4 int(4) not null
);
-- 复合索引
alter table test03 add index idx_a1_a2_a3_4(a1,a2,a3,a4) ;
```

```mysql
explain 
select a1,a2,a3,a4 from test03 
where a1=1 and a2=2 and a3=3 and a4 =4 ; 
-- 推荐写法，因为 索引的使用顺序（where后面的顺序） 和 复合索引的顺序一致
```

```mysql
explain 
select a1,a2,a3,a4 from test03 
where a4=1 and a3=2 and a2=3 and a1 =4 ; 
-- 虽然编写的顺序和索引顺序不一致，但是 sql在真正执行前 经过了SQL优化器的调整，结果与上条SQL是一致的。
```

```mysql
explain 
select a1,a2,a3,a4 from test03 
where a1=1 and a2=2 and a4=4 
order by a3; 
-- 以上SQL用到了a1 a2两个索引，该两个字段 不需要回表查询using index ;
-- 而a4因为跨列使用，造成了该索引失效，需要回表查询 因此是using where；
-- 以上可以通过 key_len进行验证
```


```mysql
explain 
select a1,a2,a3,a4 from test03 
where a1=1 and a4=4 
order by a3; 
-- 以上SQL出现了 using filesort(文件内排序，“多了一次额外的查找/排序”) ：不要跨列使用( where和order by 拼起来，不要跨列使用)
```


```mysql
explain 
select a1,a2,a3,a4 from test03 
where a1=1 and a4=4 
order by a2 , a3; 
-- 不会using filesort
```

总结：
1.如果 (a,b,c,d)复合索引 和使用的顺序全部一致(且不跨列使用)，则复合索引全部使用。如果部分一致(且不跨列使用)，则使用部分索引。
2.where和order by 拼起来，不要跨列使用 

## 避免索引失效的一些原则 

```
（1）复合索引
a.复合索引，不要跨列或无序使用（最佳左前缀）
b.复合索引，尽量使用全索引匹配

（2）不要在索引上进行任何操作（计算、函数、类型转换），否则索引失效

（3）复合索引不能使用不等于（!=  <>）或is null (is not null)，否则自身以及右侧所有全部失效。
复合索引中如果有>，则自身和右侧索引全部失效。
		
（4）尽量使用索引覆盖（using index）

(5) like尽量以“常量”开头，不要以'%'开头，否则索引失效

（6）尽量不要使用类型转换（显示、隐式），否则索引失效

（7）尽量不要使用or，否则索引失效
```

## 一些其他的优化方法

```
（1）exist和in
select ..from table where exist (子查询) ;
select ..from table where 字段 in  (子查询) ;
如果主查询的数据集大，则使用In   ,效率高。
如果子查询的数据集大，则使用exist,效率高。

（2）order by 优化
提高order by查询的策略：
a.选择使用单路、双路 ；调整buffer的容量大小；
b.避免select * ...  
c.复合索引 不要跨列使用 ，避免using filesort
d.保证全部的排序字段 排序的一致性（都是升序 或 降序）
```







