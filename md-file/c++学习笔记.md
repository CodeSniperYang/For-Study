# c++深入学习笔记 第3版

**这里有一些是新特性，有一些是为了描述新特性另外说明的一些东西。**
还有一些觉得觉得很不错的文章，权当自己学习c++以来的收获吧~

---

### c++三种继承方式-基础

[参考网址入口](https://mp.weixin.qq.com/s?src=11&timestamp=1593777865&ver=2438&signature=yRA3qiIUtlvA0cHwOiOyFc1GgOOeB2-fXE3HXcKVoviHORdMTTT*WP2pHJlD0amhyx17OI9gKv3Fxy5xydmz8qop8PdAAz1fukjJHzDNwH7udcgUnt7rxFc1pBamQSZ0&new=1) 
这篇文章对三种继承方式讲的很明白，感觉自己多说一个字都不对劲。
成员默认是private，继承默认也是private。

### 虚函数和纯虚函数-基础

[参考网址入口](https://mp.weixin.qq.com/s?src=11&timestamp=1593777513&ver=2438&signature=Bnaw-yNh*k-iI22VOzvTH1BccPyy9wby4QLRG30SEf3uDti9DlDwmb8I5jWxzUfsh7hXtzB1bXRgZHPmqtMATmLMctJt3HlCM*H18jK6u7uoJs-kOt-kPtRtu4yWMiQB&new=1) 基类中的virtual...=0，为纯虚函数，否则为虚函数。

```c++
class Person
{
    virtual void func() = 0;
};
class Student : public Person
{

};
int main()
{
    Person P;//error
    Student stu;//error    定义了纯虚函数的类称为抽象类，抽象类不能被实例化
    return 0;
}
```

```c++
class Allthing{};
class Person:public Allthing
{
    virtual void func() = 0;
};
class Student : public Person{};  //虚函数可以继承也可以被继承
```

在文章的总结2有一段话：虚函数必须实现，如果不实现，编译器将报错。
感觉不能理解，不知道这句话是在何种情况下是对的。对于上面的代码来说，既然抽象类可以被继承，那么子类是没有实现虚函数的，也没有报错。

### static

[参考网址入口](https://www.cnblogs.com/songdanzju/p/7422380.html) 优先看
[参考网址入口]([https://blog.csdn.net/ypshowm/article/details/89030194?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522159378122219724811822346%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=159378122219724811822346&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~baidu_landing_v2~default-1-89030194.first_rank_ecpm_v3_pc_rank_v2&utm_term=staticc%2B%2B](https://blog.csdn.net/ypshowm/article/details/89030194?ops_request_misc=%7B%22request%5Fid%22%3A%22159378122219724811822346%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=159378122219724811822346&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~baidu_landing_v2~default-1-89030194.first_rank_ecpm_v3_pc_rank_v2&utm_term=staticc%2B%2B)) 深入了解
**extern** int a; 并不是像 int a; 一样在内存开辟空间，语义解读可为 在其它文件中引入int型变量a

```c++
class Student
{
    //static int x = 5; c++不允许在类中初始化static对象
public:
    static int x;   
};
int Student::x = 99;//初始化
int main()
{
    Student stu;
    cout<<stu.x<<endl;
    return 0;
}
```



### Variadic Templates  ...

[参考网址入口](https://blog.csdn.net/zwvista/article/details/5450759?locationNum=11&fps=1)
这个好像和Java的类似，需要注意的是新名词的出现，//模板参数包，函数参数包，解包运算符；
实现方式是递归。

## forward

**请先看下面的右值引用**
[参考网址入口](https://www.cnblogs.com/xiaoshiwang/p/9589008.html)
写法:**forward<T>(t)**,其中t是T类型的对象。
在这之前请先了解**左值引用**和**右值引用**。
举例：func(100);//实参为右值引用。
还有一个**move**函数，就是转化为右值的。move() 

## 右值引用

[参考网址入口](https://blog.csdn.net/qq527703883/article/details/60141034?locationNum=7&fps=1)
我感觉就是看这个 总的语句是否分配了空间 ，没有分配就是右值引用
[B站侯捷老师的视频](https://www.bilibili.com/video/BV1n4411R7q9?p=23) ，这个视频对于的右值引用做了十分详细的讲述，后面还有move和forward的讲解，很深入。强力推荐。

## nullptr nullptr auto

这三个东西挺简单的，前两个之间看源码
auto是一个很好用的东西，让编译器推导变量类型。
示例：for(vector<int>::iterator it=v.begin();it!=v.end();it++)可以写成
：for(auto it=v.begin();it!=v.end();it++)

## C++11下的初始化

为了统一化管理，引入**{}花括号**初始化。
**initializer_list**：这个东西可以支持我们初始化的花括号使用
在使用vector<int> vec1{ 1,2,3,4,5 };时，会自动生成initializer_list，initializer_list内含一个array的头指针和长度
[参考网址入口](https://mp.weixin.qq.com/s?src=11&timestamp=1593334114&ver=2427&signature=Dly5GAhXZ8v*vMicn54oEe8jlhPoK-5GTLlA-*VpY1On1J9l7VsvuqLU3jQvsigf3QKraAujVskn6yZqi3kVLccyK6PdBbpOK44zhhCQGa-UGxUSKkNnHNEEMkbGycql&new=1)
**注意：**感觉讲的很好。但是我认为有一个错误，源码是没有内含数组的，只是拿了一个指针和长度进来。可能版本不同把，我就没有深究了。对于它的使用标准库真是大量用了。

```c++
    int a;cin>>a;
    int b=2;
    int c=3;
    int d=max( {a,b,c}  );//香  发现新大陆啊
    cout<<d<<endl;
    return 0;
```

## explicit

多用于构造函数中，explicit构造函数是用来防止隐式转换的。很小的一个东西，看之前如果知道**类对象的隐式转换**和**显示转换**就很容易懂了。

### 新for循环

for( decl : coll ){ statement}
搭配auto香的一批，该有全局的begin()和end(),参数放容器。
[入口](https://www.jb51.net/article/87283.htm) 这里有它的底层源码，底层还是用了常用的for，穿入头指针和尾指针，头指针++，当头指针等于尾指针时退出循环。右值引用，for结束则析构。

### =default ，=delete

[参考网址入口](https://www.cnblogs.com/Li-F/p/11517192.html
https://blog.csdn.net/u010640235/article/details/51286445)
在写代码的时候，'='是不能省略的，和两个关键字是一个整体，或者说，这就是一个关键字。
生成类的时候，会自动调动四种函数，=delete会禁用删除当前函数，=dafault用于显式要求编译器提供合成版本的四大函数。

```c++
class Text
{
private:
    int x,y;
};
int main()
{
    Text t1;
    Text t2(t1);
    Text t3;
    t3 = t1;
    return 0;
}   //当创建一个类的时候，编译器会自动创建四种函数，构造，析构，拷贝，复制。
```

## Alias template

用using定义别名，这个和typedef类似，一个最大的不同是using可以别名化模板。

```c++
template<typename T>
using Vec = std::vector<T, MyAlloc<T>>;//给一个化名
Vec<int> coll;
//代码摘自侯捷老师的课程
```

### Template Template Parameter

模板模板参数，这个感觉用的很少，想深究的自己去看。

### Type Alias

使用using定义别名，和typedef功能一样
示例：using func = void(*)(int,int);
扩充，在string头文件中，都有basic_string<char> string;
basic_string的源码在它的.h文件里面，三千多行，我就不看了。

### noexcept

[参考网址入口](https://blog.csdn.net/xiaoguyin_/article/details/79798544)
当函数不会有异常的时候，是应只当noexcept，并且是在关键字实在函数的声明或者是定义之后的。
对于异常，侯捷老师有专门的一讲课，实际开发中异常处理经常出现，值得深究。

### **override**

[参考网址入口](https://blog.csdn.net/fanyun_01/article/details/79122136)
当父类的虚函数在子类中实现的时候使用，可以避免派生类中忘记重写虚函数的错误
当参数不一致时，编译器会报错给予提示

### final

表明当前类是不可继承的。
说白了就是当前类是断子绝孙的，只有爸爸，没有儿子。

另外，**函数后面加上const**，[参考网址入口](https://wenwen.sogou.com/z/q825346133.htm)
说明当前函数为只读操作

### decltype

http://c.biancheng.net/view/3722.html 	 这篇讲的比较深入
编译器自行推导类型。
[这篇的使用比较直观](https://mp.weixin.qq.com/s?src=11&timestamp=1593481824&ver=2431&signature=HGwbJf9FtH*mOqjDEWs-9VBW5M8RQ*z8HWw8y54yjODega8bIsAPmlJ4etvhIcg*AQ9fb8PowCsjNhMnmlL7b2Ep1MFqArwTWhIpg32mjCIfwkGceVrSwIK46ZRwfySe&new=1)

### lambda

[csdn参考地址]([https://blog.csdn.net/u012198575/article/details/83339182?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522159348755419195239812644%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=159348755419195239812644&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~baidu_landing_v2~default-1-83339182.first_rank_v2_rank_v25&utm_term=c%2B%2B+lambdas](https://blog.csdn.net/u012198575/article/details/83339182?ops_request_misc=%7B%22request%5Fid%22%3A%22159348755419195239812644%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=159348755419195239812644&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~baidu_landing_v2~default-1-83339182.first_rank_v2_rank_v25&utm_term=c%2B%2B+lambdas)) 
[B站侯捷老师视频](https://www.bilibili.com/video/BV1p4411v7Dh?p=14) 
知道语法即可，深入请自行了解。

### tuple

[csdn参考连接](https://blog.csdn.net/sevenjoin/article/details/88420885?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase) 
可以看作是一个结构体或者是泛化的pair，只不过配备了很多的函数进行操作。

### **enable_if** 类

[参考网址入口](https://msdn.microsoft.com/zh-cn/vsto/ee361639(v=vs.80)) 这是我在看pair可能到的，类模板一大串。

```c++
  // implicit constructiable for this type
  template <class U1 = Ty1, class U2 = Ty2,
    typename std::enable_if<
    std::is_copy_constructible<U1>::value &&
    std::is_copy_constructible<U2>::value &&
    std::is_convertible<const U1&, Ty1>::value &&
    std::is_convertible<const U2&, Ty2>::value, int>::type = 0>
    constexpr pair(const Ty1& a, const Ty2& b)
    : first(a), second(b)
  {
  }
```

**is_convertible** 和 **is_copy_constructible** [参考网址入口](https://blog.csdn.net/weixin_37910058/article/details/98200716)
在pair中有很多类，上面是两个例子。

---

用于给各位学习者参考，仅供参考。

也是对自己的学习记录---