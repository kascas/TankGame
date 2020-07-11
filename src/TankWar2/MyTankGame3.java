package TankWar2;
/**
 * ����:̹����Ϸ��5.0
 * 1.����̹��.
 * 2.�ҵ�̹�˿������������ƶ�
 * 3.���Է����ӵ�,�ӵ�����(���5)
 * 4.���ҵ�̹�˻��е���̹��ʱ�����˾���ʧ(��ը��Ч��)
 * 5.�ұ����к���ʾ��ըЧ��
 * 6.��ֹ����̹���ص��˶�(*)
 *    6.1�������ж��Ƿ���ײ�ĺ���д��EnemyTank��
 * 7.���Էֹ�(*)
 * 	  7.1��һ����ʼ��Panle,����һ���յ�
 *    7.2��˸Ч��
 * 8.����������Ϸ��ʱ����ͣ�ͼ�����*��
 * 	  8.1���û������ͣʱ���ӵ����ٶȺ�̹���ٶ���Ϊ0,����̹�˵ķ���Ҫ�仯
 * 9.���Լ�¼��ҵĳɼ���*��
 *    9.1���ļ���.
 *    9.2��дһ����¼�࣬��ɶ���Ҽ�¼
 *    9.3����ɱ��湲�����˶���������̹�˵Ĺ���.
 *    9.4�����˳���Ϸ,���Լ�¼��ʱ�ĵ���̹�����꣬�����Իָ�
 * 10.java��β��������ļ���*��
 * 		10.1�������ļ��Ĳ���
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MyTankGame3 extends JFrame implements ActionListener {

	MyPanel mp = null;
	// ����һ����ʼ���
	MyStartPanel msp = null;
	// ��������Ҫ�Ĳ˵�
	JMenuBar jmb = null;
	// ��ʼ��Ϸ
	JMenu jm1 = null;
	JMenuItem jmi1 = null;
	// �˳�ϵͳ
	JMenuItem jmi2 = null;
	// �����˳�
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

	// ���캯��
	public MyTankGame3() {
		// mp=new MyPanel();
		// ����mp�߳�
		// Thread t=new Thread(mp);
		// t.start();
		// this.add(mp);
		// ע�����
		// this.addKeyListener(mp);
		// �����˵����˵�ѡ��
		jmb = new JMenuBar();
		// ���ÿ�ݷ�ʽ
		jm1 = new JMenu("��Ϸ(G)"); 
		jm1.setMnemonic('G');
		jm2=new JMenu("����(T)");
		jm2.setMnemonic('T');
		jm3=new JMenu("����(P)");
		jm3.setMnemonic('P');
		
		jmi1 = new JMenuItem("��ʼ����Ϸ(N)");
		jmi2 = new JMenuItem("�˳���Ϸ(E)");
		jmi3 = new JMenuItem("�����˳���Ϸ(C)");
		jmi4 = new JMenuItem("�����Ͼ���Ϸ(S)");
		jmi2_1=new JMenuItem("����");
		// ע�����
		jmi4.addActionListener(this);
		jmi4.setMnemonic('S');
		jmi4.setActionCommand("conGame");

		// ע�����
		jmi3.addActionListener(this);
		jmi3.setMnemonic('C');
		jmi3.setActionCommand("saveExit");

		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi2.setMnemonic('E');
		// ��jmi1��Ӧ
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
		// ���û���ͬ�ĵ��������ͬ�Ĵ���
		if (arg0.getActionCommand().equals("newgame")) {
			// ����ս�����
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
			// ����mp�߳�
			/*Thread*/ t = new Thread(mp);
			
			t.start();
			// ��ɾ���ɵĿ�ʼ���
			this.remove(msp);
			this.add(mp);
			// ע�����
			this.addKeyListener(mp);
			// ��ʾ,ˢ��JFrame
			this.setVisible(true);
		} else if (arg0.getActionCommand().equals("exit")) {
			// �û�������˳�ϵͳ�Ĳ˵�
			// ������ٵ�������.
			Recorder.keepRecording();
			System.exit(0);
		} // �Դ����˳�����
		else if (arg0.getActionCommand().equals("saveExit")) {
			System.out.println("111");
			System.out.println("mp.ets.size=" + mp.ets.size());
			// ����
			Recorder rd = new Recorder();
			rd.setEts(Recorder_Vector.ets);
			rd.setBls(Recorder_Vector.bls);
			rd.setRis(Recorder_Vector.ris);
			rd.heroTank=Recorder_Vector.heroTank;
			rd.heroTank2=Recorder_Vector.heroTank2;
			// ������ٵ��˵������͵��˵�����
			rd.keepEverything();
			rd.keepBlock();
			rd.keepRiver();
			// �˳�
			System.exit(0);
		} else if (arg0.getActionCommand().equals("conGame")) {
			// ����ս�����
			mp = new MyPanel("con");
			// ����mp�߳�
			/*Thread*/ t = new Thread(mp);
			t.start();
			// ��ɾ���ɵĿ�ʼ���
			this.remove(msp);
			this.add(mp);
			// ע�����
			this.addKeyListener(mp);
			// ��ʾ,ˢ��JFrame
			this.setVisible(true);
		} else if (arg0.getActionCommand().equals("set")) {
			SetMenu set=new SetMenu();
			set.createSetwin();
		} else if (arg0.getActionCommand().equals("help")) {
			
		}
	}

}
