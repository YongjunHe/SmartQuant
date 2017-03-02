package impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HttpConnectUtil {

	private static String DUOSHUO_SHORTNAME = "yoodb";//多说短域名 ****.yoodb.****
	private static String DUOSHUO_SECRET = "xxxxxxxxxxxxxxxxx";//多说秘钥

	/**
	 * get方式
	 * @param url
	 * @author www.yoodb.com
	 * @return
	 */
	public static String getHttp(String url) {
		String responseMsg = "";	
		HttpClient httpClient = new HttpClient();//构造HttpClient的实例
		
		GetMethod getMethod = new GetMethod(url);//构造GET方法的实例
		
		//设置请求头 
		List<Header>headers = new ArrayList<Header>();
		headers.add(new Header("X-Auth-Code","ff1f41d287c8276389e88ada3368b357"));
		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		
		
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());//使用系统提供的默认恢复策略
		try {
			httpClient.executeMethod(getMethod);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = getMethod.getResponseBodyAsStream();
			int len = 0;
			byte[] buf = new byte[1024];
			while((len=in.read(buf))!=-1){
				out.write(buf, 0, len);
			}
			responseMsg = out.toString("UTF-8");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * post方式
	 * @param url
	 * @param code
	 * @param type
	 * @author www.yoodb.com
	 * @return
	 */
	public static String postHttp(String url,String code,String type) {
		String responseMsg = "";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("GBK");
		PostMethod postMethod = new PostMethod(url);
		postMethod.addParameter(type, code);
		postMethod.addParameter("client_id", DUOSHUO_SHORTNAME);
		postMethod.addParameter("client_secret", DUOSHUO_SECRET);
		try {
			httpClient.executeMethod(postMethod);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = postMethod.getResponseBodyAsStream();
			int len = 0;
			byte[] buf = new byte[1024];
			while((len=in.read(buf))!=-1){
				out.write(buf, 0, len);
			}
			responseMsg = out.toString("UTF-8");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return responseMsg;
	}
	
	public Map getProfiletMap(String userId){
	    String url = "http://api.duoshuo.com/users/profile.json?user_id="+userId;
	    String response = HttpConnectUtil.getHttp(url);
	    response = response.replaceAll("\\\\", "");
	    System.out.println(response);
	    Gson gson = new Gson();
	    Map profile = gson.fromJson(response, Map.class);
	    return profile;
	}
	
	public Map<String, String> getUserToken(String code){
	    String url = "http://api.duoshuo.com/oauth2/access_token";
	    String response = HttpConnectUtil.postHttp(url, code, "code");
	    System.out.println(response);
	    Gson gson = new Gson();
	    Map<String, String> retMap = gson.fromJson(response,new TypeToken<Map<String, String>>() {}.getType()); 
	    return retMap;
	}
	
	public static void main(String[]args){
//		String url = "http://121.41.106.89:8010/api/stocks/?year=2014&exchange=sh";
		String url = "http://121.41.106.89:8010/api/benchmark/hs300?start=2016-01-01&end=2016-02-01&fields=open+close";

		String response = HttpConnectUtil.getHttp(url);
		
		response = response.replaceAll("\\\\", "");
		
		System.out.println(response);
		
		Gson gson = new Gson();
		
		Map profile = gson.fromJson(response, Map.class);
		
		
		
		
	}
}
