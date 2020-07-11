package TankWar2;

import java.util.Vector;

public class Hero extends Tank{

	//子弹
	//Shot s=null;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	boolean upMove = false, downMove = false, leftMove = false, rightMove = false, tankShot = false;
	public Hero(int x,int y)
	{
		super(x,y);	
		this.speed=1;
		this.hp=100;
		if(Recorder.getLevel()>=4&&Recorder.getLevel()<=6)
			this.speed=(int)(this.speed*1.2);
		else if(Recorder.getLevel()>=6&&Recorder.getLevel()<=8)
			this.speed=(int)(this.speed*1.5);
	}
	
	//开火
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//创建一颗子弹
			s=new Shot(x+22,y-1,0,0);
			//把子弹加入向量
			ss.add(s);
			break;
		case 1:
			s=new Shot(x+45,y+22,1,0);
			ss.add(s);
			break;
		case 2:
			s=new Shot(x+22,y+45,2,0);
			ss.add(s);
			break;
		case 3:
			s=new Shot(x-1,y+22,3,0);
			ss.add(s);
			break;	
		}
		//启动子弹线程
		Thread t=new Thread(s);
		t.start();
		
	}
	//坦克向上移动
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
}
