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
	JButton buttonupdate = new JButton("�޸�");
	UpdateEquipmentWindow() {
		init();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400,100,500,500);
		setTitle("ҽ�ƹ���ϵͳ");
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
				//update equipment set ename='2' where ename='��ѹ��';
				String sql = "update equipment set ename="+"'"+nameafter+"'"+
						"where ename="+
						"'"+name+"';";
				try {
					int count = sta.executeUpdate(sql);
					if(count>0)
						JOptionPane.showMessageDialog(null,"�޸ĳɹ�");
					else 
						JOptionPane.showMessageDialog(null,"�޸�ʧ��");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//ר��ִ��DML��䣨insert delete update��������ֵ��Ӱ�����ݿ��еļ�¼����
				ConnectMySQL.close(conn, sta, null);
			}
		});
		add(buttonupdate);

	}
}