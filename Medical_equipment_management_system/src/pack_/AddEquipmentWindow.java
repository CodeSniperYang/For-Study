package pack_;
// ������ݵĴ���
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

public class AddEquipmentWindow extends JFrame{
	JTextField fieldid = new JTextField(10);
	JTextField fieldname = new JTextField(10);
	JButton buttonadd = new JButton("���");
	AddEquipmentWindow() {
		init();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400,100,500,500);
		setTitle("ҽ�ƹ���ϵͳ");
	}
	public void init() {
		setLayout(new FlowLayout());
		
		add(new JLabel("id:"));
		add(fieldid);
		
		add(new JLabel("name:"));
		add(fieldname);
		
		buttonadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String id = fieldid.getText();
				String name = fieldname.getText();
				Connection conn = ConnectMySQL.getCon();
				Statement sta = null;
				try {
					sta = conn.createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String sql = "INSERT INTO equipment(eid, ename) values" +  
						"("+id+","+"'"+name+"'"+");";
				try {
					int count = sta.executeUpdate(sql);
					if(count==1) {
						JOptionPane.showMessageDialog(null,"��ӳɹ�");
					}
					else {
						JOptionPane.showMessageDialog(null,"���ʧ��");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//ר��ִ��DML��䣨insert delete update��������ֵ��Ӱ�����ݿ��еļ�¼����
				ConnectMySQL.close(conn, sta, null);
			}
		});
		add(buttonadd);

	}
}
