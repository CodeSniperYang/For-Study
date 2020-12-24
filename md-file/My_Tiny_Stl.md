因为对STL的应用不太熟练，干脆就干TA。在B站找了侯捷老师的视频听他讲了讲源码，不过大部分还是没有细讲，不过也差不多了。知道了一些容器的实现过程，虽然没有一行行的讲，但是这也是不可能的。然后再github上找了一个实现my_tiny_stl的小项目准备自己研究研究。
暑假估计这个事情就差不多了，如果还有时间，当然是看看SQL数据库了。
记录一下学习到的东西，**泛型编程**，大量的**typedef**，还有**常量**的使用得仔细研究研究。

### 关键字或者函数

#### static_cast

使用static_cast可以明确告诉编译器，这种损失精度的转换是在知情的情况下进行的，也可以让阅读程序的其他程序员明确你转换的目的而不是由于疏忽。

```c++
double a = 1.999;
int b = static_cast<double>(a); //相当于a = b ;
```

**static_assert()**
https://blog.csdn.net/jiayizhenzhenyijia/article/details/98175488
函数有两个参数，若第二个参数为假，提示第二个参数的信息

**constexpr关键字**

https://www.zhihu.com/question/274323507
https://blog.csdn.net/qq_36328643/article/details/62069867?locationNum=7&fps=1
总结起来就是榨干编译器，在编译的时候经进行，可以实现编译期的判断。

**remove_reference()**
https://www.cnblogs.com/tekkaman/p/10190895.html
用于移除类型的引用，返回原始类型。

**typename关键字**
https://blog.csdn.net/zjcxhswill/article/details/50631059?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-1
觉得这篇blog对此关键字讲的很明白了。基本是两个场合的使用

**move()**
https://www.cnblogs.com/xiaoshiwang/p/9582325.html
转化为右值引用

**forward()**
https://www.cnblogs.com/xiaoshiwang/p/9589008.html
保持住实参的类型。

**左值引用和右值引用**
https://zhuanlan.zhihu.com/p/97128024
https://blog.csdn.net/icefireelf/article/details/5724392
觉得第二篇讲的深入一点。
int &var = 10;无法通过编译，所以11引入右值引用int &&var = 10;这样是OK的。

**size_t**
通常我们用sizeof(XXX)操作，这个操作所得到的结果就是size_t类型。解决可移植性问题。
typedef \__SIZE_TYPE__ 	size_t;

**enable_if**
简单的来说就是当模板参数B为true时定义一个类型type（它的类型为T)

**delete和default**
引入的新特性
https://zhuanlan.zhihu.com/p/77806109

**我知识简单的看了一下，没有想到新特性这么多，我就直接看视频了**
https://www.bilibili.com/video/BV1p4411v7Dh?from=search&seid=16890398216726903147
B站的。

# alloc.h

alloc的详解参见：**https://blog.csdn.net/qq_39268442/article/details/78890315**
对于频繁使用malloc会产生内存碎片(内部碎片和外部碎片)，使用内存池的可以有效避免内存碎片，https://zhinan.sogou.com/guide/detail/?id=316512610801
内存池的意义和简单原理：https://www.cnblogs.com/bangerlee/archive/2011/08/31/2161421.html
inline：内联扩展是用来消除函数调用时的时间开销。它通常用于频繁执行的函数。一个小内存空间的函数非常受益。提高性能。

## type_traits.h





