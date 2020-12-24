package pack_;
// �����������ݿ���ͷ���Դ
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectMySQL {
	private ConnectMySQL() {}
	static Connection conn = null;
	// ���ӵ������ݿ� �����conn
	public static Connection getCon() {
		final String url = "jdbc:mysql://127.0.0.1:3306/MedicalFacilities?serverTimezone=UTC";
		final String user = "root";
		final String password = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("��ȡ��Connection:"+conn);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error��������ConnectMySQL.getCon()������");
		}
		return conn;
	}
	
	//�ͷ�
	public static void close(Connection con, Statement st, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("�ͷ�rsʧ�ܣ�close()������");
		}
		try {
			if(st != null) {
				st.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("�ͷ�stʧ�ܣ�close()������");
		}
		try {
			if(con != null) {
				con.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("�ͷ�conʧ�ܣ�close()������");
		}
	}	

}
