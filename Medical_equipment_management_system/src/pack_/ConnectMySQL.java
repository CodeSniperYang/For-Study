package pack_;
// 用来连接数据库和释放资源
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectMySQL {
	private ConnectMySQL() {}
	static Connection conn = null;
	// 连接到了数据库 获得了conn
	public static Connection getCon() {
		final String url = "jdbc:mysql://127.0.0.1:3306/MedicalFacilities?serverTimezone=UTC";
		final String user = "root";
		final String password = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("获取到Connection:"+conn);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error，错误在ConnectMySQL.getCon()函数中");
		}
		return conn;
	}
	
	//释放
	public static void close(Connection con, Statement st, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("释放rs失败，close()函数中");
		}
		try {
			if(st != null) {
				st.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("释放st失败，close()函数中");
		}
		try {
			if(con != null) {
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("释放con失败，close()函数中");
		}
	}	

}
