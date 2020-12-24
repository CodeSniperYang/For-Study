package pack_;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChangePasswordWindow extends JFrame{
	JTextField fieldname = new JTextField(10);
	JPasswordField fieldpw = new JPasswordField(10);
	JButton changepw = new JButton("修改");
	ChangePasswordWindow(String identity) {
		init(identity);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400,100,500,500);
		setTitle("修改密码");
	}
	public void init(String identity) {
		setLayout(new FlowLayout());
		
		add(new JLabel("id:"));
		add(fieldname);
		
		add(new JLabel("password:"));
		add(fieldpw);
		
		changepw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String id = fieldname.getText();
				String pw=new String(fieldpw.getPassword());
				Connection conn = ConnectMySQL.getCon();
				Statement sta = null;
				try {
					sta = conn.createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String sql = null;
				if(identity=="管理员") {
					sql = "update Administrators set adminPW="+
							"'"+pw+"'" + "where adminID=" + 
							"'"+id+"';";
				}
				else {
					sql = "update Medicalstaff set mePW="+
							"'"+pw+"'" + "where meID=" + 
							"'"+id+"';";
				}
				try {
					int count = sta.executeUpdate(sql);
					if(count>0)
						JOptionPane.showMessageDialog(null,"修改成功");
					else 
						JOptionPane.showMessageDialog(null,"修改失败");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//专门执行DML语句（insert delete update），返回值是影响数据库中的记录条数
				ConnectMySQL.close(conn, sta, null);
			}
		});
		add(changepw);
	}
}