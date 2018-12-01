<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>  
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js"></script>
		<link rel="stylesheet" href="<%=systemPath %>/kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="<%=systemPath %>/kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" type="text/javascript" src="<%=systemPath %>/kindeditor/kindeditor.js"></script>
		<script charset="utf-8" type="text/javascript" src="<%=systemPath %>/kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" type="text/javascript" src="<%=systemPath %>/kindeditor/plugins/code/prettify.js"></script>
	    <script charset="utf-8" type="text/javascript" src="<%=systemPath %>/kindeditor/kindeditor.js"></script>
		<script language="javascript">
			KindEditor.ready(function(K) {
				var editor1 = K.create('textarea[name="fbnr"]', {
					cssPath : '/kindeditor/plugins/code/prettify.css',
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
					}
				});
				prettyPrint();
			});
			function xgxw() { 
				if(KE.count('fbnr')==0){
					alert('请填写您要发布的新闻内容！');
					return false;
				}
				newForm.submit();
			}
		</script>
	</head>
	<body>

		<s:form name="newForm" method="post" action="/xtgl/xwgl_xgBcXw.html" theme="simple">

			<div class="tab">
				<table width="100%" border="0" class="formlist">
					<thead>
						<tr>
							<th colspan="4">
								<span>修改新闻</span>
							</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4">
								<div class="btn">
									<s:hidden id="xwbh" name="xwbh"></s:hidden>
									<button name="保存" onclick="xgxw();return false;">
										保存
									</button>
									<button name="返回" onclick="window.location.href='xwgl_cxXw.html';return false;">
										返回
									</button>
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>

						<tr>
							<th>
								新闻标题
							</th>
							<td>
								<s:hidden id="xwbt" name="xwbt" />
								<s:label name="xwbt"></s:label>
							</td>
						</tr>
						<tr>
							<th>
								发布时间
							</th>
							<td>
								<s:hidden id="fbsj" name="fbsj" />
								${fbsj }
							</td>
						</tr>
						<tr>
							<th width="16%">
								发布对象
							</th>
							<td width="84%">
								<s:radio name="fbdx" list="fbdxList"  listKey="key"
									listValue="value" >
								</s:radio>
								
							</td>
						</tr>
						<tr>
							<th width="16%">
								是否发布
							</th>
							<td width="84%">
								<s:radio name="sffb" list="sfList" listKey="key"
									listValue="value">
								</s:radio>
							</td>
						</tr>
						<tr>
							<th width="16%">
								是否置顶
							</th>
							<td width="84%">
								<s:radio name="sfzd" list="sfList" listKey="key"
									listValue="value">
								</s:radio>
							</td>
						</tr>
						<tr>
							<th>
								<font color="red">*</font>新闻内容
							</th>
							<td>
   								<textarea name="fbnr" cols="100" rows="8" style="width:650px;height:200px;visibility:hidden;">${fbnr}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</s:form>
		<s:if test="message != null && message !=''">
			<script defer="defer">
				alert('${message}','',{'clkFun':function(){
					document.location.href=_path+'/xtgl/xwgl_cxXw.html';
				}});
			</script>
		</s:if>
	</body>
</html>
