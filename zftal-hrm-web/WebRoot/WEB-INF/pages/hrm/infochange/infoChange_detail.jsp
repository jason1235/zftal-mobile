<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/bill/billview.js"></script>
    <script type="text/javascript">
    $(function(){
		$("#back").click(function(){//功能条增加按钮
			$.post(_path+"/infochange/infochange_check.html?query.classId=${query.classId}","query.id=${infoChange.id}",function(data){
    			if(data.success){
    				if(data.modified){
    					location.href = _path+"/normal/staffInfo_list.html?classId=${query.classId}";
    				}else{
    					$.post(_path+"/infochange/infochange_cancel.html?query.classId=${query.classId}","infoChange.id=${infoChange.id}",function(data1){
    		    			if(data1.success){
    		    				location.href = _path+"/normal/staffInfo_list.html?classId=${query.classId}";
    		    			}else{
    		    				alert(data1.message);
    		    			}
    		    		},"json");
    				}
    				
    			}else{
    				alert(data.message);
    			}
    		},"json");
			return false;
		});
		$("#btn_up").click(function(){
			var saves=$("#content").find(".btn_xxxx_bc");
			if(saves.length>0){
				showWarning("请先保存");
				return false;
			}
			showConfirm("确定要提交此次修改吗？");
			
			$("#why_cancel").click(function(){
				divClose();
			});
		
			$("#why_sure").click(function(){
				var id = $("input[name='infoChange.id']").val();
				$.post(_path+'/infochange/infochange_commit.html?query.classId=${query.classId}','infoChange.id='+id,function(data){
					var callback = function(){
						location.href = _path+"/infochange/infochange_page.html?query.classId=${query.classId}&query.status=WAIT_AUDITING";
					};
					processDataCall(data,callback);
				},"json");
			});
		});
			
		load($("#content"),'${billConfigId }',
    			'${infoChange.billInstanceId }','${privilegeExpression }',true);
		
	});
    
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_up" class="btn_up">提 交</a>
						</li>
					</ul>
					<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<div class="formbox">
	<input type="hidden" name="infoChange.id" value="${infoChange.id }"/>
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>信息修改<font color="#0457A7" style="font-weight:normal;" id="tip"></font></span>
    </h3>
	<div id="content" style="padding-top:20px;">
	</div>
  </body>
</html>
