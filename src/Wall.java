import java.awt.*;

//墙啊，不能被子弹打的
public class Wall {
	
	//墙的位置及长宽
	int x,y,h,w;
	
	TankClient tc;
	
	//构造方法
	public Wall(int x, int y, int h, int w, TankClient tc) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.tc = tc;
	}

	//画出墙
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	}
}
