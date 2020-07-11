package TankWar2;

import java.util.Vector;

public class Recorder_Vector {
	static Vector<EnemyTank> ets=new Vector<EnemyTank>();
	static Vector<Tank> ts=new Vector<Tank>();
	static Vector<Block> bls=new Vector<Block>();
	static Vector<River> ris=new Vector<River>();
	static Hero heroTank=null;
	static Hero heroTank2=null;
	public static void setEts(Vector<EnemyTank> a) {
		ets = a;
	}
	public static void setBls(Vector<Block> a) {
		bls=a;
	}
	public static void setRis(Vector<River> a) {
		ris=a;
	}
	
}
