import java.awt.Color;
import java.awt.Graphics;

//可以被打的墙，继承于普通墙
public class RWall extends Wall{

	//该墙是否活着
	private boolean live;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	//构造方法
	public RWall(int x, int y, int h, int w, boolean live,TankClient tc) {
		super(x, y, h, w, tc);
		this.live = live;
	}
	
	//画出自己
	public void draw(Graphics g) {
		if(!live) {
			tc.rWalls.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
}
