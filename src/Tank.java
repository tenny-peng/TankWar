import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
public class Tank {
	//һ��̹�˿ͻ��˵Ĵ�ܼ�
	TankClient tc;
	
	//̹�˵Ĵ�С
	static final int TANK_WIDTH = 30;
	static final int TANK_HEIGHT = 30;
	
	//����̹�˵��ƶ��ٶ�
	static final int MYTANK_XSPEED = 10;
	static final int MYTANK_YSPEED = 10;
	
	//�з�̹���ٶ�
	static final int ENTANK_XSPEED = 5;
	static final int ENTANK_YSPEED = 5;
	
	//̹�˵�λ��
	protected int x;
	protected int y;
	
	//̹�˵�ǰһ��λ��
	private int oldX;
	private int oldY;
	
	//�ĸ������֮������Щ�����ȷ��̹�˵��ƶ�����
	protected boolean bL = false;

	protected boolean bU = false;

	protected boolean bR = false;

	protected boolean bD = false;
	
	//̹��Ĭ�Ϸ���ֹͣ
	protected Direction dir = Direction.STOP;
	
	//̹����Ͳ����Ĭ�����ϰ�
	protected Direction ptDir = Direction.U;
	
	//�з�̹������ƶ��Ĳ���
	private static Random random = new Random();
	
	//̹���ж�����
	private int step = random.nextInt(15) + 3; 
	
	//̹�˵ĺû�
	protected boolean good;
	
	public boolean isGood() {
		return good;
	}

	//̹���Ƿ����
	protected boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	//���̹�˵�Ѫ��
	protected int life = 100;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	//���ӣ��Լ�̹����ɫ������ѡ��
	Color myColor = Color.ORANGE; 
	
	//̹�˵�Ѫ��
	protected BloodBar bb = new BloodBar();

	//̹�˵Ĺ��췽��
	public Tank(int x,int y,Direction dir,boolean good,TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.good = good;
		this.tc = tc;
	}
	
	//����̹��
	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			} 
			return;
		}
		//�ҷ�̹��Ϊ��ɫ���з�̹��Ϊ��ɫ
		Color c = g.getColor();
		if(good) g.setColor(myColor);
		else g.setColor(Color.BLUE);
		//��Բ����̹���� 
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		bb.draw(g);
		g.setColor(Color.BLACK);
		//�����Ͳ�ķ���ֱ�ߴ����Ͳ
		switch(ptDir) {
		case L:
			g.drawLine(x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT/2, x, y + Tank.TANK_HEIGHT/2);
			break;
		case LU:
			g.drawLine(x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH, y + Tank.TANK_HEIGHT/2);
			break;
		case RD:
			g.drawLine(x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH, y + Tank.TANK_HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT/2, x, y + Tank.TANK_HEIGHT);
			break;
		}
		g.setColor(c);
		
		move();
	}
	
	
	//�ƶ�����ݰ����ж�̹�˽�������λ��
	void move() {
			
		this.oldX = x;
		this.oldY = y;
		if(good) {
			switch(dir) {
			case L:
				x -= MYTANK_XSPEED;
				break;
			case LU:
				x -= MYTANK_XSPEED;
				y -= MYTANK_YSPEED;
				break;
			case U:
				y -= MYTANK_YSPEED;
				break;
			case RU:
				x += MYTANK_XSPEED;
				y -= MYTANK_YSPEED;
				break;
			case R:
				x += MYTANK_XSPEED;
				break;
			case RD:
				x += MYTANK_XSPEED;
				y += MYTANK_YSPEED;
				break;
			case D:
				y += MYTANK_YSPEED;
				break;
			case LD:
				x -= MYTANK_XSPEED;
				y += MYTANK_YSPEED;
				break;
			case STOP:
				break;
			}
		} else {
			switch(dir) {
			case L:
				x -= ENTANK_XSPEED;
				break;
			case LU:
				x -= ENTANK_XSPEED;
				y -= ENTANK_YSPEED;
				break;
			case U:
				y -= ENTANK_YSPEED;
				break;
			case RU:
				x += ENTANK_XSPEED;
				y -= ENTANK_YSPEED;
				break;
			case R:
				x += ENTANK_XSPEED;
				break;
			case RD:
				x += ENTANK_XSPEED;
				y += ENTANK_YSPEED;
				break;
			case D:
				y += ENTANK_YSPEED;
				break;
			case LD:
				x -= ENTANK_XSPEED;
				y += ENTANK_YSPEED;
				break;
			case STOP:
				break;
			}
		}
		if(this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		
		//̹�˿�ǽ����
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.TANK_WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.TANK_WIDTH;
		if(y + Tank.TANK_HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.TANK_HEIGHT;
		
		//�з�̹���ƶ�֮��������н�
		if(!good) {
			Direction[] dirs = Direction.values();
			if(step ==0) {
				step = random.nextInt(15)+ 3;
				int rn = random.nextInt(dirs.length);
				dir = dirs[rn];
			}		
			step--;
			//��һ�����ʷ����ӵ�
			if(random.nextInt(41)> 38) this.fire();
		}
	}

	//��ݰ����ж�̹�˷���F2������1��
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_F2 :
				if(!this.isLive()){
					this.live = true;
					this.life = 100;
				}
				break;
			case KeyEvent.VK_A :
				bL = true;
				break;
			case KeyEvent.VK_W :
				bU = true;
				break;
			case KeyEvent.VK_D :
				bR = true;
				break;
			case KeyEvent.VK_S :
				bD = true;
				break;
			}
			LocateDirection();
		}

		//����̧��ʱ̹�˵�ֹͣ�ƶ���CTRL���
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_J:
				fire();
				break;
			case KeyEvent.VK_A :
				bL = false;
				break;
			case KeyEvent.VK_W :
				bU = false;
				break;
			case KeyEvent.VK_D :
				bR = false;
				break;
			case KeyEvent.VK_S :
				bD = false;
				break;
			case KeyEvent.VK_O :
				superFire();
				break;
			}
			
			LocateDirection();
		}
		
		//��ݰ����ж�̹�˷���
		void LocateDirection() {
			if(bL && !bU && !bR && !bD) dir = Direction.L;
			else if(bL && bU && !bR && !bD) dir = Direction.LU;
			else if(!bL && bU && !bR && !bD) dir = Direction.U;
			else if(!bL && bU && bR && !bD) dir = Direction.RU;
			else if(!bL && !bU && bR && !bD) dir = Direction.R;
			else if(!bL && !bU && bR && bD) dir = Direction.RD;
			else if(!bL && !bU && !bR && bD) dir = Direction.D;
			else if(bL && !bU && !bR && bD) dir = Direction.LD;
			else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
		}
		
		
		//������ͨ�ӵ�
		public Missile fire() {
			if(!live) return null;
			//�ӵ��ĳ�ʼλ��
			int x = this.x + Tank.TANK_WIDTH/2 - Missile.WIDTH/2;
			int y = this.y + Tank.TANK_HEIGHT/2 - Missile.HEIGHT/2;
			Missile m = new Missile(x, y, good, ptDir, myColor,this.tc);
			tc.missiles.add(m);
			return m;
		}
		
		
		//�з���ķ����ӵ�
		public Missile fire(Direction dir) {
			if(!live) return null;
			int x = this.x + Tank.TANK_WIDTH/2 - Missile.WIDTH/2;
			int y = this.y + Tank.TANK_HEIGHT/2 - Missile.HEIGHT/2;
			Missile m = new Missile(x, y, good, dir, myColor,this.tc);
			tc.missiles.add(m);
			return m;
		}
		//��������ķ������˸��������ڵ�
		public void superFire() {
			Direction[] dirs = Direction.values();
			for(int i=0; i<8; i++) {
				fire(dirs[i]);
			}
		}
	
		//�з�̹����ײ���ͣ��ԭ�������ж�
		public boolean collidesWithTanks(java.util.List<Tank> tanks) {
			for(int i=0; i<tanks.size(); i++) {
				Tank t = tanks.get(i);
				if(this != t) {
					if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
						this.stay();
						t.stay();
						return true;
					}
				}
			}
			return false;
		}
		
		//����з�̹����ײҲͣ��(��ǰ��ֱ�Ӵ���ȥ�ˣ�����)
		public boolean myCollidesTanks(java.util.List<Tank> tanks) {
			for(int i=0; i<tanks.size(); i++) {
				Tank t = tanks.get(i);
				if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;	
				}
			}
			return false;
		}
		//�ҷ�̹����ײ
		public boolean myCollodesTank(Tank t) {
			if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
				this.stay();
				t.stay();
				return true;	
			}
			return false;
		}
		//̹��ײǽ��ͣ�������ܴ���
		public boolean collidesWithWall(Wall w) {
			if(isLive() && this.getRect().intersects(w.getRect())) {
				this.stay();
				return true;
			}
			return false;
		}
		
		//̹��ײ���Ա����ǽ
		public boolean collidesWithRWall(RWall w) {
			if(isLive() && this.getRect().intersects(w.getRect())) {
				this.stay();
				return true;
			}
			return false;
		}
		//̹��ײ�ܶ�ǽ
		public boolean collidesWithRWalls(java.util.List<RWall> rWalls) {
			for(int i=0; i<rWalls.size(); i++) {
				if(collidesWithWall(rWalls.get(i)))
					return true;
			}
			return false;
		}
		
		private void stay() {
			this.x = oldX;
			this.y = oldY;
		}
		
		//һ����ʾѪ����ģ��
		class BloodBar {
			public void draw(Graphics g) {
				//Color c = g.getColor();
				//if(good)g.setColor(Color.red);
				//else g.setColor(Color.BLUE);
				g.drawRect(x, y-6, TANK_WIDTH, 2);
				int w =TANK_WIDTH * life/100;
				g.fillRect(x, y-6, w, 2);
				//g.setColor(c);
			}
		}
		
		public Rectangle getRect() {
			return new Rectangle(x,y,TANK_WIDTH,TANK_HEIGHT);
		} 
	
}
