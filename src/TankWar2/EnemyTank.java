package TankWar2;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
	int times=0;
	boolean upMove = false, downMove = false, leftMove = false, rightMove = false, tankShot = false;
	//定义一个向量，可以访问到MyPanel上所有敌人的坦克
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//定义一个向量，可以存放敌人的子弹
	Vector<Shot> ss=new Vector<Shot>();
	//敌人添加子弹，应当在刚刚创建坦克和敌人的坦克子弹死亡后
	int type=0,score=0;
	boolean isStop=false;
//	Vector<Tank> ts=new Vector<Tank>();////////////////
	public EnemyTank(int x,int y,int type)//////////////////////////////
	{
		super(x,y);	
		switch (type) {
		case 0:
			//g.setColor(Color.yellow);
			this.speed=6;
			this.hp=100;
			this.type=type;
			this.score=0;
			break;
		case 1:
			this.speed=6;
			this.hp=100;
			this.type=type;
			this.score=100;
			//g.setColor(Color.cyan);
			break;
		case 2:
			this.speed=6;
			this.hp=100;
			this.type=type;
			this.score=200;
			//g.setColor(Color.pink);
			break;
		case 3:
			this.speed=8;
			this.hp=100;
			this.type=type;
			this.score=300;
			//g.setColor(Color.red);
			break;
		case 4:
			this.speed=6;
			this.hp=400;
			this.type=type;
			this.score=400;
			//g.setColor(Color.green);
			break;
		case 5:
			this.speed=8;
			this.hp=600;
			this.type=type;
			this.score=1000;
			//g.setColor(Color.blue);
			break;
		}
		//不同关卡的速度不同，因此设置不同比率
		if(Recorder.getLevel()>=4&&Recorder.getLevel()<=6)
			this.speed=(int)(this.speed*1.2*Recorder.getspeedNum());
		else if(Recorder.getLevel()>=6&&Recorder.getLevel()<=8)
			this.speed=(int)(this.speed*1.5*Recorder.getspeedNum());
		else this.speed=(int)(this.speed*1*Recorder.getspeedNum());
	}
	
	//得到MyPanel的敌人坦克向量
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
//	public void setTs(Vector<Tank> vv)///////////////
//	{
//		this.ts=vv;
//	}

	public void moveUp()
	{
		y-=this.speed;
	}
	//坦克向右移动
	public void moveRight()
	{
		x+=this.speed;
	}
	
	//坦克向下移动
	public void moveDown()
	{
		y+=this.speed;
	}
	
	//向左
	public void moveLeft()
	{
		x-=this.speed;
	}
	
	public void EnemyTankMove() {
		if (this.upMove)
			if (!this.isTouchTank() && this.y > 0 && !this.isTouchBarrier())
				this.moveUp();
		if (this.rightMove)
			if (!this.isTouchTank() && this.x < 960 - 45 && !this.isTouchBarrier())
				this.moveRight();
		if (this.downMove)
			if (!this.isTouchTank() && this.y < 720 - 45 && !this.isTouchBarrier())
				this.moveDown();
		if (this.leftMove)
			if (!this.isTouchTank() && this.x > 0 && !this.isTouchBarrier())
				this.moveLeft();
	}
	public void run() {///////////////////////////////other都去掉了
		// TODO Auto-generated method stub
		int num=0; int j=0;
		while(true)
		{
			switch(this.direct)
			{
			case 0:
				//说明坦克正在向上
				for(int i=0;i<30;i++)
				{
					
					while(j<this.speed) {
						if(y>0&&!this.isTouchTank()&&!this.isTouchBarrier())
						{
							y--;
						}
						j++;
					}
						num=i;
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				for(int i=0;i<30-num;i++) {
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				break;
			case 1:
				//向右
				for(int i=0;i<30;i++)
				{
					//保证坦克不出边界
					j=0;
					while(j<this.speed) {
						if(x<960-45&&!this.isTouchTank()&&!this.isTouchBarrier())
						{
							x++;
						}
						j++;
					}
						num=i;
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				for(int i=0;i<30-num;i++) {
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				break;
			case 2:
				//向下
				for(int i=0;i<30;i++)
				{
					j=0;
					while(j<this.speed) {
						if(y<720-45&&!this.isTouchTank()&&!this.isTouchBarrier())
						{
							y++;
						}
						j++;
					}
						num=i;
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				for(int i=0;i<30-num;i++) {
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				break;
			case 3:
				//向左
				for(int i=0;i<30;i++)
				{
					j=0;
					while(j<this.speed) {
						if(x>0&&!this.isTouchTank()&&!this.isTouchBarrier())
						{
							x--;
						}
						j++;
					}
						num=i;
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				for(int i=0;i<30-num;i++) {
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				break;
			
			}
			this.times++;
			
			if(times%2==0)
			{
				if(isLive)
				{
					if(ss.size()<5)
					{
						//System.out.println("et.ss.size()<5="+et.ss.size());
						Shot s=null;
						//没有子弹
						//添加
						switch(direct)
						{
						case 0:
							//创建一颗子弹
							 s=new Shot(x+22,y-1,0,this.type);
							//把子弹加入向量
							ss.add(s);
							break;
						case 1:
							s=new Shot(x+45,y+22,1,this.type);
							ss.add(s);
							break;
						case 2:
							 s=new Shot(x+22,y+45,2,this.type);
							ss.add(s);
							break;
						case 3:
							s=new Shot(x-1,y+22,3,this.type);
							ss.add(s);
							break;
						}
						//启动子弹
						Thread t=new Thread(s);
						t.start();
					}
				}
			}
			
			
			//让坦克随机产生一个新的方向
			this.direct=(int)(Math.random()*4);
			
			//判断敌人坦克是否死亡
			if(this.isLive==false||this.isStop)
			{
				//让坦克死亡后，退出线程.
				break;
			}
		}
		
	}
}
