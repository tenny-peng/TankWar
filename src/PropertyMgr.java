import java.io.IOException;
import java.util.Properties;

//����ѡ�������ļ�,���Է���ĸı�һЩ����ֵ
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