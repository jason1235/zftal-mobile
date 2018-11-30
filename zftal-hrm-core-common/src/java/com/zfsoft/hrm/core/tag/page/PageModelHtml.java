package com.zfsoft.hrm.core.tag.page;

import com.zfsoft.dao.page.Paginator;
import com.zfsoft.util.base.StringUtil;


/**
 * 分页内容生成(QueryModel)
 * @author jinjj
 * @date 2012-11-16 下午03:46:51 
 *
 */
public class PageModelHtml {

	private Paginator paginator;
	private String function;
	private String queryName;
	
	public PageModelHtml(Paginator paginator,String function,String queryName){
		this.paginator = paginator;
		this.function = function;
		this.queryName = queryName;
	}
	
	public String generator(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='pagination'>");
		sb.append(pageLeft());
		sb.append(pageRight());
		sb.append("</div>");
		return sb.toString();
	}
	
	private String pageLeft(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='pageleft' style='padding:4px 6px;'>");
		sb.append("<p class='pagenum'>");
		sb.append("第<input id='toPage' type='text' name='"+getPrefix()+"currentPage' style='color:red; text-align:center;width:20px;' value='"+paginator.getPage()+"' size='2' "
				+initPageInput()+" title='页码,可修改,回车跳转'/>");
		sb.append("页 / 共");
        sb.append("<span class='red' id='totalPage'>"+paginator.getPages()+"</span>页， 每页显示");
        sb.append("<input type='text' id='pageSize' name='"+getPrefix()+"showCount' style='width:20px;' value='"
        		+paginator.getItemsPerPage()+"' size='2' maxlength='2' "+initPageInput()+" title='每页大小,可修改,回车生效'>");
        sb.append("<input type='hidden' id='defalutPageSize' value='"+paginator.getItemsPerPage()+"'/>");
        sb.append("条 /  共<span class='red'>"+paginator.getItems()+"</span>条记录 ");
    	sb.append("</p>");
        sb.append("</div>");
        return sb.toString();
	}
	
	/**
	 * 输入框事件
	 * @return
	 */
	private String initPageInput(){
		StringBuilder sb = new StringBuilder();
		sb.append(" onmouseover=\"initPageInput(this");
		if(!StringUtil.isEmpty(function)){
			sb.append(","+function);
		}
		sb.append(")\"");
		return sb.toString();
	}
	
	private String pageRight(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='pageright'>");
		sb.append("<div id='pagediv' class='paging'>");
		sb.append(firstPage());
		sb.append(prePage());
		sb.append(nextPage());
		sb.append(endPage());
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private String firstPage(){
		StringBuilder sb = new StringBuilder();
		if(paginator.getPage()>1&&paginator.getPages()>0){
			sb.append("<a id='first' class='first' title='首页' href='#' alt='1' "+initPageAnchor()+">首&#12288;页</a> ");
		}else{
			sb.append("<span class='text'>首&#12288;页</span> ");
		}
		return sb.toString();
	}
	
	private String nextPage(){
		StringBuilder sb = new StringBuilder();
		if(paginator.getPages()>paginator.getPage()){
			sb.append("<a id='next' class='next' title='下一页' href='#' alt='"+paginator.getNextPage()+"' "+initPageAnchor()+">下一页</a> ");
		}else{
			sb.append("<span class='text'>下一页</span> ");
		}
		return sb.toString();
	}
	
	private String prePage(){
		StringBuilder sb = new StringBuilder();
		if(paginator.getPage()>1){
			sb.append("<a id='pre' class='prev' title='上一页' href='#' alt='"+paginator.getPreviousPage()+"' "+initPageAnchor()+">上一页</a> ");
		}else{
			sb.append("<span class='text'>上一页</span> ");
		}
		return sb.toString();
	}
	
	private String endPage(){
		StringBuilder sb = new StringBuilder();
		if(paginator.getPages()>paginator.getPage()){
			sb.append("<a id='last' class='last' title='末页' href='#' alt='"+paginator.getLastPage()+"' "+initPageAnchor()+">末&#12288;页</a> ");
		}else{
			sb.append("<span class='text'>末&#12288;页</span> ");
		}
		return sb.toString();
	}
	
	private String initPageAnchor(){
		StringBuilder sb = new StringBuilder();
		sb.append(" onmouseover=\"initPageAnchor(this");
		if(!StringUtil.isEmpty(function)){
			sb.append(","+function);
		}
		sb.append(")\"");
		return sb.toString();
	}
	
	private String getPrefix(){
		if("model".equals(queryName)){
			return "";
		}
		return queryName+".";
	}
}
