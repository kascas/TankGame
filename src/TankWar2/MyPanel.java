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
	// 定义一个我的坦克
	Hero hero = null;
	Hero hero2 = null;
	// 定义敌人的坦克组
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	Vector<Tank> ts = new Vector<Tank>();///////////////////////
	Vector<Node> nodes = new Vector<Node>();
	Vector<Node> rnode = new Vector<Node>();
	Vector<Node> bnode = new Vector<Node>();
	// 定义炸弹集合
	Vector<Bomb> bombs = new Vector<Bomb>();
	Vector<Block> bls = new Vector<Block>();
	Vector<River> ris = new Vector<River>();
	int tankSwitch[] = new int[2];
	int enSize = 20;
	// 定义三张图片,三张图片才能组成一颗炸弹
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	int i = 0;
//	Thread s = null;

	static MyPanel mp2 = null;
	// 构造函数

	public MyPanel(String flag) {
		mp2 = this;
		if (flag.equals("newGame")) {// 开始新游戏
			// 播放开战声音
			AePlayWave apw = new AePlayWave("./111.wav");
			apw.start();
			// 根据level生成地图
			mapMaker(Recorder.getLevel());
			if (SetMenu.flag == 0) {
				Recorder.setDefaultEnNum(Recorder.getLevel());
			}
			hero = new Hero(600, 650);
			hero.setTs(ts);
			hero.setBls(bls);
			hero.setRis(ris);
			ts.add((Tank) hero);
			// 双人作战则生成hero2
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
					//延时生成坦克
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					// 初始化敌人的坦克
					for (int j = 0; j < enSize / 5; j++) {
						
						for (int i = 0; i < 5; i++) {
							// 随机生成敌方坦克类型
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
							// 创建敌方坦克
							EnemyTank et = new EnemyTank(160*(i+1), 50, type);
//							et.setColor(0);
							et.setDirect((int) (Math.random() * 4));
							// 将敌方坦克添加进向量
							ets.add(et);
							ts.add((Tank) et);
							// 将向量添加进敌方坦克
							et.setEts(ets);
							et.setTs(ts);
							et.setBls(bls);
							et.setRis(ris);
							// 启动敌人的坦克
							Thread t = new Thread(et);
							t.start();
						}
						// 每隔10秒生成敌方坦克
						try {
							Thread.sleep(10000);
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
					}
				}

			};
			// 线程开始
			s.start();
		} else {
			System.out.println("接着玩");
			//Vector类型数据的读取
			Recorder rc = new Recorder();
			nodes = rc.getTankNodes();
			bnode = rc.getBlock();
			rnode = rc.getRiver();
			//hero和hero2的再现
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
			// 初始化敌人的坦克
			Thread s = new Thread() {
				public void run() {
					int judge=1;
					if(Recorder.getplayerNum() == 2)
						judge=2;
					//存盘时存在的坦克的再现
					for (int i = judge; i < nodes.size(); i++) {
						Node node = nodes.get(i);
						// 创建一辆敌人的坦克对象
						EnemyTank et = new EnemyTank(node.x, node.y, node.type);
						et.setDirect(node.direct);
						// 将MyPanel的敌人坦克向量交给该敌人坦克
						et.setEts(ets);
						et.setTs(ts);
						// 启动敌人的坦克
						Thread t = new Thread(et);
						t.start();
						ets.add(et);
						ts.add((Tank) et);
						et.setEts(ets);
						et.setTs(ts);
						et.setBls(bls);
						et.setRis(ris);
					}
					//障碍物再现
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
					//将关卡中剩余的坦克依次生成
					enSize = Recorder.getEnNum()-ets.size();
					for (int j = 0; j < enSize / 5; j++) {
						for (int i = 0; i < 5; i++) {
							// 随机生成敌方坦克类型
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
							// 创建敌方坦克
							EnemyTank et = new EnemyTank(160*(i+1), 50, type);
//							et.setColor(0);
							et.setDirect((int) (Math.random() * 4));
							// 将敌方坦克添加进向量
							ets.add(et);
							ts.add((Tank) et);
							// 将向量添加进敌方坦克
							et.setEts(ets);
							et.setTs(ts);
							et.setBls(bls);
							et.setRis(ris);
							// 启动敌人的坦克
							Thread t = new Thread(et);
							t.start();
						}
						// 每隔10秒生成敌方坦克
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

// 			初始化图片d
//			image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//			image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//			image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	
	// 画出提示信息
	public void showInfo(Graphics g) {
		// 画出提示信息坦克(该坦克不参与战斗)
		Font f = new Font("宋体", Font.BOLD, 20);
		g.setFont(f);
		this.drawTank(1000, 600, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum() + "", 1050, 620);
		this.drawTank(1100, 600, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife() + "", 1150, 620);
		// 画出玩家的总成绩
		g.setColor(Color.black);
		g.drawString("您的总成绩", 1000, 100);
		this.drawTank(1000, 125, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(/* Recorder.getAllEnNum() */ Recorder.getScore() + "", 1100, 150);
	}

	// 重新paint
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 960, 720);
		// 画出提示信息
		this.showInfo(g);
		// 画出不同类型的墙
		for (int i = 0; i < bls.size(); i++) {
			Block b = bls.get(i);
			if (b.isLive) {
				this.drawBlock(b.getX(), b.getY(), g, b.type);
			} else {
				bls.remove(b);
			}
		}
		// 画出河流
		for (int i = 0; i < ris.size(); i++) {
			River b = ris.get(i);
			this.drawRiver(b.getX(), b.getY(), g);
		}
		// 画出自己的坦克
		if (hero.isLive) {
			this.drawTank(hero.getX(), hero.getY(), g, this.hero.direct, 0);
		}

		// 从ss中取出每颗子弹，并画出
		for (int i = 0; i < hero.ss.size(); i++) {
			Shot myShot = hero.ss.get(i);
			// 画出子弹,画出一颗子弹
			if (myShot != null && myShot.isLive == true) {
				g.draw3DRect(myShot.x, myShot.y, 3, 3, true);
			}
			if (myShot.isLive == false) {
				// 从ss中删除掉该子弹
				hero.ss.remove(myShot);
			}
		}
		// 如果双人作战，画出hero2
		if (hero2 != null) {
			if (hero2.isLive) {
				this.drawTank(hero2.getX(), hero2.getY(), g, this.hero2.direct, 0);
			}
			// 从ss中取出每颗子弹，并画出
			for (int i = 0; i < hero2.ss.size(); i++) {
				Shot myShot = hero2.ss.get(i);
				// 画出子弹,画出一颗子弹
				if (myShot != null && myShot.isLive == true) {
					g.draw3DRect(myShot.x, myShot.y, 3, 3, true);
				}
				if (myShot.isLive == false) {
					// 从ss中删除掉该子弹
					hero2.ss.remove(myShot);
				}
			}
		}
		// 画出炸弹
		for (int i = 0; i < bombs.size(); i++) {
			// 取出炸弹
			Bomb b = bombs.get(i);
			if (b.life > 6) {
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 3) {
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else {
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			// 让b的生命值减小
			b.lifeDown();
			// 如果炸弹生命值为0,就把该炸弹重bombs向量去掉
			if (b.life == 0) {
				bombs.remove(b);
			}
		}

		// 画出敌人的坦克
		for (int i = 0; i < ets.size(); i++) {
			EnemyTank et = ets.get(i);
			if (et.isLive) {
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), et.type);/////////////////////
				// 再画出敌人的子弹
				// System.out.println("坦克子弹有:"+et.ss.size());
				for (int j = 0; j < et.ss.size(); j++) {
					// 取出子弹
					Shot enemyShot = et.ss.get(j);
					if (enemyShot.isLive) {
						// System.out.println("第 "+i+"坦克的 "+j+"颗子弹x="+enemyShot.x);
						g.draw3DRect(enemyShot.x, enemyShot.y, 3, 3, true);//////////////
					} else {
						// 如果敌人的坦克死亡就从Vector去掉
						et.ss.remove(enemyShot);
					}
				}
			}
		}

	}

	// 敌人的坦克是否击中我
	public void hitMe() {
		// 取出每一个敌人的坦克
		for (int i = 0; i < this.ets.size(); i++) {
			// 取出坦克
			EnemyTank et = ets.get(i);
			// 取出每一颗子弹
			for (int j = 0; j < et.ss.size(); j++) {
				// 取出子弹
				Shot enemyShot = et.ss.get(j);
				if (hero.isLive) {
					// 如果中弹
					if (this.hitTank(enemyShot, hero)) ///////////////////////////////////////////
					{
						Recorder.reduceMyLife();
						ts.remove(hero);
						if (Recorder.getMyLife() >= 1) {
							// 新的hero线程
							Thread newThread = new Thread() {
								public void run() {
									try {
										Thread.sleep(500);
									} catch (Exception e) {
										e.printStackTrace();
										// TODO: handle exception
									}
									// 重新构造hero
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
				//玩家二的构造原理同玩家一
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

	// 判断我的子弹是否击中敌人的坦克
	public void hitEnemyTank() {
		// 判断是否击中敌人的坦克
		for (int i = 0; i < hero.ss.size(); i++) {
			// 取出子弹
			Shot myShot = hero.ss.get(i);
			// 判断子弹是否有效
			if (myShot.isLive) {
				// 取出每个坦克，与它判断
				for (int j = 0; j < ets.size(); j++) {
					// 取出坦克
					EnemyTank et = ets.get(j);
//					if (et.isLive) {
					if (this.hitTank(myShot, et)) {
						if (!et.isLive) {
							// 减少敌人数量
							Recorder.reduceEnNum();
							// 增加我的记录
							Recorder.addEnNumRec();
							// 增加分数
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
				// 取出子弹
				Shot myShot = hero2.ss.get(i);
				// 判断子弹是否有效
				if (myShot.isLive) {
					// 取出每个坦克，与它判断
					for (int j = 0; j < ets.size(); j++) {
						// 取出坦克
						EnemyTank et = ets.get(j);
//						if (et.isLive) {
						if (this.hitTank(myShot, et)) {
							if (!et.isLive) {
								// 减少敌人数量
								Recorder.reduceEnNum();
								// 增加我的记录
								Recorder.addEnNumRec();
								// 增加分数
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

	// 写一个函数专门判断子弹是否击中敌人坦克
	public boolean hitTank(Shot s, Tank et) {
		boolean b2 = false;
		// 判断该坦克的方向
		switch (et.direct) {
		// 如果敌人坦克的方向是上或者是下
		case 0:
		case 2:
			if (s.x > et.x && s.x < et.x + 45 && s.y > et.y && s.y < et.y + 45) {
				// 击中
				// 子弹死亡
				s.isLive = false;
				// 敌人坦克死亡
				et.hp -= 100;
				if (et.hp <= 0)
					et.isLive = false;
				b2 = true;
				// 创建一颗炸弹,放入Vector
				Bomb b = new Bomb(et.x, et.y);
				// 放入Vector
				bombs.add(b);
			}
			break;
		case 1:
		case 3:
			if (s.x > et.x && s.x < et.x + 45 && s.y > et.y && s.y < et.y + 45) {
				// 击中
				// 子弹死亡
				s.isLive = false;
				// 敌人坦克死亡
//				et.isLive = false;
				et.hp -= 100;
				if (et.hp <= 0)
					et.isLive = false;
				b2 = true;
				// 创建一颗炸弹,放入Vector
				Bomb b = new Bomb(et.x, et.y);
				// 放入Vector
				bombs.add(b);
			}
		}
		return b2;
	}
	//判断是否击中墙
	public void hitBlock() {
		for (int k = 0; k < bls.size(); k++) {
			Block bl = bls.get(k);
			//对每一个坦克的子弹进行判断
			for (int i = 0; i < this.ets.size(); i++) {
				EnemyTank et = ets.get(i);
				for (int j = 0; j < et.ss.size(); j++) {
					// 取出子弹
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
			//对hero的子弹进行判断
			for (int i = 0; i < hero.ss.size(); i++) {
				// 取出子弹
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
					// 取出子弹
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

	// 画出坦克的函数
	public void drawTank(int x, int y, Graphics g, int direct, int type) {
		// 判断是什么类型的坦克
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
		// 判断方向
		switch (direct) {
		// 向上
		case 0:
			// 画出我的坦克(到时再封装成一个函数)
			// 1.画出左边的矩形
			g.fill3DRect(x, y, 12, 45, false);
			// 2.画出右边矩形
			g.fill3DRect(x + 33, y, 12, 45, false);
			// 3.画出中间矩形
			g.fill3DRect(x + 12, y + 9, 21, 27, false);
			// 4.画出圆形
			g.fillOval(x + 15, y + 15, 15, 15);
			// 5.画出线
			// g.drawLine(x + 15, y + 15, x + 15, y);
			g.fill3DRect(x + 22, y, 2, 22, false);
			break;
		case 1:
			// 炮筒向右
			// 画出上面矩形
			g.fill3DRect(x, y, 45, 12, false);
			// 画出下面的矩形
			g.fill3DRect(x, y + 33, 45, 12, false);
			// 画出中间的矩形
			g.fill3DRect(x + 9, y + 12, 27, 21, false);
			// 画出圆形
			g.fillOval(x + 15, y + 15, 15, 15);
			// 画出线
			// g.drawLine(x + 15, y + 15, x + 30, y + 15);
			g.fill3DRect(x + 22, y + 22, 22, 2, false);
			break;
		case 2:
			// 向下
			// 画出我的坦克(到时再封装成一个函数)
			// 1.画出左边的矩形
			g.fill3DRect(x, y, 12, 45, false);
			// 2.画出右边矩形
			g.fill3DRect(x + 33, y, 12, 45, false);
			// 3.画出中间矩形
			g.fill3DRect(x + 12, y + 9, 21, 27, false);
			// 4.画出圆形
			g.fillOval(x + 15, y + 15, 15, 15);
			// 5.画出线
			// g.drawLine(x + 15, y + 15, x + 15, y);
			g.fill3DRect(x + 22, y + 22, 2, 22, false);
			break;
		case 3:
			// 向左
			// 画出上面矩形
			g.fill3DRect(x, y, 45, 12, false);
			// 画出下面的矩形
			g.fill3DRect(x, y + 33, 45, 12, false);
			// 画出中间的矩形
			g.fill3DRect(x + 9, y + 12, 27, 21, false);
			// 画出圆形
			g.fillOval(x + 15, y + 15, 15, 15);
			// 画出线
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
	
	//每关的地图生成
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

	//按下即触发，改变方向并使运动判定置true
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
		// 必须重新绘制Panel
		this.repaint();
	}

	//松开即置false，运动停止
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
		// 每隔100毫秒去重绘
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
			// 函数，判断敌人的子弹是否击中我
			this.hitMe();
			this.hitBlock();
			this.heroMove();
			// 重绘
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
			 * i < 5; i++) { // 创建一辆敌人的坦克对象 EnemyTank et = new EnemyTank((i+1)%6 * 50,
			 * (i)/6*50); et.setColor(0); et.setDirect(2); // 将MyPanel的敌人坦克向量交给该敌人坦克
			 * et.setEts(ets); // 启动敌人的坦克 Thread t = new Thread(et); t.start(); //
			 * 给敌人坦克添加一颗子弹 Shot s = new Shot(et.x + 10, et.y + 30, 2); // 加入给敌人坦克
			 * et.ss.add(s); Thread t2 = new Thread(s); t2.start(); // 加入 ets.add(et); } } }
			 */
		}
	}
}
