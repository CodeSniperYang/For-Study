# JDBC

mysql 查看表信息 ：desc 表名；

## JDBC编程六步

第一步：注册驱动（告诉JVM，即将要连接的是那个品牌的数据库）

第二步：获取连接 （表示JVM的进程和数据库的通道打开了，属于进程之间的通信，重量级的，使用完之后一定关闭通道）

第三步：获取数据库操作对象（专门执行sql语句的对象）

第四步：执行SQL语句（DQL，DML......）

第五步：处理查询结果集（只有当第四步执行的是select语句的时候，才有这第五步的查询结果集）

第六步：释放资源（使用完资源之后一定要关闭资源，这属于进程之间的通信，开启之后一定要关闭）

代码实现： （此处省略了处理查询结果集合）

```java
/*
	首先需要下载jar包，下好后：
	Project->properties->Java Build Path->Add External JARs,选择你下载的jar包位置
*/
import java.sql.*;

public class JDBCTest02 {
	public static void main(String[] args)   {
		Connection conn = null;
		Statement sta = null;
		try {
			//1.注册驱动
			Driver driver = new com.mysql.cj.jdbc.Driver(); 
			DriverManager.registerDriver(driver);
			// 注册驱动的第二种方式
			//Class.forName("com.mysql.cj.jdbc.Driver"); 类加载异常throws ClassNotFoundException
			//2.获取连接
			/**
			 *URL: 统一资源定位符（网络中某个资源的绝对路径）https://www.baidu.com/ 这就是一个URL。
			   包括：协议，IP，PORT，资源名 例如：http://182.61.200.7:80/index.html
			   
			 jdbc:mysql// 协议
			 127.0.0.1和localhost是一样的，表示本机IP。192开头是局域网头
			 3306 mysql的端口号
			 bjpowernode 数据库名称
			 后面是时区
			 */
			String url = "jdbc:mysql://127.0.0.1:3306/dbtest?serverTimezone=UTC";
			String username = "root";
			String password = "root";
			conn = DriverManager.getConnection(url,username,password);
			System.out.println(conn);
			//3.获取数据库操作对象
			sta = conn.createStatement();//import java.sql.Statement;
			//4.执行SQL
			String sql = "INSERT INTO table_test1" + 
					"     (stu_name, stu_age)" + 
					"     VALUES" + 
					"     ('ghj','20');";
			int count = sta.executeUpdate(sql);//专门执行DML语句（insert delete update），返回值是影响数据库中的记录条数
			System.out.println(count==1?"保存成功":"保存失败");
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error");
			e.printStackTrace();
		} finally {
			//6.释放资源
			//为了保证资源一定释放，在finally中关闭资源
			//遵循从小到大关闭
			//分别对其try...catch...
			try {
				if(sta != null) {
					sta.close();
				}
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			try {
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}	
		}
	}
}

```

### 使用资源绑定器绑定属性配置文件

jdbc.properties //在eclipse中创建source文件夹，在文件夹下创建jdbc.properties

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/dbtest?serverTimezone=UTC
username=root
password=root
```

xxx.java

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class JDBCTest02 {
	public static void main(String[] args) {
		// 使用资源绑定器绑定属性配置文件
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
		String driver = bundle.getString("driver");
		String url = bundle.getString("url");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		Connection conn=null;
		try {
			Class.forName(driver); //注册
			conn = DriverManager.getConnection(url,username,password);//连接
			System.out.println(conn);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		} finally {
			try {
				if(conn!=null)
					conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
```

### 处理查询结果集

```java
import java.sql.*;
import java.util.ResourceBundle;

public class JDBCTest02 {
	public static void main(String[] args) {
		Connection conn=null;
		Statement sta = null;
		ResultSet rs = null; //查询结果存在这里 ★
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //注册
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbtest?serverTimezone=UTC","root","root");//连接
			sta = conn.createStatement();// 创建对象
			System.out.println(conn);
			String sql = "select * from table_test1";//执行select
			rs = sta.executeQuery(sql);//★
			//处理查询结果集
			while(rs.next()) {//★
				/*
				 String name = rs.getString(1);
				 String age = rs.getString(2);
				 System.out.println(name+" "+age);
				 
				 // int a = getInt(); double b = getDouble()
				*/
				 String name = rs.getString("stu_name");
				 String age = rs.getString("stu_age");
				 System.out.println(name+" "+age); 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		} finally {
      try {if(rs!=null) {rs.close();}} catch (Exception e2) {}
			try {if(sta!=null) {sta.close();}} catch (Exception e2) {}
			try {if(conn!=null)conn.close();} catch (Exception e2) {}
		}
	}
}
```

### 模拟登陆数据库

```java
package pro_2;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * -模拟登陆数据库
 * @author yy
 * 
 * Y 这里会出现一个问题，SQL注入，所以没有使用Statement 
 * 解决方法参照 https://www.bilibili.com/video/BV1Bt41137iB?p=20
 */
public class JDBCTest06 {
	public static void main(String[] args) {
		//初始化一个界面
		Map<String,String> LoginUser = init();
		//验证用户名和密码
		boolean LoginJudge = Login(LoginUser);
		System.out.println(LoginJudge?"Successful":"Failed");
	}
	/**
	 * user登陆信息
	 * @param LoginUser
	 * @return
	 */
	public static boolean Login(Map<String,String> LoginUser) {
		Connection conn = null;
		Statement sta = null;
		ResultSet rs = null;
		boolean loginseccess = false; 
		try {
			String name = LoginUser.get("LoginName");
			String pwd = LoginUser.get("LoginPwd");
			//1.注册驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			//2.连接数据库
			String url = "jdbc:mysql://127.0.0.1:3306/dbtest?serverTimezone=UTC";
			String username = "root";
			String password = "root";
			conn = DriverManager.getConnection(url,username,password);
			//3.获取数据库操作对象
			sta = conn.createStatement();
			//4.执行sql语句
			String sql = "select * from t_user where loginName = '"+name+"' and loginPwd = '"+pwd+"'";
			rs= sta.executeQuery(sql);
			if(rs.next()) {
				loginseccess = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			//6.释放资源
		      try {if(rs!=null) {rs.close();}} catch (Exception e2) {}
		      try {if(sta!=null) {sta.close();}} catch (Exception e2) {}
		      try {if(conn!=null)conn.close();} catch (Exception e2) {}
		}
		return loginseccess;
	}
	
	/**
	 * input 用户输入用户名和密码 
	 * @return
	 */
	public static Map<String,String> init() {		
		Map<String,String> LoginUser = new HashMap<String, String>();
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入用户名:");
		String name = scanner.nextLine();
		System.out.print("请输入密码:");
		String pwd = scanner.nextLine();
		LoginUser.put("LoginName",name);
		LoginUser.put("LoginPwd",pwd);
		scanner.close();
		return LoginUser;
	}
}

```



