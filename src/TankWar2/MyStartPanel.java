package TankWar2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MyStartPanel extends JPanel implements Runnable{
	int times = 0;

	public void paint(Graphics g) {
		super.paint(g);
//		g.fillRect(0, 0, 400, 300);
		g.fillRect(0, 0, 960, 720);
		// 提示信息

		if (times % 2 == 0) {
			g.setColor(Color.yellow);
			// 开关信息的字体
			Font myFont = new Font("华文新魏", Font.BOLD, 60);
			g.setFont(myFont);
			g.drawString("坦 克 大 战", 320, 350);
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			// 休眠
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			times++;
			// 重画
			this.repaint();
		}
	}
}
