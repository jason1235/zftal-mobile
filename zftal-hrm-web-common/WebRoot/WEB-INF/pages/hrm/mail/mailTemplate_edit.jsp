<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="<%=request.getContextPath() %>/kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" type="text/javascript" src="<%=request.getContextPath() %>/kindeditor/kindeditor.js"></script>
		<script charset="utf-8" type="text/javascript" src="<%=request.getContextPath() %>/kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" type="text/javascript" src="<%=request.getContextPath() %>/kindeditor/plugins/code/prettify.js"></script>
	    <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath() %>/kindeditor/kindeditor.js"></script>
		<script type="text/javascript">
		KindEditor.ready(function(K) {
			editPlugin(K);
		});
		
		$(function(){
			$('a[class="btn_ccg"]').click(function(){
				save();								
			});
			
			$('a[class="btn_fh"]').click(function(){
				back();								
			});
			
			$('#action').click(function(){
				save();
			});
			
			editPlugin(KindEditor);
			
			initSelect("taskId","${model.taskId}");		
		});
		
		function save(){
			var taskId = $('select[name="taskId"]').val();
			if(taskId.length == 0){
				alert('模板名称不能为空！');
				return;
			}
			
			var content = $('textarea[name="content"]').val();
			if(content.length == 0){
				alert('模板制作不能为空！');
				return;
			}

			var id = $('input[name="id"]').val();
			var urlStr = "<%=request.getContextPath()%>/mail/mailTemplate_modify.html";
			if(id.length == 0){
				urlStr = "<%=request.getContextPath()%>/mail/mailTemplate_save.html";
			}
			
			$.post(urlStr,$("#mailTemplate input,#mailTemplate select,#mailTemplate textarea").serialize(),function(data){
				if(data.success){
					window.location.href = "<%=request.getContextPath()%>/mail/mailTemplate_list.html";
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						divClose();
					});
				}
			},"json");
		}
		
		function back(){
			window.location.href = "<%=request.getContextPath()%>/mail/mailTemplate_list.html";
		}
		
		function setTaskName(selectNode){
			var value = selectNode.options[selectNode.selectedIndex].text;
			$("input[name='taskName']").val(value);
		}
		
		function editPlugin(K){
			var editor1 = K.create('textarea[name="content"]', {
					themeType: 'simple',
					resizeType: 1,
					cssPath :  _path+'/kindeditor/plugins/code/prettify.css',
					uploadJson : _path+'/kindeditor/uploadJson.html',
					fileManagerJson : _path+'/kindeditor/fileManagerJson.html',
					allowFileManager : true,
					afterCreate : function() {
						var self = this;
						K.ctrl(document, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
						K.ctrl(self.edit.doc, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
					},
					afterBlur: function(){this.sync();}
					});
					prettyPrint();
		}	
		</script>
	</head>
	<body>
		<div class="toolbox">
		    <!-- 按钮 -->
		    <div class="buttonbox">
		        <ul>
		            <li>
		                <a onclick="return false;" class="btn_ccg" href="#">
		                  保 存
		                </a>
		            </li>
		            <li>
		                <a onclick="return false;" class="btn_fh" href="#">
		                   返 回
		                </a>
		            </li>
		        </ul>
			</div>	
		</div>
	    <div class="tab" id="mailTemplate">
	    	<input type="hidden" name="id" value="${model.id }">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="formlist">
			    <thead>
			    	<tr>
			        	<th colspan="4"><span>邮件模板维护<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
			        </tr>
			    </thead>
			    <tfoot>
			      <tr>
			        <td colspan="4">
			        	<div class="bz">"<span class="red">*</span>"为必填项</div>
			        	<div class="btn" style="display: none;">
				            <button id="action" name="action" >保 存</button>
				            <button name="cancel" onclick='back();'>取 消</button>
				        </div>
			        </td>
			      </tr>
			    </tfoot>
			    <tbody>
			      <tr>
			      	<th width="15%"><span class="red">*</span>模板名称</th>
			        <td colspan="3">
			        	<select style="width:134px" name="taskId" onchange="setTaskName(this);">
							<option value="">全部</option>
		                  <c:forEach items="${taskList }" var="st">
			                <option value="${st.key }">${st.text }</option>
		                  </c:forEach>
		                </select>
				        <input type="hidden" style="width:130px" class="text_nor" name="taskName" value="${model.taskName }"/>
			        </td>
			      </tr>
			      <tr>
			        <th width="15%"><span class="red">*</span>模板制作</th>
			        <td colspan="3">
			        	<textarea id ="profession" style="width: 100%; height: 600px;" name="content">${model.content }</textarea>
			        </td>
			      </tr>
			    </tbody>
		    </table>
	    </div>
	</body>
</html>
