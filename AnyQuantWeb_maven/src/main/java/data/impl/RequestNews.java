package data.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import data.message.News;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.DADD;

public class RequestNews {

	private static String channelID1 = "5572a109b3cdc86cf39001e0";//财经最新
	private static String channelID2 = "5572a108b3cdc86cf39001d0";//财经焦点
	private static String Url = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
	private static String Arg = "channelId="+channelID2+"&channelName="+"财经焦点"+"&page=1&needContent=0&needHtml=0&title=";




	public static void  main(String[]args){
		Date date  = new Date();
		String jsonResult = request("沪深");
		Date date2  = new Date();
		List<News> news = getNews(jsonResult);
		Iterator<News>iterator = news.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next().getTitle());
		}
		
	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String CName) {
		String httpArg = Arg + CName;
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String httpUrl = Url + "?" + httpArg;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey",  "0b13821432c88cd18897a7b2c7ee4c33");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


	public static List<News> getNews(String jsonStr){
		List<News> list = new ArrayList<News>();
		JSONObject jsonObj;
		try{
			jsonObj = new JSONObject(jsonStr);
			JSONObject  response = jsonObj.getJSONObject("showapi_res_body").getJSONObject("pagebean");
			JSONArray newsList = response.getJSONArray("contentlist");
			
			for (int i = 0; i < newsList.length(); i++){
				
				JSONObject jsonItem = newsList.getJSONObject(i);
				News news = new News();
				news.setTitle(jsonItem.getString("title"));
				news.setUrl("link");
				list.add(news);
			}


		}catch(Exception e){
			e.getMessage();
		}
		return list;
	}
}
