import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank2 extends Tank{
	
	//���2�����е�һ����־���Ƿ��ս
	static boolean exist = false;
	
	//̹�˵��ƶ��ٶ�
	static final int MYTANK_XSPEED = 10;
	static final int MYTANK_YSPEED = 10;
	
	//ͬ�����Լ�̹�˵���ɫ
	Color myColor = Color.GREEN;

	public Tank2(int x, int y, Direction dir, boolean good, TankClient tc) {
		super(x, y, dir, good, tc);
	}

	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			} 
			return;
		}
		//��дdraw������ʹ���2̹�˺����1��ɫ��ͬ
		Color c = g.getColor();
		if(good) g.setColor(myColor);
		else g.setColor(Color.BLUE);
		//��Բ����̹���� 
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		bb.draw(g);
		g.setColor(Color.BLACK);
		//������Ͳ�ķ���ֱ�ߴ�����Ͳ
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
	
	//��дfire������Ϊ���ӵ���̹����ɫһ��
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
	
	//���ݰ����ж�̹�˷���F4������2��
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch(key) {
				case KeyEvent.VK_F3 :
					if(exist == false){
							this.setLive(true);
							exist = true;
						}
					break;
				case KeyEvent.VK_F4 :
					if(exist == true && !this.isLive()){
						this.live = true;
						this.life = 100;
					}
					break;
				case KeyEvent.VK_LEFT:
					bL = true;
					break;
				case KeyEvent.VK_UP :
					bU = true;
					break;
				case KeyEvent.VK_RIGHT :
					bR = true;
					break;
				case KeyEvent.VK_DOWN :
					bD = true;
					break;
				}
				LocateDirection();
			}

			//����̧��ʱ̹�˵�ֹͣ�ƶ���1������
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				switch(key) {
				case KeyEvent.VK_NUMPAD1:
					fire();
					break;
				case KeyEvent.VK_LEFT :
					bL = false;
					break;
				case KeyEvent.VK_UP :
					bU = false;
					break;
				case KeyEvent.VK_RIGHT :
					bR = false;
					break;
				case KeyEvent.VK_DOWN :
					bD = false;
					break;
				case KeyEvent.VK_NUMPAD6:
					superFire();
					break;
				}
				
				LocateDirection();
			}
}
