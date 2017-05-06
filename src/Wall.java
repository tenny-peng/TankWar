import java.awt.*;

//ǽ�������ܱ��ӵ����
public class Wall {
	
	//ǽ��λ�ü�����
	int x,y,h,w;
	
	TankClient tc;
	
	//���췽��
	public Wall(int x, int y, int h, int w, TankClient tc) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.tc = tc;
	}

	//����ǽ
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
