### 多线程     这个十分重要，不是一点两点的时间能够明白的

我的理解：多个任务同时进行，同时对多个任务加以控制

引入：

```java
//main.java
public static void main(String[] args) {
    Music musicThread = new Music();
    Eat eatThread = new Eat();
    musicThread.start();
    eatThread.start();
}
//Music.java
public class Music extends Thread {
	public void run() {
		for(int i=1;i<=1000;i++)
			System.out.println("听音乐");
	}
}
//Eat.java
public class Eat extends Thread {
	public void run() {
		for(int i=1;i<=1000;i++)
			System.out.println("吃饭");
	}
}
//可能因为计算机速度太快而看不出来 可以在函数中加入Thread.sleep(100)

```









