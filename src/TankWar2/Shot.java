package TankWar2;

public class Shot implements Runnable{
	int x;
	int y;
	int direct;
	int speed=9;
	//是否还活着
	boolean isLive=true;
	public Shot(int x,int y,int direct,int type)/////////////////////add type and fix the speed
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
		switch (type) {
		case 0:
			//g.setColor(Color.yellow);
			this.speed=9;
			break;
		case 1:
			this.speed=9;
			//g.setColor(Color.cyan);
			break;
		case 2:
			this.speed=11;
			//g.setColor(Color.pink);
			break;
		case 3:
			this.speed=9;
			//g.setColor(Color.red);
			break;
		case 4:
			this.speed=9;
			//g.setColor(Color.green);
			break;
		case 5:
			this.speed=11;
			//g.setColor(Color.blue);
			break;
		}
		this.speed=(int)(this.speed*Recorder.getspeedNumShot());
	}
	public void run() {
		
		while(true)
		{
			
			try {
				Thread.sleep(25);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			switch(direct)
			{
			case 0:
				//上
				y-=this.speed;
				break;
			case 1:
				x+=this.speed;
				break;
			case 2:
				y+=this.speed;
				break;
			case 3:
				x-=this.speed;
				break;
			}
			
		//	System.out.println("子弹坐标x="+x+" y="+y);
			//子弹何时死亡???
			
			//判断该子弹是否碰到边缘.
			if(x<0||x>960||y<0||y>720)
			{
				this.isLive=false;
				break;
			}
		}
	}
}
