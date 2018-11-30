<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
KindEditor.ready(function(K) {
			editPlugin(K);
		});
$(function(){
	editPlugin(KindEditor);
	var id = $("#form1 input[name='notice.guid']").val();//判断增加还是修改
	if(id==""){
		$("#action").click(function(){
			addEntity();
		});
	}else{
		$("#action").click(function(){
			updateEntity();
		});
		initRadio("notice.top","${notice.top}");
	}
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}

	$("#form1").attr("action","<%=request.getContextPath()%>/message/notice_save.html");
	$("#form1").attr("style","display:none;");
	$("#hiddenDiv").removeAttr("style");
	$("#form1").submit();
}

function updateEntity(){//修改
	if(!validate()){
		return false;
	}
	$("#form1").attr("action","<%=request.getContextPath()%>/message/notice_update.html");
	$("#form1").attr("style","display:none;");
	$("#hiddenDiv").removeAttr("style");
	$("#form1").submit();
}

function validate(){
	var v = $("#form1 input[name='notice.title']");
	if($.trim(v.val()).length==0){
		//showDownError(v,"所属部门不能为空");
		alert("标题不能为空");
		return false;
	}
	if(!testFileNames()){
		return false;
	}
	return true;
}

function addFileInput(obj)
{
	var text = "<br /><input type=\"file\" class=\"text_nor\" style=\"width: 400px;\" name=\"notice.files\" onchange=\"testFileName(this)\" />";
	$(obj).before(text);
}

function removeFile(guId)
{
	var text = "<input type=\"hidden\" name=\"notice.removeAttachementIds\" value=\"" + guId +"\">";
	$("#form1").append(text);
	$('#'+guId).remove();
}

function downloadFile(guId)
{
	var url = "<%=request.getContextPath() %>/file/attachement_download.html?guId=" + guId;
	window.open( url, "400", "300", true);
}

function testFileName(obj){	
	var val = obj.value;
	if(val == null || val == "" ){
		return;
	}
	var lastWords = val.substring(val.lastIndexOf("."));
	if(lastWords == null || lastWords == ""){
		alert("上传文件格式不正确");
	}
	if(lastWords == ".exe" || lastWords == ".dll"){
		alert("不可以上传.exe和.dll文件");
	}
}

function testFileNames(){
	var files = document.getElementsByName("notice.files");
	var res = true;
	for(var i = 0; i < files.length; i++){
		var file = files[i];
		if(file.value != null && file.value != ""){
			var lastWords = file.value.substring(file.value.lastIndexOf("."));
			if(lastWords == null || lastWords == ""){
				alert("上传文件格式不正确");
				res = false;
				break;
			}
			if(lastWords == ".exe" || lastWords == ".dll"){
				alert("不可以上传.exe和.dll文件");
				res = false;
				break;
			}
		}
	}
	return res;
}

function editPlugin(K){
			var editor1 = K.create('textarea[name="notice.content"]', {
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
	<div id="hiddenDiv" class="formlist" style="display:none;" >
		<td><p class="loading">正在上传中，请稍候...</p></td>
	</div>
	
	<form id="form1" name="form1" method="post" enctype="multipart/form-data" target="frame">
		<div id="testID">    
		  <div class="tab">
			<table align="center" class="formlist">
				<thead>
					<tr>
						<th colspan="4">
							<span id="title">最新通知<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tbody>
				   <tr>
						<th width="30%"><span class="red">*</span>标题</th>
						<td width="70%">
							<input type="text" name="notice.title" class="text_nor" style="width:280px" value="${notice.title }" maxlength="32"/>
						</td>
					</tr>
					<tr>
						<th><span class="red"></span>置顶</th>
						<td>
							<input type="radio" name="notice.top" value="0" checked/>否
							<input type="radio" name="notice.top" value="1"/>是
						</td>
					</tr>
					<tr>
						<th>附件</th>
						<td colspan="3">
							<c:forEach items="${notice.attachements }" var="attachMent">
								<p id="${attachMent.guId }">
									<a style="color: blue;" href="javascript:downloadFile('${attachMent.guId }');" >${attachMent.name }<span class="close-icon"></span></a>
									<a style="color: green;" href="javascript:removeFile('${attachMent.guId }');">删除</a>
								</p>
							</c:forEach>
							<input type="file" class="text_nor" style="width: 400px;" name="notice.files" onchange="testFileName(this)"/>
							<button class="btn_common" onclick="addFileInput(this); return false;" title="增加一个文件">增加</button>
								说明：不能上传.exe和.dll文件,附件大小不要超过20M
						</td>
					</tr>
					<tr>
						<th><span class="red"></span>备注</th>
						<td>
							<input type="text" name="notice.remark" class="text_nor" style="width:280px" value="${notice.remark }" maxlength="32"/>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<textarea name="notice.content" rows="3" style="width:100%;font-size:12px">${notice.content }</textarea>
							<input type="hidden" name="notice.guid" value="${notice.guid }"/>
							<input type="hidden" name="notice.status" value="${notice.status }"/>
						</td>
					</tr>
				</tbody>
		    <tfoot>
		      <tr>
		        <td colspan="4">
		            <div class="bz">"<span class="red">*</span>"为必填项</div>
		          	<div class="btn">
		            	<button type="button" id="action" name="action" >保 存</button>
		            	<button type="button" name="cancel" onclick='divClose();'>取 消</button>
		          	</div>
		        </td>
		      </tr>
			</tfoot>
			</table>
			</div>
		</div>
	</form>
</body>
</html>
