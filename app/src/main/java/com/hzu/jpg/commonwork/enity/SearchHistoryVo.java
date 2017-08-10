package com.hzu.jpg.commonwork.enity;

/**
 * 搜索记录vo
 * 
 * @author GuoJian
 * 
 */
public class SearchHistoryVo {
	public String content; // 搜索内容
	public String searchtime; // 搜索时间

	public SearchHistoryVo() {
	}

	public SearchHistoryVo(String content, String searchtime) {
		super();
		this.content = content;
		this.searchtime = searchtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSearchtime() {
		return searchtime;
	}

	public void setSearchtime(String searchtime) {
		this.searchtime = searchtime;
	}
}
