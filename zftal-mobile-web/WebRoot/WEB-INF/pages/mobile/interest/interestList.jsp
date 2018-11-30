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
	          <button type="button" class="btn btn-default" id="btn_xg">审核</button>
	          <button type="button" class="btn btn-default" id="btn_sc">删除</button>
	          <button type="button" class="btn btn-default" id="btn_seetz">查看帖子</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	   
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/interest/interest_htInterestList.html" method="post">
		            	
        <table class="table table-striped sl_all_bg table-bordered text-left" style="table-layout: fixed;">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>圈子名称</th>
                    <th>圈子图片</th>
                    <th>圈子状态</th>
                    <th>圈子描述</th>
                    <th>圈子分类</th>
                    <th>学生证</th>
                    <th>成员头衔</th>
                    
                </tr>
            </thead>
		    <tbody id="list_body" >
		    	<s:iterator value="list" var="p" status="st">
	                <tr class="pxtr">
	                    <td>
	                    	<input name="idckb" type="checkbox" id="id" value="${p.id }"/>
	                    </td>
	                    <td>${p.name }</td>
	                    <td><img style="height: 40px;width: 40px;" src="<%=request.getContextPath() %>/${p.img}"></td>
	                    <td>
	                    	<s:if test="%{#p.status == 0}"><span id="tdstatus" style="color:red">未审核</span></s:if>
                        	<s:if test="%{#p.status == 1}"><span id="tdstatus" style="color:green">审核通过</span></s:if>
                        	<s:if test="%{#p.status == 2}"><span id="tdstatus" style="color:red">审核不通过</span></s:if>
	                    </td>
	                    <td style="white-space:nowrap;overflow: hidden;text-overflow:ellipsis" title="${p.content }">${p.content }</td>
	                    <td>
	                    	<s:if test="%{#p.type == 1}">涨姿势</s:if>
                        	<s:if test="%{#p.type == 2}">段子手</s:if>
                        	<s:if test="%{#p.type == 3}">我最美</s:if>
                        	<s:if test="%{#p.type == 4}">娱乐八卦</s:if>
                        	<s:if test="%{#p.type == 5}">悦读</s:if>
                        	<s:if test="%{#p.type == 6}">吃喝玩乐</s:if>
                        	<s:if test="%{#p.type == 7}">交友</s:if>
                        	<s:if test="%{#p.type == 8}">学生社团</s:if>
	                    </td>
	                    <td><img style="height: 40px;width: 40px;" src="<%=request.getContextPath() %>/${p.idCard}"></td>
	                    <td>${p.title }</td>
	                </tr>
                </s:iterator>
		    </tbody>
        </table>
        <ct:page pageList="${list }" query="${interestQuery }" />
	    </form>
	    </div>
		<script type="text/javascript">
			$('#list_body tr').find('input[type=checkbox]').bind('click',function(e){
					e.stopPropagation();
					var tdstatus = $(this).parent().parent().find('#tdstatus').html();
					
					tdstatus = (tdstatus==null?'':trim(tdstatus));
					if (tdstatus == '未审核') {
						$('#btn_xg').css('display','');
						$('#btn_seetz').css('display','none');
					} else {
						$('#btn_xg').css('display','none');
						$('#btn_seetz').css('display','');
					}
					if (!$(this).prop("checked")) {
						$('#btn_xg').css('display','');
						$('#btn_seetz').css('display','');
					}
					
					var $that = $(this);
					$("#list_body").find('input[type="checkbox"]').each(function(){
					
						if ($that.val() != $(this).val()) {
							$(this).removeAttr('checked');
							$(this).parent().parent().removeClass('current');
						}
					});
					$(this).parent().parent().toggleClass('current');
				});
			
			//审核按钮
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
	    	 	var url = _path+"/interest/interest_getInterest.html?interestId="+id;
	    	 	window.location.href = url;
			});
			
			//帖子按钮
			$("#btn_seetz").click(function(){
			 	if($("input[name='idckb']:checked").length==0){
	              	openAlert("请先选中操作行！", "e");
	              	return false;
	          	}
	      	 	if($("input[name='idckb']:checked").length>1){
	              	openAlert("不能选中多行！", "e");
	              	return false;
	          	}	
	      	 
	      		var id = $("input[name='idckb']:checked").val();
	    	 	var url = _path+"/interest/interest_htInterestPostList.html?interestPostQuery.interestId="+id;
	    	 	window.location.href = url;
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
					var source = checked.parent().find('input[type="hidden"]').val();
                    openAlert("确定要删除吗？", "w");

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/interest/interest_delInterest.html?interestId="+id, null, function(data){
							var callback = function(){
								location.reload();
							};
							processCall(data, callback);
						}, "json");
					});
				});
		</script>
	</body>
</html>
