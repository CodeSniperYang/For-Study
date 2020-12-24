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
 * Y 不适用任何布局 然后用setBounds函数设置位置
 */


public class Window extends JFrame{
	JLabel labelid = new JLabel("账号："); 
	JLabel labelpw = new JLabel("密码：");
	
	ButtonGroup group = new ButtonGroup();
	JTextField fieldid = new JTextField(10); //输入账号
	JPasswordField fieldpw = new JPasswordField(10); //输入密码
	
	JRadioButton jrb1 = new JRadioButton("管理员");
	JRadioButton jrb2 = new JRadioButton("医护人员");
	
	JButton buttonlogin = new JButton("登陆"); //登陆按钮
	JButton buttoncancel = new JButton("取消"); //取消按钮
	
	public Window() {
		init();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(400,100,500,500);
		setTitle("医疗管理系统");
	}
	public void init() {
		setLayout(new FlowLayout());
		
		add(labelid);
		add(fieldid);//账号
		
		add(labelpw);
		add(fieldpw);//密码
		
		group.add(jrb1);
		group.add(jrb2);//选项
		add(jrb1);
		add(jrb2);
		
		//监视器
		buttonlogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String id = fieldid.getText();
				String pw=new String(fieldpw.getPassword());
				
				if(jrb1.isSelected()) { //是管理员
				 	if(JudgeLogin.judge("管理员", id, pw)==true) {
				 		AdministrationWindow adminwin = new AdministrationWindow("管理员");
						
					}
					else {
						JOptionPane.showMessageDialog(null,"登陆失败");
						
					}
				}
				else { //是医护人员
					if(JudgeLogin.judge("医护人员", id, pw)==true) {
						AdministrationWindow adminwin = new AdministrationWindow("医护人员");  
					}
					else {
						JOptionPane.showMessageDialog(null,"登陆失败");
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
		add(buttoncancel);//按钮添加
	}
}
