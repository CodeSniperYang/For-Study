package pack_;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
/*
 * Y �������κβ��� Ȼ����setBounds��������λ��
 */


public class Window extends JFrame{
	JLabel labelid = new JLabel("�˺ţ�"); 
	JLabel labelpw = new JLabel("���룺");
	
	ButtonGroup group = new ButtonGroup();
	JTextField fieldid = new JTextField(10); //�����˺�
	JPasswordField fieldpw = new JPasswordField(10); //��������
	
	JRadioButton jrb1 = new JRadioButton("����Ա");
	JRadioButton jrb2 = new JRadioButton("ҽ����Ա");
	
	JButton buttonlogin = new JButton("��½"); //��½��ť
	JButton buttoncancel = new JButton("ȡ��"); //ȡ����ť
	
	public Window() {
		init();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(400,100,500,500);
		setTitle("ҽ�ƹ���ϵͳ");
	}
	public void init() {
		setLayout(new FlowLayout());
		
		add(labelid);
		add(fieldid);//�˺�
		
		add(labelpw);
		add(fieldpw);//����
		
		group.add(jrb1);
		group.add(jrb2);//ѡ��
		add(jrb1);
		add(jrb2);
		
		//������
		buttonlogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String id = fieldid.getText();
				String pw=new String(fieldpw.getPassword());
				
				if(jrb1.isSelected()) { //�ǹ���Ա
				 	if(JudgeLogin.judge("����Ա", id, pw)==true) {
				 		AdministrationWindow adminwin = new AdministrationWindow("����Ա");
						
					}
					else {
						JOptionPane.showMessageDialog(null,"��½ʧ��");
						
					}
				}
				else { //��ҽ����Ա
					if(JudgeLogin.judge("ҽ����Ա", id, pw)==true) {
						AdministrationWindow adminwin = new AdministrationWindow("ҽ����Ա");  
					}
					else {
						JOptionPane.showMessageDialog(null,"��½ʧ��");
					}
				}
			}
		});
		buttoncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 System.exit(0);
			}
		});
		
		add(buttonlogin);
		add(buttoncancel);//��ť���
	}
}
