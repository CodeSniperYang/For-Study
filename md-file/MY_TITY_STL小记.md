# MY_TITY_STL小记

这个链接可能对你有用：[小站入口](https://codesniperyang.github.io/2020/06/30/c++newfeatures/) [CSDN入口](https://blog.csdn.net/qq_43693424/article/details/107052139) 

### 泛型编程代码简写

```c++
template<typename T1,typename T2>  //（1）
class Complex{};

template<>
class Complex<double,double>{}; //特化

template<typename T>
class Complex<T,float>{};//偏特化

template<typename T,bool>    //（2）
class judege{};

template<typename T>
class judege<T,false>{};//偏特化

// 萃取迭代器的特性
template <class Iterator>   //（3）
struct iterator_traits
  : public iterator_traits_helper<Iterator, has_iterator_cat<Iterator>::value> {};
// 针对原生指针的偏特化版本
template <class T>
struct iterator_traits<T*>
{
  typedef random_access_iterator_tag           iterator_category;
  typedef T                                    value_type;
  typedef T*                                   pointer;
  typedef T&                                   reference;
  typedef ptrdiff_t                            difference_type;
};

```

无论特化还是偏特化，前面应该具有一个特化父类(我是这么叫的)，便于后面特化，偏特化版本的写成
对于特化，template<>无参数，在类后面接尖括号<>，里面写上对应类型的参数
对于偏特化，template<>有部分参数，已经指定的类型就不用写在里面了。在类后面的尖括号，里面写上对应的参数。
对于（3），看着挺简单，不过还是挺有意思的。iterator_traits的父类有模板参数，所以要写template <class Iterator>。由于继承，后面写子类的时候就不用加template了。只是指定了一个T的模板参数。



### pair的运算符重载简计

```c++
/*************************重载运算符****************/
template <class Ty1, class Ty2>
bool operator==(const pair<Ty1, Ty2>& lhs, const pair<Ty1, Ty2>& rhs)
{
  return lhs.first == rhs.first && lhs.second == rhs.second;
}

template <class Ty1, class Ty2>
bool operator!=(const pair<Ty1, Ty2>& lhs, const pair<Ty1, Ty2>& rhs)
{
  return !(lhs == rhs);
}

template <class Ty1, class Ty2>
bool operator<(const pair<Ty1, Ty2>& lhs, const pair<Ty1, Ty2>& rhs)
{
  return lhs.first < rhs.first || (lhs.first == rhs.first && lhs.second < rhs.second);
  //优先比较first
}

template <class Ty1, class Ty2>
bool operator>(const pair<Ty1, Ty2>& lhs, const pair<Ty1, Ty2>& rhs)
{
  return rhs < lhs;
}

template <class Ty1, class Ty2>
bool operator<=(const pair<Ty1, Ty2>& lhs, const pair<Ty1, Ty2>& rhs)
{
  return !(rhs < lhs);
}

template <class Ty1, class Ty2>
bool operator>=(const pair<Ty1, Ty2>& lhs, const pair<Ty1, Ty2>& rhs)
{
  return !(lhs < rhs);
}
/*************************重载运算符****************/
```

重载==之后，重载!=可以直接利用==。
重载>之后，重载<,<=,>=可以利用>的重载。
如果不是看到源码，我可能会傻乎乎的一个一个写，而不会利用已经重载的运算符，这里给我一个小提示。在类的swap等函数中都用了这种小技巧，第一次见到，还是记录一下。
若问T1，T2是一个类pair无法重载怎么办，这需要在该类中重载运算，这不是pair应该纠结的事情。



### 右值引用简记

```c++
template <class Ty>
void construct(Ty* ptr)
{
  ::new ((void*)ptr) Ty();
}

template <class Ty1, class Ty2>
void construct(Ty1* ptr, const Ty2& value)
{
  ::new ((void*)ptr) Ty1(value);
}	
```

在construct.h中看到这么一串代码，这里说一下Ty()和Ty(value)，你可以认为是两个临时对象。第一个有默认值，第二个有一个初值。
对于类而言，就是一个临时对象。如果是一个数据类型，定义了一个右值对象。
我在编译器下测试如下：

```c++
class C
{
    int x,y;
};
void fun(int &&x)
{
    cout<<"右值引用"<<endl;
}
int main()
{
    C c;
    cout<<sizeof(c)<<endl;    //    8
    cout<<sizeof(C)<<endl;    //    8
    cout<<sizeof( C() )<<endl; //   1
    cout<<sizeof(int())<<endl;//    1
    fun( int() );             //右值引用
    return 0;
}

```

对于构造的临时对象，按道理是不会有内存空间的，但是这里要注意的是，输出的结果是1。
对于int() ，如果输出，值为0，这就是它的默认值。对于字符串，默认值为空串。



### iterator.h  简析

[源码地址入口](https://github.com/Alinshans/MyTinySTL/blob/master/MyTinySTL/iterator.h) 

```c++
// 五种迭代器类型
struct input_iterator_tag {};
struct output_iterator_tag {};
struct forward_iterator_tag : public input_iterator_tag {};
struct bidirectional_iterator_tag : public forward_iterator_tag {};
struct random_access_iterator_tag : public bidirectional_iterator_tag {};
//不熟知的类 前面的是父类，箭头后面是子类
iterator
has_iterator_cat
iterator_traits_impl(有bool不同的两个版本)->iterator_traits_helper(有bool不同的两个版本)  -> iterator_traits(有偏特化的多个版本)

|   m_bool_constant(type_traits.h文件中的类) -> has_iterator_cat_of     |
|   m_false_type(type_traits.h文件中的类)    -> has_iterator_cat_of		 | 

has_iterator_cat_of -> is_input_iterator
									  -> is_output_iterator
									  -> is_forward_iterator
									  -> is_bidirectional_iterator
									  -> is_random_access_iterator(五个子类)
									  
m_bool_constant -> is_iterator
```

上面是对类关系的一些提炼，不然要被各种类搞的玩迷宫一样。

在这里举一个例子来说明萃取机：

```c++
// 萃取某个迭代器的 value_type
template <class Iterator>
typename iterator_traits<Iterator>::value_type*
value_type(const Iterator&)
{
  return static_cast<typename iterator_traits<Iterator>::value_type*>(0);
}
/*  传进去一个迭代器value_type(const Iterator&)，问这个迭代器的value是什么类型啊？
iterator_traits做出解释：   */
template <class T>
struct iterator_traits<T*>
{
  typedef random_access_iterator_tag           iterator_category;
  typedef T                                    value_type;
  typedef T*                                   pointer;
  typedef T&                                   reference;
  typedef ptrdiff_t                            difference_type;
};
/*  哦，我知道了，原来是T啊！  */
/*对于typedef可以看成是一问一答的形式，一层一层往上问，最底层的类给出答案，然后一层一层又传到疑问这里。  
typedef A B ，问A的类型，这时候代码往上看，底层是什么，那么A就是什么。*/
```

对于计算迭代器间的距离，重载两个函数，一个是一步一步不能跳的，一个是可以跳的

```c++
// distance 的 input_iterator_tag 的版本
template <class InputIterator>
typename iterator_traits<InputIterator>::difference_type
distance_dispatch(InputIterator first, InputIterator last, input_iterator_tag)
{ 																						//Input
  typename iterator_traits<InputIterator>::difference_type n = 0;
  while (first != last)
  {
    ++first;
    ++n;
  }
  return n;
}
// distance 的 random_access_iterator_tag 的版本
template <class RandomIter>
typename iterator_traits<RandomIter>::difference_type
distance_dispatch(RandomIter first, RandomIter last, //Random
                  random_access_iterator_tag)
{
  return last - first;
}
//根据迭代器的类型选择哪个函数，根据函数的参数判断。

//advance差不多，多了个BidirectionalIterator，它可以后退，输入负数是可以的。
```

### **functional.h**

```c++
template <class T>
struct plus :public binary_function<T, T, T>
{
  T operator()(const T& x, const T& y) const { return x + y; }
};
//仿函数的写法   示例
```

在此文件中，有很多的仿函数，定义一个struct,在里面重载运算符，这就是仿函数了。
自己简单的写了一个，自我学习。

```c++
template<typename T>
struct myplus
{
    T operator() (const T& a,const T& b) const noexcept  {return a+b;}
};

int main()
{
    myplus<int> mp;
    int x = mp(5,15);
    cout<<x<<endl;   //20
    return 0;
}
```

