import java.io.IOException;
import java.util.Properties;

//用来选择配置文件,可以方便的改变一些常量值
public class PropertyMgr {
	static Properties pro = new Properties();
	
	static {
		try {
			pro.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private PropertyMgr() {
		
	}
	
	public static String getProperty(String key) {
		return pro.getProperty(key);
	}
}