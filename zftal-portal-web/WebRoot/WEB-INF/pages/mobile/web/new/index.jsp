<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script src="<%=request.getContextPath() %>/assets/js/amazeui.js"></script>
<script src="<%=request.getContextPath() %>/assets/js/handlebars.min.js"></script>
<script src="<%=request.getContextPath() %>/assets/js/amazeui.widgets.helper.js"></script>
<style type="text/css">

        .am-gallery-title {
            text-align: center;
        }


        .am-gallery-item {
            text-align: center;
        }

        .am-gallery .am-gallery-item img{
            width: 3.2rem;
        }
        .heightstyle img {
        	width:100%;
        	height:200px;
        }
        .am-table td {
            cursor: pointer;
            text-align: center;
        }

        .am-table td:hover {
            background-color: #eeeeee;
            border-bottom: 1px solid deepskyblue;
        }
        
        
        ::-webkit-scrollbar {
        	display:none;
        }

    </style>


<title></title>

</head>
<body>
	
<script type="text/x-handlebars-template" id="amz-tpl">
    {{>slider slider}}
<div id="gallery">
    <ul id="common" data-am-widget="gallery" class="am-gallery am-avg-sm-4  am-avg-md-4 am-avg-lg-4 am-gallery-default am-no-layout" data-am-gallery="{  }">
        
    </ul>
</div>


{{>list_news list_news}}
</script>
<script>
	var recommendList = ${recommendList};
	var contents=new Array(recommendList.length);
	for(var i=0;i<recommendList.length;i++){
			contents[i] = {
                    "img": recommendList[i].logopath,
                    "desc" : recommendList[i].title,
                    "link": recommendList[i].url,
                    "className": "heightstyle",
                }
		}   
	
    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    var TTlist = ${TTlist}; 
	var TTnews = TTlist.news;
    var mainData = new Array(TTnews.length);;
  		for (var i = 0,len = TTnews.length; i < len; i++) {
  			mainData[i]= {
  				  "title": TTnews[i].title,            // 新闻标题
			      "link": _path + "/mobile/web_content.html?newsId=" + TTnews[i].id,             // 新闻链接
			      "date": TTnews[i].timecreate  ,          // 日期
		          "className": "liststyle",  // 自定 class
  			};
  		}
    var data = {
        slider : {
            "theme" : "c1",
            "content": contents
        },
		list_news : {
			  // id
			  "id": "123",
			
			  // 自定义 class
			  "className": "tou",
			
			  // 主题
			  "theme": "",
			
			  "options": {
			   // normal、thumb
			    "type": "",
			
			    // 当type为 thumb 时必传
			    "thumbPosition": "bottom"
			    // top-一般用于全宽模式
			    // bottom - bottom-left | bottom-right
			    // left - right
			  },
			
			  // 数据传递
			  "content": {
			    "header": {
			      "title": "新闻资讯", // 栏目标题
			      "className": "listtitle",
			      "moreText": "更多 >", // 更多链接显示文字
			      "morePosition": "bottom" // 【更多】链接位置，top、bottom，如果不设置则不显示更多链接
			    },
			    main: mainData
			}
    }
    }
    var html = template(data);
    $tpl.before(html);
    
    var apps = ${apps};
    var appHtml = "";
    for(var i=0;i<apps.length;i++){
		appHtml += "<li><div class=\"am-gallery-item\"> " +
               	  "<a href=\"" + apps[i].url + "\"> " +
                  "  <img class=\"am-circle\" width=\"50\" height=\"50\" src=\""+ apps[i].icon +"\"> "+
                  "  <h3 class=\"am-gallery-title\">"+ apps[i].name +"</h3></a></div></li>"	;	
	}  
	appHtml += "<li><div class=\"am-gallery-item\"> "+
               "<a href=\"\"> "+
               "<img class=\"am-circle\" width=\"50\" height=\"50\" src=\"<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>/minimages/bgrc.png\">"+
               " <h3 class=\"am-gallery-title\">更多</h3></a></div></li>";
    $("#common").append(appHtml);
    
    </script>
    


<hr data-am-widget="divider"  style="margin: 0px;" class="am-divider am-divider-default" />





<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default  testNavClass am-no-layout" id="">
          <ul class="am-navbar-nav am-cf am-avg-sm-3">
              <li>
                <a href="<%=request.getContextPath() %>/mobile/web_index.html" class="">
                      <span class="am-icon-home"></span>
                    <span class="am-navbar-label">首页</span>
                </a>
              </li>
              <li>
                <a href="<%=request.getContextPath() %>/mobile/web_appCenter.html" class="">
                      <span class="am-icon-th-large"></span>
                    <span class="am-navbar-label">应用中心</span>
                </a>
              </li>
              <li>
                <a href="<%=request.getContextPath() %>/portal/portal_portal.html" class="">
                      <span class="am-icon-user"></span>
                    <span class="am-navbar-label">我的门户</span>
                </a>
              </li>
          </ul>
      </div>
</body>
</html>