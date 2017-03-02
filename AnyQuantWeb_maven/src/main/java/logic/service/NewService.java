package logic.service;

import java.util.Iterator;

import data.message.News;

public interface NewService {
	
	public Iterator<News> getNews(String CName);

}
