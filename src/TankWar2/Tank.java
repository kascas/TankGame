package TankWar2;

import java.util.Vector;

public class Tank {
	//��ʾ̹�˵ĺ�����
		int x=0;
		//̹��������
		int y=0;
		//̹�˷���
		//0��ʾ�� 1��ʾ �� 2��ʾ��  3��ʾ��
		int direct=0;
		int color;
		static Vector<Tank> ts=new Vector<Tank>();////////////////
		static Vector<Block> bls=new Vector<Block>();
		static Vector<River> ris=new Vector<River>();
		boolean isLive=true;
		int hp=0;
		
		//̹�˵��ٶ�
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
		public void setTs(Vector<Tank> vv)///////////////�޸ģ���֮��Ϊ�˾�̬��ȥ����this������bug��������
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
		public boolean isTouchTank()//////////////�������е�enemytank���ĳ���tank��ets���ĳ���etss
		{
			boolean b=false;
			switch(this.direct)
			{
			case 0:
				//�ҵ�̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<ts.size();i++)
				{
					//ȡ����һ��̹��
					Tank et=ts.get(i);
					//��������Լ�
					if(et!=this)
					{
						//������˵ķ��������»�������
						if(et.direct==0||et.direct==2)
						{
							//���
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
				//̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<ts.size();i++)
				{
					//ȡ����һ��̹��
					Tank et=ts.get(i);
					//��������Լ�
					if(et!=this)
					{
						//������˵ķ��������»�������
						if(et.direct==0||et.direct==2)
						{
							//�ϵ�
							if(this.x+45>=et.x&&this.x+45<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.x--;
								return true;
							}
							//�µ� 
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
				//̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<ts.size();i++)
				{
					//ȡ����һ��̹��
					Tank et=ts.get(i);
					//��������Լ�
					if(et!=this)
					{
						//������˵ķ��������»�������
						if(et.direct==0||et.direct==2)
						{
							//�ҵ����
							if(this.x>=et.x&&this.x<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.y--;
								return true;
							}
							//�ҵ��ҵ�
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
				//����
				//ȡ�����еĵ���̹��
				for(int i=0;i<ts.size();i++)
				{
					//ȡ����һ��̹��
					Tank et=ts.get(i);
					//��������Լ�
					if(et!=this)
					{
						//������˵ķ��������»�������
						if(et.direct==0||et.direct==2)
						{
							//�ҵ���һ��
							if(this.x>=et.x&&this.x<=et.x+45&&this.y>=et.y&&this.y<=et.y+45)
							{
//								this.x++;
								return true;
							}
							//��һ��
							if(this.x>=et.x&&this.x<=et.x+45&&this.y+45>=et.y&&this.y+45<=et.y+45)
							{
//								this.x++;
								return true;
							}
						}
						if(et.direct==3||et.direct==1)
						{
							//��һ��
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
				//�ҵ�̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<bls.size();i++)
				{
					//ȡ����һ��̹��
					Block et=bls.get(i);
					//��������Լ�
					if(this.x+45>et.x&&this.x<et.x+50&&this.y<et.y+50&&this.y+45>et.y+50) {
//						this.y+=1;
						return true;
					}
				}
				for(int i=0;i<ris.size();i++)
				{
					//ȡ����һ��̹��
					River et=ris.get(i);
					//��������Լ�
					if(this.x+45>et.x&&this.x<et.x+50&&this.y<et.y+50&&this.y+45>et.y+50) {
//						this.y+=1;
						return true;
					}
				}
				break;
			case 1:
				//̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<bls.size();i++)
				{
					//ȡ����һ��̹��
					Block et=bls.get(i);
					//��������Լ�
					if(this.y+45>et.y&&this.y<et.y+50&&this.x+45>et.x&&this.x<et.x) {
//						this.x-=1;
						return true;
					}
				}
				for(int i=0;i<ris.size();i++)
				{
					//ȡ����һ��̹��
					River et=ris.get(i);
					//��������Լ�
					if(this.y+45>et.y&&this.y<et.y+50&&this.x+45>et.x&&this.x<et.x) {
//						this.x-=1;
						return true;
					}
				}
				break;
			case 2:
				//̹������
				//ȡ�����еĵ���̹��
				for(int i=0;i<bls.size();i++)
				{
					//ȡ����һ��̹��
					Block et=bls.get(i);
					//��������Լ�
					if(this.x+45>et.x&&this.x<et.x+50&&this.y+45>et.y&&this.y<et.y) {
//						this.y-=1;
						return true;
					}
				}
				for(int i=0;i<ris.size();i++)
				{
					//ȡ����һ��̹��
					River et=ris.get(i);
					//��������Լ�
					if(this.x+45>et.x&&this.x<et.x+50&&this.y+45>et.y&&this.y<et.y) {
//						this.y-=1;
						return true;
					}
				}
				break;
			case 3:
				//����
				//ȡ�����еĵ���̹��
				for(int i=0;i<bls.size();i++)
				{
					//ȡ����һ��̹��
					Block et=bls.get(i);
					//��������Լ�
					if(this.y+45>et.y&&this.y<et.y+50&&this.x<et.x+50&&this.x+45>et.x+50) {
//						this.x+=1;
						return true;
					}
				}
				for(int i=0;i<ris.size();i++)
				{
					//ȡ����һ��̹��
					River et=ris.get(i);
					//��������Լ�
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
