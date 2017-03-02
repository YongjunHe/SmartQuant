package impl;

/*
 * @author: xuan
 * @date: 2016/03/04
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 调用Http的GET方法
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpHelper {
	
	private final static String KEY = "X-Auth-Code";//Http头信息
	private final static String CODE = "ff1f41d287c8276389e88ada3368b357";//open code
	private final static String NAME = "http.default-headers";
	private final static String FORMAT = "UTF-8";
	private final static String URL = "http://121.41.106.89:8010/";//请求地址

	
	public static String getHttp(String url) throws Exception{
		String responseMsg = "";	
		HttpClient httpClient = new HttpClient();//构造HttpClient的实例
		httpClient.setTimeout(15000);
		
		GetMethod getMethod = new GetMethod(URL + url);//构造GET方法的实例
		
		//设置请求头 
		List<Header>headers = new ArrayList<Header>();
		headers.add(new Header(KEY, CODE));
		httpClient.getHostConfiguration().getParams().setParameter(NAME, headers);
		
		
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		org.apache.commons.httpclient.DefaultMethodRetryHandler retryhandler = new org.apache.commons.httpclient.DefaultMethodRetryHandler();  
		retryhandler.setRequestSentRetryEnabled(false);  
		retryhandler.setRetryCount(1);  
		getMethod.setMethodRetryHandler(retryhandler);
		
		
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
			responseMsg = out.toString(FORMAT);
		} catch (HttpException e) {
			e.printStackTrace();
			throw new HttpException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		} finally {
			//释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}
	

}
