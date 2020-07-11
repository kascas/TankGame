package TankWar2;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

public class Recorder {
		//记录每关有多少敌人
		private static int enNum=20;
		//设置我有多少可以用的人
		private static int myLife=3;
		//记录总共消灭了多少敌人
		private static int allEnNum=0;
		//从文件中恢复记录点
		private static int allScore=0;
		private static int level=1;
		static Vector<Node>  nodes=new Vector<Node>();
		static Vector<Node>  bnode=new Vector<Node>();
		static Vector<Node>  rnode=new Vector<Node>();
		private static int t1Num=0;
		private static int t2Num=0;
		private static int t3Num=0;
		private static int t4Num=0;
		private static int t5Num=0;
		private static int playerNum=1;
		private static double speedNum=1;
		private static double speedNumShot=1;
		private static FileWriter fw=null;
		private static BufferedWriter bw=null;
		private static FileReader fr=null;
		private static BufferedReader br=null;
		
		Vector<EnemyTank> ets=new Vector<EnemyTank>();
		Vector<Tank> ts=new Vector<Tank>();
		Vector<Block> bls=new Vector<Block>();
		Vector<River> ris=new Vector<River>();
		Hero heroTank=null;
		Hero heroTank2=null;
		
		
		
		//得到坦克等存储的数据
		public Vector<Node> getTankNodes()
		{
			try {
				fr=new FileReader("./myRecording.txt");
				br=new BufferedReader(fr);
				String n=""; String m=""; String s=""; String t="";  String l="";
				String t1=""; String t2=""; String t3=""; String t4=""; String t5="";
				String spn=""; String spns=""; String pn="";
				//先读取第一行
				n=br.readLine();
				Recorder.allEnNum=Integer.parseInt(n);
				m=br.readLine();
				Recorder.allScore=Integer.parseInt(m);
				s=br.readLine();
				Recorder.enNum=Integer.parseInt(s);
				t=br.readLine();
				Recorder.myLife=Integer.parseInt(t);
				l=br.readLine();
				Recorder.level=Integer.parseInt(l);
				t1=br.readLine();
				Recorder.t1Num=Integer.parseInt(t1);
				t2=br.readLine();
				Recorder.t2Num=Integer.parseInt(t2);
				t3=br.readLine();
				Recorder.t3Num=Integer.parseInt(t3);
				t4=br.readLine();
				Recorder.t4Num=Integer.parseInt(t4);
				t5=br.readLine();
				Recorder.t5Num=Integer.parseInt(t5);		
				spn=br.readLine();
				Recorder.speedNum=Double.parseDouble(spn);
				spns=br.readLine();
				Recorder.speedNumShot=Double.parseDouble(spns);
				pn=br.readLine();
				Recorder.playerNum=Integer.parseInt(pn);
				while((n=br.readLine())!=null)
				{
					String []xyz=n.split(" "); 		
					Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]),Integer.parseInt(xyz[3]));
					nodes.add(node);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				
				try {
					//后打开则先关闭
					br.close();
					fr.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
			return nodes;
		}
		
		//得到障碍物的数据
		public Vector<Node> getBlock() {
			try {
				fr=new FileReader("./myRecordingBlock.txt");
				br=new BufferedReader(fr);
				String n="";
				while((n=br.readLine())!=null)
				{
					String []xyz=n.split(" "); 		
					Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),0,Integer.parseInt(xyz[2]));
					bnode.add(node);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				try {
					//后打开则先关闭
					br.close();
					fr.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
			return bnode;
		}
		//得到河流的数据
		public Vector<Node> getRiver() {
			try {
				fr=new FileReader("./myRecordingRiver.txt");
				br=new BufferedReader(fr);
				String n="";
				while((n=br.readLine())!=null)
				{
					String []xyz=n.split(" "); 		
					Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),0,0);
					rnode.add(node);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				try {
					//后打开则先关闭
					br.close();
					fr.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
			return rnode;
		}
		//进行数据的存储
		public void keepEverything()
		{
			try {
				
				//创建
				fw=new FileWriter("./myRecording.txt");
				bw=new BufferedWriter(fw);
				bw.write(Recorder.allEnNum+"\r\n"+Recorder.allScore+"\r\n"+Recorder.enNum+"\r\n"+Recorder.myLife+"\r\n"+Recorder.level+"\r\n");
				bw.write(Recorder.t1Num+"\r\n"+Recorder.t2Num+"\r\n"+Recorder.t3Num+"\r\n"+Recorder.t4Num+"\r\n"+Recorder.t5Num+"\r\n");
				bw.write(Recorder.speedNum+"\r\n"+Recorder.speedNumShot+"\r\n"+Recorder.playerNum+"\r\n");
				String herorecode=heroTank.x+" "+heroTank.y+" "+heroTank.direct+" "+"0";
				bw.write(herorecode+"\r\n");
				if(heroTank2!=null) {
					String herorecode2=heroTank2.x+" "+heroTank2.y+" "+heroTank2.direct+" "+"0";
					bw.write(herorecode2+"\r\n");
				}
				System.out.println("size="+ets.size());
				//保存当前活的敌人坦克的坐标和方向
				for(int i=0;i<ets.size();i++)
				{
					//取出第一个坦克
					EnemyTank et=ets.get(i);
					if(et.isLive)
					{
						//活的就保存 坐标，方向，类型
						String recode=et.x+" "+et.y+" "+et.direct+" "+et.type;
						//写入
						bw.write(recode+"\r\n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				//关闭流
				try {
					//后开先关闭
					bw.close();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
		//保存障碍物
		public void keepBlock()
		{
			try {
				fw=new FileWriter("./myRecordingBlock.txt");
				bw=new BufferedWriter(fw);
				for(int i=0;i<bls.size();i++)
				{
					Block bl=bls.get(i);
					if(bl.isLive) {
						String recode=bl.x+" "+bl.y+" "+bl.type;
						bw.write(recode+"\r\n");
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				//关闭流
				try {
					//后开先关闭
					bw.close();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
		//保存河流数据
		public void keepRiver()
		{
			try {
				fw=new FileWriter("./myRecordingRiver.txt");
				bw=new BufferedWriter(fw);
				for(int i=0;i<ris.size();i++)
				{
					River riv=ris.get(i);
					String recode=riv.x+" "+riv.y;
					bw.write(recode+"\r\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				//关闭流
				try {
					//后开先关闭
					bw.close();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
		
		
		//从文件中读取，记录
		public static void getRecoring()
		{
			try {
				fr=new FileReader("./myScore.txt");
				br=new BufferedReader(fr);
				String n=br.readLine();
				allEnNum=Integer.parseInt(n);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				
				try {
					//后打开则先关闭
					br.close();
					fr.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
		
		//把玩家击毁敌人坦克数量保存到文件中
		public static void keepRecording()
		{
			try {
				
				//创建
//				fw=new FileWriter("d:\\myRecording.txt");
				fw=new FileWriter("./myScore.txt");
				bw=new BufferedWriter(fw);
				
				bw.write(allEnNum+"\r\n");
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
			
				//关闭流
				try {
					//后开先关闭
					bw.close();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
		
		public static int getEnNum() {
			return enNum;
		}
		public static void setEnNum(int enNum) {
			Recorder.enNum = enNum;
		}
		public static void setDefaultEnNum(int level) {//每关多少敌人
			switch(level) {
				case 1: Recorder.enNum=20; break;
				case 2: Recorder.enNum=20; break;
				case 3: Recorder.enNum=20; break;
				case 4: Recorder.enNum=25; break;
				case 5: Recorder.enNum=25; break;
				case 6: Recorder.enNum=25; break;
				case 7: Recorder.enNum=30; break;
				case 8: Recorder.enNum=30; break;
			}
		}
		public static int getMyLife() {
			return myLife;
		}
		public static void setMyLife(int myLife) {
			Recorder.myLife = myLife;
		}
		public static double getspeedNum() {//坦克速度比率
			return speedNum;
		}
		public static void setspeedNum(double speedNum) {
			Recorder.speedNum = speedNum;
		}
		public static double getspeedNumShot() {//子弹速度比率
			return speedNumShot;
		}
		public static void setspeedNumShot(double speedNumShot) {
			Recorder.speedNumShot = speedNumShot;
		}
		public static void reduceMyLife() {
			myLife--;
		}////////////////////////////////////////////////////////////////

		public static void reduceEnNum(){//减少敌人数
			enNum--;
		}
		//消灭敌人
		public static void addEnNumRec(){
			allEnNum++;
		}
		public static int getAllEnNum() {
			return allEnNum;
		}
		public static void setAllEnNum(int allEnNum) {
			Recorder.allEnNum = allEnNum;
		}
		public static void addScore(int score) {
			allScore+=score;
		}
		public static int getScore() {
			return allScore;
		}
		public void setEts(Vector<EnemyTank> ets1) {
			this.ets = ets1;
		}
		public void setBls(Vector<Block> bls) {
			this.bls=bls;
		}
		public void setRis(Vector<River> ris) {
			this.ris=ris;
		}
		public Vector<EnemyTank> getEts() {
			return ets;
		}
		public Vector<Block> getBls() {
			return bls;
		}
		public Vector<River> getRis() {
			return ris;
		}
		public static void addTank(int type) {
			switch (type) {
			case 1:
				t1Num++;
				break;
			case 2:
				t2Num++;
				break;
			case 3:
				t3Num++;
				break;
			case 4:
				t4Num++;
				break;
			case 5:
				t5Num++;
				break;

			}
		}
		public static int getTank(int type) {
			int i=0;
			switch (type) {
			case 1:
				i=t1Num;
				break;
			case 2:
				i=t2Num;
				break;
			case 3:
				i=t3Num;
				break;
			case 4:
				i=t4Num;
				break;
			case 5:
				i=t5Num;
				break;

			}
			return i;
		}
		public static void addLevel() {
			level++;
		}
		public static int getLevel() {
			return level;
		}
		public static void setLevel(int level) {
			Recorder.level=level;
		}
		public static void reSet() {
			Recorder.allEnNum = 0;
			Recorder.myLife = 3;
			Recorder.enNum = 20;
			Recorder.allScore=0;
			Recorder.level=1;
		}
		public static int getplayerNum() {
			return playerNum;
		}
		public static void setplayerNum(int playerNum) {
			Recorder.playerNum = playerNum;
		}
}
