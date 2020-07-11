package TankWar2;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
	int times=0;
	boolean upMove = false, downMove = false, leftMove = false, rightMove = false, tankShot = false;
	//����һ�����������Է��ʵ�MyPanel�����е��˵�̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//����һ�����������Դ�ŵ��˵��ӵ�
	Vector<Shot> ss=new Vector<Shot>();
	//��������ӵ���Ӧ���ڸոմ���̹�˺͵��˵�̹���ӵ�������
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
		//��ͬ�ؿ����ٶȲ�ͬ��������ò�ͬ����
		if(Recorder.getLevel()>=4&&Recorder.getLevel()<=6)
			this.speed=(int)(this.speed*1.2*Recorder.getspeedNum());
		else if(Recorder.getLevel()>=6&&Recorder.getLevel()<=8)
			this.speed=(int)(this.speed*1.5*Recorder.getspeedNum());
		else this.speed=(int)(this.speed*1*Recorder.getspeedNum());
	}
	
	//�õ�MyPanel�ĵ���̹������
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
	public void run() {///////////////////////////////other��ȥ����
		// TODO Auto-generated method stub
		int num=0; int j=0;
		while(true)
		{
			switch(this.direct)
			{
			case 0:
				//˵��̹����������
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
				//����
				for(int i=0;i<30;i++)
				{
					//��֤̹�˲����߽�
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
				//����
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
				//����
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
						//û���ӵ�
						//���
						switch(direct)
						{
						case 0:
							//����һ���ӵ�
							 s=new Shot(x+22,y-1,0,this.type);
							//���ӵ���������
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
						//�����ӵ�
						Thread t=new Thread(s);
						t.start();
					}
				}
			}
			
			
			//��̹���������һ���µķ���
			this.direct=(int)(Math.random()*4);
			
			//�жϵ���̹���Ƿ�����
			if(this.isLive==false||this.isStop)
			{
				//��̹���������˳��߳�.
				break;
			}
		}
		
	}
}
