package TankWar2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;
import java.io.*;
import javax.swing.JFrame;

public class ScoreList_Graphic extends JPanel{
	JFrame f=null;
	FileReader fr = null;
	BufferedReader br = null;
	
	public void showInfo(Graphics g) {
		Font fp1 = new Font("宋体", Font.BOLD, 20);
		g.setFont(fp1);
		g.setColor(Color.white); 
		g.drawString("积分榜",350, 50);
		String n=null; int i=0;
		String[][]list=new String[10][2];
		try {
			fr = new FileReader("./TGame.confg");
			br=new BufferedReader(fr);
			while((n=br.readLine())!=null) {
				String[]row=n.split(" ");
				list[i][0]=row[0]; list[i][1]=row[1];
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Font fp2 = new Font("宋体", Font.BOLD, 20);
		g.setFont(fp2);
		for(int j=0;j<i;j++) {
			g.drawString(Integer.toString(j+1), 175,50*(j+3));
			g.drawString(list[j][0], 350, 50*(j+3));
			g.drawString(list[j][1], 525, 50*(j+3));
		}
		
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 750, 700);
		this.showInfo(g);
	}
	public void showScore() {
		f=new JFrame("Score List");
		f.setSize(750,700);
		f.setVisible(true);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
