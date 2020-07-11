package TankWar2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;

public class ScoreList implements ActionListener {
	JFrame board = null;
	JPanel jp1 = null, jp2 = null, jp3 = null;
	JLabel lb = null;
	JTextField tf = null;
	JButton bt = null;
	FileWriter fw = null;
	BufferedWriter bw = null;
	FileReader fr = null;
	BufferedReader br = null;
	int score=0;

	public ScoreList(int score) {
		this.score=score;
		board = new JFrame("ScoreList");
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		lb = new JLabel("输入你的名字");
		lb.setFont(new Font("方正舒体", Font.BOLD, 20));
		jp1.add(lb, BorderLayout.CENTER);
		bt = new JButton("确定");
		jp3.add(bt);
		tf = new JTextField(15);
		jp2.add(tf);
		board.setLayout(new GridLayout(3, 1));
		board.add(jp1);
		board.add(jp2);
		board.add(jp3);
		board.setSize(300, 180);
		board.setVisible(true);
		bt.addActionListener(this);
		bt.setActionCommand("yes");
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("yes")) {
			String n=null; int i=0; int num=0;
			String[][]list=new String[10][2];
			try {
				fr = new FileReader("./TGame.confg");
				br=new BufferedReader(fr);
				while((n=br.readLine())!=null) {
					String[]row=n.split(" ");
					list[i][0]=row[0]; list[i][1]=row[1];
					System.out.println(n);
					System.out.println(row[0]+" "+row[1]+" while读取row");
					System.out.println(list[i][0]+" "+list[i][1]+" while读取list");
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
			//对分数进行排序
			try {
				fw=new FileWriter("./TGame.confg");
				bw=new BufferedWriter(fw);
				num=i--;
				System.out.println(num);
				if(num==0) {
					list[0][0]=tf.getText();
					list[0][1]=Integer.toString(score);
					System.out.println(tf.getText()+" "+Integer.toString(score)+" if判断");
					num++;
				}
				else if(num<10) {
					list[num][0]=tf.getText();
					list[num][1]=Integer.toString(score);
					for(int k=0;k<=num-1;k++)
						for(int h=k+1;h<=num;h++) {
							if(Integer.parseInt(list[k][1])<Integer.parseInt(list[h][1])) {
								String[] temp=new String[2];
								temp=list[h];
								list[h]=list[k];
								list[k]=temp;
							}
						}
					num++;
				}else if(num==10) {
					for(int j=0;j<10;j++) {
						if(score>Integer.parseInt(list[j][1])) {
							for(int k=8;k>=j;k--) {
								list[k+1][0]=list[k][0];
								list[k+1][1]=list[k][1];
							}
							list[j][0]=tf.getText();
							list[j][1]=Integer.toString(score);
							break;
						}
					}
				}
				for(int j=0;j<num;j++) {
					bw.write(list[j][0]+" "+list[j][1]+"\r\n");
					System.out.println(list[j][0]+" "+list[j][1]+" for循环输出");
				}
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			} finally {
				try {
					bw.close();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ScoreList_Graphic slg=new ScoreList_Graphic();
			slg.showScore();
			board.dispose();
		}
	}

}
