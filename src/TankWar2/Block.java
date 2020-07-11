package TankWar2;

import java.util.Vector;

public class Block {
	Vector<Block> bls=new Vector<Block>();
	int x=0,y=0;
	int type=0;
	boolean isSoft=true;
	boolean isLive=true;
	public Block(int x,int y,int type) {
		this.x=x;
		this.y=y;
		this.type=type;
		switch(type) {
			case 0: this.isSoft=true; break;
			case 1: this.isSoft=false; break;
		}
	}
	
	public void setBls(Vector<Block> vv)
	{
		this.bls=vv;
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
