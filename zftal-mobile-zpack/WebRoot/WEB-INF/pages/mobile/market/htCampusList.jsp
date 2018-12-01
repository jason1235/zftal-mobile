<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/hrm/head_v5.ini" %>
		<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
		<style type="text/css">
			.virtal-catalog td {
				
				font-weight:bold;
			}
		</style>
	</head>
	<body>
	    <div class="container_ydht sl_all_bg">
	    <div class="toolbox">
	        <div class="btn-group" id="but_ancd">
	          <button type="button" class="btn btn-default" id="btn_zj">增加</button>
	          <button type="button" class="btn btn-default" id="btn_xg">修改</button>
	          <button type="button" class="btn btn-default" id="btn_sc">删除</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	   
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/market/market_htCampusList.html" method="post">
		            	
        <table class="table table-striped sl_all_bg table-bordered text-left" style="table-layout: fixed;">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>校区名称</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    	<s:iterator value="list" var="p" status="st">
	                <tr class="pxtr">
	                    <td>
	                    	<input name="idckb" type="checkbox" id="id" value="${p.id }"/>
	                    </td>
	                    <td>${p.name }</td>
	                </tr>
                </s:iterator>
		    </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
	    </form>
	    </div>
		<script type="text/javascript">
		
			$("#btn_zj").click(function(){
		    	location.href="<%=request.getContextPath()%>/market/market_htAddCampus.html";
			});
				
			
			//修改按钮
			$("#btn_xg").click(function(){
			 	if($("input[name='idckb']:checked").length==0){
	              	openAlert("请先选中操作行！", "e");
	              	return false;
	          	}
	      	 	if($("input[name='idckb']:checked").length>1){
	              	openAlert("不能选中多行！", "e");
	              	return false;
	          	}	
	      	 
	      		var id = $("input[name='idckb']:checked").val();
	    	 	var url = _path+"/market/market_htAddCampus.html?id="+id;
            	location.href=url;
			});
				
				//删除按钮
		     $("#btn_sc").click(function(){
					var checked=$("input[id='id']:checked");
                    if(checked.length==0){
                        openAlert("请选择行", "e");
                        return false;
                    }else if(checked.length>1){
                        openAlert("只能对单行进行操作", "e");
                        return false;
                    }
                    
					var id = checked.val();
                    openAlert("确定要删除吗？", "w");
                    
                    $("#why_sure").click(function(){
                    	
                    	$.ajax({
							type: 'POST',
							dataType:"text",
							url: "<%=request.getContextPath() %>/market/market_htDelCampus.html",
							data: {"id":id},
							success: function(data){
								var obj = eval('(' + data + ')');//由JSON字符串转换为JSON对象
								if(obj.flag == "true" ){
									openAlert(obj.msg, "s","auto", function(){
										location.href = _path + "/market/market_htCampusList.html";
								    });
								}else{
									errorAlert(obj.msg);
								}
							},
							error: function(data, textStatus, errorThrown){
								errorAlert("删除校区失败");
							}
						});
                    });
				});
		</script>
	</body>
</html>
