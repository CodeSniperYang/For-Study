package pack_;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
//ɾ�����ݵĴ���
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DeleteEquipmentWindow extends JFrame{
	JTextField fieldname = new JTextField(10);
	JButton buttondelete = new JButton("ɾ��");
	DeleteEquipmentWindow() {
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
		
		buttondelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = fieldname.getText();
				Connection conn = ConnectMySQL.getCon();
				Statement sta = null;
				try {
					sta = conn.createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String sql = "delete from equipment where ename = "+ 
						"'"+name+"';";
				try {
					int count = sta.executeUpdate(sql);
					if(count>0)
						JOptionPane.showMessageDialog(null,"ɾ���ɹ�");
					else 
						JOptionPane.showMessageDialog(null,"ɾ��ʧ��");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//ר��ִ��DML��䣨insert delete update��������ֵ��Ӱ�����ݿ��еļ�¼����
				ConnectMySQL.close(conn, sta, null);
			}
		});
		add(buttondelete);

	}
}
