
import java.awt.*;
import java.util.List;

public class Missile {
	
	//子弹移动的速度
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	//子弹大小
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	
	//子弹位置
	private int x,y;
	
	private Direction dir;
	
	//好弹还是坏弹
	private boolean good;
	
	public boolean isGood() {
		return good;
	}

	//子弹是否活着
	private boolean live = true;
	
	public void setLive(boolean live) {
		this.live = live;
	}
	private boolean isLive() {
		return live;
	}

	//敌我的攻击力，及打中后所掉的血量
	int enemyAttack = Integer.parseInt(PropertyMgr.getProperty("enemyAttack"));
	int myAttack = Integer.parseInt(PropertyMgr.getProperty("myAttack"));
	//管家的引用
	TankClient tc;
	
	//为了配合玩家坦克的颜色不同，子弹也要有自己的颜色
	Color myColor = null;
	
	//子弹的构造方法，初始位置及方向
	public Missile(int x, int y, boolean good,Direction dir,Color myColor,TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.good = good;
		this.myColor = myColor;
		this.tc = tc;
	}

	//画出自己
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

	//根据方向重置子弹的位置，达到移动子弹的效果
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
		
		//如果子弹出界，就让子弹死亡
		if(x <0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
		}
	}

	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	} 
	
	//打坦克:如果是坏弹打了我方坦克，我方坦克掉血，血量少于0则死亡；若是我方坦克打了敌方坦克，敌方坦克直接死亡
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
	
	//子弹撞墙就死亡
	public boolean hitWall(Wall w) {
		if(isLive() && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	//子弹撞可以打的墙
	public boolean hitRWall(RWall rw) {
		if(isLive() && this.getRect().intersects(rw.getRect())) {
			this.live = false;
			rw.setLive(false);
			return true;
		}
		return false;
	}
	//子弹撞很多可以打的墙
	public boolean hitRWalls(java.util.List<RWall> rWalls) {
		for(int i=0; i<rWalls.size(); i++) {
			if(hitRWall(rWalls.get(i)))
					return true;
		}
		return false;
	}
	//敌我子弹相碰就都死亡
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