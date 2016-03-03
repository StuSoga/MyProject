import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by zero on 16/3/2.
 */
public class Main {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml","applicationContext-data.xml","applicationContext-provider.xml"});
		context.start();
		System.in.read();
	}
}
