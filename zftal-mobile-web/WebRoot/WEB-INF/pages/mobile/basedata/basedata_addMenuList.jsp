<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=systemPath %>/js/jquery/dragsort/jquery.dragsort-0.4.min.js"></script>
    <script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="<%=systemPath %>/js/json/json2.js"></script>
<title>信息类菜单管理</title>
	<style type="text/css">
		.formlist td {vertical-align: top;}
		 ul { margin:0px; padding:0px; margin-left:0px; }
		.ul1_class, .ul2_class { width:350px; list-style-type:none; margin:0px; }
		.ul1_class li, .ul2_class li { float:left; padding:5px; width:100px; height:25px; }
		.ul1_class div, .ul2_class div { width:90px; height:12px; border:solid 1px black; background-color:#E0E0E0; text-align:center; padding-top:3px; }
	    .ul2_class { float:right; }
		.placeHolder div { background-color:white !important; border:dashed 1px gray !important; }
	</style>
	
  <script type="text/javascript">  
  		$(function(){
  		
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
  		
  		function addMenu(){
  			var xxlid = $("input[name='xxlid']").val();
  			showWindowV2("添加菜单", "<%=request.getContextPath()%>/basedata/basedata_addMenuItem.html?xxlid=" + xxlid, 500, 200);
  		}
  		function addMenuSecond(obj){
  			var xxlid = $("input[name='xxlid']").val();
  			var yjcdId = $(obj).closest(".menuInfo").find("input[name='yjcdId']").val();
  			showWindowV2("添加菜单", "<%=request.getContextPath()%>/basedata/basedata_addMenuItem.html?xxlid=" + xxlid +"&yjcdId=" + yjcdId, 500, 200);
  		}
  		
  		function editMenu(obj){
            var cdId = $(obj).closest("li").find("input[name='yjcdId']").val();
            showWindowV2("添加菜单", "<%=request.getContextPath()%>/basedata/basedata_editMenuItem.html?cdId=" + cdId, 500, 200);
        }
        
        function deleteMenu(obj){
        	var xxlid = $("input[name='xxlid']").val();
        	var cdId = $(obj).closest("li").find("input[name='yjcdId']").val();
            $.post("<%=request.getContextPath()%>/basedata/basedata_deleteMenuItem.html",
                "cdId=" + cdId +"&xxlid=" + xxlid  ,function(data){
                    	var callback = function(){
							//reflashPage();
							location.reload();
						};
						if(data.success){
							processDataCall(data, callback);
						}else{
							showWarning(data.text);
						}
                            
            },"json");
            }
        function getback(){
        	var guid = $("input[name='xxlid']").val();
            window.location.href = "<%=request.getContextPath() %>/basedata/basedata_setInfoClazz.html?classId="+guid;

        }
  </script> 
  
</head>
<body>

<div class="searchbox_rs"> 
	<input type="hidden"  name="xxlid" value="${infoClass.guid }"/>
	
        <div>
            <h1>说明： 创建一级菜单，每个一级菜单下可创建二级菜单。</h1>
            <input id="menuInfo_val" name="menuInfo" type="hidden" value="" />
        </div>
    </div>
    <div  class="demo_add">
        <a href="#" onclick="getback()">← 返回</a>
    </div>
    <div id="conDiv" class="demo_college">
        <div>
	        <div>
	            	<c:forEach var="firstmenu" items="${firstMenuList}" varStatus="st">
                    
		                <li class="menuInfo"  style="float: none">
	                 <input type="hidden"  value="${firstmenu.classCdid}" name="yjcdId"/>
		                <div>
		                    <h3 class="college_title" id="">
		                    <div>
		                    
			                    <span id="" name="" class="title_name">
				                    <span style="text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;">
				                          ${firstmenu.menuName}
				                    </span>
			                    </span>
			                    
			                    <div class="explain_left01" style="display:none;top:24px;" >
			                    <div class="explain_con" >
            
						            <table width="100%" border="0" class="explain_tab" >
						            <tbody>
						            <tr>
						            <td><span class="title">菜单名称：</span>${firstmenu.menuName}</td>
						            </tr>
						            </tbody>
							            <tfoot><tr><td colspan='2'>
							            <a name='xg1' href='#' class='name' style='float:none' onclick='editMenu(this)'>修改</a>
							            <a name='sc' href='#' class='name' style='float:none' onclick='deleteMenu(this)' >删除</a>
							            </td></tr>
							            </tfoot>
						            </table>
						            
					            </div>
					            </div>
			                    
		                    </div>
		                    </h3>
		                    
		                    <div id="secondDiv" name="" class="con after">
		                    <ul id="" class="sub_menu">
	                            <c:forEach var="secondmenu" items="${secondMenuList}" varStatus="st">
		                    		<c:if test = "${secondmenu.firstMenuId eq firstmenu.classCdid}"> 
		                    		
				                    	<li class="subMenu"  id=""><div>
		                    		<input type="hidden"  value="${secondmenu.classCdid}" name="yjcdId"/>
			                                <span class="college_li college_checkbox"  id="" >
			                                 	${secondmenu.menuName}
			                                </span>
			                                
			                                <div class="explain_left01" style="display:none;top:24px;" >
						                    <div class="explain_con" >
			            
									            <table width="100%" border="0" class="explain_tab" >
									            <tbody>
									            <tr>
									            <td><span class="title">菜单名称：</span>${secondmenu.menuName}</td>
									            </tr>
									            </tbody>
										            <tfoot><tr><td colspan='2'>
										            <a name='xg1' href='#' class='name' style='float:none' onclick='editMenu(this)'>修改</a>
										            <a name='sc' href='#' class='name' style='float:none' onclick='deleteMenu(this)' >删除</a>
										            </td></tr>
										            </tfoot>
									            </table>
									            
								            </div>
								            </div>
			                                
		                                </div>
		                                </li>
		                    		
		                    		</c:if>
		                    	</c:forEach>
		                    </ul> 
		                    
		                    <ul>
		                         <li><a name="zj" class="college_add" href="#" onclick="addMenuSecond(this)" >+ 添加</a></li>
		                    </ul>
		                    
		                   </div>
	                   </div>
	                   </li>
	                   
	                </c:forEach>
	            </ul>
	        </div>
        </div>
    </div> 
    <div id="menu_add" class="demo_add">
        <a href="#" onclick="addMenu()">+ 添加</a>
    </div>
    
</body>
</html>