package pack_;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/* 
 * @author yy
 * �������ݿ�Ȼ��ֱ���ж����ݣ�֮���ͷ���Դ
 */
public class JudgeLogin {
	private JudgeLogin() {}
	public static boolean judge(String identity,String id,String pw)  {
		Connection conn = ConnectMySQL.getCon();
		ResultSet rs = null;
		boolean loginseccess = false;
		Statement sta = null;
		if(identity=="����Ա") {
			try {
				sta = conn.createStatement();
				String sql = "select * from Administrators where adminID = '"+id+"' and adminPW = '"+pw+"'";
				rs= sta.executeQuery(sql);
				if(rs.next()) 	return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				sta = conn.createStatement();
				String sql = "select * from Medicalstaff where meID = '"+id+"' and mePW = '"+pw+"'";
				rs= sta.executeQuery(sql);
				if(rs.next()) 	return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ConnectMySQL.close(conn, sta, rs);
		if(loginseccess==true)
			return true;
		else return false;
	}
}
