package TankWar2;

import java.util.Vector;

public class Tank {
	//表示坦克的横坐标
		int x=0;
		//坦克纵坐标
		int y=0;
		//坦克方向
		//0表示上 1表示 右 2表示下  3表示左
		int direct=0;
		int color;
		static Vector<Tank> ts=new Vector<Tank>();////////////////
		static Vector<Block> bls=new Vector<Block>();
		static Vector<River> ris=new Vector<River>();
		boolean isLive=true;
		int hp=0;
		
		//坦克的速度
		int speed;
		public Tank(int x,int y)
		{
			this.x=x;
			this.y=y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getDirect() {
			return direct;
		}

		public void setDirect(int direct) {
			this.direct = direct;
		}

		public int getSpeed() {
			return speed;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}
		public void setTs(Vector<Tank> vv)///////////////修改：将之改为了静态，去掉了this，如有bug到这来找
		{
			ts=vv;
		}
		public void setBls(Vector<Block> vv)
		{
			bls=vv;
		}
		public void setRis(Vector<River> vv)
		{
			ris=vv;
		}
		public static Vector<Tank> getEts() {
			return ts;
		}
		public static Vector<Block> getBls() {
			return bls;
		}
		public static Vector<River> getRis() {
			return ris;
		}
		public boolean isTouchTank()//////////////这里所有的enemytank都改成了tank，ets都改成了etss
		{
			boolean b=false;
			switch(this.direct)
			{
			case 0:
				//我的坦克向上
				//取出所有的敌人坦克
				for(int i=0;i<ts.size();i++)
				{
					//取出第一个坦克
					Tank et=ts.get(i);
					//如果不是自己
					if(et!=this)
					{
						//如果敌人的方向是向下或者向上
						if(et.direct==0||et.direct==2)
						{
							//左点
							if(this.x>=et.x&&this.x<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.y++;
								return true;
							}
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.y++;
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							if(this.x>=et.x&&this.x<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.y++;
								return true;
							}
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.y++;
								return true;
							}
						}
					}
				}
				break;
			case 1:
				//坦克向右
				//取出所有的敌人坦克
				for(int i=0;i<ts.size();i++)
				{
					//取出第一个坦克
					Tank et=ts.get(i);
					//如果不是自己
					if(et!=this)
					{
						//如果敌人的方向是向下或者向上
						if(et.direct==0||et.direct==2)
						{
							//上点
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.x--;
								return true;
							}
							//下点 
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.x--;
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.x--;
								return true;
							}
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.x--;
								return true;
							}
						}
					}
				}
				break;
			case 2:
				//坦克向下
				//取出所有的敌人坦克
				for(int i=0;i<ts.size();i++)
				{
					//取出第一个坦克
					Tank et=ts.get(i);
					//如果不是自己
					if(et!=this)
					{
						//如果敌人的方向是向下或者向上
						if(et.direct==0||et.direct==2)
						{
							//我的左点
							if(this.x>=et.x&&this.x<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.y--;
								return true;
							}
							//我的右点
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.y--;
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							if(this.x>=et.x&&this.x<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.y--;
								return true;
							}
							
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.y--;
								return true;
							}
						}
					}
				}
				break;
			case 3:
				//向左
				//取出所有的敌人坦克
				for(int i=0;i<ts.size();i++)
				{
					//取出第一个坦克
					Tank et=ts.get(i);
					//如果不是自己
					if(et!=this)
					{
						//如果敌人的方向是向下或者向上
						if(et.direct==0||et.direct==2)
						{
							//我的上一点
							if(this.x>=et.x&&this.x<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.x++;
								return true;
							}
							//下一点
							if(this.x>=et.x&&this.x<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.x++;
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							//上一点
							if(this.x>=et.x&&this.x<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.x++;
								return true;
							}
							if(this.x>=et.x&&this.x<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.x++;
								return true;
							}
						}
					}
				}
				break;
			}
			
			
			return b;
		}
		
		
		public boolean isTouchBarrier() {
			boolean judge=false;
			
			switch(this.direct)
			{
			case 0:
				//我的坦克向上
				//取出所有的敌人坦克
				for(int i=0;i<bls.size();i++)
				{
					//取出第一个坦克
					Block et=bls.get(i);
					//如果不是自己
					if(this.x+45>et.x&&this.x<et.x+50&&this.y<et.y+50&&this.y+45>et.y+50) {
//						this.y+=1;
						return true;
					}
				}
				for(int i=0;i<ris.size();i++)
				{
					//取出第一个坦克
					River et=ris.get(i);
					//如果不是自己
					if(this.x+45>et.x&&this.x<et.x+50&&this.y<et.y+50&&this.y+45>et.y+50) {
//						this.y+=1;
						return true;
					}
				}
				break;
			case 1:
				//坦克向右
				//取出所有的敌人坦克
				for(int i=0;i<bls.size();i++)
				{
					//取出第一个坦克
					Block et=bls.get(i);
					//如果不是自己
					if(this.y+45>et.y&&this.y<et.y+50&&this.x+45>et.x&&this.x<et.x) {
//						this.x-=1;
						return true;
					}
				}
				for(int i=0;i<ris.size();i++)
				{
					//取出第一个坦克
					River et=ris.get(i);
					//如果不是自己
					if(this.y+45>et.y&&this.y<et.y+50&&this.x+45>et.x&&this.x<et.x) {
//						this.x-=1;
						return true;
					}
				}
				break;
			case 2:
				//坦克向下
				//取出所有的敌人坦克
				for(int i=0;i<bls.size();i++)
				{
					//取出第一个坦克
					Block et=bls.get(i);
					//如果不是自己
					if(this.x+45>et.x&&this.x<et.x+50&&this.y+45>et.y&&this.y<et.y) {
//						this.y-=1;
						return true;
					}
				}
				for(int i=0;i<ris.size();i++)
				{
					//取出第一个坦克
					River et=ris.get(i);
					//如果不是自己
					if(this.x+45>et.x&&this.x<et.x+50&&this.y+45>et.y&&this.y<et.y) {
//						this.y-=1;
						return true;
					}
				}
				break;
			case 3:
				//向左
				//取出所有的敌人坦克
				for(int i=0;i<bls.size();i++)
				{
					//取出第一个坦克
					Block et=bls.get(i);
					//如果不是自己
					if(this.y+45>et.y&&this.y<et.y+50&&this.x<et.x+50&&this.x+45>et.x+50) {
//						this.x+=1;
						return true;
					}
				}
				for(int i=0;i<ris.size();i++)
				{
					//取出第一个坦克
					River et=ris.get(i);
					//如果不是自己
					if(this.y+45>et.y&&this.y<et.y+50&&this.x<et.x+50&&this.x+45>et.x+50) {
//						this.x+=1;
						return true;
					}
				}
				break;
			}
			return judge;
		}
		
}
