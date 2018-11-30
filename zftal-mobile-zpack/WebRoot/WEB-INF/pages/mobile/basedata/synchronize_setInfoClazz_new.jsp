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
      .sl_yx_con li a {padding: 3px 10px 3px 5px;border: none;background:#FFFFFF;text-decoration: none;}
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
    	var catalogName = '${infoClass.catalog.name}';
    	if(catalogName.indexOf("个人档案") != -1 ){
	    	$("#btn_szda").css("display","block");
    	}else{
    		$("#btn_szda").css("display","none");
    	}
    	$(".panel-heading .expand_close").eq(0).removeClass('expand1').addClass('close1');
        $(".panel-body").eq(0).show();
        $(".panel-heading").click(function(e) {
            if($(this).children(".expand_close").attr("class").indexOf("expand1")>0){
                $(this).children(".expand_close").removeClass('expand1').addClass('close1');
                $(this).next(".panel-body").slideDown();
            }else{
                $(this).children(".expand_close").removeClass('close1').addClass('expand1');
                $(this).next(".panel-body").slideUp();
            }

        });
        
        $(".sl_yx_con li a").hover(function(){
        	$(this).find("img").css({ opacity:1});
        	},function(){
                $(this).find("img").css({ opacity:0});
            }
        )
        
        $(".sl_yx_con li a .infolcasName").click(function(){
        	$(".sl_yx_con").find("a").css("background","#FFFFFF");
        	$(this).css("background","#E7E7E7");
        	var guid = $(this).closest("li").find("input[name='claszid']").val();
        	$("#classId").val(guid);
        	var claszname = $(this).closest("li").find("input[name='infoClassname']").val();
        	$("#infoClassname").val(claszname);
        	var catalogName = $(this).closest(".sl_yx_con").find(".infolcasCatalog").html();
        	if(catalogName.indexOf("个人档案") != -1 ){
    	    	$("#btn_szda").css("display","block");
        	}else{
        		$("#btn_szda").css("display","none");
        	}
        	$("form:first").submit();
        })
        
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
        
        
     // 信息类增加
        $(".addInclass").click(function(){
            var cid = $(this).closest(".sl_yx_con").find("input[name='catalogid']").val();
            var url = "<%=request.getContextPath() %>/basedata/infoclass_editNew.html?catalog.guid="+cid;
            $("#myModal_toji").find(".modal-content").width(800);
            $("#myModal_toji").find(".modal-content").height(450);
            $("#myFrame").height(450);
            $("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
        })
        
        $("#xxlmladd").click(function(){
        	var url = "<%=request.getContextPath() %>/basedata/infocatalog_editNew.html?type=baseData";
        	$("#myModal_toji").find(".modal-content").width(700);
        	$("#myModal_toji").find(".modal-content").height(200);
        	$("#myFrame").height(200);
        	$("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
        })
        
        $(".modClass").click(function(){
        	var guid = $(this).closest("li").find("input[name='claszid']").val();
        	var url = "<%=request.getContextPath() %>/basedata/infoclass_editNew.html?guid="+guid;
        	$("#myModal_toji").find(".modal-content").width(800);
        	$("#myModal_toji").find(".modal-content").height(550);
        	$("#myFrame").height(550);
        	$("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
        })
        
        $(".delClass").click(function(){
        	var guid = $(this).closest("li").find("input[name='claszid']").val();
        	$.post("<%=request.getContextPath() %>/basedata/basedata_isPublishPicture.html","classId="+guid,function(data){
                if(data.success){
                	openAlert("该信息类已发布为服务，请先去服务管理中进行信息类服务删除！", "e");
                }else{
                	openAlert("确定要删除此信息类吗？", "w");
                	$("#why_sure").click(function(){
                		$.post('<%=request.getContextPath() %>/basedata/infoclass_remove.html', 'guid='+guid, function(data){
                			var callback = function(){
        						//reflashPage();
        						location.href = '<%=request.getContextPath() %>/basedata/basedata_setInfoClazzNew.html';
        					};
                           	openAlert("成功删除信息类", "s", "auto", callback);
                        }, "json");
                		
        				
        			});
		
                }
            },"json");
        	
        })
        
        $(".infolcasCatalogMod").click(function(){
        	var guid = $(this).closest(".Catalog").find("input[name='catalogid']").val();
        	var url = "<%=request.getContextPath() %>/basedata/infocatalog_editNew.html?guid="+guid;
        	$("#myModal_toji").find(".modal-content").width(700);
        	$("#myModal_toji").find(".modal-content").height(200);
        	$("#myFrame").height(200);
        	$("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
        })
        
        $(".infolcasCatalogDel").click(function(){
        	var len = $(this).closest(".sl_yx_con").find("ul").children("li").length;
        	if(len > 1){
        		openAlert("该信息类目录下存在信息类，请先删除该目录下信息类！", "e");
        	}else{
	        	var guid = $(this).closest(".Catalog").find("input[name='catalogid']").val();
	        	openAlert("确定要删除此信息类目录吗？", "w");
	        	$("#why_sure").click(function(){
	        		$.post('<%=request.getContextPath() %>/basedata/infocatalog_remove.html', 'guid='+guid, function(data){
	        			var callback = function(){
							//reflashPage();
							location.reload();
						};
	                   	openAlert("成功删除信息类目录", "s", "auto", callback);
	                }, "json");
	        		
					
				});
        	}
        	
        })
        
        

    });
    
    </script>
    </head>
  <body>
  <div class="container sl_all_bg" style="width: 100%;">
  <form action="<%=request.getContextPath() %>/basedata/basedata_setInfoClazzNew.html" method="post">
      <input type="hidden" id="classId" name="classId" value="${classId }" />
      <input type="hidden" name="catalogId" value="${catalogId }" />
      <input type="hidden" name="fixedWin" value="${fixedWin }" />
    </form>
  
  	<div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><span style="width: 100px;">信息类列表</span></h3>
            <a href="javascript:;" class="expand_close expand1">展开关闭</a>
        </div>
        <div class="panel-body table-responsive">
        
        	<c:forEach items="${catalogs }" var="catalog">
	            <div class="sl_yx_con" style="padding-bottom: 0px;padding-top: 0px;background: #FFFFFF;">
	                <div class="col-xs-1 col-sm-1 Catalog" style="padding-left: 4px;padding-right: 0px;">
	                
	                				<input type="hidden" value="${catalog.guid}" name="catalogid"/>
	                                <span class="infolcasCatalog">${catalog.name }：&nbsp;</span>
	                    			<br>
	                                <span class="infolcasCatalogMod" ><img src="<%=request.getContextPath()%>/img/tab_xg.png" alt="修改" width="16" height="16" style="border:none; "></span>
	                                <span class="infolcasCatalogDel" ><img src="<%=request.getContextPath()%>/img/tab_sc.png" width="15" height="15"></span>
	                </div>
		                <div class="col-xs-11 col-sm-11">
		                    <ul >
		                    	<c:forEach items="${catalog.classes}" var="clasz">
			                        <li >
			                            <a href="#" <c:if test="${clasz.guid == classId}"> style="background:#E7E7E7;"</c:if>>
			                            	<input type="hidden" value="${clasz.guid}" name="claszid"/>
			                            	<input type="hidden" value="${clasz.name}" name="claszname"/>
			                                <span class="infolcasName" >${clasz.name }&nbsp;</span>
			                                <span class="modClass" ><img src="<%=request.getContextPath()%>/img/tab_xg.png" alt="修改" width="16" height="16" style="border:none; "></span>
			                                <span class="delClass" ><img src="<%=request.getContextPath()%>/img/tab_sc.png" width="15" height="15"></span>
			                            </a>
			                        </li>
		                    	</c:forEach>
		                    		<li>
			                             <button type="button" class="btn btn-default btn-sm addInclass"  ><span class="glyphicon glyphicon-plus"></span> 增加信息类 </button>
			                        </li>
		                    </ul>
		                </div>
		                
	            </div>
        	</c:forEach>
        	<div class="sl_yx_con" style="background: #FFFFFF;">
	        	<div class="col-xs-1 col-sm-1 " style="padding-left: 1px;padding-right: 0px;">
                    <button type="button" class="btn btn-default btn-sm" id="xxlmladd"><span class="glyphicon glyphicon-plus"></span> 增加目录 </button>
	        	</div>

            </div>
        </div>
    </div>
  </div>
  <jsp:include page="synchronize_property_new.jsp" flush="true"></jsp:include>
	</body>
</html>