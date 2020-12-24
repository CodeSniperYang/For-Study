# linux 常用命令

统计大小 du name
umask 文件权限的相关命令

which
查看命令的绝对路径

whereis 
查看命令或者配置文件的绝对路径

man 命令或配置文件
man ls：查看命令的帮助信息  info差不多
man services：查看配置文件的帮助信息 不用加绝对路径

whatis 命令
whatis ls 读取ls的简短介绍信息

apropos 配置文件
查看配置文件的信息                    
whatis是精确查找，apropos是模糊查找

help 命令
可以找到命令的相关信息

## 用户管理命令

useradd 添加新用户
passwd 设置用户密码

who 查看当前所有的用户名
登陆用户名 登陆终端(tty 本地终端，pts远程终端) 登陆时间 IP地址

w 和who差不多 但是更加的详细

uptime (时间 已经连续运行的时间 用户数量 系统的负载值等等)

## 压缩解压命令

gzip 文件ing
压缩：gzip 文件名 ---->文件名.gz（只能文件，不能目录。不保存原文件）。
解压缩：gunzip 压缩文件名字

打包：tar 选项 打包后的名字 打包目录
-c 打包 ，-v显示信息，-f指定文件名 -z打包后压缩，-x解压
例子：tar -cvf testing.tar dirtesting(将dirtesting打包成testing.tar)，之后：gzip testing.tar(生成testing.tar.gz)
简化为一步：tar -zcvf testingsum.tar.gz testing(z要放前面，直接生成testingsum.tar.gz)
解包：
tar -zxvf 文件名
例子(接上)：tar -zxvf testing.tar.gz

zip：源文件保留 压缩后文件后缀 .zip
zip 选项 压缩后文件名 目标文件
unzip：后接文件名

bzip2：-k 压缩比666
压缩：bzip2 文件名 ：bzip2 -k FILE（保留源文件，生成后缀位.bz2的文件）
bunzip2: -k 保留压缩包
bunzip2 文件名

## 网络命令

write 用户名
ctrl-D 结束书写

wall 接发送信息
给所有用户发消息

ping 选项 IP
ping -c 3 IP，ping三次 

ifconfig：查看和设置网卡信息
查看信息：ifconfig
范例：ifconfig eth0 192.168.8.520

mail：发送邮件
发送范例：mail root
接收示例：mail
&后的选项：help看命令，number(填入一个数字)，看第几封信，h看邮件列表，q退出，d 序列号，删除

所有用户登陆信息：last

最后一次登陆信息：lastlog
last -u 502(uid)，用户的ID

traceroute：显示数据包到主机间的路径，跟踪路由
traceroute 网站：traceroute www.sina.con.cn 

netstat：显示网络的相关信息
-t TCP协议，-u UDP协议，-l 监听， -r 路由， -n显示IP地址和端口号
范例：netstat -tlun 查看本机监听的端口
netstat -an 查看本机所有的网络连接
nenstat -rn 查看本机的路由表

setup：配置网络 永久生效
配置后要重启服务：service network restart

mount

## 关机重启命令

shutdown -h 20:30 具体时间关机
shutdown -r now 现在重启
-c取消前一个关机命令，-h关机，-r重启

其他关机命令：halt，poweroff，init 0

其他重启命令：reboot，init 6

cat /etc/inittab，修改系统默认运行级别，不能将0和6设置为默认运行级别
查询当前默认运行级别：runlevel

退出登陆命令：logout

## Vim文本编辑器

插入命令：a，A，i，I，o，O
设置行号 :set nu
取消行号 :set nonu
到第一行 gg
到最好一行 G
到第n行 nG，:n
移至行尾 $
移至行首 0
删除光标所在处字符 x
删除光标所在处后n个字符 nx
删除光标所在行 dd
删除光标所在行到文件末尾位置 dG
删除光标所在处到文件末尾位置 dG
删除指定范围的行 :n1,n2d
复制当前行 yy
复制当前行一下n行 nyy
剪切当前行 dd
剪切当前行一下n行 ndd
粘贴再当前光标所在行下或行上 p，P
取消上一步操作：u
恢复撤销：ctrl+r
替换：略

**技巧**：
导入命令执行结果：r !命令
定义快捷键：map 快捷键 出发命令
连续行注释：1,4s/^/#/g，在1-4行前面加注释
替换：ab mymail 229794825@qq.com 将myemail替换为邮箱

## 软件包管理

源码包和二进制包
rpm：RPM包的一般格式为：命名规则
name-version-arch.rpm
name-version-arch.src.rpm
例：
httpd-2.2.3-29.el5.i386.rpm
(1)name，如：httpd，是软件的名称
(2)version，如:2.2.3 ,是软件的版本号。版本号的格式通常为“主版本号.次版本号.
修正号”29，是发布版本号，表示这个RPM包是第几次编译生成的
(3)arch,如:i386,表示包的适用的硬件平台，目前RPM支持的平台有：i386、i586、i686、sparc、alpha
(4).rpm或.src.rpm,是RPM包类型的后缀，.rpm是编译好的二进制包，可用rpm命令直接安装；.src.rpm表示是源

RPM包依赖性：树形依赖，环形依赖，模板依赖(www.rpmfind.net)
解决依赖性：yum在线管理

**rpm**：
RPM安装：rpm -ivh 包全名
-i 安装，-v显示详细信息，-h显示进度，--nodeps不检测依赖性
RPM升级：rpm -Uvh 包全名

RPM卸载：rpm -e 包名(不是包全名，erase)

RPM查询：rpm -q 包名(不是全名)
rpm -qa(查询已经安装的包)
查询某个包的信息：rpm -qi 包名
-p 查询未安装包：rpm -qip 包全名
rpm -ql 包名：查询包中文件安装位置
查询系统文件属于哪个包：rpm -qf 系统文件名
查询软件包的依赖性：rpm -qR 包名

校验和文件提取：
rpm -V 已安装的包名 （8个信息，文件类型，哪个文件被修改）
验证内容中的8个信息的具体内容如下：
S  文件大小是否改变
M  文件的类型或文件的权限（rwx）是否变改变
5  文件MD5校验和是否改变（可以看作文件内容是否改变）
D  装置的主/次代码已经改变
L  Link路径已被改变
U  文件的所属人已被改变
G  文件的所属群组已被改变
T  文件的创建时间忆被改变

提取：一般用来误操作的删除
假设ls被删除
查询文件属于哪个包：rpm -qf /bin/ls
通过cpio从指定包中提取：rpm2cpio /mnt/cdrom/Packages/coreutils-8.4-19.e16.i8686.rpm|cpio -ivd ./bin/ls
将rpm转化为cpio，然后通过cpio将ls提取到当前目录

**yum**：
IP地址配置和网络yum源：配置IP，/etc/yum.repos.d/Cen[tab]
查询所有可用软件包列表：yum  list
搜索服务器上所有和关键字相关的包：yum search 关键字
安装：yum -y install 包名 (-y自动回答yes) 
升级：yum -y update 包名
下载：yum -y remove 包名 （升级和下载慎用）

## 用户和用户组管理

grep 关键字 文件(绝对路径)：查找文件里关键字的一行 

用户信息文件： /etc/passwd
影子文件： /etc/shadow实际密码存放的文件，密文
组文件信息： /etc/group和组密码文件/etc/gshadow
查看配置文件的man信息：man 5 passwd
普通用户家目录：/home/用户名/
root家目录：/root/
用户的邮箱：/var/spool/main/用户名/，内存传递邮件
用户模板用户：/ect/skel

用户管理命令：
添加用户：useradd 用户名
添加命令：passwd 用户名
用户默认设置文件：/etc/login.defs
普通用户修改：直接password
-l：锁定用户(shadow文件下用户密码前加!!)，-u解锁用户
删除用户：userdel -r 用户名，-r表示删除用户的同时删除家目录
查询用户的相关ID：id 用户名
修改用户的相关信息：usermod
查看当前用户的环境变量：env
切换用户身份：su，后面接－好表示连同环境变量一起切换，是独立选项，左右都要空格，正确的方式su - root
借用root身份执行一次操作：su - root -c "useradd user1"	

用户组管理命令：
添加组：groupadd [选项] 组名，group -g GID
修改用户组：groupmod [选项] 组名，-g GID 修改组ID，-n 新组名
删除用户组：groupdel 组名
添加用户指定初始组：useradd -g tg lamp，-G 附加组
把用户加入组或从组中删除：gpasswd 选项 组名，-a 用户名 将用户加入组，-d 用户名 删除

## 权限管理

8.1ACL权限：Access Control List
前提：文件所在分区支持ACL权限
查看分区是否支持：dumpe2fs -h /dev/sad3
在文件中的行：Default mount options，默认存在
查看分区：df -h
临时开启分区ACL权限：mount -o remount ,acl/，重新挂载根分区，并挂载加入acl权限
永久生效：vi /etc/fstab，开机自动挂载文件，文件生效需重启加上  ,acl
重新挂载文件系统：mount -o remount /	
查看ACL命令：getfacl 文件名
设定权限：setfacl [选项] 文件名
setfacl -m u:st:rx /project，类别：用户：权限，g：组名：权限
getfcal /project/ 查看文件权限
最大有效权限mask：与mask相与才真实的权限,不影响所有者
设置mask的权限：setfacl -m m:rx 文件名
删除ACL权限：-x，-b 文件名，删除文件的所有ACL权限
递归ACL权限：setfacl -m u:用户名:权限 -R 文件名，后添加的没有相应权限
默认acl权限：setfacl -m d:u:用户名:权限

8.2文件特殊权限
SetUID：只有可以执行的二进制程序才能设定SUID权限，执行者要有x权限，在执行的时候获得属主身份，只在程序运行中有效。
文件所有者有s权限。
设定SUID的方法：chmod 4755 文件名，chmod u+s 文件名，4代表SUID，2代表GID，1代表SBIT权限。
取消SUID的方法：chmod u-s，chmod 755
大写的S表示报错。
SetGID：(作用对象为二进制文件和目录)
只有可执行的二进制程序才能设置SGID权
命令执行者要对该程序有x权限；
程序运行时，在执行时暂时成为所属组；
和SUID类似；
针对目录：对目录需拥有r和x权限，才能进入此目录。
Sticky BIT：粘着位权限，只针对目录，对other的限制
普通用户需对文件有x和w权限。若拥有粘着位，只能删除自己创建的文件，不能删除其他人创建的文件。

8.3文件系统属性权限chatter权限
chattr命令格式：chattr \[+-=][选项] 文件或目录名 ，可添加可删除
i属性：对文件，不允许对文件进行删除，改名，也不能添加和修改数据。对目录，只能修改目录文件的数据，但不允许建立和删除文件。chattr +i abc
查看文件系统属性：lsattr 选项 文件名 ，-a 显示所有文件和目录，-d若目标时目录，列出目录本身的属性：lsattr -a abc 
a属性：对文件，只能在文件中添加数据，不能删除也不能修改。对目录，只允许建立和修改，不能删除。（相当于对现有数据加锁）

8.4系统命令权限 sudo
visudo实际上修改的是etc/sudoers
\# 用户名被管理主机的地址=(可使用的身份) 授权命令(绝对路径)
\# %组名 被管理主机的地址=(可使用的身份) 授权命令(绝对路径)
例：visudo sc ALL=/sbin/shutdown -r now
可以用whereis 文件名查看命令所在位置
用户查看特殊权限命令：sudo -l

## 文件系统管理

分区文件名：/dev/sda
9.2.1件系统常用命令
统计文件系统的占用情况：df \[选项][挂载点] 
-a显示全部，-h使用习惯单位
统计目录或文件大小：du \[选项][目录或文件名]
ls 统计目录大小只会统计文件名占用总大小
-s 只统计大小，不列出子文件大小
文件系统修复命令：fsck
fsck [选项] 分区设备文件名
-y：自动修复
显示磁盘状态命令dumpe2fs
dumpe2fs 分区设备文件名
9.2.2挂载命令
1.查询与设备挂载
查询已经挂载的文件：mount [-l]，-l会显示卷标名称
mount -a 按照/etc/fstab文件自动挂载
2挂载命令格式
mount [-t 文件系统] [-L 卷标名] [-o 特殊选项] 设备文件名 挂载点
9.2.3挂载光盘和U盘，需人为手工挂载
1.挂载光盘
mkdir  /mnt/cdrom/ \#建立挂载点
mount -t ios9660 /dev/cdrom/ /mnt/cdrom \#挂载光盘
mount /dev/sr0/ /mnt/cdrom/ 
2.卸载命令
unmount 设备文件名或挂载点
unmount /mnt/cdrom
3.挂载U盘
fdisk -l \#查看U盘设备文件名
mount -t vfat /dev/sdb1 /mnt/usb/








