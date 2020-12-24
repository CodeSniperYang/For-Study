#  运算符重载

对于运算符的重载，主要有两种方式:**友元**和**成员函数**
@表示某一种操作符。 
 以下有**+ - \* / >> <<** 的用法，其他没有。

----

### 对于 + - * / 的重载

在这四个例子中，我**串入两种重载方法的使用**。
以**复数**作为例子，定义**实部**和**虚部**两个数据成员。没有学复数的读者可以认为就是两个float变量，后面只是一些计算。

**友元方法**的声明： + * 举例

```c++
friend Complex operator+(Complex &,Complex &);/// +
friend Complex operator*(Complex &,Complex &);/// *
```

定义：

```c++
Complex operator+(Complex &a,Complex &b) /// +
{
    Complex c;
    c.r = a.r+b.r;
    c.i = a.i+b.i;  ///定义类c，进行运算
    return c;
}
Complex operator*(Complex &a,Complex &b) /// *
{
    Complex c;
    c.r = a.r*b.r-a.i*b.i;
    c.i = a.r*b.i+a.i*b.r;
    return c;
}
```

使用：

```c++
Complex a,b;
// ***省略赋值语句
a = a + b;
a = a * b;///相加之后赋给a，直接覆盖掉
```

对于`friend Complex operator+(Complex &,Complex &);`，我们忽略掉friend和&简化得到
`Complex operator+(Complex,Complex;。可以看成是有两个参数的一个函数，返回类Complex。`
第一个Complex你可以看成是int，void这样的返回类型，不过这是类，不是基础数据类型。
用友元的方法，看上去比较**整洁**，是几目运算符，形参就有几个。
定义的时候，在声明的基础上去掉friend。

----

**成员函数方法**的声明： - / 举例

```c++
Complex operator-(const Complex &b);/// -
Complex operator/(const Complex &b);/// /
```

定义：

```c++
Complex Complex::operator-(const Complex &b)
{
    Complex c;
    c.r = this->r-b.r;
    c.i = this->i-b.i;
    return c;
}
Complex Complex::operator/(const Complex &b)
{
    Complex c;
    if(b.r*b.r+b.i*b.i==0)
    {
        cout<<"分子为0,不能相除，a的值不变"<<endl;
        c.r = this->r;
        c.i = this->i;  ///不能相除，a的值不发生变化，返回a原来的值
    }
    else
    {
        c.r = (this->r*b.r+this->i*b.i)/(b.r*b.r+b.i*b.i);
        c,i = (this->i*b.r-this->r*b.i)/(b.r*b.r+b.i*b.i);
    }                    ///计算结果我没有仔细看了
    return c;
}
```

声明：对于`Complex Complex::operator-(const Complex &b),`定义的时候，因为是成员函数，所有要遵循类函数定义规则。先是返回类型，然后接类名::，然后是operator@，n目运算符，后参数为n-1.
定义：在定义中，肯定有人发现了这个，**this**，之前我也被这个整蒙了，在这里你只需要知道，它就相当与友元当中a就完了。this->r,this>i分别表示a.r,a.i。不过还是要说明的是，参数带了个&，表示的是引用(我是理解为C里面的指针的)，对于指针，那就是用箭头了啊。用**.**的情况我只在学Java见识过。
**函数参数的const表示当前对象的值不会被改变**

----

### 对于输入输出>>和<<的重载方法：

在类CommEntry中有两个私有数据成员name和tel，分别是电话(string)和姓名(string)
在类中声明：

```c++
friend ostream& operator<<(ostream&, CommEntry&);
friend istream& operator>>(istream&, CommEntry&);
```

对于函数的实现：

```c++
istream& operator>>(istream& in,CommEntry& c )
{
    cout<<"Name:";in>>c.name;
    cout<<"Tel:";  in>>c.tel;
    return in;
}
ostream& operator<<(ostream& out,CommEntry& c )
{
    out<<"Name:"<<c.name<<endl;
    out<<"Tel:" <<c.tel <<endl;
    return out;
}
```

调用：

```c++
CommEntry c1;
cin>>c1;
cout<<c1;
```

自己的想法：
我们忽略friend和&，那么就变成了 `istream operator<<(istream in, CommEntry c);`
第一部分：对于第一个出现的istream来说，其实就是返回类型，和成员函数的声明一个意思，相当于写了个void或者int。
第二部分：之后是固定的写法，operator@。
第三部分：在括号中是两个形参，我觉得可以把istream可以int一样的东西，不过是一个类对象。然后接一个名字，后面是类对象加名字，记得两个都是引用，需要加上&。
注意，所有的都是引用，前面有个&，look到没~(不能理解什么是引用？你可以比作是c里面的指针)

经验和教训：
1.我们只能以自定义类的**友元函数的形式**重载这两个运算符
2.对于输入，形参必须为非 const，因为输入操作符的目的是将数据读到这个对象中。
3.在类声明和定义文件中不需要#include\<iostream>，写了也没有什么关系。

----



### 为了方便理解，我把全部的代码发了！

读者自己创建三个文件，运行理解一下

```c++
///main.cpp
#include <iostream>
#include <string>
#include "declaration.h"
#define cin_b; cout<<"b.r:";cin>>b.r;cout<<"b.i:";cin>>b.i;
#define cin_a; cout<<"a.r:";cin>>a.r;cout<<"a.i:";cin>>a.i;
using namespace std;
int displayMenu()
{
    cout << "===================="<<endl;
    cout << "1. 输入复数"         <<endl;
    cout << "2. 查看输入的复数"   <<endl;
    cout << "3. 复数相加"         <<endl;
    cout << "4. 复数相减"         <<endl;
    cout << "5. 复数相乘"         <<endl;
    cout << "6. 复数相除"         <<endl;
    cout << "7. 输出结果"         <<endl;
    cout << "0. 退出"             <<endl;
    cout << "===================="<<endl;
    cout << "请输入操作数："      <<endl;
    int iChoice;
    cin>>iChoice;
    return iChoice;
}
int main()
{
    Complex a,b;
    int iChoice =1;
    while (iChoice!=0)
    {
        iChoice = displayMenu();
        switch (iChoice)
        {
            case 1:{
                cin_a; ///宏定义
                break;
            }
            case 2:{
                a.output();
                break;
            }
            case 3:{       /// +
                cin_b;
                a = a + b;
                break;
            }
            case 4:{       /// -
                cin_b;
                a = a - b;
                break;
              }
            case 5:{       /// *
                cin_b;
                a = a * b;
                break;
            }
            case 6:{       /// /
                cin_b;
                a = a / b;
                break;
            }
            case 7:{    
                a.output();
                break;
            }
            case 0:{
                break;
            }
        }
    }
    return 0;
}
///definition.cpp
#include <iostream>
#include "declaration.h"
using namespace std;
Complex::Complex()
{
    r = i =0;
}
void Complex::input(int r,int i)
{
    this->r = r;
    this->i = i;
}
void Complex::output()
{
    cout<<"r:"<<r<<endl;
    cout<<"i:"<<i<<endl;
}
Complex operator+(Complex &a,Complex &b) /// +
{
    Complex c;
    c.r = a.r+b.r;
    c.i = a.i+b.i;
    return c;
}
Complex operator*(Complex &a,Complex &b) /// *
{
    Complex c;
    c.r = a.r*b.r-a.i*b.i;
    c.i = a.r*b.i+a.i*b.r;
    return c;
}
Complex Complex::operator-(const Complex &b)
{
    Complex c;
    c.r = this->r-b.r;
    c.i = this->i-b.i;
    return c;
}
Complex Complex::operator/(const Complex &b)
{
    Complex c;
    if(b.r*b.r+b.i*b.i==0)
    {
        cout<<"分子为0,不能相除，a的值不变"<<endl;
        c.r = this->r;
        c.i = this->i;
    }
    else
    {
        c.r = (this->r*b.r+this->i*b.i)/(b.r*b.r+b.i*b.i);
        c,i = (this->i*b.r-this->r*b.i)/(b.r*b.r+b.i*b.i);
    }
    return c;
}
///declaration.h
#include <iostream>
using namespace std;
class Complex
{
    public:
        float r;
        float i;
        Complex();
        void input(int ,int);
        void output();
        friend Complex operator+(Complex &,Complex &);/// +
        friend Complex operator*(Complex &,Complex &);/// *
        Complex operator-(const Complex &b);/// -
        Complex operator/(const Complex &b);/// /
};
```



```c++
///main.cpp   ///主文件 重载了>> <<
#include <iostream>
#include <string>
#include "declaration.h"
using namespace std;

int displayMenu()
{
    cout << "==================="<<endl;
    cout << "1.输入通讯录条目"   <<endl;
    cout << "2.输出通讯录条目"   <<endl;
    cout << "3.修改姓名"         <<endl;
    cout << "4.修改电话"         <<endl;
    cout << "0.退出"             <<endl;
    cout << "==================="<<endl;
    cout << "请输入操作数："     <<endl;
    int iChoice;
    cin>>iChoice;
    return iChoice;
}
int main()
{
    CommEntry c1;

    int iChoice =1;   ///获得操作数
    while (iChoice!=0){
    iChoice = displayMenu();
    switch (iChoice)
    {
        case 1:{
            cin>>c1;///c1.input();
            break;
        }
        case 2:{
            cout<<c1;///c1.output();
            break;
        }
        case 3:{
            string nm;
            cout<<"请输入修改之后的名字:";
            cin>>nm;
            c1.setName(nm);
            break;
        }
        case 4:{
            string t;
            cout<<"请输入修改之后的电话：";
            cin>>t;
            c1.setTel(t);
            break;
          }
        case 0:
            break;
       }
    }
    return 0;
}
///declaration.h   函数声明文件
#include <string>
#include <iostream>
using namespace std;
class CommEntry
{
    friend ostream& operator<<(ostream&, CommEntry&);
    friend istream& operator>>(istream&, CommEntry&);
    /*error w  错误的写法
    friend istream operator>>(CommEntry& c);
    friend ostream operator<<(CommEntry& c);
    */
    public:
        void input();
        void output();
        void setName(string nm);
        void setTel(string t);
        string getName();
        string getTel();
    private:
        string name;
        string tel;
};
///definition.cpp   函数定义文件
#include <string>
#include "declaration.h"
#include <iostream>
using namespace std;
void CommEntry::input()
{
    cout<<"Name:";  cin>>name;
    cout<<"Tel:";   cin>>tel;
}
void CommEntry::output()
{
    cout<<"Name:"<<name<<endl;
    cout<<"Tel:" <<tel<<endl;
}
void CommEntry::setName(string nm)
{
    name = nm;
}
void CommEntry::setTel(string t)
{
    tel=t;
}
string CommEntry::getName()
{
   return name;
}
string CommEntry::getTel()
{
    return tel;
}
istream& operator>>( istream& in,CommEntry& c )
{
    cout<<"Name:";in>>c.name;
    cout<<"Tel:";  in>>c.tel;
    return in;
}
ostream& operator<<( ostream& out,CommEntry& c )
{
    out<<"Name:"<<c.name<<endl;
    out<<"Tel:" <<c.tel <<endl;
    return out;
}

```





本文只是自己的理解，免责保护~~~

如有错误，还请指出~~~