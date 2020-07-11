package TankWar2;
/**
 * 功能:坦克游戏的5.0
 * 1.画出坦克.
 * 2.我的坦克可以上下左右移动
 * 3.可以发射子弹,子弹连发(最多5)
 * 4.当我的坦克击中敌人坦克时，敌人就消失(爆炸的效果)
 * 5.我被击中后，显示爆炸效果
 * 6.防止敌人坦克重叠运动(*)
 *    6.1决定把判断是否碰撞的函数写到EnemyTank类
 * 7.可以分关(*)
 * 	  7.1做一个开始的Panle,它是一个空的
 *    7.2闪烁效果
 * 8.可以在玩游戏的时候暂停和继续（*）
 * 	  8.1当用户点击暂停时，子弹的速度和坦克速度设为0,并让坦克的方向不要变化
 * 9.可以记录玩家的成绩（*）
 *    9.1用文件流.
 *    9.2单写一个记录类，完成对玩家记录
 *    9.3先完成保存共击毁了多少辆敌人坦克的功能.
 *    9.4存盘退出游戏,可以记录当时的敌人坦克坐标，并可以恢复
 * 10.java如何操作声音文件（*）
 * 		10.1对声音文件的操作
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MyTankGame3 extends JFrame implements ActionListener {

	MyPanel mp = null;
	// 定义一个开始面板
	MyStartPanel msp = null;
	// 作出我需要的菜单
	JMenuBar jmb = null;
	// 开始游戏
	JMenu jm1 = null;
	JMenuItem jmi1 = null;
	// 退出系统
	JMenuItem jmi2 = null;
	// 存盘退出
	JMenuItem jmi3 = null;
	JMenuItem jmi4 = null;
	JMenu jm2=null;
	JMenuItem jmi2_1=null;
	JMenu jm3=null;
	
	Thread t=null;
	String mainThread="Main";
	static MyTankGame3 mtg2=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread p=Thread.currentThread();
		p.setName("Main");
		System.out.println(p.getName());
		MyTankGame3 mtg = new MyTankGame3();
		mtg2=mtg;
	}

	// 构造函数
	public MyTankGame3() {
		// mp=new MyPanel();
		// 启动mp线程
		// Thread t=new Thread(mp);
		// t.start();
		// this.add(mp);
		// 注册监听
		// this.addKeyListener(mp);
		// 创建菜单及菜单选项
		jmb = new JMenuBar();
		// 设置快捷方式
		jm1 = new JMenu("游戏(G)"); 
		jm1.setMnemonic('G');
		jm2=new JMenu("设置(T)");
		jm2.setMnemonic('T');
		jm3=new JMenu("帮助(P)");
		jm3.setMnemonic('P');
		
		jmi1 = new JMenuItem("开始新游戏(N)");
		jmi2 = new JMenuItem("退出游戏(E)");
		jmi3 = new JMenuItem("存盘退出游戏(C)");
		jmi4 = new JMenuItem("继续上局游戏(S)");
		jmi2_1=new JMenuItem("设置");
		// 注册监听
		jmi4.addActionListener(this);
		jmi4.setMnemonic('S');
		jmi4.setActionCommand("conGame");

		// 注册监听
		jmi3.addActionListener(this);
		jmi3.setMnemonic('C');
		jmi3.setActionCommand("saveExit");

		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi2.setMnemonic('E');
		// 对jmi1相应
		jmi1.addActionListener(this);
		jmi1.setMnemonic('N');
		jmi1.setActionCommand("newgame");
		
		jmi2_1.addActionListener(this);
		jmi2_1.setActionCommand("set");
		
		jm1.add(jmi1);	jm1.add(jmi2);	jm1.add(jmi3);	jm1.add(jmi4);
		jm2.add(jmi2_1);
		jmb.add(jm1);	jmb.add(jm2);	jmb.add(jm3);//add menubar 

		msp = new MyStartPanel();
		Thread t = new Thread(msp);
		t.start();						//start the game

		this.setJMenuBar(jmb);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(msp);
		this.setSize(1250, 810);
		this.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// 对用户不同的点击作出不同的处理
		if (arg0.getActionCommand().equals("newgame")) {
			// 创建战场面板
			if(mp!=null) {
				while(!mp.ets.isEmpty()) {
					while(!mp.ets.get(0).ss.isEmpty()) {
						mp.ets.get(0).ss.remove(mp.ets.get(0).ss.get(0));
					}
					mp.ets.remove(mp.ets.get(0));
				}
				while(!mp.bls.isEmpty()) {
					mp.bls.remove(mp.bls.get(0));
				}
				while(!mp.ts.isEmpty()) {
					mp.ts.remove(mp.ts.get(0));
				}
				this.removeKeyListener(mp);
				mp.hero=null;
				mp.hero2=null;
				Recorder.reSet();
				this.remove(msp);
				this.remove(mp);
			}
			mp = new MyPanel("newGame");
			// 启动mp线程
			/*Thread*/ t = new Thread(mp);
			
			t.start();
			// 先删除旧的开始面板
			this.remove(msp);
			this.add(mp);
			// 注册监听
			this.addKeyListener(mp);
			// 显示,刷新JFrame
			this.setVisible(true);
		} else if (arg0.getActionCommand().equals("exit")) {
			// 用户点击了退出系统的菜单
			// 保存击毁敌人数量.
			Recorder.keepRecording();
			System.exit(0);
		} // 对存盘退出处理
		else if (arg0.getActionCommand().equals("saveExit")) {
			System.out.println("111");
			System.out.println("mp.ets.size=" + mp.ets.size());
			// 工作
			Recorder rd = new Recorder();
			rd.setEts(Recorder_Vector.ets);
			rd.setBls(Recorder_Vector.bls);
			rd.setRis(Recorder_Vector.ris);
			rd.heroTank=Recorder_Vector.heroTank;
			rd.heroTank2=Recorder_Vector.heroTank2;
			// 保存击毁敌人的数量和敌人的坐标
			rd.keepEverything();
			rd.keepBlock();
			rd.keepRiver();
			// 退出
			System.exit(0);
		} else if (arg0.getActionCommand().equals("conGame")) {
			// 创建战场面板
			mp = new MyPanel("con");
			// 启动mp线程
			/*Thread*/ t = new Thread(mp);
			t.start();
			// 先删除旧的开始面板
			this.remove(msp);
			this.add(mp);
			// 注册监听
			this.addKeyListener(mp);
			// 显示,刷新JFrame
			this.setVisible(true);
		} else if (arg0.getActionCommand().equals("set")) {
			SetMenu set=new SetMenu();
			set.createSetwin();
		} else if (arg0.getActionCommand().equals("help")) {
			
		}
	}

}
