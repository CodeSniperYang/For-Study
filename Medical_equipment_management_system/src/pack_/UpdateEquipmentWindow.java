package pack_;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UpdateEquipmentWindow extends JFrame{
	JTextField fieldname = new JTextField(10);
	JTextField fieldnameafter = new JTextField(10);
	JButton buttonupdate = new JButton("修改");
	UpdateEquipmentWindow() {
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
		add(new JLabel("update:"));
		add(fieldnameafter);
		
		buttonupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = fieldname.getText();
				String nameafter = fieldnameafter.getText();
				Connection conn = ConnectMySQL.getCon();
				Statement sta = null;
				try {
					sta = conn.createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//update equipment set ename='2' where ename='测压机';
				String sql = "update equipment set ename="+"'"+nameafter+"'"+
						"where ename="+
						"'"+name+"';";
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
		add(buttonupdate);

	}
}