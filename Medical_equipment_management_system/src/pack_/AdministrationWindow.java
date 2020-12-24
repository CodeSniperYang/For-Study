package pack_;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class AdministrationWindow extends JFrame{
	String identity;
	 
	JButton buttonadd = new JButton("��������");
	JButton buttondelete = new JButton("ɾ������");
	JButton buttonupdate = new JButton("�޸�����");
	JButton buttonselect = new JButton("��������");
	JButton changepw = new JButton("�޸�����");
	
	
	AdministrationWindow(String identity) {
		this.identity = identity;
		init();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400,100,500,500);
		setTitle(identity);
	}
	// ���������ť����ɾ���
	public void init() {
		setLayout(new FlowLayout());
		//��������
		buttonadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddEquipmentWindow addwin = 
						new AddEquipmentWindow();
			}
		});
		add(buttonadd);
		//ɾ������
		buttondelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DeleteEquipmentWindow deletewin = 
						new DeleteEquipmentWindow();
			}
		});
		add(buttondelete);
		
		//��������
		buttonupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UpdateEquipmentWindow updatewin = 
						new UpdateEquipmentWindow();
			}
		});
		add(buttonupdate);
		
		//��������
		buttonselect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SelectEquipmentWindow selectwin = 
						new SelectEquipmentWindow();
			}
		});
		add(buttonselect);
		
		//�޸�����
		
		changepw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ChangePasswordWindow chpwwin = 
						new ChangePasswordWindow(identity);
			}
		});
		add(changepw);
	}
}
