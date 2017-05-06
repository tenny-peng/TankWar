
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class TankClient extends Frame{
	//游戏窗口大小
	static final int GAME_WIDTH = 1200;
	static final int GAME_HEIGHT = 740;
	
	//开始和暂停的控制
	static boolean start = false;
	static boolean pause = false;
	
	Image offScreenImage = null;
	//创建自己的坦克
	Tank myTank = new Tank(300,680,Direction.STOP,true,this);
	Tank2 myTank2 = new Tank2(900,680,Direction.STOP,true,this);
	Tank BigTank = new Tank(605,670,Direction.STOP,true,this);
	
	//子弹，敌方坦克，爆炸,墙等对象
	List<Missile> missiles = new ArrayList<Missile>(); 
	List<Tank> tanks = new ArrayList<Tank>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<RWall> rWalls = new ArrayList<RWall>();
	
	//不能被穿的墙
	Wall w1 = new Wall(200,450,10,150,this), w2 = new Wall(880,350,200,10,this);
		
	//主方法
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lunchFrame();
		
	}
	//画出线程
	Thread drawThread = new Thread(new paintThread());
	//程序运行的开始方法 ，窗口的初始化
	public void lunchFrame() {
		
		//调用方法初始化可击打墙的布置
		initWall();
		//调用方法初始化敌方坦克
		initTank();

		
		this.setBounds(120,0,GAME_WIDTH,GAME_HEIGHT);
		this.setBackground(Color.BLACK);
		this.setTitle("TankWar--FIRE!!");
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			
		});
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		drawThread.start();//主要画的进程
	}
	
	//初始墙布置
	public void initWall() {
		//外墙，保护自己家的
		for(int i = 1;i<=20;i++) {
			RWall rw = new RWall(500+10*i,600,10,10,true,this);
			rWalls.add(rw);
		}
		for(int i = 1;i<=13;i++) {
			RWall rw = new RWall(510,600+10*i,10,10,true,this);
			rWalls.add(rw);
		}
		for(int i = 1;i<=13;i++) {
			RWall rw = new RWall(700,600+10*i,10,10,true,this);
			rWalls.add(rw);
		}
		//内墙
		for(int i = 1;i<=18;i++) {
			RWall rw = new RWall(510+10*i,610,10,10,true,this);
			rWalls.add(rw);
		}
		for(int i = 1;i<=12;i++) {
			RWall rw = new RWall(520,610+10*i,10,10,true,this);
			rWalls.add(rw);
		}
		for(int i = 1;i<=12;i++) {
			RWall rw = new RWall(690,610+10*i,10,10,true,this);
			rWalls.add(rw);
		}
		//墙,家前面的加强墙
		for(int i=1; i<=12; i++) {
			rWalls.add(new RWall(410+30*i,340,20,20,true,this));
		}
		for(int i=1; i<=12; i++) {
			rWalls.add(new RWall(480+20*i,350+20*i,10,10,true,this));
		}
		for(int i=1; i<=12; i++) {
			rWalls.add(new RWall(740-20*i,350+20*i,10,10,true,this));
		}
		//以下墙是娱乐用
		//棱形
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(120+15*i,150+20*i,10,10,true,this));
		}
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(130-15*i,150+20*i,10,10,true,this));
		}
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(220-15*i,260+20*i,10,10,true,this));
		}
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(30+15*i,250+20*i,10,10,true,this));
		}
		//正方形
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(860+30*i,120,10,20,true,this));
		}
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(860+30*i,160,10,20,true,this));
		}
		for(int i=1; i<=4; i++) {
			rWalls.add(new RWall(880,100+30*i,20,10,true,this));
		}
		for(int i=1; i<=4; i++) {
			rWalls.add(new RWall(1060,90+30*i,20,10,true,this));
		}
		//箭头
		for(int i=1; i<=8; i++) {
			rWalls.add(new RWall(880-15*i,300+20*i,20,10,true,this));
		}
		for(int i=1; i<=8; i++) {
			rWalls.add(new RWall(880+15*i,300+20*i,20,10,true,this));
		}
		for(int i=1; i<=20; i++) {
			rWalls.add(new RWall(1030-15*i,510,10,20,true,this));
		}
		//加强己方
		for(int i=1; i<=9; i++) {
			rWalls.add(new RWall(280-20*i,520+20*i,20,20,true,this));
		}
		for(int i=1; i<=5; i++) {
			rWalls.add(new RWall(260+30*i,540,20,20,true,this));
		}
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(400+20*i,520+20*i,20,10,true,this));
		}
		
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(980+20*i,550+20*i,20,20,true,this));
		}
		//削弱敌方
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(380,100+30*i,20,10,true,this));
		}
		for(int i=1; i<=8; i++) {
			rWalls.add(new RWall(460+20*i,100+20*i,10,20,true,this));
		}
	}
	
	//初始坦克布置
	public void initTank() {
		//初始时的所有敌方坦克
		int initTankCount = Integer.parseInt(PropertyMgr.getProperty("initTankCount"));
		int initTankCount2 = Integer.parseInt(PropertyMgr.getProperty("initTankCount2"));
		for(int i=0; i<initTankCount; i++) {
			tanks.add(new Tank(80+80*(i+1),60,Direction.STOP,false,this));
		}
		for(int i=0; i<initTankCount; i++) {
			tanks.add(new Tank(80+80*(i+1),260,Direction.STOP,false,this));
		}
		for(int i=0; i<initTankCount2; i++) {
			tanks.add(new Tank(1100,280+60*(i+1),Direction.STOP,false,this));
		}
		for(int i=0; i<initTankCount2; i++) {
			tanks.add(new Tank(60,280+60*(i+1),Direction.STOP,false,this));
		}
		//玩家2的坦克默认不出现
		myTank2.setLive(false);
		
		//设定家中坦克血量
		BigTank.life = 50;
		
		//设定家中的坦克颜色
		BigTank.myColor = Color.RED;
	}
	
	//画出整个背景和所有坦克,子弹等
	public void paint(Graphics g) {	
		
		if(BigTank.live == false) {
			//myTank.life = 0;
			myTank.setLive(false);
			//myTank2.life = 0;
			myTank2.setLive(false);
		}
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		//一系列数据提示
		g.drawString("TANK1 LIFE: " + myTank.getLife(), 30, 50);
		g.drawString("TANK2 LIFE: " + myTank2.getLife(), 30, 70);
		g.drawString("BIGTANK LIFE: " + BigTank.getLife(), 1050, 70);
		g.drawString("ENEMY COUNT: " + tanks.size(), 30, 90);
		g.drawString("MISSLIE COUNTS: " + missiles.size(), 30, 110);
		if(!BigTank.isLive()) g.drawString("GAME OVER! ", 600,380);
		if(tanks.size()==0) g.drawString("YOU WIN! ", 600,380);
		g.setColor(c);
		
		//敌我间坦克碰撞
		myTank.myCollidesTanks(tanks);
		myTank2.myCollidesTanks(tanks);
		BigTank.myCollidesTanks(tanks);
		//myTank.collidesWithWall(w1);
		//myTank.collidesWithWall(w2);
		
		//己方坦克碰撞
		myTank.myCollodesTank(myTank2);
		myTank.myCollodesTank(BigTank);
		myTank2.myCollodesTank(myTank);
		myTank2.myCollodesTank(BigTank);
		
		//己方坦克碰撞可打墙
		myTank.collidesWithRWalls(rWalls);
		myTank2.collidesWithRWalls(rWalls);
		
		//画出己方坦克
		myTank.draw(g);
		myTank2.draw(g);
		BigTank.draw(g);
		
		//画出所有墙 
		w1.draw(g);
		w2.draw(g);
		for(int i=0; i<rWalls.size(); i++) {
			rWalls.get(i).draw(g);
		}
		
		//子弹
		for(int i=0; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitTank(myTank2);
			m.hitTank(BigTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.hitRWalls(rWalls);
			m.collidesWithmissiles(missiles);
			m.draw(g);
		}
		
		//敌方坦克
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithTanks(tanks);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithRWalls(rWalls);
			t.draw(g);
		}
		
		//爆炸
		for(int i=0; i<explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
	}
	
	//利用双缓冲消除画面闪烁
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();//拿到背后画布的画笔
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);//重置背景画布
		g.setColor(c);
		paint(gOffScreen);//完成背后画布
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	//此线程实现一直重画
	class paintThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//从键盘控制坦克行为
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
			myTank2.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {			
			myTank.keyPressed(e);
			myTank2.keyPressed(e);
		}
	}
}
