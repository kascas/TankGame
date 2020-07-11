package TankWar2;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SetMenu implements ActionListener{
	JFrame setwin=null;
	//单选框组--玩家数量
	ButtonGroup player_num=null;
	JRadioButton sin_player=null;
	JRadioButton dou_player=null;
	//单选框组--游戏模式
	ButtonGroup game_mode=null;
	JRadioButton common=null;
	JRadioButton customize=null;
	//复选框组
	String[] list1=null;
	String[] list2=null;
	String[] list3=null;
	JComboBox<String> box1=null;
	JComboBox<String> box2=null;
	JComboBox<String> box3=null;
	JPanel gp1=null;	JPanel gp2=null;
	JPanel jp1=null;	JLabel lb1=null;
	JPanel jp2=null;	JLabel lb2=null;
	JPanel jp3=null;	JLabel lb3=null;
	JTextField tx=null;
	JPanel jp4=null;	JLabel lb4=null;
	JCheckBox jcb1=null;	JCheckBox jcb2=null;
	JPanel jp5=null;
	JButton jb1=null;	JButton jb2=null;
	JPanel jp6=null;
	static int flag=0;
	
	public void createSetwin(){
		player_num=new ButtonGroup();
		sin_player=new JRadioButton("单人游戏",false);
		dou_player=new JRadioButton("双人游戏",false);
		game_mode=new ButtonGroup();
		common=new JRadioButton("正常游戏",false);
		customize=new JRadioButton("自选游戏",false);
		
		player_num.add(sin_player);
		player_num.add(dou_player);
		gp1=new JPanel();	gp2=new JPanel();
		gp1.add(sin_player);	gp1.add(dou_player);
		game_mode.add(common);
		game_mode.add(customize);
		gp2.add(common);	gp2.add(customize);
		
		list1=new String[] {"1","2","3","4","5","6","7","8"};
		list2=new String[] {"慢","中","快"};
		list3=new String[] {"慢","中","快"};
		box1=new JComboBox<String>(list1);	box1.setSelectedIndex(0);	lb1=new JLabel("请选择关数");
		box2=new JComboBox<String>(list2);	box2.setSelectedIndex(1);	lb2=new JLabel("坦克速度");
		box3=new JComboBox<String>(list3);	box3.setSelectedIndex(1);	lb3=new JLabel("子弹速度");
		jp1=new JPanel();	jp2=new JPanel();	jp3=new JPanel();	jp4=new JPanel();
		jp1.add(lb1);	jp1.add(box1);
		jp2.add(lb2);	jp2.add(box2);
		jp3.add(lb3);	jp3.add(box3);
		
		lb4=new JLabel("坦克数量(10-50)");
		tx=new JTextField(2); tx.setText(null);
		jp4.add(lb4);	jp4.add(tx);
//		jcb1=new JCheckBox("继续游戏");
//		jcb2=new JCheckBox("出现关底");
//		jp5=new JPanel();
//		jp5.add(jcb1);	jp5.add(jcb2);
		
		jb1=new JButton("确定");
		jb2=new JButton("取消");
		jp6=new JPanel();
		jp6.add(jb1);	jp6.add(jb2);
		
		setwin=new JFrame("游戏设置");
		setwin.setSize(300, 400);
		setwin.setLayout(new GridLayout(7,1));
		setwin.add(gp1);	setwin.add(gp2);
		setwin.add(jp1);	setwin.add(jp2);	
		setwin.add(jp3);	setwin.add(jp4);
		setwin.add(jp6);
//		setwin.add(jp5);	
		setwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setwin.setVisible(true);
		jb1.addActionListener(this);
		jb1.setActionCommand("yes");
		jb2.addActionListener(this);
		jb2.setActionCommand("no");
		common.addActionListener(this);
		common.setActionCommand("common");
		customize.addActionListener(this);
		customize.setActionCommand("customize");
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// 对用户不同的点击作出不同的处理
		if (arg0.getActionCommand().equals("yes")) {
			// 创建战场面板
			if(sin_player.isSelected())
				Recorder.setplayerNum(1);
			else if(dou_player.isSelected())
				Recorder.setplayerNum(2);
			System.out.println("playerNum:"+Recorder.getplayerNum());
			if(tx.isEnabled()) {
				Recorder.setEnNum(Integer.parseInt(tx.getText()));
				Recorder.setLevel(Integer.parseInt(list1[box1.getSelectedIndex()]));
			} 
			else {
				Recorder.setLevel(Integer.parseInt(list1[box1.getSelectedIndex()]));
				Recorder.setDefaultEnNum(Recorder.getLevel());
			}
			System.out.println("enNum:"+Recorder.getEnNum());
			System.out.println("level:"+Recorder.getLevel());
			if(list2[box2.getSelectedIndex()].equals("慢"))
				Recorder.setspeedNum(0.8);
			else if(list2[box2.getSelectedIndex()].equals("中"))
				Recorder.setspeedNum(1);
			else if(list2[box2.getSelectedIndex()].equals("快"))
				Recorder.setspeedNum(1.2);
			if(list3[box3.getSelectedIndex()].equals("慢"))
				Recorder.setspeedNumShot(0.8);
			else if(list3[box3.getSelectedIndex()].equals("中"))
				Recorder.setspeedNumShot(1);
			else if(list3[box3.getSelectedIndex()].equals("快"))
				Recorder.setspeedNumShot(1.2);
			setwin.dispose();
		} 
		if(arg0.getActionCommand().equals("common")) {
			System.out.println("unable");
			box2.setEnabled(false);
			box3.setEnabled(false);
			tx.setEnabled(false);
		}
		if(arg0.getActionCommand().equals("customize")) {
			System.out.println("able");
			SetMenu.flag=1;
			box2.setEnabled(true);
			box3.setEnabled(true);
			tx.setEnabled(true);
			tx.setText(null);
		}
		if(arg0.getActionCommand().equals("no")){
			setwin.dispose();
		}
	}
}
