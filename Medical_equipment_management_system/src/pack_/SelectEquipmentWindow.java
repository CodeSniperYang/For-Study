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
import javax.swing.JTextField;

public class SelectEquipmentWindow extends JFrame{
	JTextField fieldname = new JTextField(10);
	JButton buttonselect = new JButton("查找");
	SelectEquipmentWindow() {
		init();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400,100,500,500);
		setTitle("医疗管理系统");
	}
	public void init() {
		setLayout(new FlowLayout());
		
		add(new JLabel("name:"));
		add(fieldname);
		
		buttonselect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = fieldname.getText();
				Connection conn = ConnectMySQL.getCon();
				Statement sta = null;
				ResultSet rs = null;
				try {
					sta = conn.createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//update equipment set ename='2' where ename='测压机';
				String sql = "select * from equipment where ename=" +
						"'"+name+"';";
				try {
					rs= sta.executeQuery(sql);
					boolean flag = false;
					while(rs.next()) {
						 flag = true;
						 String rsid = rs.getString("eid");
						 String rsname = rs.getString("ename");
						 JOptionPane.showMessageDialog(null,rsid+","+rsname);
					}
					if(flag==false) {
						JOptionPane.showMessageDialog(null,"无数据");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ConnectMySQL.close(conn, sta, rs);
			}
		});
		add(buttonselect);
	}
}