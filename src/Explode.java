import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Image;
public class Explode {
	
	//��ը���·��ص�
	private int x ,y ;
	
	//��ը�Ƿ����
	private boolean live = true;
	
	private static boolean init = false;
	
	//�ܼ�����
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	//�Ӵ�С��ըͼ
	private static Image[] imgs = {
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode0.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode9.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode10.gif"))
	};
	
	//��ը�Ĳ���
	private int step = 0;
	
	//��ʼ����ը
	public Explode(int x, int y,TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	//�������
	public void draw(Graphics g) {
		
		if(!init) {
			for (int j = 0; j < imgs.length; j++) {
				g.drawImage(imgs[j], -100, -100, null);
			}
			init = true;
		}
		
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		if(step == imgs.length) {
			step = 0;
			live = false;
			return;
		}
		
		g.drawImage(imgs[step], x, y, null);
		
		step++;
	} 
}
