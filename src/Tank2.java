import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank2 extends Tank{
	
	//玩家2的特有的一个标志，是否参战
	static boolean exist = false;
	
	//坦克的移动速度
	static final int MYTANK_XSPEED = 10;
	static final int MYTANK_YSPEED = 10;
	
	//同样有自己坦克的颜色
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
		//重写draw方法，使玩家2坦克和玩家1颜色不同
		Color c = g.getColor();
		if(good) g.setColor(myColor);
		else g.setColor(Color.BLUE);
		//以圆代替坦克体 
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		bb.draw(g);
		g.setColor(Color.BLACK);
		//根据炮筒的方向画直线代表炮筒
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
	
	//重写fire方法，为了子弹与坦克颜色一致
	//发射普通子弹
	public Missile fire() {
		if(!live) return null;
		//子弹的初始位置
		int x = this.x + Tank.TANK_WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.TANK_HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, ptDir, myColor,this.tc);
		tc.missiles.add(m);
		return m;
	}

	//有方向的发射子弹
	public Missile fire(Direction dir) {
		if(!live) return null;
		int x = this.x + Tank.TANK_WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.TANK_HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, dir, myColor,this.tc);
		tc.missiles.add(m);
		return m;
	}
	//利用上面的方法朝八个方向发射炮弹
	public void superFire() {
		Direction[] dirs = Direction.values();
		for(int i=0; i<8; i++) {
			fire(dirs[i]);
		}
	}
	
	//根据按键判断坦克方向，F4复活（玩家2）
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

			//按键抬起时坦克当停止移动，1键开火
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
