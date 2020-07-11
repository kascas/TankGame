package TankWar2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MyPanel extends JPanel implements KeyListener, Runnable {
	// ����һ���ҵ�̹��
	Hero hero = null;
	Hero hero2 = null;
	// ������˵�̹����
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	Vector<Tank> ts = new Vector<Tank>();///////////////////////
	Vector<Node> nodes = new Vector<Node>();
	Vector<Node> rnode = new Vector<Node>();
	Vector<Node> bnode = new Vector<Node>();
	// ����ը������
	Vector<Bomb> bombs = new Vector<Bomb>();
	Vector<Block> bls = new Vector<Block>();
	Vector<River> ris = new Vector<River>();
	int tankSwitch[] = new int[2];
	int enSize = 20;
	// ��������ͼƬ,����ͼƬ�������һ��ը��
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	int i = 0;
//	Thread s = null;

	static MyPanel mp2 = null;
	// ���캯��

	public MyPanel(String flag) {
		mp2 = this;
		if (flag.equals("newGame")) {// ��ʼ����Ϸ
			// ���ſ�ս����
			AePlayWave apw = new AePlayWave("./111.wav");
			apw.start();
			// ����level���ɵ�ͼ
			mapMaker(Recorder.getLevel());
			if (SetMenu.flag == 0) {
				Recorder.setDefaultEnNum(Recorder.getLevel());
			}
			hero = new Hero(600, 650);
			hero.setTs(ts);
			hero.setBls(bls);
			hero.setRis(ris);
			ts.add((Tank) hero);
			// ˫����ս������hero2
			if (Recorder.getplayerNum() == 2) {
				hero2 = new Hero(700, 650);
				hero2.setTs(ts);
				hero2.setBls(bls);
				hero2.setRis(ris);
				ts.add((Tank) hero2);
			}
			Thread s = new Thread() {
				int enSize = Recorder.getEnNum();

				public void run() {
					//��ʱ����̹��
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					// ��ʼ�����˵�̹��
					for (int j = 0; j < enSize / 5; j++) {
						
						for (int i = 0; i < 5; i++) {
							// ������ɵз�̹������
							int type = 1;
							type = (int) (Math.random() * 10);
							if (type <= 6)
								type = 1;
							else if (Recorder.getLevel() != 3 && Recorder.getLevel() != 6 && Recorder.getLevel() != 8)
								do {
									type = (int) (Math.random() * 5);
								} while (type <= 1);
							else if (Recorder.getLevel() == 3 || Recorder.getLevel() == 6 || Recorder.getLevel() == 8)
								do {
									type = (int) (Math.random() * 6);
								} while (type <= 1);
							// �����з�̹��
							EnemyTank et = new EnemyTank(160*(i+1), 50, type);
//							et.setColor(0);
							et.setDirect((int) (Math.random() * 4));
							// ���з�̹����ӽ�����
							ets.add(et);
							ts.add((Tank) et);
							// ��������ӽ��з�̹��
							et.setEts(ets);
							et.setTs(ts);
							et.setBls(bls);
							et.setRis(ris);
							// �������˵�̹��
							Thread t = new Thread(et);
							t.start();
						}
						// ÿ��10�����ɵз�̹��
						try {
							Thread.sleep(10000);
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
					}
				}

			};
			// �߳̿�ʼ
			s.start();
		} else {
			System.out.println("������");
			//Vector�������ݵĶ�ȡ
			Recorder rc = new Recorder();
			nodes = rc.getTankNodes();
			bnode = rc.getBlock();
			rnode = rc.getRiver();
			//hero��hero2������
			Node heroNode = nodes.get(0);
			hero = new Hero(heroNode.x, heroNode.y);
			hero.setDirect(heroNode.direct);
			hero.setTs(ts);
			hero.setBls(bls);
			hero.setRis(ris);
			ts.add((Tank) hero);
			if (Recorder.getplayerNum() == 2) {
				Node heroNode2 = nodes.get(1);
				hero2 = new Hero(heroNode2.x, heroNode2.y);
				hero2.setTs(ts);
				hero2.setBls(bls);
				hero2.setRis(ris);
				ts.add((Tank) hero2);
			}
			// ��ʼ�����˵�̹��
			Thread s = new Thread() {
				public void run() {
					int judge=1;
					if(Recorder.getplayerNum() == 2)
						judge=2;
					//����ʱ���ڵ�̹�˵�����
					for (int i = judge; i < nodes.size(); i++) {
						Node node = nodes.get(i);
						// ����һ�����˵�̹�˶���
						EnemyTank et = new EnemyTank(node.x, node.y, node.type);
						et.setDirect(node.direct);
						// ��MyPanel�ĵ���̹�����������õ���̹��
						et.setEts(ets);
						et.setTs(ts);
						// �������˵�̹��
						Thread t = new Thread(et);
						t.start();
						ets.add(et);
						ts.add((Tank) et);
						et.setEts(ets);
						et.setTs(ts);
						et.setBls(bls);
						et.setRis(ris);
					}
					//�ϰ�������
					for (int i = 0; i < bnode.size(); i++) {
						Node node = bnode.get(i);
						Block b = new Block(node.x, node.y, node.type);
						bls.add(b);
						b.setBls(bls);
					}
					for (int i = 0; i < rnode.size(); i++) {
						Node node = rnode.get(i);
						River r = new River(node.x, node.y);
						ris.add(r);
						r.setRis(ris);
					}
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					//���ؿ���ʣ���̹����������
					enSize = Recorder.getEnNum()-ets.size();
					for (int j = 0; j < enSize / 5; j++) {
						for (int i = 0; i < 5; i++) {
							// ������ɵз�̹������
							int type = 1;
							type = (int) (Math.random() * 10);
							if (type <= 6)
								type = 1;
							else if (Recorder.getLevel() != 3 && Recorder.getLevel() != 6 && Recorder.getLevel() != 8)
								do {
									type = (int) (Math.random() * 5);
								} while (type <= 1);
							else if (Recorder.getLevel() == 3 || Recorder.getLevel() == 6 || Recorder.getLevel() == 8)
								do {
									type = (int) (Math.random() * 6);
								} while (type <= 1);
							// �����з�̹��
							EnemyTank et = new EnemyTank(160*(i+1), 50, type);
//							et.setColor(0);
							et.setDirect((int) (Math.random() * 4));
							// ���з�̹����ӽ�����
							ets.add(et);
							ts.add((Tank) et);
							// ��������ӽ��з�̹��
							et.setEts(ets);
							et.setTs(ts);
							et.setBls(bls);
							et.setRis(ris);
							// �������˵�̹��
							Thread t = new Thread(et);
							t.start();
						}
						// ÿ��10�����ɵз�̹��
						try {
							Thread.sleep(10000);
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
					}
				}
			};
			s.start();
		}
		try {
			image1 = ImageIO.read(new File("bomb_1.gif"));
			image2 = ImageIO.read(new File("bomb_2.gif"));
			image3 = ImageIO.read(new File("bomb_3.gif"));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

// 			��ʼ��ͼƬd
//			image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//			image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//			image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	
	// ������ʾ��Ϣ
	public void showInfo(Graphics g) {
		// ������ʾ��Ϣ̹��(��̹�˲�����ս��)
		Font f = new Font("����", Font.BOLD, 20);
		g.setFont(f);
		this.drawTank(1000, 600, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum() + "", 1050, 620);
		this.drawTank(1100, 600, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife() + "", 1150, 620);
		// ������ҵ��ܳɼ�
		g.setColor(Color.black);
		g.drawString("�����ܳɼ�", 1000, 100);
		this.drawTank(1000, 125, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(/* Recorder.getAllEnNum() */ Recorder.getScore() + "", 1100, 150);
	}

	// ����paint
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 960, 720);
		// ������ʾ��Ϣ
		this.showInfo(g);
		// ������ͬ���͵�ǽ
		for (int i = 0; i < bls.size(); i++) {
			Block b = bls.get(i);
			if (b.isLive) {
				this.drawBlock(b.getX(), b.getY(), g, b.type);
			} else {
				bls.remove(b);
			}
		}
		// ��������
		for (int i = 0; i < ris.size(); i++) {
			River b = ris.get(i);
			this.drawRiver(b.getX(), b.getY(), g);
		}
		// �����Լ���̹��
		if (hero.isLive) {
			this.drawTank(hero.getX(), hero.getY(), g, this.hero.direct, 0);
		}

		// ��ss��ȡ��ÿ���ӵ���������
		for (int i = 0; i < hero.ss.size(); i++) {
			Shot myShot = hero.ss.get(i);
			// �����ӵ�,����һ���ӵ�
			if (myShot != null && myShot.isLive == true) {
				g.draw3DRect(myShot.x, myShot.y, 3, 3, true);
			}
			if (myShot.isLive == false) {
				// ��ss��ɾ�������ӵ�
				hero.ss.remove(myShot);
			}
		}
		// ���˫����ս������hero2
		if (hero2 != null) {
			if (hero2.isLive) {
				this.drawTank(hero2.getX(), hero2.getY(), g, this.hero2.direct, 0);
			}
			// ��ss��ȡ��ÿ���ӵ���������
			for (int i = 0; i < hero2.ss.size(); i++) {
				Shot myShot = hero2.ss.get(i);
				// �����ӵ�,����һ���ӵ�
				if (myShot != null && myShot.isLive == true) {
					g.draw3DRect(myShot.x, myShot.y, 3, 3, true);
				}
				if (myShot.isLive == false) {
					// ��ss��ɾ�������ӵ�
					hero2.ss.remove(myShot);
				}
			}
		}
		// ����ը��
		for (int i = 0; i < bombs.size(); i++) {
			// ȡ��ը��
			Bomb b = bombs.get(i);
			if (b.life > 6) {
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 3) {
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else {
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			// ��b������ֵ��С
			b.lifeDown();
			// ���ը������ֵΪ0,�ͰѸ�ը����bombs����ȥ��
			if (b.life == 0) {
				bombs.remove(b);
			}
		}

		// �������˵�̹��
		for (int i = 0; i < ets.size(); i++) {
			EnemyTank et = ets.get(i);
			if (et.isLive) {
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), et.type);/////////////////////
				// �ٻ������˵��ӵ�
				// System.out.println("̹���ӵ���:"+et.ss.size());
				for (int j = 0; j < et.ss.size(); j++) {
					// ȡ���ӵ�
					Shot enemyShot = et.ss.get(j);
					if (enemyShot.isLive) {
						// System.out.println("�� "+i+"̹�˵� "+j+"���ӵ�x="+enemyShot.x);
						g.draw3DRect(enemyShot.x, enemyShot.y, 3, 3, true);//////////////
					} else {
						// ������˵�̹�������ʹ�Vectorȥ��
						et.ss.remove(enemyShot);
					}
				}
			}
		}

	}

	// ���˵�̹���Ƿ������
	public void hitMe() {
		// ȡ��ÿһ�����˵�̹��
		for (int i = 0; i < this.ets.size(); i++) {
			// ȡ��̹��
			EnemyTank et = ets.get(i);
			// ȡ��ÿһ���ӵ�
			for (int j = 0; j < et.ss.size(); j++) {
				// ȡ���ӵ�
				Shot enemyShot = et.ss.get(j);
				if (hero.isLive) {
					// ����е�
					if (this.hitTank(enemyShot, hero)) ///////////////////////////////////////////
					{
						Recorder.reduceMyLife();
						ts.remove(hero);
						if (Recorder.getMyLife() >= 1) {
							// �µ�hero�߳�
							Thread newThread = new Thread() {
								public void run() {
									try {
										Thread.sleep(500);
									} catch (Exception e) {
										e.printStackTrace();
										// TODO: handle exception
									}
									// ���¹���hero
									hero = new Hero(600, 650);
									hero.setTs(ts);
									hero.setBls(bls);
									hero.setRis(ris);
									ts.add((Tank) hero);
								}
							};
							newThread.start();
						}
					}
				}
				//��Ҷ��Ĺ���ԭ��ͬ���һ
				if (hero2 != null) {
					if (hero2.isLive) {
						if (this.hitTank(enemyShot, hero2)) ///////////////////////////////////////////
						{
							Recorder.reduceMyLife();
							ts.remove(hero2);
							if (Recorder.getMyLife() >= 1) {
								Thread newThread = new Thread() {
									public void run() {
										try {
											Thread.sleep(500);
										} catch (Exception e) {
											e.printStackTrace();
											// TODO: handle exception
										}
										hero2 = new Hero(700, 650);
										hero2.setTs(ts);
										hero2.setBls(bls);
										ts.add((Tank) hero2);
									}
								};
								newThread.start();
							}
						}
					}
				}
			}
		}
	}

	// �ж��ҵ��ӵ��Ƿ���е��˵�̹��
	public void hitEnemyTank() {
		// �ж��Ƿ���е��˵�̹��
		for (int i = 0; i < hero.ss.size(); i++) {
			// ȡ���ӵ�
			Shot myShot = hero.ss.get(i);
			// �ж��ӵ��Ƿ���Ч
			if (myShot.isLive) {
				// ȡ��ÿ��̹�ˣ������ж�
				for (int j = 0; j < ets.size(); j++) {
					// ȡ��̹��
					EnemyTank et = ets.get(j);
//					if (et.isLive) {
					if (this.hitTank(myShot, et)) {
						if (!et.isLive) {
							// ���ٵ�������
							Recorder.reduceEnNum();
							// �����ҵļ�¼
							Recorder.addEnNumRec();
							// ���ӷ���
							Recorder.addScore(et.score);
							Recorder.addTank(et.type);
							ets.remove(et);/////////////////////
							ts.remove(et);
						}
					}
					// }
				}
			}
		}
		if (hero2 != null) {
			for (int i = 0; i < hero2.ss.size(); i++) {
				// ȡ���ӵ�
				Shot myShot = hero2.ss.get(i);
				// �ж��ӵ��Ƿ���Ч
				if (myShot.isLive) {
					// ȡ��ÿ��̹�ˣ������ж�
					for (int j = 0; j < ets.size(); j++) {
						// ȡ��̹��
						EnemyTank et = ets.get(j);
//						if (et.isLive) {
						if (this.hitTank(myShot, et)) {
							if (!et.isLive) {
								// ���ٵ�������
								Recorder.reduceEnNum();
								// �����ҵļ�¼
								Recorder.addEnNumRec();
								// ���ӷ���
								Recorder.addScore(et.score);
								Recorder.addTank(et.type);
								ets.remove(et);/////////////////////
								ts.remove(et);
							}
						}
						// }
					}
				}
			}
		}
	}

	// дһ������ר���ж��ӵ��Ƿ���е���̹��
	public boolean hitTank(Shot s, Tank et) {
		boolean b2 = false;
		// �жϸ�̹�˵ķ���
		switch (et.direct) {
		// �������̹�˵ķ������ϻ�������
		case 0:
		case 2:
			if (s.x > et.x && s.x < et.x + 45 && s.y > et.y && s.y < et.y + 45) {
				// ����
				// �ӵ�����
				s.isLive = false;
				// ����̹������
				et.hp -= 100;
				if (et.hp <= 0)
					et.isLive = false;
				b2 = true;
				// ����һ��ը��,����Vector
				Bomb b = new Bomb(et.x, et.y);
				// ����Vector
				bombs.add(b);
			}
			break;
		case 1:
		case 3:
			if (s.x > et.x && s.x < et.x + 45 && s.y > et.y && s.y < et.y + 45) {
				// ����
				// �ӵ�����
				s.isLive = false;
				// ����̹������
//				et.isLive = false;
				et.hp -= 100;
				if (et.hp <= 0)
					et.isLive = false;
				b2 = true;
				// ����һ��ը��,����Vector
				Bomb b = new Bomb(et.x, et.y);
				// ����Vector
				bombs.add(b);
			}
		}
		return b2;
	}
	//�ж��Ƿ����ǽ
	public void hitBlock() {
		for (int k = 0; k < bls.size(); k++) {
			Block bl = bls.get(k);
			//��ÿһ��̹�˵��ӵ������ж�
			for (int i = 0; i < this.ets.size(); i++) {
				EnemyTank et = ets.get(i);
				for (int j = 0; j < et.ss.size(); j++) {
					// ȡ���ӵ�
					Shot enemyShot = et.ss.get(j);
					if (bl.type == 0 && enemyShot.x > bl.x && enemyShot.x < bl.x + 50 && enemyShot.y > bl.y
							&& enemyShot.y < bl.y + 50) {
						bl.isLive = false;
						// bls.remove(bl);
						enemyShot.isLive = false;

						Bomb b = null;
						switch (enemyShot.direct) {
						case 0:
							b = new Bomb(bl.x, bl.y + 45);
							break;
						case 1:
							b = new Bomb(bl.x - 25, bl.y);
							break;
						case 2:
							b = new Bomb(bl.x, bl.y);
							break;
						case 3:
							b = new Bomb(bl.x + 45, bl.y);
							break;
						}
//						et.ss.remove(enemyShot);
						bombs.add(b);
						System.out.println("hit the Block0");
					} else if (bl.type == 1 && enemyShot.x > bl.x && enemyShot.x < bl.x + 50 && enemyShot.y > bl.y
							&& enemyShot.y < bl.y + 50) {
						enemyShot.isLive = false;

						Bomb b = null;
						switch (enemyShot.direct) {
						case 0:
							b = new Bomb(bl.x, bl.y + 45);
							break;
						case 1:
							b = new Bomb(bl.x - 25, bl.y);
							break;
						case 2:
							b = new Bomb(bl.x, bl.y);
							break;
						case 3:
							b = new Bomb(bl.x + 45, bl.y);
							break;
						}
//						et.ss.remove(enemyShot);
						bombs.add(b);
						System.out.println("hit the Block1");
					}
				}
			}
			//��hero���ӵ������ж�
			for (int i = 0; i < hero.ss.size(); i++) {
				// ȡ���ӵ�
				Shot myShot = hero.ss.get(i);
				if (bl.type == 0 && myShot.x > bl.x && myShot.x < bl.x + 50 && myShot.y > bl.y
						&& myShot.y < bl.y + 50) {
					bl.isLive = false;
					// bls.remove(bl);
					myShot.isLive = false;
					Bomb b = null;
					switch (myShot.direct) {
					case 0:
						b = new Bomb(bl.x, bl.y + 45);
						break;
					case 1:
						b = new Bomb(bl.x - 25, bl.y);
						break;
					case 2:
						b = new Bomb(bl.x, bl.y);
						break;
					case 3:
						b = new Bomb(bl.x + 45, bl.y);
						break;
					}
					bombs.add(b);
					System.out.println("hit the Block");
				} else if (bl.type == 1 && myShot.x > bl.x && myShot.x < bl.x + 50 && myShot.y > bl.y
						&& myShot.y < bl.y + 50) {
					myShot.isLive = false;
					Bomb b = null;
					switch (myShot.direct) {
					case 0:
						b = new Bomb(bl.x, bl.y + 45);
						break;
					case 1:
						b = new Bomb(bl.x - 25, bl.y);
						break;
					case 2:
						b = new Bomb(bl.x, bl.y);
						break;
					case 3:
						b = new Bomb(bl.x + 45, bl.y);
						break;
					}
					bombs.add(b);
				}
			}
			if (hero2 != null) {
				for (int i = 0; i < hero2.ss.size(); i++) {
					// ȡ���ӵ�
					Shot myShot = hero2.ss.get(i);
					if (bl.type == 0 && myShot.x > bl.x && myShot.x < bl.x + 50 && myShot.y > bl.y
							&& myShot.y < bl.y + 50) {
						bl.isLive = false;
						// bls.remove(bl);
						myShot.isLive = false;
						Bomb b = null;
						switch (myShot.direct) {
						case 0:
							b = new Bomb(bl.x, bl.y + 45);
							break;
						case 1:
							b = new Bomb(bl.x - 25, bl.y);
							break;
						case 2:
							b = new Bomb(bl.x, bl.y);
							break;
						case 3:
							b = new Bomb(bl.x + 45, bl.y);
							break;
						}
						bombs.add(b);
						System.out.println("hit the Block");
					} else if (bl.type == 1 && myShot.x > bl.x && myShot.x < bl.x + 50 && myShot.y > bl.y
							&& myShot.y < bl.y + 50) {
						myShot.isLive = false;
						Bomb b = null;
						switch (myShot.direct) {
						case 0:
							b = new Bomb(bl.x, bl.y + 45);
							break;
						case 1:
							b = new Bomb(bl.x - 25, bl.y);
							break;
						case 2:
							b = new Bomb(bl.x, bl.y);
							break;
						case 3:
							b = new Bomb(bl.x + 45, bl.y);
							break;
						}
						bombs.add(b);
					}
				}
			}
		}

	}

	// ����̹�˵ĺ���
	public void drawTank(int x, int y, Graphics g, int direct, int type) {
		// �ж���ʲô���͵�̹��
		switch (type) {
		case 0:
			g.setColor(Color.yellow);
			break;
		case 1:
			g.setColor(Color.cyan);
			break;
		case 2:
			g.setColor(Color.pink);
			break;
		case 3:
			g.setColor(Color.red);
			break;
		case 4:
			g.setColor(Color.green);
			break;
		case 5:
			g.setColor(Color.blue);
			break;

		}
		// �жϷ���
		switch (direct) {
		// ����
		case 0:
			// �����ҵ�̹��(��ʱ�ٷ�װ��һ������)
			// 1.������ߵľ���
			g.fill3DRect(x, y, 12, 45, false);
			// 2.�����ұ߾���
			g.fill3DRect(x + 33, y, 12, 45, false);
			// 3.�����м����
			g.fill3DRect(x + 12, y + 9, 21, 27, false);
			// 4.����Բ��
			g.fillOval(x + 15, y + 15, 15, 15);
			// 5.������
			// g.drawLine(x + 15, y + 15, x + 15, y);
			g.fill3DRect(x + 22, y, 2, 22, false);
			break;
		case 1:
			// ��Ͳ����
			// �����������
			g.fill3DRect(x, y, 45, 12, false);
			// ��������ľ���
			g.fill3DRect(x, y + 33, 45, 12, false);
			// �����м�ľ���
			g.fill3DRect(x + 9, y + 12, 27, 21, false);
			// ����Բ��
			g.fillOval(x + 15, y + 15, 15, 15);
			// ������
			// g.drawLine(x + 15, y + 15, x + 30, y + 15);
			g.fill3DRect(x + 22, y + 22, 22, 2, false);
			break;
		case 2:
			// ����
			// �����ҵ�̹��(��ʱ�ٷ�װ��һ������)
			// 1.������ߵľ���
			g.fill3DRect(x, y, 12, 45, false);
			// 2.�����ұ߾���
			g.fill3DRect(x + 33, y, 12, 45, false);
			// 3.�����м����
			g.fill3DRect(x + 12, y + 9, 21, 27, false);
			// 4.����Բ��
			g.fillOval(x + 15, y + 15, 15, 15);
			// 5.������
			// g.drawLine(x + 15, y + 15, x + 15, y);
			g.fill3DRect(x + 22, y + 22, 2, 22, false);
			break;
		case 3:
			// ����
			// �����������
			g.fill3DRect(x, y, 45, 12, false);
			// ��������ľ���
			g.fill3DRect(x, y + 33, 45, 12, false);
			// �����м�ľ���
			g.fill3DRect(x + 9, y + 12, 27, 21, false);
			// ����Բ��
			g.fillOval(x + 15, y + 15, 15, 15);
			// ������
			// g.drawLine(x + 15, y + 15, x + 30, y + 15);
			g.fill3DRect(x, y + 22, 22, 2, false);
			break;
		}
	}

	public void drawBlock(int x, int y, Graphics g, int type) {
		switch (type) {
		case 0:
			g.setColor(Color.orange);
			break;
		case 1:
			g.setColor(Color.white);
			break;
		}
		g.fill3DRect(x, y, 50, 50, false);
	}

	public void drawRiver(int x, int y, Graphics g) {
		g.setColor(new Color(135, 206, 235, 255));
		g.fill3DRect(x, y, 50, 50, false);
	}
	
	//ÿ�صĵ�ͼ����
	public void mapMaker(int level) {
		switch (level) {
		case 1:
			for (int i = 6; i <= 12; i++) {
				Block k = new Block(i * 50, 50 * 3, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 6; i <= 12; i++) {
				Block k = new Block(i * 50, 50 * 11, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 4; i <= 10; i++) {
				Block k = new Block(6 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}

			for (int i = 3; i <= 11; i++){
				River r = new River(16 * 50, 50 * i);
				ris.add(r);
				r.setRis(ris);
			}
			for(int i=3;i<=11;i++) {
				Block k = new Block(2 * 50, 50 * i, 1);
				bls.add(k);
				k.setBls(bls);
			}
			break;
		case 2:
			for (int i = 5; i <= 9; i++) {
				Block k = new Block(i * 50, 50 * (i - 3), 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 10; i <= 14; i++) {
				Block k = new Block(i * 50, 50 * (16-i), 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 9; i <= 10; i++)
				for (int j = 6; j <= 11; j++) {
					Block k = new Block(i * 50, 50 * j, 0);
					bls.add(k);
					k.setBls(bls);
				}
			for (int i = 3; i <= 11; i++){
				River r = new River(16 * 50, 50 * i);
				ris.add(r);
				r.setRis(ris);
			}
			for(int i=3;i<=11;i++) {
				Block k = new Block(2 * 50, 50 * i, 1);
				bls.add(k);
				k.setBls(bls);
			}
			break;
		case 3:
			for (int i = 8; i <= 11; i++) {
				Block k = new Block(i * 50, 50 * 2, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 8; i <= 12; i++) {
				Block k = new Block(i * 50, 50 * 6, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 8; i <= 12; i++){
				Block k = new Block(i * 50, 50 * 11, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 2; i <= 11; i++){
				Block k = new Block(50 * 7, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 3; i <= 5; i++){
				Block k = new Block(11 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 7; i <= 10; i++){
				Block k = new Block(12 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 3; i <= 11; i++){
				River r = new River(16 * 50, 50 * i);
				ris.add(r);
				r.setRis(ris);
			}
			for(int i=3;i<=11;i++) {
				Block k = new Block(2 * 50, 50 * i, 1);
				bls.add(k);
				k.setBls(bls);
			}
			break;
		case 4:
			for (int i = 6; i <= 12; i++) {
				Block k = new Block(i * 50, 50 * 3, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 6; i <= 12; i++) {
				Block k = new Block(i * 50, 50 * 11, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 4; i <= 10; i++) {
				Block k = new Block(6 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for(int i=7;i<=11;i++) {
				Block k = new Block(i * 50, 50 * 7, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 3; i <= 11; i++){
				River r = new River(16 * 50, 50 * i);
				ris.add(r);
				r.setRis(ris);
			}
			for(int i=3;i<=11;i++) {
				Block k = new Block(2 * 50, 50 * i, 1);
				bls.add(k);
				k.setBls(bls);
			}
			break;
		case 5:
			for (int i = 8; i <= 11; i++) {
				Block k = new Block(i * 50, 50 * 2, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 8; i <= 11; i++) {
				Block k = new Block(i * 50, 50 * 6, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 2; i <= 11; i++){
				Block k = new Block(50 * 7, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 3; i <= 5; i++) {
				Block k = new Block(11 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 9; i <= 13; i++){
				Block k = new Block(i * 50, 50 * (i-2), 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 3; i <= 11; i++){
				River r = new River(16 * 50, 50 * i);
				ris.add(r);
				r.setRis(ris);
			}
			for(int i=3;i<=11;i++) {
				Block k = new Block(2 * 50, 50 * i, 1);
				bls.add(k);
				k.setBls(bls);
			}
			break;
		case 6:
			for (int i = 9; i <= 13; i++){
				Block k = new Block(i * 50, 50 * 3, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 4; i <= 11; i++){
				Block k = new Block(11 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 7; i <= 10; i++){
				Block k = new Block(i * 50, 50 * 11, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 9; i <= 10; i++){
				Block k = new Block(7 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 3; i <= 11; i++){
				River r = new River(16 * 50, 50 * i);
				ris.add(r);
				r.setRis(ris);
			}
			for(int i=3;i<=11;i++) {
				Block k = new Block(2 * 50, 50 * i, 1);
				bls.add(k);
				k.setBls(bls);
			}
			break;
		case 7:
			for (int i = 9; i <= 10; i++){
				Block k = new Block(i * 50, 50 * 4, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 8; i <= 12; i++){
				Block k = new Block(i * 50, 50 * 8, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 5; i <= 7; i++){
				Block k = new Block(8 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 5; i <= 7; i++){
				Block k = new Block(11 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 8; i <= 9; i++){
				Block k = new Block(7 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 8; i <= 9; i++){
				Block k = new Block(12 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 10; i <= 11; i++){
				Block k = new Block(6 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 10; i <= 11; i++){
				Block k = new Block(13 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 3; i <= 11; i++){
				River r = new River(16 * 50, 50 * i);
				ris.add(r);
				r.setRis(ris);
			}
			for(int i=3;i<=11;i++) {
				Block k = new Block(2 * 50, 50 * i, 1);
				bls.add(k);
				k.setBls(bls);
			}
			break;
		case 8:
			for (int i = 3; i <= 4; i++){
				Block k = new Block(5 * 50, 50 * i, 0);
				Block j = new Block(14 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
				bls.add(j);
				j.setBls(bls);
			}
			for (int i = 5; i <= 6; i++){
				Block k = new Block(6 * 50, 50 * i, 0);
				Block j = new Block(13 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
				bls.add(j);
				j.setBls(bls);
			}
			for (int i = 7; i <= 8; i++){
				Block k = new Block(7 * 50, 50 * i, 0);
				Block j = new Block(12 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
				bls.add(j);
				j.setBls(bls);
			}
			for (int i = 9; i <= 10; i++){
				Block k = new Block(8 * 50, 50 * i, 0);
				Block j = new Block(11 * 50, 50 * i, 0);
				bls.add(k);
				k.setBls(bls);
				bls.add(j);
				j.setBls(bls);
			}
			for (int i = 9; i <= 10; i++){
				Block k = new Block(i * 50, 50 * 11, 0);
				bls.add(k);
				k.setBls(bls);
			}
			for (int i = 3; i <= 11; i++){
				River r = new River(16 * 50, 50 * i);
				ris.add(r);
				r.setRis(ris);
			}
			for(int i=3;i<=11;i++) {
				Block k = new Block(2 * 50, 50 * i, 1);
				bls.add(k);
				k.setBls(bls);
			}
			break;
		}
	}

	//���¼��������ı䷽��ʹ�˶��ж���true
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int arg0 = e.getKeyCode();
		if (arg0 == KeyEvent.VK_UP || arg0 == KeyEvent.VK_RIGHT || arg0 == KeyEvent.VK_DOWN
				|| arg0 == KeyEvent.VK_LEFT) {
			hero.upMove = false;
			hero.downMove = false;
			hero.leftMove = false;
			hero.rightMove = false;
		}
		if (hero2 != null) {
			if (arg0 == KeyEvent.VK_W || arg0 == KeyEvent.VK_D || arg0 == KeyEvent.VK_S || arg0 == KeyEvent.VK_A) {
				hero2.upMove = false;
				hero2.downMove = false;
				hero2.leftMove = false;
				hero2.rightMove = false;
			}
		}
		if (arg0 == KeyEvent.VK_UP) {
			this.hero.setDirect(0);
			hero.upMove = true;
		} else if (arg0 == KeyEvent.VK_RIGHT) {
			this.hero.setDirect(1);
			hero.rightMove = true;
		} else if (arg0 == KeyEvent.VK_DOWN) {
			this.hero.setDirect(2);
			hero.downMove = true;
		} else if (arg0 == KeyEvent.VK_LEFT) {
			this.hero.setDirect(3);
			hero.leftMove = true;
		}
		if (arg0 == KeyEvent.VK_SPACE) {
			if (this.hero.ss.size() <= 4)
				hero.shotEnemy();
		}
		if (hero2 != null) {
			if (arg0 == KeyEvent.VK_W) {
				this.hero2.setDirect(0);
				hero2.upMove = true;
			} else if (arg0 == KeyEvent.VK_D) {
				this.hero2.setDirect(1);
				hero2.rightMove = true;
			} else if (arg0 == KeyEvent.VK_S) {
				this.hero2.setDirect(2);
				hero2.downMove = true;
			} else if (arg0 == KeyEvent.VK_A) {
				this.hero2.setDirect(3);
				hero2.leftMove = true;
			}
			if (arg0 == KeyEvent.VK_J) {
				if (this.hero2.ss.size() <= 4)
					hero2.shotEnemy();
			}
		}
		// �������»���Panel
		this.repaint();
	}

	//�ɿ�����false���˶�ֹͣ
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int arg0 = e.getKeyCode();
		if (arg0 == KeyEvent.VK_UP) {
			hero.upMove = false;
		} else if (arg0 == KeyEvent.VK_RIGHT) {
			hero.rightMove = false;
		} else if (arg0 == KeyEvent.VK_DOWN) {
			hero.downMove = false;
		} else if (arg0 == KeyEvent.VK_LEFT) {
			hero.leftMove = false;
		}
		if (hero2 != null) {
			if (arg0 == KeyEvent.VK_W) {
				hero2.upMove = false;
			} else if (arg0 == KeyEvent.VK_D) {
				hero2.rightMove = false;
			} else if (arg0 == KeyEvent.VK_S) {
				hero2.downMove = false;
			} else if (arg0 == KeyEvent.VK_A) {
				hero2.leftMove = false;
			}
		}

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
/*
	public void failmenu() {
		JFrame ff = null;
		ff = new JFrame("Game Over");
		JLabel jt = new JLabel("Game Over");
		ff.setSize(100, 80);
		ff.setLayout(new FlowLayout(FlowLayout.CENTER));
		ff.add(jt);
		ff.setVisible(true);
		ff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/

	public void heroMove() {
		if (hero.upMove)
			if (!hero.isTouchTank() && hero.y > 0 && !hero.isTouchBarrier())
				hero.moveUp();
		if (hero.rightMove)
			if (!hero.isTouchTank() && hero.x < 960 - 45 && !hero.isTouchBarrier())
				hero.moveRight();
		if (hero.downMove)
			if (!hero.isTouchTank() && hero.y < 720 - 45 && !hero.isTouchBarrier())
				hero.moveDown();
		if (hero.leftMove)
			if (!hero.isTouchTank() && hero.x > 0 && !hero.isTouchBarrier())
				hero.moveLeft();
		if (hero2 != null) {
			if (hero2.upMove)
				if (!hero2.isTouchTank() && hero2.y > 0 && !hero2.isTouchBarrier())
					hero2.moveUp();
			if (hero2.rightMove)
				if (!hero2.isTouchTank() && hero2.x < 960 - 45 && !hero2.isTouchBarrier())
					hero2.moveRight();
			if (hero2.downMove)
				if (!hero2.isTouchTank() && hero2.y < 720 - 45 && !hero2.isTouchBarrier())
					hero2.moveDown();
			if (hero2.leftMove)
				if (!hero2.isTouchTank() && hero2.x > 0 && !hero2.isTouchBarrier())
					hero2.moveLeft();
		}

	}

	public void run() {
		// TODO Auto-generated method stub
		// ÿ��100����ȥ�ػ�
		while (true) {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			Recorder_Vector.setEts(ets);
			Recorder_Vector.setBls(bls);
			Recorder_Vector.setRis(ris);
			Recorder_Vector.heroTank=hero;
			Recorder_Vector.heroTank2=hero2;
			this.hitEnemyTank();
			// �������жϵ��˵��ӵ��Ƿ������
			this.hitMe();
			this.hitBlock();
			this.heroMove();
			// �ػ�
			this.repaint();
			if (i == 0 && Recorder.getMyLife() == 0) {
				ScoreBoard b = new ScoreBoard();
				b.showScore();
				i++;
				break;
			}
			if (ets.size() == 0 && Recorder.getEnNum() == 0) {
				ScoreBoard b = new ScoreBoard();
				b.showScore();
				break;
			}

			/*
			 * if(Recorder.getEnNum()==0) { try { Thread.sleep(5000); } catch (Exception e)
			 * { e.printStackTrace(); } Recorder.setEnNum(20); enSize=Recorder.getEnNum();
			 * hero = new Hero(100, 200); for(int j = 0;j < enSize/5;j++) { for (int i = 0;
			 * i < 5; i++) { // ����һ�����˵�̹�˶��� EnemyTank et = new EnemyTank((i+1)%6 * 50,
			 * (i)/6*50); et.setColor(0); et.setDirect(2); // ��MyPanel�ĵ���̹�����������õ���̹��
			 * et.setEts(ets); // �������˵�̹�� Thread t = new Thread(et); t.start(); //
			 * ������̹�����һ���ӵ� Shot s = new Shot(et.x + 10, et.y + 30, 2); // ���������̹��
			 * et.ss.add(s); Thread t2 = new Thread(s); t2.start(); // ���� ets.add(et); } } }
			 */
		}
	}
}
