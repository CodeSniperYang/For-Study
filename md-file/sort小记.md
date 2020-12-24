# STL-sort学习

学习sort发现不错的文章，摘录一下。
并且做一个自己的理解，默认的sort下排序，即升序。

### 前景知识 三种排序

**快速排序**：[视频入口](https://www.bilibili.com/video/BV1a741127ym?from=search&seid=5164983531185707913) B站的视频，简单的讲了一下快排，并且手把手的带你简单的实现了一遍。
优化：[优化参考1](https://blog.csdn.net/insistGoGo/article/details/7785038?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.nonecase) [优化参考2](https://blog.csdn.net/lingling_nice/article/details/80943231?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase)  
1.三数取中法，在头元素，尾元素，中间元素上，取得中位数。
2.随机选取基准，用随机数代替基准点。
3.插排的引入，当递归到一定程度后，last和first中间元素个数不多，并且已经基本有序，改用插排。

```c++
int n = 1000,a[1000];
void swap(int i,int j)
{
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
}
void quick_sort(int l,int r)
{
    if(l>r) return ;
    int i = l,
        j = r,
        index = l;
    while(i!=j)
    {
        while(i<j&&a[j]>=a[index]) j--;
        while(i<j&&a[i]<=a[index]) i++;
        if(i<j) swap(i,j);
    }
    swap(index,i);
    quick_sort(l,i-1);
    quick_sort(i+1,r);
}
int main()
{
    srand(time(NULL));
    for(int i=0;i<n;i++)
        a[i] = rand();
    quick_sort(0,n-1);

    for(int i=0;i!=n;i++)
        cout<<a[i]<<endl;

    return 0;
}
```

---

**插入排序**：很简单的东西，要多说一点的是，
它在数据大致有序的情况表现非常好，可以达到O(N)。
[参考入口地址]([https://blog.csdn.net/zxm317122667/article/details/83344178?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522159387384419726869032198%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=159387384419726869032198&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_click~default-3-83344178.first_rank_ecpm_v3_pc_rank_v2&utm_term=%E6%8F%92%E5%85%A5%E6%8E%92%E5%BA%8F](https://blog.csdn.net/zxm317122667/article/details/83344178?ops_request_misc=%7B%22request%5Fid%22%3A%22159387384419726869032198%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=159387384419726869032198&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_click~default-3-83344178.first_rank_ecpm_v3_pc_rank_v2&utm_term=插入排序)) 虽然笔者是Java的，不过丝毫不影响对思路的理解

```c++
int a[1000];
int len = 1000;

void insert_sort()
{
    for(int i=1;i<len;i++)
    {
        int key = a[i]; //选取基准值
        int j = i-1;
        while (j>=0 && a[j] > key)  
        {
            a[j+1] = a[j];    //往后移动
            j--;
        }
        a[j+1] = key;   //选取基准值的位置并且插入
    }
}

int main()
{
    srand(time(NULL));
    for(int i=0;i<len;i++)
        a[i] = rand();    //输入

    insert_sort(); 				//插排

    for(int i=0;i<len;i++)
        cout<<a[i]<<endl;  //输出
    return 0;
}

```



---

**堆排序**：[B站视频入口](https://www.bilibili.com/video/BV1Eb41147dK?from=search&seid=11348061540197705082) 讲得很好，也是一样，讲完原理之后敲代码了。

```c++
int n=1000,tree[1000] = {1,7,4,5,8,9,6,3,2,0};
void heapify(int n,int pa) //有n个节点，对第i个节点做heapify
{													//在第i个节点进行调整，在i的左右儿子中选出最大，进行交换
    if(pa>=n) return ;		//如果左儿子大，交换。然后在左儿子中的孩子中选出最大，重复下去
    int lson = 2*pa+1;		//自己画图理解
    int rson = 2*pa+2;
    int maxn = pa;
    if(lson<n&&tree[lson]>tree[maxn]) maxn = lson;
    if(rson<n&&tree[rson]>tree[maxn]) maxn = rson;
    if(pa!=maxn) {
        swap(tree[maxn],tree[pa]);
        heapify(n,maxn);
    }
}
void heap_build(int n)//有n个节点   heap_build的操作是制造根堆，0元素是最值
{																	//根堆建好之后，堆根节点的每一次heapify都能将次大值选到第二层
    int lastnode = n-1;//最后一个节点
    int startpa = (lastnode-1)/2; //最后一个父节点
    for(int i=startpa; i>=0; i--) //逆序对每一个父节点做heapify
        heapify(n,i);
}
void heap_sort(int n) //有n个节点
{
    heap_build(n);
    for(int i=n-1;i>=0;i--)
    {
        swap(tree[0],tree[i]);
        heapify(i,0);
    }
}
int main()
{

//    heapify(n,0);
// 7 8 4 5 1 9 6 3 2 0
//    heap_build(n);
// 9 8 6 5 7 4 1 3 2 0    这些数据是初写的时候用的，数量少，便于理解
    srand(time(NULL));
    for(int i=0;i<n;i++)
        tree[i] = rand();

    heap_sort(n);

    for(int i:tree)
        cout<<i<<endl;
    return 0;
}
```

---

# sort

[参考网址入口]([https://blog.csdn.net/qq_16836151/article/details/51527160?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522159378757819195188462580%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=159378757819195188462580&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v3~pc_rank_v2-1-51527160.first_rank_ecpm_v3_pc_rank_v2&utm_term=sort%E6%BA%90%E7%A0%81](https://blog.csdn.net/qq_16836151/article/details/51527160?ops_request_misc=%7B%22request%5Fid%22%3A%22159378757819195188462580%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=159378757819195188462580&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v3~pc_rank_v2-1-51527160.first_rank_ecpm_v3_pc_rank_v2&utm_term=sort源码)) 这篇是我见过写的最好的了。没有找到侯捷老师的视频。
以下很多引用自原文：
**（1）**在数据量很大时采用正常的快速排序，此时效率为O(logN)。//最好情况
**（2）**一旦分段后的数据量小于某个阈值，就改用插入排序，因为此时这个分段是基本有序的，这时效率可达O(N)。//最好情况
**（3）**在递归过程中，如果递归层次过深，分割行为有恶化倾向时，它能够自动侦测出来，使用堆排序来处理，在此情况下，使其效率维持在堆排序的O(N logN)，但这又比一开始使用堆排序好。

```c++
// 1  sort()
template <class RandomAccessIterator>
inline void sort(RandomAccessIterator first, RandomAccessIterator last) {
    if (first != last) {
        __introsort_loop(first, last, value_type(first), __lg(last - first) * 2);     //__lg(last - first) * 2 这是计算深度阈值 以2为底的对数*2
        __final_insertion_sort(first, last); //满足(2)
    }
}//首先调用__introsort_loop，在里面有 快排，堆排 的调用
//其次是__final_insertion_sort插排的调用
```





```c++
// 2     __introsort_loop()
template <class RandomAccessIterator, class T, class Size>
void __introsort_loop(RandomAccessIterator first,
                      RandomAccessIterator last, T*,
                      Size depth_limit)
{    //__introsort_loop()  begin--------------------------------------
    while (last - first > __stl_threshold) { //(2)的条件 
//__stl_threshold的定义：const int __stl_threshold = 16; 
        if (depth_limit == 0) {   //满足(3) 调用堆排
            partial_sort(first, last, last);
            return;
        }
        --depth_limit; 
        RandomAccessIterator cut = __unguarded_partition 
          (first, last, T(__median(*first, *(first + (last - first)/2),
                                   *(last - 1)))); //__median三值取中法进行快排
          //cut = __unguarded_partition(first,last,中值); 
        __introsort_loop(cut, last, value_type(first), depth_limit);//递归调用自己
        last = cut;
    }
}//__introsort_loop()  end---------------------------------------------
/*while循环的条件：当快排到一定程度的时候，小区间的个数会变的很少，元素差不多是有序的，这个时候递归回去，为的是结束退出该函数调用插排。当元素个数很小的时候，继续递归快排付出的代价是要比插排大得多的。这个临界值(__stl_threshold) 16的确定感觉恰到好处。
depth_limit的确定：假设是八个元素(实际上调用插排，我只是举例子)，递归可以认为是这样的
   8 
 4   4
2 2 2 2 ，六次，基本上是log以2为底元素的个数的2倍，这就是调用此函数的时候是
__lg(last - first) * 2的原因。
之前说，当递归层数太深，会调用堆排就是在这里了。
```

```c++
// 3 __unguarded_partition()  快排
template <class RandomAccessIterator, class T>
RandomAccessIterator __unguarded_partition(RandomAccessIterator first, 
                                           RandomAccessIterator last, 
                                           T pivot) {
    while (true) {
        while (*first < pivot) ++first;  
        --last;
        while (pivot < *last) --last;
        if (!(first < last)) return first;
        iter_swap(first, last);
        ++first;
    }
}//这里的--last;和++first; 相当于上述我的快排代码中的quick_sort(l,i-1);中的i-1和i+1
/*关于为什么没有边界判断，文章中说的很清楚
我觉得是有两个原因，最主要还是第二个，举两个例子。
(1) 如果存的是 1 2 3 4 5 6 7 8 9 10 ，你可能会说，first会一直向后移动直到超出范围。不会，因为pivot取的是中值，上述例子中first会在5这个位置停下，三值取中法。
(2) 源代码和我写的不一样，用的是<(>),不像我的代码中用的<=(>=)，这个时候会在pivot的时候停下来，结束循环。如果是0 2 5 16 11 7 9 1 4 19， pivot是11的话，first会指向16，last会指向4，交换之后变成0 2 5 4 11 7 9 1 16 19，之后first会在pivot的时候停下来，而last指向1，交换后变成0 2 5 4 1 7 9 11 16 19，first又会在11这个地方停下来，而左边肯定会有比pivot小的数，last也会停下来，这时候已经交叉return了。
可能讲的不是很清楚，在本子上多举几个例子运行一边便会觉得十分精妙。last肯定会在左边停下来，因为左边基本是比pivot小的值了，first肯定会在右边停下来，同理。一个小小的小于大于号，竟然如此巧妙。
*/
```





```c++
// 4 partial_sort()和__partial_sort()  堆排
//sort中的__introsort_loop调用partial_sort，而partial_sort调用__partial_sort 

//后调用：__partial_sort()
template <class RandomAccessIterator, class T, class Compare>
void __partial_sort(RandomAccessIterator first, RandomAccessIterator middle,
                    RandomAccessIterator last, T*, Compare comp) 
{
    make_heap(first, middle, comp);//对原始容器内区间为[first, middle)的元素运行make_heap()操作构造一个最大堆 那么头元素为最大值 即下面的*first 此时为堆序
    for (RandomAccessIterator i = middle; i < last; ++i)
        if (comp(*i, *first))  //将middle到last的元素与对顶元素进行比较
            __pop_heap(first, middle, i, T(*i), comp, distance_type(first));
  //如果小于该值，就互换位置并重新保持 max-heap 状态
    sort_heap(first, middle, comp); //此时为增序
}
//先调用：partial_sort()
template <class RandomAccessIterator, class Compare>
inline void partial_sort(RandomAccessIterator first,
                         RandomAccessIterator middle,
                         RandomAccessIterator last, Compare comp) {
    __partial_sort(first, middle, last, value_type(first), comp);//主体
}
// __pop_heap()
template <class RandomAccessIterator, class T, class Distance>
inline void __pop_heap(RandomAccessIterator first, RandomAccessIterator last,
                       RandomAccessIterator result, T value, Distance*) {
  *result = *first; // 弹出元素放vector尾端
  __adjust_heap(first, Distance(0), Distance(last - first), value);//保持最大堆
}
/*
__partial_sort():当需要对某个区间进行排序的时候，将调用时的参数改成(first，last，last)就可以了。从最初的调用到make_heap(),其实原型都是(first，last，last)。
虽然上述的参数直接实现了堆排，但是我另外说一下。在__partial_sort()中可以看到make_heap(参数1，参数2),是从[参数1,参数2]的区间做一次build_heap()（我的代码中的函数），函数完成之后就是一个最大堆，元素顺序为堆序。之后拿[middle,last]区间的元素对首元素进行比较,若小，进行一次__pop_heap()。__pop_heap()中就是将首元素取出来放在[middle,last]区间的最后，再将[middle,last]区间的首元素覆盖最大堆的首元素，此时最大堆已经不是最大堆，要进行heapify(我的代码中的函数)，即__adjust_heap()。
之后的sort_heap是将最大堆的堆序调成为升序。即我的代码中sort_heap()中的循环。

起先我感觉：这个for循环有一点多余(当然，这个函数并不只是只用在sort里面被调用)，它是对整个[first,last]区间进行造堆，那么顶元素肯定是最大值啊，middle后的区间完全没必要比。
后来：for循环的条件直接跳过了。middle就是last

下有参考链接。
*/
```

[__partial_sort参考网址入口](https://www.2cto.com/kf/201407/318957.html)  
[参考2](https://www.cnblogs.com/bhlsheji/p/5276771.html) 

```c++
// 5  __final_insertion_sort()------------------>__unguarded_linear_insert()
//    |_-> __insertion_sort()->__linear_insert->__unguarded_linear_insert()

//__final_insertion_sort() sort调用：  插排阅读顺序(1) 
template <class RandomAccessIterator>
void __final_insertion_sort(RandomAccessIterator first, 
                            RandomAccessIterator last)
{
    if (last - first > __stl_threshold) {
        __insertion_sort(first, first + __stl_threshold);
        __unguarded_insertion_sort(first + __stl_threshold, last);
    }
    else
        __insertion_sort(first, last);
}
//__unguarded_linear_insert() 插排阅读顺序(4)
template <class RandomAccessIterator, class T>
void __unguarded_linear_insert(RandomAccessIterator last, T value) {
    RandomAccessIterator next = last;
    --next;
    while (value < *next) {
        *last = *next;
        last = next;
        --next;
    }
    *last = value;
}
//__linear_insert() 插排阅读顺序(3)
template <class RandomAccessIterator, class T>
inline void __linear_insert(RandomAccessIterator first, 
                            RandomAccessIterator last, T*) {
    T value = *last;
    if (value < *first) {
        copy_backward(first, last, last + 1);
        *first = value;
    }
    else
        __unguarded_linear_insert(last, value);//调__unguarded_linear_insert（）
}
//__insertion_sort()  插排阅读顺序(2)
template <class RandomAccessIterator>
void __insertion_sort(RandomAccessIterator first, RandomAccessIterator last) {
    if (first == last) return; 
    for (RandomAccessIterator i = first + 1; i != last; ++i)
        __linear_insert(first, i, value_type(first)); //调用__linear_insert()
}
/*文章将的很清楚，没有什么特别的理解了

*/
```





# 万事具备 

学了，那就自己敲一下吧，用数组简单实现

```c++
//先是写了快排   第一版本
#include <iostream>
#include <time.h>
#include <cstdio>
#include <stdlib.h>

#include "help.h"

using namespace std;

int a[1000];
int len = 1000;

//快排
int quick_sort(int first ,int last, int pivot)
{
    while(true)
    {
        while(a[first]<pivot) ++first;
        --last;
        while(a[last]>pivot)  --last;
        if(!(first<last)) return first;
        swap(a[first],a[last]);
        ++first;
    }
}

void myintrosort_loop(int first ,int last)
{
    while( last-first>1 )  //没有加上插排的时候，这么写。如果写成first<last会 死递归
    {                                                      
        int mid_value = get_mid_value( a[first], a[last-1], a[(first+last)/2] );//三值取中法 调用函数 我写在了help.h文件里 我就没有粘出来了
        int cut = quick_sort(first , last , mid_value);
        myintrosort_loop(cut,last);
        last = cut;
    }
}

void mysort(int first ,int last)
{
    myintrosort_loop(first,last); 
}

int main()
{

    srand(time(NULL));
    for(int i=0;i<len;i++)
        a[i] = rand();

    mysort(0,len);  //开始sort

    for(int i:a)
        cout<<i<<endl;

    return 0;
}
```



```c++
//堆排测试  __begin()
int a[100],len=100;
void heapify(int first,int last,int pa)
{
    if(pa>=last) return ;
    int lson = first + 2*(pa-first)+1;
    int rson = first + 2*(pa-first)+2;
    int maxn = pa;
    if(lson<last&&a[lson]>a[maxn]) maxn = lson;
    if(rson<last&&a[rson]>a[maxn]) maxn = rson;
    if(pa!=maxn) {
        swap(a[maxn],a[pa]);
        heapify(first, last ,maxn);
    }
}
void heap_build(int first,int last)
{
    int lastnode = last-1;//最后一个节点
    int startpa = first+(lastnode-first-1)/2; //最后一个父节点
    for(int i=startpa; i>=first; i--) //逆序对每一个父节点做heapify
        heapify(first,last,i);
}
void heap_sort(int first ,int last)
{
    heap_build(first,last);
    for(int i=last-1;i>=first;i--)
    {
        swap(a[first],a[i]);
        heapify(first,i,first);
    }
}
int main()
{
  	srand(time(NULL));
  	for(int i=0;i<len;i++)
    		a[i] = rand();
  
  	heap_sort(90,101);//在区间[90,100]进行堆排 左闭右开区间，所有last要+1
  	
  	for(int i:a)
      	cout<<i<<endl;
  
		return 0;
}
//  __end
  						 /*将两个函数整合到里面去*/
int a[10000];
int len = 10000;
//////////////////////////堆排__start() 实现一个区间堆排
void heapify(int first,int last,int pa)
{
    if(pa>=last) return ;
    int lson = first + 2*(pa-first)+1;
    int rson = first + 2*(pa-first)+2;
    int maxn = pa;
    if(lson<last&&a[lson]>a[maxn]) maxn = lson;
    if(rson<last&&a[rson]>a[maxn]) maxn = rson;
    if(pa!=maxn) {
        swap(a[maxn],a[pa]);
        heapify(first, last ,maxn);
    }
}
void heap_build(int first,int last)
{
    int lastnode = last-1;//最后一个节点
    int startpa = first+(lastnode-first-1)/2; //最后一个父节点
    for(int i=startpa; i>=first; i--) //逆序对每一个父节点做heapify
        heapify(first,last,i);
}
void heap_sort(int first ,int last)
{
    heap_build(first,last);
    for(int i=last-1;i>=first;i--)
    {
        swap(a[first],a[i]);
        heapify(first,i,first);
    }
}
//////////////////////////堆排__end()

////////////////////////////快排 __begin
int quick_sort(int first ,int last, int pivot)
{
    while(true)
    {
        while(a[first]<pivot) ++first;
        --last;
        while(a[last]>pivot)  --last;
        if(!(first<last)) return first;
        swap(a[first],a[last]);
        ++first;
    }
}
////////////////////////////快排 __end

void myintrosort_loop(int first ,int last,int depth_limit)
{
    while( last-first>1 )
    {
        if(depth_limit==0)
        {
            heap_sort(first,last);
            //make_heap(&a[first],&a[last]);  //I'm here. 堆排的调用
          	/*想整合自己的函数进去，发现竟然也不是易事，这个函数可以实现区间造堆，
          	又有研究的东西了*/
            return ;
        }
        depth_limit--;
        int mid_value = get_mid_value( a[first], a[last-1], a[(first+last)/2] );//三值取中法 调用函数
        int cut = quick_sort(first , last , mid_value);
        myintrosort_loop(cut,last,depth_limit);
        last = cut;
    }
}

void mysort(int first ,int last)
{
    int depth_limit = get_depth_limit(last,first); //获取深度阈值 调用函数
    myintrosort_loop(first,last,depth_limit);
}

int main()
{

    srand(time(NULL));
    for(int i=0;i<len;i++)
        a[i] = rand();

    mysort(0,len);

    for(int i:a)
        cout<<i<<endl;

    return 0;
}
```

```c++
//插排的整合之后再更新
```



**看完之后，这里我有两个问题，
1.就是(2)一旦分段后的数据量小于某个阈值，就改用插入排序，因为此时这个分段是基本有序的，这时效率可达O(N)。
这个到底是什么意思，是a[15]的时候直接调用插排，还是说a[10000]在快排递归，区段小于16的时候调用插排。如果是第二种，插排是在sort()函数里面的，也就是说在introsort_loop()执行完之后才开始调用。如果他是在intro_loop()函数里调用了，那我认为要在while(){}后面加上插排的调用代码。
2.还有，插排的里是分段的，>16是一段，小于是一段，分段处理。从introsort_loop()出来之后肯定是<=16的，为什么要分这个段呢，没必要啊。我的想法是这个插排的函数不只是由sort()调用，其他也会调用这个函数，所以才这么写
希望解答.......** 

---

本文是自己的学习记录，读者可参考，仅供参考
不完善的地方请指正

欢迎来小站一逛 [戳一下](https://codesniperyang.github.io/) 