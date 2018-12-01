<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=systemPath %>/js/jquery/dragsort/jquery.dragsort-0.4.min.js"></script>
    <script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="<%=systemPath %>/js/json/json2.js"></script>
    <script type="text/javascript">
        var menuInfo=${menuInfo};
        if (typeof menuInfo == undefined) {
        	menuInfo = {};
        } else {
        	if (!menuInfo.hasOwnProperty('menu')) {
        		menuInfo.menu = {};
        	}
        	if (!menuInfo.menu.hasOwnProperty('button')) {
        		menuInfo.menu.button = [];
        	}
        }
        $(function(){
        	viewMenu(menuInfo);

        	$("#menuList").dragsort({ dragSelector: ".menuInfo", 
                dragSelectorExclude:"a", 
                dragBetween: true, 
                dragEnd: refulshView, 
                placeHolderTemplate: "<li class='placeHolder' style='float: none' ><div></div></li>" });

            

            $(".demo_college li .college_li").live('mouseenter',function(){
                $(this).next().show();
                $(this).parent().css("position","relative");
            })
            
            $(".demo_college li .college_li").live('mouseleave',function(){
                $(this).next().hide();
                $(this).parent().css("position","");
            })

            $(".college_title .title_name").live('mouseenter',function(){
                $(this).next().show();
                $(this).parent().css("position","relative");
            })
            
            $(".college_title .title_name").live('mouseleave',function(){
                $(this).next().hide();
                $(this).parent().css("position","");
            })
            
            $(".explain_left01,.explain_right01").live('mouseenter',function(){
                $(this).show();
                $(this).parent().css("position","relative");
            })
            
            $(".explain_left01,.explain_right01").live('mouseleave',function(){
                $(this).hide();
                $(this).parent().css("position","");
            })
            
        });

        
        function typeText(obj){
            var text="待定";
            var trHtml="";
            if(obj.sub_button!=null&&obj.sub_button.length>0){
                text="菜单";
            }
            else if(obj.type=='click'){
            	text="点击推事件";
            	trHtml="<tr><td  style='word-break:break-all;' colspan='2'><span class=\"title\">菜单KEY值：</span>"+obj.key+"</td></tr>"
            }
            else if(obj.type=='view'){
                text="跳转URL";
                trHtml="<tr><td style='word-break:break-all;' colspan='2'><span class=\"title\">菜单链接：</span>"+obj.url+"</td></tr>"
            }

        	return "<div class=\"explain_left01\" style=\"display:none;top:24px;\" ><div class=\"explain_con\" >"
            +"<table width=\"100%\" border=\"0\" class=\"explain_tab\" >"
            +"<tbody><tr>"
            +"<td><span class=\"title\">菜单名称：</span>"+obj.name+"</td>"
            +"<td><span class=\"title\">菜单类型：</span>"+text+"</td></tr>"
            +trHtml
            +"</tbody>"
            +"<tfoot><tr><td colspan='2'>"
            +"<a name='xg1' href='#' class='name' style='float:none' onclick='editMenu(this)'>修改</a>"
            +"<a name='sc' href='#' class='name' style='float:none' onclick='deleteMenu(this)' >删除</a>"
            +"</td></tr></tfoot>"
            +"</table></div></div>";
        }

        function deleteMenu(obj){
            $(obj).closest("li").remove();
            refulshView();
        }

        function view2menuObj(){
            var buttonList=new Array();
            var menuList=$(".menuInfo");
            for(var i=0;i<menuList.length;i++){
            	buttonList[i]={};
            	var li_menu=$(menuList[i]);
            	var no=parseInt(li_menu.attr("menuIndex"));
                var menu=menuInfo.menu.button[no];
                buttonList[i].name=menu.name;
                buttonList[i].type=menu.type;
                buttonList[i].url=menu.url;
                buttonList[i].key=menu.key;
                buttonList[i].sub_button=new Array();
                var subMenuList=$(menuList[i]).find(".subMenu");
                for(var j=0;j<subMenuList.length;j++){
                	var li_submenu=$(subMenuList[j]);
                    var no_menu=parseInt(li_submenu.attr("menuIndex"));
                    var no_sub=parseInt(li_submenu.attr("subMenuIndex"));
                    buttonList[i].sub_button[j]=menuInfo.menu.button[no_menu].sub_button[no_sub];
                }
            }
            menuInfo.menu.button=buttonList;
        }

        function refulshView(){
        	view2menuObj();
            viewMenu();
        }

        function viewMenu(){
        	$("#menuList").html();
        	$("#notFindMenu").show();
        	$("#menu_add").show();
        	if(menuInfo.menu.button.length>0){
                $("#notFindMenu").hide();
                var buttonList=menuInfo.menu.button;
                var html = "";
                var i=0;
                for(;i<buttonList.length;i++){
                    var b=buttonList[i];
                    html+="<li class='menuInfo' menuIndex='"+i+"' style='float: none'><div>"
                    +"<h3 class=\"college_title\" id=\"college_title_"+i+"\"><div>"
                    +"<span id=\"college_span_"+i+"\" name=\"college_span\" class=\"title_name\">"
                    +"<span style=\"text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;\">"
                    +b.name+"</span></span>";
                    html+=typeText(b)+"</div></h3>"
                    +"<div id=\"con_"+i+"\" name=\"con_name_"+b.name+"\" class=\"con after\">"
                    +"<ul id=\"ul_"+i+"\" class='sub_menu'>";
                    var j=0;
                    if(b.sub_button!=null&&b.sub_button.length>0){
                        for(;j<b.sub_button.length;j++){
                            var subtn=b.sub_button[j];
                            html+="<li class='subMenu' menuIndex='"+i+"' subMenuIndex='"+j+"' id=\"li_"+j+"\"><div>"
                                +"<span class=\"college_li college_checkbox\" href=\"#\" id=\"college_li_"+j+"\" >"+subtn.name+"</span>"
                                +typeText(subtn);
                                +"</div>"
                                +"</li>"
                        }
                    }
                    html+="</ul>"; 
                    if(j<5){
                    html+="<ul>"
                         +"<li><a name=\"zj\" class=\"college_add\" href=\"#\" onclick=\"addMenu('"+i+"')\" >+ 添加</a></li>"
                         +"</ul>"
                    }
                    html+="</div>"; 
                    html+="</div></li>";
                }
                $("#menuList").html(html);
                if(i>=3){
                    $("#menu_add").hide();
                    }
                //alert( JSON.stringify(menuInfo.menu));
            }
        	$(".sub_menu").dragsort({ dragSelector: "li", 
                dragSelectorExclude:"a", 
                dragBetween: true, 
                dragEnd: refulshView,
                placeHolderTemplate: "<li class='placeHolder'><div></div></li>" });
        }

        function addMenu(num){
        	var menu={};
            showMenuInfoWin();
            initWin(null);
            $("#saveMenu_btn").click(function(){
                menu.name=$('#menu_name').val();
                menu.type=$('#menu_type').val();
                menu.key=$('#menu_key').val();
                url=$('#menu_url').val();
                type=$("#business_type").val();
                if($("#menu_url_login")[0].checked){
                	url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri="
                        +urlencode("${ssoserverLink}/wechatlogin?url="+urlencode(url))
                        +"&response_type=code&scope=snsapi_base&state="+type+"#wechat_redirect";
                }
                menu.url=url;
                var buttonList;
                if(num==null){
                	if(menuInfo.menu.button==null){
                		menuInfo.menu.button=new Array();
                    }
                    buttonList=menuInfo.menu.button;
                }else{
                    if(menuInfo.menu.button[num].sub_button==null){
                    	menuInfo.menu.button[num].sub_button=new Array();
                    }
                	buttonList=menuInfo.menu.button[num].sub_button;
                }
                buttonList[buttonList.length]=menu;
                divClose();
                viewMenu();
            });
        }

        function editMenu(obj){
            obj = $(obj).closest("li");
            var i=parseInt(obj.attr("menuIndex"));
            var menu=menuInfo.menu.button[i];
            if("subMenu"==obj.attr("class")){
                var j=parseInt(obj.attr("subMenuIndex"));
                menu=menu.sub_button[j];
            }
            showMenuInfoWin();
            initWin(menu);
            $("#saveMenu_btn").click(function(){
            	menu.name=$('#menu_name').val();
            	menu.type=$('#menu_type').val();
            	menu.key=$('#menu_key').val();
            	url=$('#menu_url').val();
            	type=$("#business_type").val();
            	if($("#menu_url_login")[0].checked){
            	    url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri="
                	    +urlencode("${ssoserverLink}/wechatlogin?&url="+urlencode(url))
                	    +"&response_type=code&scope=snsapi_base&state="+type+"#wechat_redirect";
                }
            	menu.url=url;
            	divClose();
            	refulshView();
            });
        }
        function showMenuInfoWin(){
        	var html="<div class='tab'>"
                +"<table width='100%'  border='0' class='formlist' cellpadding='0' cellspacing='0'>"
                +"<tfoot><tr><td colspan='2'>"
                +"<div class='bz'>'<span class='red'>*</span>'为必填项</div>"
                +"<div class='btn'><button id='saveMenu_btn'>保 存</button>"
                +"<button type='button' onclick='divClose();'>取 消</button></div>"
                +"</td></tr></tfoot>"
                +"<tbody>"
                +"<tr><th>菜单名称</th><td><input id='menu_name' type='text' /></td></tr>"
                +"<tr><th>菜单类型</th><td><select id='menu_type' style='width:120px'>"
                    +"<option value='view'>跳转URL</option>"
                    +"<option value='click'>点击推事件</option></select></td></tr>"
                +"<tr id='menu_key_tr'><th>菜单KEY</th><td><input id='menu_key' type='text' /></td></tr>"
                +"<tr id='menu_url_tr'><th>菜单链接</th><td><textarea rows='5' cols='40' id='menu_url'></textarea><br>"
                +"<input id='menu_url_login' type='checkbox' />自动登录"
                +"&nbsp; <select id='business_type' style='width:120px'>"
                +"<option value=''>--无--</option>"
                +"</select></td></tr></tr></tbody></table></div>";
            ymPrompt.win({message:html,
                width:"500",
                height:"300",
                title:"菜单信息",
                maxBtn:true,
                minBtn:true,
                showShadow:false,
                useSlide:true,
                maskAlphaColor:"#FFFFFF",
                maskAlpha:0.3
                 }
            );
            $("#menu_type").change(function(){
                if($(this).val()=='click'){
                    $("#menu_url_tr").hide();
                    $("#menu_key_tr").show();
                }
                else if($(this).val()=='view'){
                    $("#menu_key_tr").hide();
                    $("#menu_url_tr").show();
                }
            });
            $("#business_type").hide();
            var typeList="${businessType}".split(";");
            var str="";
            for(var i=0;i<typeList.length;i++){
                var t=typeList[i].split(":");
                if(t.length==2){
                    str+="<option value='"+t[0]+"'>"+t[1]+"</option>";
                }
            }
            if(str.length>0){
            	$("#business_type").html(str);
            }
            $("#menu_url_login").click(function(){
            	if($("#menu_url_login")[0].checked){
            	    $("#business_type").show();
                }else{
                	$("#business_type").hide();
                }
            });
        }

        function initWin(obj){
        	$("#menu_key_tr").hide();
            $("#menu_url_tr").hide();
            if(obj!=null){
	        	var text="";
	            var trHtml="";
	            $("#menu_name").val(obj.name);
	            $("#menu_type").val(obj.type);
	            $("#menu_key").val(obj.key);
	            $("#menu_url").val(obj.url);
	            if(obj.sub_button!=null&&obj.sub_button.length>0){
	                var td = $("#menu_type").closest("td");
	                $("#menu_type").remove();
	                td.html("菜单");
	                return;
	            }
            }
            if($("#menu_type").val()=='click'){
                $("#menu_key_tr").show();
            }
            else if($("#menu_type").val()=='view'){
                $("#menu_url_tr").show();
            }
            
        }

        function urlencode(str) {  
            str = (str + '').toString();
            return encodeURIComponent(str).replace(/!/g, '%21').replace(/'/g, '%27').replace(/\(/g, '%28').  
            replace(/\)/g, '%29').replace(/\*/g, '%2A').replace(/%20/g, '+');  
        }

        function saveMenu() {  
        	$("#menuInfo_val").val(JSON.stringify(menuInfo.menu));
        	$.post('<%=request.getContextPath()%>/wechat/menu/menu_save.html',
                	$("#menuInfo_val").serialize(),function(data){
        		var callback = function(){
        			location.href = "<%=request.getContextPath()%>/wechat/menu/menu_list.html#";
                };
                if(data.success){
                    processDataCall(data, callback);
                }else{
                    showWarning(data.text);
                }
            },"json");
        }

        
    </script>
  </head>
  <body>
    <div class="searchbox_rs"> 
        <div>
            <h1>说明： 可创建最多3个一级菜单，每个一级菜单下可创建最多5个二级菜单。</h1>
            <input id="menuInfo_val" name="menuInfo" type="hidden" value="" />
        </div>
    </div>
        
    <div id="conDiv" class="demo_college">
        <div id="notFindMenu" class="page_prompt">
            <div class="page_promptcon">
                <h3>温馨提示：</h3>
                <p><font color="#0f5dc2">暂无菜单信息</font></p>
            </div>
            <p>&nbsp;</p>
        </div>
        <div>
	        <div>
	            <ul id="menuList"></ul>
	        </div>
        </div>
    </div> 
    <div id="menu_add" class="demo_add">
        <a href="#" onclick="addMenu()">+ 添加</a>
    </div>
    <div id="menu_save" class="demo_add">
        <a href="#" onclick="saveMenu()">保存并同步至微信</a>
    </div>
  </body>
</html>
