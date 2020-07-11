package TankWar2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;

public class ScoreBoard extends JPanel{
	JFrame f=null;
	public void showInfo(Graphics g) {
		Font fp = new Font("宋体", Font.BOLD, 20);
		g.setFont(fp);
		g.setColor(Color.white); 
		g.drawString("stage "+Recorder.getLevel(), 350, 50);
		this.drawTank(100, 75, g, 1);  g.setColor(Color.white); g.drawString(Recorder.getTank(1)+" ", 200, 100); g.drawString("×", 300, 100); g.drawString("100", 400, 100);g.drawString("=", 500, 100);g.drawString(Integer.toString(100*Recorder.getTank(1)), 600, 100);
		this.drawTank(100, 175, g, 2); g.setColor(Color.white); g.drawString(Recorder.getTank(2)+" ", 200, 200); g.drawString("×", 300, 200); g.drawString("200", 400, 200); g.drawString("=", 500, 200);g.drawString(Integer.toString(200*Recorder.getTank(2)), 600, 200);
		this.drawTank(100, 275, g, 3); g.setColor(Color.white); g.drawString(Recorder.getTank(3)+" ", 200, 300); g.drawString("×", 300, 300); g.drawString("300", 400, 300); g.drawString("=", 500, 300);g.drawString(Integer.toString(300*Recorder.getTank(3)), 600, 300);
		this.drawTank(100, 375, g, 4); g.setColor(Color.white); g.drawString(Recorder.getTank(4)+" ", 200, 400); g.drawString("×", 300, 400); g.drawString("400", 400, 400); g.drawString("=", 500, 400);g.drawString(Integer.toString(400*Recorder.getTank(4)), 600, 400);
		this.drawTank(100, 475, g, 5); g.setColor(Color.white); g.drawString(Recorder.getTank(5)+" ", 200, 500); g.drawString("×", 300, 500); g.drawString("1000", 400,500); g.drawString("=", 500, 500);g.drawString(Integer.toString(1000*Recorder.getTank(5)), 600,500);
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 750, 700);
		this.showInfo(g);
		g.drawLine(25, 550, 710, 550);
		g.drawString("Enemy: "+Recorder.getAllEnNum(), 100, 580);
		g.drawString("Score: "+Recorder.getScore(), 500, 580);
		g.drawString("<按回车键继续>", 300, 600);
	}
	
	public void drawTank(int x, int y, Graphics g, int type) {
		// 判断是什么类型的坦克
		switch (type) {
		case 0:
			g.setColor(Color.yellow);
			break;
		case 1:
			g.setColor(Color.cyan);
			break;
		case 2:
			g.setColor(Color.pink);
			break;
		case 3:
			g.setColor(Color.red);
			break;
		case 4:
			g.setColor(Color.green);
			break;
		case 5:
			g.setColor(Color.blue);
			break;

		}

		// 画出我的坦克(到时再封装成一个函数)
		// 1.画出左边的矩形
		g.fill3DRect(x, y, 12, 45, false);
		// 2.画出右边矩形
		g.fill3DRect(x + 33, y, 12, 45, false);
		// 3.画出中间矩形
		g.fill3DRect(x + 12, y + 9, 21, 27, false);
		// 4.画出圆形
		g.fillOval(x + 15, y + 15, 15, 15);
		// 5.画出线
		// g.drawLine(x + 15, y + 15, x + 15, y);
		g.fill3DRect(x + 22, y, 2, 22, false);

	}

	public void showScore() {
//		ScoreBoard b=new ScoreBoard();
		f=new JFrame("Your Score");
		f.setSize(750,700);
		f.setVisible(true);
		f.add(this);
		f.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					f.dispose();
					if(Recorder.getLevel()==8) {
						ScoreList sl=new ScoreList(Recorder.getScore());
					}else if(Recorder.getMyLife()>0)
					{
						Recorder.addLevel();
						MyTankGame3.mtg2.remove(MyPanel.mp2);
						MyPanel mp3 = new MyPanel("newGame");
						Thread t = new Thread(mp3);
						t.start();
						// 先删除旧的开始面板
						MyTankGame3.mtg2.add(mp3);
						// 注册监听
						MyTankGame3.mtg2.addKeyListener(mp3);
						// 显示,刷新JFrame
						MyTankGame3.mtg2.setVisible(true);
					}
					else{
						ScoreList sl=new ScoreList(Recorder.getScore());
//						System.exit(0);
					}
				}
					
			}
		});
	}
}
