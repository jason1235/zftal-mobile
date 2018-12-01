<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/commons/hrm/head_v5.ini" %>

	<script type="text/javascript">
		$(function(){
			$("#cancel").click(function(){
	            window.parent.$('#myModal_toji').modal('hide');
	            return false;
	        });
	        
	        $(".close").click(function(){
	            window.parent.$('#myModal_toji').modal('hide');
	            return false;
	        });
	        
	        $("#save_btn").click(function(){
				var tb = $("input[name='fruit']:checked").val();
				if(tb==undefined){
					errorAlert("请选择表");
					return false;
				}
				var type = $('#type').val();
				if(type==1){
					window.parent.$("#pmdb").val(tb);
				}
				else {
					window.parent.$("#mdb").val(tb);
				}
				
				window.parent.$('#myModal_toji').modal('hide');
			});	
			
			function doFilter(contents, keyword){	
			var viewTable = "";
			$.each(contents,function(i,n){
				var pattern = new RegExp(keyword, 'gi');
				if(pattern.test(n)){
					viewTable +=n+",";
				}
			});
			if(viewTable==""||viewTable.length==0){
				$("#table_tbody").html("");
				return;
			}
			createTable(viewTable.substring(0,viewTable.length-1).split(","));
		}
		function createTable(el){
				var html = "";
				$.each(el,function(i,n){
					if(i%4==0){
						html+="<tr>";
					}
					html += "<td><input type='radio' name='fruit' value ='"+n+"' />"+n+"</td>";
					if(i%4==3){
						html+="</tr>";
					}
				})
				if(el.length%3!=0){
					html+="</tr>";
				}
				$("#table_tbody").html(html);
		}
			
		var tableStr =  $("#tableStr").val().split(",");
		var tableGrid = $("#tableGrid").val()
		$("#search").click(function(){
			var keyword = $("#searchBox").val();
			if(keyword==""){
				$("#table_tbody").html(tableGrid);
				return;
			}
			doFilter(tableStr, keyword);
		});
		
		$(".tabletd").click(function(){
			$(".autocut").prop("checked",false);
			$(this).find(".autocut").prop("checked",true);
		})
		
})	
	</script>
</head>

<body>
	<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
        <div class="sl_mod modal-content" style="border:none">
        
            
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">请选择表</h4>
            </div>
          
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;">
                <input type="hidden" id="tableStr"  value="<s:property value='#tableStr' />" />
				<input type="hidden" id="tableGrid" value="<s:property value='#tables' />" />
				<input type="hidden" id="type" value="<s:property value='#type'/>"/>
				
			<div class="form-horizontal sl_all_form">
            <div class="input-group col-md-6 col-md-offset-3 col-xs-10 col-xs-offset-1">
				<div class="input-group">
					<input type="text" id="searchBox" class="form-control" placeholder="请输入关键词">
					<span class="input-group-btn">
						
						<button type="button" id="search" class="btn btn-primary">
						<span class="glyphicon glyphicon-search"> </span>
							查询
						</button>
					</span>
				</div>
				
				
			</div>
          </div>
         <div class="clearfix"><br></div>
					
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            	
				<tbody id="table_tbody">
				 	<%=request.getAttribute("tables") %>
				 </tbody>
   		  </table>	
            </div>
            </div>
           <div class="row">
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
            </div>
        	
        </div>
</body>
</html>
