import java.awt.Color;
import java.awt.Graphics;

//���Ա����ǽ���̳�����ͨǽ
public class RWall extends Wall{

	//��ǽ�Ƿ����
	private boolean live;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	//���췽��
	public RWall(int x, int y, int h, int w, boolean live,TankClient tc) {
		super(x, y, h, w, tc);
		this.live = live;
	}
	
	//�����Լ�
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
