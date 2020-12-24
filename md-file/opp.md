# static

一个类中的成员，可以是一个**实例成员**，也可以是一个**类范围成员**(static).

### 静态数据成员：

目的：为了解决数据共享的问题。多个类对象实现数据的互通。

即使对象不存在，静态数据成员也是存在的。
如有有很多对象，也只有一份。

可以看成一个全局变量，为何要封装在类中呢，为了限制该变量的作用范围，还有一个作用，就是增加可读性和可维护性。

```c++
///在.h声明文件当中声明静态数据成员
class A
{
	static int x;
	static int y;
};
///在.cpp类函数实现中进行初始化
int A::x = 5;
int A::y = 10;
```

静态数据成员的访问：
1：无需创建对象便可访问，如：A::x=5;
2:  在类的成员函数中可以访问，如：x=5或者A::x = 5;

### 静态成员函数：

说明：
1：静态成员函数没有宿主对象，所以不能用this
2：静态成员函数不能直接访问非静态数据成员
3：普通成员函数可以调用静态成员函数
非静态的数据成员肯定实在对象确定以后才有的，对象存在非静态数据才存在(对应第2条)。

### 注意

下面讲我觉得非常重要的一点，就是静态数据成员在类中，你可以认为是一个声明，就和函数一样，然后在定义文件.cpp文件中进行定义(并初始化)。

---

为了方便理解，给出全部代码。例子：通讯录条目，有姓名和电话两个数据成员。创建三个文件，主文件，声明文件，定义文件，下面是代码:

```c++
///main.cpp
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
    int iChoice =1;
    while (iChoice!=0){
    iChoice = displayMenu();
    switch (iChoice)
    {
        case 1:{
            c1.input();
            break;
        }
        case 2:{
            c1.output();
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
///definition.cpp
#include <string>
#include "declaration.h"
#include <iostream>
using namespace std;

string CommEntry::tel;   ///定义并且初始化
void CommEntry::input()
{
    cout<<"Name:";cin>>name;
    cout<<"Tel:"; cin>>tel;
}
void CommEntry::output()
{
    cout<<"Name:" <<name<<endl;
    cout<<"Tel:"  <<tel<<endl;
}
string CommEntry::getName()
{
   return name;
}
void CommEntry::setName(string nm)
{
    name = nm;
}
string CommEntry::getTel()
{
    return tel;
}
void CommEntry::setTel(string t)
{
    tel = t;
}
///declaration.h
#include <string>
using namespace std;
class CommEntry
{
    public:
        string name;
        static string tel; ///前向声明

        void input();
        void output();

        string getName();
        void setName(string nm);

        static string getTel();
        static void setTel(string ow);
};


```



















