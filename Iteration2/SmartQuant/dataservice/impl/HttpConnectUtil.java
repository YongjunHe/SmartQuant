package impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

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
		httpClient.getParams().setContentCharset("GBK");

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
			responseMsg = out.toString();
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
//		String url = "http://121.41.106.89:8010/api/stocks/?year=2016&exchange=sz";
//		url = "http://hq.sinajs.cn/list=sh601006";
				String url = "http://121.41.106.89:8010/api/stock/sz300059/?start=2016-06-17&end=2016-06-20&fields=open+high+close+volume";
		//
		String response = HttpConnectUtil.getHttp(url);
		System.out.println(response);
		




	}


	public  static String getName(String name) { 
		System.out.println(name);

		String urlStr="http://hq.sinajs.cn/list="+name;


		URL url = null;              

		HttpURLConnection httpConn = null;            

		BufferedReader in = null;   
		StringBuffer sb = new StringBuffer();   
		try{     
			url = new URL(urlStr);     
			in = new BufferedReader(new InputStreamReader(url.openStream(),"GBK") );   
			String str = null;    
			while((str = in.readLine()) != null) {    
				sb.append( str );     
			}     
		} catch (Exception ex) {   

		} finally{    
			try{             
				if(in!=null) {  
					in.close();     
				}     
			}catch(IOException ex) {      
			}     
		}     
		String result =sb.toString();     


		int index=result.indexOf("=");
		int index_2=result.indexOf(",");
		try {
			result=result.substring(index+2, index_2);

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
			result = "";
		}

		return result;    
	}    

}
