package com.zfsoft.mobile.configuration.action;

import java.util.List;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsService;

public class NewsConfigAction extends HrmAction {
	
	private NewsConfigQuery query = new NewsConfigQuery();
	private INewsConfigService newsConfigService;
	private INewsService newsService;
	private List<NewsConfig> list;
	public NewsConfigQuery getQuery() {
		return query;
	}
	public void setQuery(NewsConfigQuery query) {
		this.query = query;
	}
	public INewsConfigService getNewsConfigService() {
		return newsConfigService;
	}
	public void setNewsConfigService(INewsConfigService newsConfigService) {
		this.newsConfigService = newsConfigService;
	}
	
	public List<NewsConfig> getList() {
		return list;
	}
	public void setList(List<NewsConfig> list) {
		this.list = list;
	}
	public String index() {
		list = newsConfigService.getAll();
		return "index";
	}
	
	public String add() {
		try {
			newsConfigService.add(query);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.setErrorMessage(e.getMessage());
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	public String update() {
		try {
			
			if(query.getKey().equals("max_news_top")){
				NewsQuery newsQuery = new NewsQuery();
				newsQuery.setTop("1");
				int count = newsService.getPageList(newsQuery).size();
				if(count > Integer.valueOf(query.getValue())){
					this.setErrorMessage("资讯已置顶的记录数量大于您正在设置的值，请先修改已经置顶的资讯，保持小于您设置的值!");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
			}else if(query.getKey().equals("max_news_headline")){
				NewsQuery newsQuery = new NewsQuery();
				newsQuery.setHeadline("1");
				int count = newsService.getPageList(newsQuery).size();
				if(count > Integer.valueOf(query.getValue())){
					this.setErrorMessage("资讯已设置为头条的记录数量大于您正在设置的值，请先修改已设置为头条的资讯，保持小于您设置的值!");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
			}else if(query.getKey().equals("max_news_recommend")){
				NewsQuery newsQuery = new NewsQuery();
				newsQuery.setRecommend("1");
				int count = newsService.getPageList(newsQuery).size();
				if(count > Integer.valueOf(query.getValue())){
					this.setErrorMessage("资讯已推荐的记录数量大于您正在设置的值，请先修改已推荐的资讯，保持小于您设置的值!");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}
			}
			newsConfigService.update(query);
			this.setSuccessMessage("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.setErrorMessage(e.getMessage());
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	public String delete() {
		try {
			newsConfigService.delete(query);
			this.setSuccessMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.setErrorMessage(e.getMessage());
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}
	public INewsService getNewsService() {
		return newsService;
	}
}
