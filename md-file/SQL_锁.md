# 锁

解决因资源共享 而造成的并发问题。

**分类**：
**操作类型**：
	a.读锁（共享锁）： 对同一个数据，多个读操作可以同时进行，互不干扰。
	b.写锁（互斥锁）： 如果当前写操作没有完毕，则无法进行其他的读操作、写操作

**操作范围**：
	a.表锁 ：一次性对一张表整体加锁。如MyISAM存储引擎使用表锁，开销小、加锁快；无死锁；但锁的范围大，容易发生锁冲突、并发度低。
	b.行锁 ：一次性对一条数据加锁。如InnoDB存储引擎使用行锁，开销大，加锁慢；容易出现死锁；锁的范围较小，不易发生锁冲突，并发度高（很小概率 发生高并发问题：脏读、幻读、不可重复度、丢失更新等问题）。
	c.页锁	

---

```mysql
-- 创建表tablelock 字段：id name
create table tablelock
(
  id int primary key auto_increment , 
  name varchar(20)
)engine myisam;
-- 插入数据
insert into tablelock(name) values('a1');
insert into tablelock(name) values('a2');
insert into tablelock(name) values('a3');
insert into tablelock(name) values('a4');
insert into tablelock(name) values('a5');
```

增加锁：

```mysql
lock table 表1  read/write  ,表2  read/write   ,...
```

查看加锁的表：

```mysql
show open tables ;
```

会话：session :每一个访问数据的dos命令行、数据库客户端工具  都是一个会话

## 加读锁

```mysql
lock table  tablelock read ; -- 加 tablelock 读锁
```

会话0：

```mysql
-- 对此表的操作
select * from tablelock; -- 读（查），可以
delete from tablelock where id =1 ; -- 写（增删改），不可以
-- 对其他表的操作
select * from emp ; -- 读，不可以
delete from emp where eid = 1; -- 写，不可以
-- 结论1：
-- 如果某一个会话 对A表加了read锁，则 该会话 可以对A表进行读操作、不能进行写操作，且该会话不能对其他表进行读、写操作。
-- 综上所述：即如果给A表加了读锁，则当前会话只能对A表进行读操作。
```

（其他会话）：

```mysql
-- 对此表的操作
select * from tablelock;   -- 读（查），可以
delete from tablelock where id =1 ; -- 写，会“等待”会话0将锁释放
-- 对其他表的操作
select * from emp ;  -- 读（查），可以
delete from emp where eno = 1; -- 写，可以

-- 结论2：
-- 会话0给A表加了锁,其他会话的操作：
-- a.可以对其他表（A表以外的表）进行读、写操作
-- b.对A表：读-可以；  写-需要等待释放锁。

-- 释放锁: 
unlock tables ;
```



## 加写锁

```mysql
lock table tablelock write ; -- 对 tablelock 加写锁
```

结论：

	当前会话（会话0）：
	 a.可以对加了写锁的表，进行任何操作（增删改查）。
	 b.但是不能 操作（增删改查）其他表
	其他会话：
		对会话0中加写锁的表 可以进行增删改查的前提是：等待会话0释放写锁

## 

MyISAM在执行查询语句（SELECT）前，会自动给涉及的所有表加读锁，
在执行更新操作（DML）前，会自动给涉及的表加写锁。
所以对MyISAM表进行操作，会有以下情况：
1:对MyISAM表的读操作（加读锁），不会阻塞其他进程（会话）对同一表的读请求，
但会阻塞对同一表的写请求。只有当读锁释放后，才会执行其它进程的写操作。
2:对MyISAM表的写操作（加写锁），会阻塞其他进程（会话）对同一表的读和写操作，只有当写锁释放后，才会执行其它进程的读写操作。


