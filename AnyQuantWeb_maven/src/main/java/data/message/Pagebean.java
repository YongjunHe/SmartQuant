package data.message;

import java.util.List;

public class Pagebean {
	
	private int allNum;
	private int allPages;
	private List<News>contentlist;
	
	public int getAllNum() {
		return allNum;
	}
	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}
	public int getAllPages() {
		return allPages;
	}
	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}
	public List<News> getContentlist() {
		return contentlist;
	}
	public void setContentlist(List<News> contentlist) {
		this.contentlist = contentlist;
	}


}
