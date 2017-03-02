package src;

/*
 * @author: xuan
 * @date: 2016/03/09
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 对HttpHelper的单元测试
 */

import org.junit.Test;

import static org.junit.Assert.*;

import java.net.SocketException;

import impl.HttpHelper;




public class HttpHelperTest {
	
	
	/*
	 * @author: xuan
	 * @date: 2016/03/09
	 * @description: 测试Get方法，直接检测获得的首页信息正确性
	 */

	@Test
	public void testGetHttp() throws Exception {
		String response = HttpHelper.getHttp("");
		String answer = "{\"status\": \"ok\", \"data\": \"Welcome AnyQuant API, Visit Doc For Usage\"}";
		assertEquals(answer, response);
		
	}

}
