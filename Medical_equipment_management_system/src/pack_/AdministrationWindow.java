package pack_;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class AdministrationWindow extends JFrame{
	String identity;
	 
	JButton buttonadd = new JButton("增加数据");
	JButton buttondelete = new JButton("删除数据");
	JButton buttonupdate = new JButton("修改数据");
	JButton buttonselect = new JButton("查找数据");
	JButton changepw = new JButton("修改密码");
	
	
	AdministrationWindow(String identity) {
		this.identity = identity;
		init();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400,100,500,500);
		setTitle(identity);
	}
	// 创建五个按钮，增删查改
	public void init() {
		setLayout(new FlowLayout());
		//增加数据
		buttonadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddEquipmentWindow addwin = 
						new AddEquipmentWindow();
			}
		});
		add(buttonadd);
		//删除数据
		buttondelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DeleteEquipmentWindow deletewin = 
						new DeleteEquipmentWindow();
			}
		});
		add(buttondelete);
		
		//更新数据
		buttonupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UpdateEquipmentWindow updatewin = 
						new UpdateEquipmentWindow();
			}
		});
		add(buttonupdate);
		
		//查找数据
		buttonselect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SelectEquipmentWindow selectwin = 
						new SelectEquipmentWindow();
			}
		});
		add(buttonselect);
		
		//修改密码
		
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
