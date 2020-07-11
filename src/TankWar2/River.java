package TankWar2;

import java.util.Vector;

public class River {
	Vector<River> ris=new Vector<River>();
	int x=0,y=0;
	public River(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	public void setRis(Vector<River> vv)
	{
		this.ris=vv;
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
}
