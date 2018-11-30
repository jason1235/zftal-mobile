<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <%--<script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>--%>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/normal/resume/resume.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <style type="text/css">
    span.history{
    	padding:0 30px;
    	float:right;
    	margin-top:-1px;
    	cursor:pointer;
    	background:#ECF5FF;
    	color:#2672d6;
    	border-left:#CBE4F8 1px solid;
    	border-top:#CBE4F8 1px solid;
    }
    span.on{
    	background:#4381D1;
    	color:#ffffff;
    	border-left:#4169E1 1px solid;
    	border-top:#4169E1 1px solid;
    }
    li a.selected{
    	border:1px solid #adc6e7;
    	background:#ffffff;
    	color:#333333;
    	height: 22px;
	    line-height: 22px;
	    padding: 0 9px;
    }
    </style>
    <script type="text/javascript">
    $(function(){
    	if($.browser.msie){
    		if($.browser.version=="6.0"){
    			//IE6暂不支持菜单浮动
    		}else if($.browser.version=="7.0"){
    			//IE7暂不支持菜单浮动
    		}else{
    			//initFloatMenu();
    		}
    	}else{
    		//initFloatMenu();
    	}
    	buttonInitialize();
    	loadDataByCatalog();
    	//buttonInitialize();//简历页面按钮事件初始化
    	menuLayerInitialize();//菜单层事件初始化

    	$("#all-info").click(function(){
            //  showWindow("全部信息",_path+"/normal/staffResume_listAll.html?globalid="+getPersonGh(),720,450);
                showTopWin( _path + "/normal/staffResume_listAll.html?globalid="+getPersonGh(), "810", "730", "yes" );
            });
    });

    </script>
  </head>
  <body>
  <div class="toolbox">
<!-- 按钮 -->
	<div class="buttonbox">
        <ul>
            <li id="all-info">
                <a class="btn_ck" href="#">教职工档案</a>
            </li>
        </ul>
		<a class="btn_fh_rs" style="cursor: pointer" onclick="javascript:history.go(-1)">返回</a>
	</div>
  	<p class="toolbox_fot">
		<em></em>
	</p>
	</div>
  <div id="position-fixed" style="top:0; background:#fff;">
  <div class="title_xxxx">
	<span class="people_xx">${bean.values.xm } （职工号： ${bean.values.gh }）
		<input type="hidden" name="gh" value="${bean.values.gh }"/>
		<input type="hidden" name="globalid" value="${bean.values.globalid }"/>
		<input type="hidden" name="returnUrl" value="${returnUrl }"/>
		<input type="hidden" id='editable' name="editable" value="${editable}"/>
	</span>
    <span class="wxts">温馨提醒：<span> 点击下面的类别， 可以快速定位到您所要查看的信息</span></span>
    <span class="history">未显示完整记录</span><input type="hidden" id="history" value="${history }"/>
  </div>
    <div class="position_xxxx after">
	    <ul class="list_xxxx">
	    <input type="hidden" id="currentCatalog" value="${catalogId }"/>
	    	<c:forEach items="${catalogs}" var="catalog" varStatus="vsc">
		    	<li id="${catalog.guid}" style="position:relative;">
		    	<a href="#">${catalog.name }</a>
		    	<c:if test="${catalog.classes != null}">
		    	<c:forEach items="${catalog.classes}" var="clz" varStatus="vs">
		    	<c:if test="${vs.first}"><div class="list_xxxx_downmenu" style="display:none;z-index:1;"><dl></c:if>
		    	<c:if test="${clz.typeInfo.editable}">
		    	<dd><a style="cursor: pointer;">${clz.name }</a><input type="hidden" value="${clz.guid }"/></dd>
		    	</c:if>
		    	<c:if test="${vs.last}"></dl></div></c:if>
		    	</c:forEach>
		    	</c:if>
		    	</li>
	    	</c:forEach>
	    	
	    </ul>
	</div>
	</div>
    <div id="infoContent">
    </div>
  </body>
</html>
