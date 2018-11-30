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
		
		$(function(){
			$('a[class="btn_ccg"]').click(function(){
				save();								
			});
			//恢复默认
			$('a[class="btn_fh"]').click(function(){
				setdef();								
			});
			//重置
			$('a[class="btn_sx"]').click(function(){
                reset();                             
            });
			
			$('#action').click(function(){
				save();
			});
			$("#configset").click(function(){
                showWindow("服务配置","<%=request.getContextPath()%>/sms/smsTemplate_config.html", 600,380);
            });
			setNumber();
			window.setInterval("setNumber()", 700);
			$("select[name='model.sendType']").val('${model.sendType}');
			
		});
		function setdef(){
			$("#profession").val($("#defaultCont").val());
		}
		function reset(){
            $("#profession").val($("#content").val());
        }
		function save(){

			var content = $('#profession').val();
			if(content.length == 0){
				alert('发送内容不能为空！');
				return;
			}

			var id = $('input[name="id"]').val();

			$.post("<%=request.getContextPath()%>/sms/smsTemplate_save.html",$("#smsTemplate input,#smsTemplate select,#smsTemplate textarea").serialize(),function(data){
				if(data.success){
					changeTemp();
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						divClose();
					});
				}
			},"json");
		}
		
		function changeTemp(){
			$("#search").attr("action","<%=request.getContextPath()%>/sms/smsTemplate_page.html");
            $("#search").attr("method","post");
            $("#search").submit();
            e.preventDefault();//阻止默认的按钮事件，防止多次请求
        }

        function setNumber(){
            var str = $("#profession").val();
            var realLength = 0, len = str.length, charCode = -1;
            for (var i = 0; i < len; i++) {
                charCode = str.charCodeAt(i);
                if (charCode >= 0 && charCode <= 128) realLength += 1;
                else realLength += 2;
            }
            $("#num").html(realLength);
        }
		
		</script>
	</head>
	<body>
		<div class="toolbox">
		    <!-- 按钮 -->
		    <div class="buttonbox">
		        <ul>
		            <li>
                        <a onclick="return false;" id="configset" class="btn_ck" href="#">
                                        服务配置
                        </a>
                    </li>
		            <li>
		                <a onclick="return false;" class="btn_ccg" href="#">
		                  保 存
		                </a>
		            </li>
		            <li>
		                <a onclick="return false;" class="btn_sx" href="#">
		                 重置
		                </a>
		            </li>
		      <li>
                        <a onclick="return false;" class="btn_fh" href="#">
                                       恢复默认模板
                        </a>
                    </li>
		        </ul>
			</div>	
		</div>
	    <div class="tab" id="smsTemplate">
	    	<input type="hidden" name="id" value="${model.id }">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="formlist">
			    <thead>
			    	<tr>
			        	<th colspan="4"><span>短信模板维护<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
			        </tr>
			    </thead>
			    <tfoot>
			      <tr>
			        <td colspan="4">
			        	<div class="bz">当前内容长度为<span class="red" id="num"></span>字符</div>
			        	<div class="btn" style="">
				            <button id="action" name="action" >保 存</button>
				            <button name="cancel" onclick='reset();'> 重 置</button>
				            <button name="cancel" onclick='setdef();'>  恢复默认</button>
				        </div>
			        </td>
			      </tr>
			    </tfoot>
			    <tbody>
			      <tr>
			      	<th width="15%"><span class="red">*</span>发送类型</th>
			        <td colspan="3">
				        <form method="post" id="search">
				        	<select style="width:134px" name="model.sendType" onchange="changeTemp();">
			                  <c:forEach items="${tyleList }" var="st">
				                <option value="${st.key }">${st.text }</option>
			                  </c:forEach>
			                </select>
			            </form>
			        </td>
			      </tr>
			      <tr>
			        <th width="15%"><span class="red">*</span>发送内容</th>
			        <td colspan="3">
			        	<textarea id ="profession" style="width: 90%; height: 100px;" name="model.content">${model.content }</textarea>
			        	<textarea id ="defaultCont" style="display: none;">${model.defaultCont }</textarea>
			        	<textarea id ="content" style="display: none;">${model.content }</textarea>
			        </td>
			      </tr>
			    </tbody>
		    </table>
	    </div>
	</body>
</html>
