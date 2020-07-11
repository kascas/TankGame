package TankWar2;

import java.util.Vector;

public class Hero extends Tank{

	//�ӵ�
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
	
	//����
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//����һ���ӵ�
			s=new Shot(x+22,y-1,0,0);
			//���ӵ���������
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
		//�����ӵ��߳�
		Thread t=new Thread(s);
		t.start();
		
	}
	//̹�������ƶ�
	public void moveUp()
	{
		y-=this.speed;
	}
	//̹�������ƶ�
	public void moveRight()
	{
		x+=this.speed;
	}
	
	//̹�������ƶ�
	public void moveDown()
	{
		y+=this.speed;
	}
	
	//����
	public void moveLeft()
	{
		x-=this.speed;
	}
}
