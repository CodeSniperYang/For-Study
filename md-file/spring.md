# spring

1、https://blog.csdn.net/weixin_45496190/article/details/107059038
2、https://blog.csdn.net/weixin_45496190/article/details/107067200
3、https://blog.csdn.net/weixin_45496190/article/details/107071204
4、https://blog.csdn.net/weixin_45496190/article/details/107082732
5、https://blog.csdn.net/weixin_45496190/article/details/107092107

大佬笔记，非本人所写

此篇主要记录坑点，给自己长个记性

### 1------

第一个spring程序总结 : (万事总是开头难)

```
做的东西很简单，就是用spring的方法创建一个对象，不适用new，但是折腾了好一会儿（用的eclipse）
1.com.yy包和bean1.xml位于同一级目录下，都在src下
src
	com.yy
		SpringText.java
		User.java
	bean.xml
2.Spring下：
ApplicationContext context = 
				new ClassPathXmlApplicationContext("bean1.xml");
3.bean1.xml
<bean id="user" class="com.yy.User"> </bean>
```

### 2------

##### set方式注入：

在xml配置文件中：

```xml
<bean id="user" class="com.yy.User">
  <property name="name" value="yy"> </property>
  <property name="age" value="20"> </property>		
</bean>
```

遇到的坑点：

​	1.即使age变量是int型，value也要加上双引号
​	2.User.java文件中要写set方法

### 3------

##### 使用有参构造注入：

```xml
<bean id="user" class="com.yy.User">
  <constructor-arg name="name" value="yy"> </constructor-arg>			
  <constructor-arg name="age" value="20">  </constructor-arg>	
</bean>
```

遇到的坑点：
	1.被笨死了，标签没有加/

User.java中需要写User的有参构造函数

### 4------

#### 

 



















