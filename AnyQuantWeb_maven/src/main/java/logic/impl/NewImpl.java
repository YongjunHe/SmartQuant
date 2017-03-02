package logic.impl;

import java.util.Iterator;

import data.impl.RequestNews;
import data.message.News;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import logic.service.NewService;
import net.sf.json.JSON;

public class NewImpl implements NewService{

	@Override
	public Iterator<News> getNews(String CName) {
		// TODO Auto-generated method stub
		
		String jsonStr = RequestNews.request(CName);
		
		return RequestNews.getNews(jsonStr).iterator();
	}
	
	
	

}
