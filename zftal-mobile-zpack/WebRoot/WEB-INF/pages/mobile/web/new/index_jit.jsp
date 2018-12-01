<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<title></title>

<style type="text/css">
        .am-table td {
            cursor: pointer;
            text-align: center;
        }
        
        .li-focus a {
        	color:#000;
        }
        
        ::-webkit-scrollbar {
        	display:none;
        }
        
        .hide-menu{
        	display:none;
        }
        
        .am-list-item-thumbed {
        	padding:1rem;
        }
        .am-article-title {
        	margin:2rem 0 !important;
        }
        #content {
        	margin-top:3rem;
        }
        
        .am-list {
            margin: 0;
        }

        .am-list >li {
            background: none;
            box-shadow: inset 0 1px 0 rgba(255,255,255,0.8);
        }

        .pull-action {
            text-align: center;
            height: 45px;
            line-height: 45px;
            color: #999;
        }

        .pull-action .am-icon-spin {
            display: none;
        }

        .loading .am-icon-spin {
            display: block;
        }

        .loading .pull-label {
            display: none;
        }
        
        /*菜单CSS*/
        #menuWrapper {
            position: fixed;
            z-index: 999;
            width: 90%;
            height: 3rem;
            background: #cccccc;
            overflow: hidden;
        }

        #menuScroller {
            -webkit-tap-highlight-color: rgba(0,0,0,0);
            width: 2000px;
            height: 100%;
            background-color: #CCCCCC;
            -webkit-transform: translateZ(0);
            -moz-transform: translateZ(0);
            -ms-transform: translateZ(0);
            -o-transform: translateZ(0);
            transform: translateZ(0);
            -webkit-touch-callout: none;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            -webkit-text-size-adjust: none;
            -moz-text-size-adjust: none;
            -ms-text-size-adjust: none;
            -o-text-size-adjust: none;
            text-size-adjust: none;
        }

        #menuScroller ul {
            list-style: none;
            padding: 0;
            margin: 0;
            width: 100%;
            height: 100%;
            text-align: center;
        }

        #menuScroller li {
            display: block;
            float: left;
            width: 100px;
            height: 100%;
            /*border-right: 1px solid #ccc;*/
            background-color: #fafafa;
            /*font-size: 14px;*/
            text-align:center;
        }
        
        .am-list-date{
        	float:right;
        	position:relative;
        	bottom:0rem;
        	right:1rem
        }

    </style>
</head>
<body>
<div id="main"></div>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script type="text/javascript">
	var newsLists = eval('(${newsList})');
	var showType = '${type}';
	if (showType == 'DETAIL_SHOW') {
		var news = ${news};
		
		data.news = news;
		
		
	}
			
	var menu = eval('(${menu})');
    data.menuInfo = menu;
    data.currentCatalog = '${catalogId}';
    data.header.content.title='${title}';
    data.list_news.options.type = "normal";
    var home = new Mobile.Home({
    	showMenu:false, // 隐藏菜单
    	showFooter:false, // 隐藏底部
    	userData : newsLists.news,
    	data : data,
    	listType : '${listType}',
    	showType : '${type}',
    	total : newsLists['@pagesize']
    });
    home.selectTab(0);
    
    function selectMenu(url) {
    	var now = new Date();
        var nowStr = now.getFullYear() + "-"  + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
    	if (url != null && url != '') {
    		if (/[(http)|(https)]:.*/i.test(url)) {
    			location.href = url + "&time=" + nowStr;
    		} else {
    			location.href = _path + '/mobile/' + url;
    		}
    	}
    	
        
    }
    
//    $(function() {
//            var app = new PullToRefresh(home, {
//                api : _path + "/mobile/web_list.html",
//                params : {
//                    start : 1,
//                    catalogId : '${catalogId}',
//                    size : 10,
//                    total : parseInt(newsLists['@pagesize']) 
//                }
//            });
//            app.init();
//        });  
</script>	
</body>
</html>