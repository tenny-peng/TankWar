
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class TankClient extends Frame{
	//��Ϸ���ڴ�С
	static final int GAME_WIDTH = 1200;
	static final int GAME_HEIGHT = 740;
	
	//��ʼ����ͣ�Ŀ���
	static boolean start = false;
	static boolean pause = false;
	
	Image offScreenImage = null;
	//�����Լ���̹��
	Tank myTank = new Tank(300,680,Direction.STOP,true,this);
	Tank2 myTank2 = new Tank2(900,680,Direction.STOP,true,this);
	Tank BigTank = new Tank(605,670,Direction.STOP,true,this);
	
	//�ӵ����з�̹�ˣ���ը,ǽ�ȶ���
	List<Missile> missiles = new ArrayList<Missile>(); 
	List<Tank> tanks = new ArrayList<Tank>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<RWall> rWalls = new ArrayList<RWall>();
	
	//���ܱ�����ǽ
	Wall w1 = new Wall(200,450,10,150,this), w2 = new Wall(880,350,200,10,this);
		
	//������
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lunchFrame();
		
	}
	//�����߳�
	Thread drawThread = new Thread(new paintThread());
	//�������еĿ�ʼ���� �����ڵĳ�ʼ��
	public void lunchFrame() {
		
		//���÷�����ʼ���ɻ���ǽ�Ĳ���
		initWall();
		//���÷�����ʼ���з�̹��
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
		drawThread.start();//��Ҫ���Ľ���
	}
	
	//��ʼǽ����
	public void initWall() {
		//��ǽ�������Լ��ҵ�
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
		//��ǽ
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
		//ǽ,��ǰ��ļ�ǿǽ
		for(int i=1; i<=12; i++) {
			rWalls.add(new RWall(410+30*i,340,20,20,true,this));
		}
		for(int i=1; i<=12; i++) {
			rWalls.add(new RWall(480+20*i,350+20*i,10,10,true,this));
		}
		for(int i=1; i<=12; i++) {
			rWalls.add(new RWall(740-20*i,350+20*i,10,10,true,this));
		}
		//����ǽ��������
		//����
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
		//������
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
		//��ͷ
		for(int i=1; i<=8; i++) {
			rWalls.add(new RWall(880-15*i,300+20*i,20,10,true,this));
		}
		for(int i=1; i<=8; i++) {
			rWalls.add(new RWall(880+15*i,300+20*i,20,10,true,this));
		}
		for(int i=1; i<=20; i++) {
			rWalls.add(new RWall(1030-15*i,510,10,20,true,this));
		}
		//��ǿ����
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
		//�����з�
		for(int i=1; i<=6; i++) {
			rWalls.add(new RWall(380,100+30*i,20,10,true,this));
		}
		for(int i=1; i<=8; i++) {
			rWalls.add(new RWall(460+20*i,100+20*i,10,20,true,this));
		}
	}
	
	//��ʼ̹�˲���
	public void initTank() {
		//��ʼʱ�����ез�̹��
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
		//���2��̹��Ĭ�ϲ�����
		myTank2.setLive(false);
		
		//�趨����̹��Ѫ��
		BigTank.life = 50;
		
		//�趨���е�̹����ɫ
		BigTank.myColor = Color.RED;
	}
	
	//������������������̹��,�ӵ���
	public void paint(Graphics g) {	
		
		if(BigTank.live == false) {
			//myTank.life = 0;
			myTank.setLive(false);
			//myTank2.life = 0;
			myTank2.setLive(false);
		}
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		//һϵ��������ʾ
		g.drawString("TANK1 LIFE: " + myTank.getLife(), 30, 50);
		g.drawString("TANK2 LIFE: " + myTank2.getLife(), 30, 70);
		g.drawString("BIGTANK LIFE: " + BigTank.getLife(), 1050, 70);
		g.drawString("ENEMY COUNT: " + tanks.size(), 30, 90);
		g.drawString("MISSLIE COUNTS: " + missiles.size(), 30, 110);
		if(!BigTank.isLive()) g.drawString("GAME OVER! ", 600,380);
		if(tanks.size()==0) g.drawString("YOU WIN! ", 600,380);
		g.setColor(c);
		
		//���Ҽ�̹����ײ
		myTank.myCollidesTanks(tanks);
		myTank2.myCollidesTanks(tanks);
		BigTank.myCollidesTanks(tanks);
		//myTank.collidesWithWall(w1);
		//myTank.collidesWithWall(w2);
		
		//����̹����ײ
		myTank.myCollodesTank(myTank2);
		myTank.myCollodesTank(BigTank);
		myTank2.myCollodesTank(myTank);
		myTank2.myCollodesTank(BigTank);
		
		//����̹����ײ�ɴ�ǽ
		myTank.collidesWithRWalls(rWalls);
		myTank2.collidesWithRWalls(rWalls);
		
		//��������̹��
		myTank.draw(g);
		myTank2.draw(g);
		BigTank.draw(g);
		
		//��������ǽ 
		w1.draw(g);
		w2.draw(g);
		for(int i=0; i<rWalls.size(); i++) {
			rWalls.get(i).draw(g);
		}
		
		//�ӵ�
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
		
		//�з�̹��
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithTanks(tanks);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithRWalls(rWalls);
			t.draw(g);
		}
		
		//��ը
		for(int i=0; i<explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
	}
	
	//����˫��������������˸
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();//�õ����󻭲��Ļ���
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);//���ñ�������
		g.setColor(c);
		paint(gOffScreen);//��ɱ��󻭲�
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	//���߳�ʵ��һֱ�ػ�
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

	//�Ӽ��̿���̹����Ϊ
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
