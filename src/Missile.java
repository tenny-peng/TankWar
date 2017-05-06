
import java.awt.*;
import java.util.List;

public class Missile {
	
	//�ӵ��ƶ����ٶ�
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	//�ӵ���С
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	
	//�ӵ�λ��
	private int x,y;
	
	private Direction dir;
	
	//�õ����ǻ���
	private boolean good;
	
	public boolean isGood() {
		return good;
	}

	//�ӵ��Ƿ����
	private boolean live = true;
	
	public void setLive(boolean live) {
		this.live = live;
	}
	private boolean isLive() {
		return live;
	}

	//���ҵĹ������������к�������Ѫ��
	int enemyAttack = Integer.parseInt(PropertyMgr.getProperty("enemyAttack"));
	int myAttack = Integer.parseInt(PropertyMgr.getProperty("myAttack"));
	//�ܼҵ�����
	TankClient tc;
	
	//Ϊ��������̹�˵���ɫ��ͬ���ӵ�ҲҪ���Լ�����ɫ
	Color myColor = null;
	
	//�ӵ��Ĺ��췽������ʼλ�ü�����
	public Missile(int x, int y, boolean good,Direction dir,Color myColor,TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.good = good;
		this.myColor = myColor;
		this.tc = tc;
	}

	//�����Լ�
	void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		if(good) g.setColor(myColor);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
			
		move();
	}

	//���ݷ��������ӵ���λ�ã��ﵽ�ƶ��ӵ���Ч��
	private void move() {
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		
		//����ӵ����磬�����ӵ�����
		if(x <0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
		}
	}

	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	} 
	
	//��̹��:����ǻ��������ҷ�̹�ˣ��ҷ�̹�˵�Ѫ��Ѫ������0�������������ҷ�̹�˴��˵з�̹�ˣ��з�̹��ֱ������
	public boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			if(!good) {
				t.setLife(t.getLife() - enemyAttack);
				if(t.getLife()<=0) t.setLive(false);
			} else {
				t.setLife(t.getLife() - myAttack);
				if(t.getLife()<=0) t.setLive(false);
			}
			
			this.live  = false;
			Explode e = new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++){
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	//�ӵ�ײǽ������
	public boolean hitWall(Wall w) {
		if(isLive() && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	//�ӵ�ײ���Դ��ǽ
	public boolean hitRWall(RWall rw) {
		if(isLive() && this.getRect().intersects(rw.getRect())) {
			this.live = false;
			rw.setLive(false);
			return true;
		}
		return false;
	}
	//�ӵ�ײ�ܶ���Դ��ǽ
	public boolean hitRWalls(java.util.List<RWall> rWalls) {
		for(int i=0; i<rWalls.size(); i++) {
			if(hitRWall(rWalls.get(i)))
					return true;
		}
		return false;
	}
	//�����ӵ������Ͷ�����
	void collidesWithmissiles(java.util.List<Missile> missiles) {
		for(int i=0; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			if(this != m && this.good != m.isGood()) {
				if(this.live && m.isLive() && this.getRect().intersects(m.getRect())) {
					this.live = false;
					m.setLive(false);
				}
			}
		}
	}
	
}