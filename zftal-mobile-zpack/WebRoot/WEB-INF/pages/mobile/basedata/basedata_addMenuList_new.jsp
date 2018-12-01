<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    
    <style type="text/css">
      .sl_yx_con li a .modClass img{opacity:0;}
      .sl_yx_con li a .delClass img{opacity:0;}
      .sl_yx_con .infolcasCatalogMod{opacity:0;}
      .sl_yx_con .infolcasCatalogDel{opacity:0;}
      .sl_yx_con li a {padding: 3px 10px 3px 5px;border: none;background:#F5F5F5;text-decoration: none;}
      .sl_yx_con li .addInfoClass{padding: 3px 10px 3px 5px;border: 1px solid #ddd;background: #fff;text-decoration: none;}
    </style>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
    	$(".sl_yx_con li a").hover(function(){
        	$(this).find("img").css({ opacity:1});
        	},function(){
                $(this).find("img").css({ opacity:0});
            }
        )
        
        $(".sl_yx_con .Catalog").hover(function(){
        	$(this).find(".infolcasCatalogMod").css({ opacity:1});
        	$(this).find(".infolcasCatalogDel").css({ opacity:1});
        	$(this).find("img").css({ opacity:1});
        	},function(){
                $(this).find(".infolcasCatalogMod").css({ opacity:0});
                $(this).find(".infolcasCatalogDel").css({ opacity:0});
                $(this).find("img").css({ opacity:0});
            }
        )
        
        $(".modClass").click(function(){
        	var cdId = $(this).closest("li").find("input[name='ejcdId']").val();
        	editMenu(cdId);
        })
        
        $(".delClass").click(function(){
        	var cdId = $(this).closest("li").find("input[name='ejcdId']").val();
        	openAlert("确定要删除此菜单吗？", "w");
        	$("#why_sure").click(function(){
        		deleteMenu(cdId);
			});
        	
        })
        
        $(".infolcasCatalogMod").click(function(){
        	var cdId = $(this).closest(".Catalog").find("input[name='yjcdId']").val();
        	editMenu(cdId);
        })
        
        $(".infolcasCatalogDel").click(function(){
        	var cdId = $(this).closest(".Catalog").find("input[name='yjcdId']").val();
        	openAlert("确定要删除此菜单吗？", "w");
        	$("#why_sure").click(function(){
        		deleteMenu(cdId);
			});
        })
        
        $(".addInclass").click(function(){
            var yjcdId = $(this).closest(".sl_yx_con").find("input[name='yjcdId']").val();
            var xxlid = $("input[name='xxlid']").val();
            addMenuSecond(xxlid,yjcdId);
        })
        
        $("#xxlmladd").click(function(){
        	var xxlid = $("input[name='xxlid']").val();
        	addMenu(xxlid);
        })
        
        $("#cancel").click(function(){
        	var guid = $("input[name='xxlid']").val();
        	window.location.href = "<%=request.getContextPath() %>/basedata/basedata_setInfoClazzNew.html?classId="+guid;
        });
        
        $(".close").click(function(){
        	var guid = $("input[name='xxlid']").val();
        	window.location.href = "<%=request.getContextPath() %>/basedata/basedata_setInfoClazzNew.html?classId="+guid;
        });
    });
    
    function editMenu(cdId){
        var url = "<%=request.getContextPath()%>/basedata/basedata_editMenuItem.html?cdId=" + cdId;
    	$("#myModal_toji").find(".modal-content").width(700);
    	$("#myModal_toji").find(".modal-content").height(200);
    	$("#myFrame").height(200);
    	$("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
    }
    function addMenu(xxlid){
		var url = "<%=request.getContextPath()%>/basedata/basedata_addMenuItem.html?xxlid=" + xxlid;
    	$("#myModal_toji").find(".modal-content").width(700);
    	$("#myModal_toji").find(".modal-content").height(200);
    	$("#myFrame").height(200);
    	$("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
	}
	function addMenuSecond(xxlid,yjcdId){
		var url = "<%=request.getContextPath()%>/basedata/basedata_addMenuItem.html?xxlid=" + xxlid +"&yjcdId=" + yjcdId;
    	$("#myModal_toji").find(".modal-content").width(700);
    	$("#myModal_toji").find(".modal-content").height(200);
    	$("#myFrame").height(200);
    	$("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
	}
		
    function deleteMenu(cdId){
        $.post("<%=request.getContextPath()%>/basedata/basedata_deleteMenuItem.html",
            "cdId=" + cdId  ,function(data){
		        	var callback = function(){
						location.reload();
					};
					if(data.success){
						openAlert("成功删除菜单", "s", "auto", callback);
					}else{
						openAlert(data.text, "e");
					}
                        
        	},"json");
        }
    </script>
    </head>
  <body>
  <input type="hidden"  name="xxlid" value="${infoClass.guid }"/>
  <div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">信息类移动端展示效果菜单维护</h4>
  </div>
  <div class="modal-body" style="background-color: #fff;">
  <div class="toolbox" style="border: 0;padding: 0;margin: 0">
        <div class="btn-group" id="but_ancd">
            <span type="button" class="btn btn-default" id="btn_zj">当前信息类为:&nbsp;<font color="green">${infoClass.name }</font></span>
        </div>
    </div>
    <br />
   <jsp:include page="/WEB-INF/pages/globalweb/modal.jsp" flush="true"></jsp:include>
  	<div class="panel panel-default" style="margin-top: 1px;">
  		<div class="panel-body table-responsive">
        
        	<c:forEach var="firstmenu" items="${firstMenuList}" varStatus="st">
	            <div class="sl_yx_con" style="padding-bottom: 0px;padding-top: 0px;">
	                <div class="col-xs-1 col-sm-1 Catalog" style="padding-left: 4px;padding-right: 0px;">
	                	<input type="hidden"  value="${firstmenu.classCdid}" name="yjcdId"/>
	                				
	                                <span class="infolcasCatalog">${firstmenu.menuName}：&nbsp;</span>
	                    			<br>
	                                <span class="infolcasCatalogMod" ><img src="<%=request.getContextPath()%>/img/tab_xg.png" alt="修改" width="16" height="16" style="border:none; "></span>
	                                <span class="infolcasCatalogDel" ><img src="<%=request.getContextPath()%>/img/tab_sc.png" width="15" height="15"></span>
	                </div>
		                <div class="col-xs-11 col-sm-11">
		                    <ul >
		                    	<c:forEach var="secondmenu" items="${secondMenuList}" varStatus="st">
		                    		<c:if test = "${secondmenu.firstMenuId eq firstmenu.classCdid}"> 
			                        <li >
			                            <a href="#" <c:if test="${clasz.guid == classId}"> style="background:#FFFFFF;"</c:if>>
			                            	<input type="hidden"  value="${secondmenu.classCdid}" name="ejcdId"/>
			                                <span class="infolcasName" >${secondmenu.menuName}&nbsp;</span>
			                                <span class="modClass" ><img src="<%=request.getContextPath()%>/img/tab_xg.png" alt="修改" width="16" height="16" style="border:none; "></span>
			                                <span class="delClass" ><img src="<%=request.getContextPath()%>/img/tab_sc.png" width="15" height="15"></span>
			                            </a>
			                        </li>
			                        </c:if>
		                    	</c:forEach>
		                    		<li>
			                             <button type="button" class="btn btn-default btn-sm addInclass"  ><span class="glyphicon glyphicon-plus"></span> 增加二级菜单 </button>
			                        </li>
		                    </ul>
		                </div>
		                
	            </div>
        	</c:forEach>
        	<div class="sl_yx_con">
	        	<div class="col-xs-1 col-sm-1 " style="padding-left: 1px;padding-right: 0px;">
                    <button type="button" class="btn btn-default btn-sm" id="xxlmladd"><span class="glyphicon glyphicon-plus"></span> 增加一级菜单 </button>
	        	</div>

            </div>
        </div>
  	</div>
  	</div>
  	
  	<div class="modal-footer" style="background-color: #fff;">
                <button type="button" class="btn btn-primary" id="cancel">返 回</button>
    </div>
            
	</body>
</html>