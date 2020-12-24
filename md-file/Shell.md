# Shell

## 10.1 Shell概述

Shell是命令行解释器，是一种强大的编程语言。主文件名为.sh，主要是只Bash。
查询支持的Shell，文件中：/etc/shells

## 10.2 Shell脚本的执行方式

echo 输出命令
-e:支持反斜线控制的字符转换

```shell
echo -e "\e[1;31m abcd \e[0m"
# 颜色输出,颜色31m表示红色，\e[1;为开始标志，\e[0m为结束标
```

shell脚本文件第一句话一般是：#!/bin/bash，不是注释，表示以下为shell语句

chmod 755 hello.sh 赋予可执行权限

运行：程序需要绝对路径，/home/codesniperyang/Temp/hello.sh 
或者 bash hello.sh(不需要执行权限)
格式转换，从DOS格式到Unix格式：dos2unix 文件名

## 10.3 Bash的基本功能

10.3.1 历史命令和命令补全
history [选项] [历史命令保存文件] （~/.bash_history）
-c：清空历史命令，-w：将缓存中历史命令写入文件
历史命令默认是保存1000条，可以在环境变量配置文件/etc/profile中进行修改 
命令和目录tab补全

10.3.2 命令别名与常用快捷键
alias 别名='原命令'，直接输入alias查看所有别名
命令执行优先级：绝对路径,相对路径>别名>Bash的内部命令>$PATH环境变量
用命令行执行只会临时生效，永久生效：vim /root/.bashrc（家目录下的.bashrc）
删除别名：unalias 别名

```shell
# Bash常用快捷键：^:ctrl
^+U，剪切光标左侧命令
^+Y，粘贴
^+R，进入搜索执行界面
^+D，退出
```

10.3.3 输入输出重定向
输入重定向：
标准输入设备：键盘，/dev/stdin，0
标准输出设备：显示器，/dev/stdout，1
标准错误输出：显示器，/dev/stderr，2
命令 > 文件，覆盖。命令 >> 文件，追加。
错误命令 2> 文件，错误命令 2>> 文件。
命令 >> 文件 2>&1，命令 &>> 文件，无论对错，都将结果写入文件。
命令 >> 文件1 2>>文件2，把正确的结果输出到文件1，错误的输出到文件2

```shell
# 输入重定向：
wc [选项] [文件名] 
-c： 统计字节数
-w： 统计单词数
-l： 统计行数
ctrl+D，结束输入。
```

命令 < 文件。

10.3.4 多命令顺序执行与管道符
命令1;命令2，多个命令顺序执行，之间没有任何逻辑关联。
命令1&&命令2，当1正确执行，则2才会执行。当1执行不正确，则2不会执行。
命令1||命令2，当命令1执行不正确，2才会执行。当1正确，2不会执行。
判断语句是否正确执行：命令 && echo yes || echo no
管道符：命令1 | 命令2，命令1的正确输出作为命令2的操作对象。

10.3.5 通配符与其他特殊符号，用来匹配文件名
?:匹配一个任意字符；
*:匹配0个或任意多个任意字符
[]:匹配括号中任意一个字符
\[-]:匹配括号中任意一个字符，- 代表范围
\[^]:匹配不是括号中的一个字符
其他字符举例：

```shell
# -> 右侧是输出
name=sc
echo name -> name
echo $name -> sc
echo 'name' -> name 
echo '$name' -> $name 
echo "$name" -> sc
abc=`date` 
bcd=$(date) #命令的两种格式
echo $abc -> 2020年 09月 07日 星期一 22:47:32 CST
```

## 10.4 Bash的变量

10.4.1 用户自定义变量
在Bash中，默认都是字符串型。对当前的shell生效

```shell
a=123
echo $a #输出 123
a="$a"456
echo $a # 123456
a=${a}798
echo $a # 123456789
```

```shell
set #查看全部变量
unset 变量名 #删除变量
```

10.4.2 环境变量
当前的shell以及子shell生效。如果把环境变量写入相应的配置文件，那么这个环境变量将对所有的shell生效。

```shell
# 申明变量
export 变量名=变量值
#查询变量
env
# 删除变量
unset 变量名
```

$PATH 中包含大多数命令路径。
在后面追加：PATH="$PATH":/root/user1/
$PS1 定义系统提示符的变量
echo $PS

10.4.3 位置参数变量
$n

```shell

# 在canshu.sh文件中写入以下内容
#!/usr/bin/bash
echo $0
echo $1
echo $2
echo $3
---------------
执行：
./canshu.sh 11 22 33
输出：
./canshu.sh 
11 
22
33
```

$#，$*，$@

```shell
# 在test.sh中写入：
#!/usr/bin/bash/
echo $#
echo $* #将参数看成一个整体
echo $@ #将参数看成是独立的
-----------------
执行：
bash test.sh 1 2 3 4 5 6 7 8 9
输出：
9
1 2 3 4 5 6 7 8 9
1 2 3 4 5 6 7 8 9
```

10.4.4 预定义变量

```shell
$?：若是0，表示上一条命令正确执行
$$：当前进程的进程号
$!：后台最后一个进程运行的进程号，在命令末尾加入&表示放入后台运行
```

```shell
#接受键盘输入
read [选项] [变量名]
-p：提示信息，等待read时候给出提示信息
-t：秒数，指定等待秒数
-n 字符数：read命令只接受指定的字符数，就会执行
-s：隐藏输入的数据
------------------示例
#!/usr/bin/bash
#在30秒内输入姓名，打印提示信息
read -t 30 -p "please input your name:" name
echo "Your name: $name"
#在30秒内输入年龄，打印提示信息，年龄的输入不可见
read -t 30 -p "please input your age:" -s age
echo "\n"
echo "Your Age: $age"
#在30秒内输入性别，打印提示信息，性别为一个字符
read -t 30 -n 1 -p "please input your gender[M/F]:" gender
echo "\n"
echo "Your gender: $gender" 
```

## 10.5 Bash的运算符

10.5.1 数值运算与运算符

```shell
declare声明变量类型
declare [+/-][选项] 变量名
-：给变量设定类型属性
+：取消变量的类型属性
-i：将变量声明成整数型
-x：将变量声明为环境变量
-p：显示指定变量的被声明的类型
```

```shell
# 数值加法的几种写法
a=5
b=10
declare -i c=$a+$b
d=$(expr $a + $b)
let e=$a+$b
f=$(($a+$b))
```

10.5.2 变量测试和内容替换

## 10.6 环境变量配置文件

10.6.1 简介

```shell
# 1.sourece命令 修改后的文件立即生效
source 配置文件 
. 配置文件 #有空格，两种方法皆可
```

10.6.2 作用

 # 11 Shell编程

## 11.1 基础正则表达式

1.简介
正则表达式用来在文件中匹配符合条件的字符串，正则是包含匹配。grep，awk，sed
通配符用来匹配符合条件的文件名，通配符是完全匹配。ls，find，cp只能用自己的通配符。
2.基础正则表达式

```shell
# "*"前一个字符匹配0次或任意多次
# 匹配所有内容 包括空白行
grep "*a" file.txt
# 匹配至少包含有一个a的行
grep "aa*" file.txt
# 匹配至少包含连续两个a的行
grep "aaa*" file.txt
```

```shell
# "."匹配除了换行符外任意一个字符
# 匹配在s和d中间有两个字符的行
grep "s..d" file.txt
# 一行有s和d
grep "s.*d" file.txt
# 匹配所有内容
grep ".*" file.txt
```

```shell
# "^"匹配行首,"$"匹配行尾
# 匹配以大写"M"开头的行
grep "^M" file.txt
# 匹配以小写"n"结尾的行
grep "$n" file.txt
# 会匹配空白行,打印行号
grep -n "^$" file.txt 
```

```shell
# "[^]"匹配除中括号的字符以外的任意一个字符
# 匹配不用小写字符开头的行
grep ^[^a-z] file.txt
# 匹配不用字母开头的行
grep ^[^a-zA-Z] file.txt
```

```shell
# "\{n\}" 表示其前面的字符恰好出现n次 ,"\{n\}"表示至少出现n次，"\{n,m\}"表示至少n次，最多m次
# 匹配a字母连续出现三次的字符串
grep "a\{3\}" file.txt
# 匹配包含连续的三个数字的字符串
grep "[0-9]\{3\}" file.txt
```

## 11.2 字符截取命令

```
文件file.txt及其内容
name	age	aco	aver	sex
zhang	18	521	90	m
li	19	518	91	f
wang	20	520	92	m
```

11.2.1 cut字段截取命令

```shell
cut [选项] 文件名
-f列号:提取第几列
-d分隔符:按照指定分隔符分割列
若文件中有多个字段(字段间用空格隔开)，提取：
cut -f 2 test.file # 提取第二个字段
cut -f 2,3 testfile # 提取第2，3个字段
cut -d ":" -f 1,3 /etc/passwd # 在passwd文件中提取一列和第三列
# 若是空格作为分隔符，则cut比较难处理
```

cat /etc/passwd | grep /bin/bash | grep -v root  # 提取非root用户,-v表示取反

11.2.2 printf命令

``` shell
printf '输出类型输出格式' 输出类容
输出类型：
%ns 输出字符串，n表示输出n个字符串
%ni 输出整数，n是数字表示输出n个数字
%m.nf 输出浮点数，共m位，n位小数位
输出格式：
\a,\b,\f,\n,\r,\t,\v
# 打印文件的内容
printf '%s\n' $(cat file.txt)
```

11.2.3 awk命令
支持print和printf，print默认加换行
支持多种分隔符的截取

``` shell
# awk '条件1{动作1} 条件2{动作2} ...' 文件名
命令输入：awk {'printf $1"\t" $4 "\n"'} file.txt #$1 $4 表示第1列和第4列 
输出：
name	aver
zhang	90
li	91
wang	92
# 输出文件系统的信息的第1列和第5列
df -h | awk '{print $1 "\t" $5 }'
```

11.2.4 sed命令
是一种数据流编辑器

```shell
sed [选项] '[动作]' 文件名 # 不会更改文件本身的内容
选项：
-n: 将处理的行输出到屏幕 
-e: 允许对输入数据应用多条sed命令编辑
-i: 用sed的修改结果直接修改读取数据的文件
动作：
a\: 追加
c\: 行替换
i\: 插入
d: 删除指定的行
p: 打印，输出指定的行
s: 字符替换
# 将文件的第二行输出
sed -n '2p' file.txt
# 管道符的联合使用
df -h | sed -n '2p' 
# 将删除第2,3行的数据打印
sed '2,3d' file.txt
# 在第二行后追加
sed '2a Hello' file.txt 
# 在第三行前插入
sed '3i Hello Sed~~~' file.txt
# 将第1行替换
sed '1c No much person' file.txt
# 将第一行字符串替换 sed 's/旧字串/新字串/g' 文件名
sed '1s/name/NAME/g' file.txt
# 若将修改后的内容写入文件 需要加上-i选项
```

## 11.3 字符处理命令

```shell
sort [选项] 文件名
选项:
-f: 忽略大小写
-n: 以数值型进行排序，默认使用字符串排序
-r: 反向排序
-t: 指定分隔符，默认是制表符
-k n[,m]: 按照指定的字段范围排序。从第n字段开始，m字段结束
# 排序passwd文件，分隔符位":"，以第三个字段为基准
sort -t ":" -k 3,3 /etc/passwd
sort -n -t ":" -k 3,3 /etc/passwd
```

```shell
wc [选项] 文件名
选项：
-l: 只统计行数
-w: 只统计单词数
-m: 只统计字符数
```

## 11.4 条件判断

1.判断文件类型

```shell
test -e /root/install.log 或
[ -e /root/install.log ]
[ -d /root ] && echo 'yes' || echo "no"
-d:判断该文件是否存在，并且是否为目录文件
-e:判断该文件是否存在
-f:判断该文件是否存在，并且是否为普通文件
```

2.按照文件权限进行判断

```shell
-r:判断该文件是否存在，并且该文件是否具有读权限
-w:是否存在，并且拥有写权限
-x:是否存在，是否具有执行权限
```

## 流程控制

11.5.1 if语句

```shell
if [ 条件判断式 ];then
	程序
fi
或者
if [ 条件判断式 ]
	then
		程序
fi    	
--------------------
if [ 条件判断式 ]
	then
		程序
	else
		程序
fi
--------------------
多分枝：
if [ 条件判断式 ]
	then
		程序
elseif [ 条件判断式 ]
	then
  	程序
	else
		程序
fi
```

11.5.2 case语句

```shell
case $变量名 in
	"值1")
		如果变量的值为值1，执行程序1
		;;
	"值2")
		如果变量的值为值2，执行程序2
		;;
	 *)
		...................
		;;
esac
```

11.5.3 for循环

```shell
# 语法1
for 变量 in 值1 值2 值3...
	do
		程序
	done
---
for i in $(cat ls.log)
	do
		tar -zxf $i &>/dev/null
	done
---
s=0
for (( i=1;i<=100;i=i+1 ))
	do
		s=$(($s+$i))
	done
```

11.5.4 while循环和until循环

```shell
i=1
s=0
while [ $i -le 100 ] # <=100
	do
		s=$(( $s+$i ))
		i=$(( $s+$i ))
	done
---
# 直到型循环
i=1
s=0
until [ $i -gt 100 ]
	do
		s=$(( $s+$i ))
		i=$(( $s+1 ))
	done
```









