<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%@ include file="/commons/hrm/head.ini" %>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	initSelect("file.type","${file.type}");  	
	//initValue();
	
	var id = $("#form1 input[name='file.guid']").val();//判断增加还是修改
	if(id==""){
		$("#action").click(function(){
			addEntity();
		});
	}else{
		$("#action").click(function(){
			updateEntity();
		});
		initRadio("file.top","${file.top}");
	}
	
	//$("#fileType").change(function(){
	//	var type = $("#fileType option:selected").text();
	//	$("#fileName").val("");
	//	var name = $("#fileName").val();
	//	var strHtml = "("+type+")"+"+"+name;
	//	$("#fileName").val(strHtml);		
	//});
});

function initValue(){
	var id = $("#form1 input[name='file.guid']").val();//判断增加还是修改
	var type = $("#fileType option:selected").text();
	var name = $("#fileName").val();
	if(id==""){
		var strHtml = "("+type+")"+"+"+name;		
	}else{
		var strHtml = name;	
	}
	$("#fileName").val(strHtml);
}

function addEntity(){//增加
	if(!validate()){
		return false;
	}

	$("#form1").attr("action","<%=request.getContextPath()%>/message/file_save.html");
	//$("#form1").attr("style","display:none;");
	//$("#hiddenDiv").removeAttr("style");
	$("#form1").submit();
}

function updateEntity(){//修改
	if(!validate()){
		return false;
	}
	$("#form1").attr("action","<%=request.getContextPath()%>/message/file_update.html");
	//$("#form1").attr("style","display:none;");
	//$("#hiddenDiv").removeAttr("style");
	$("#form1").submit();
}

function validate(){
	var v = $("#form1 input[name='file.name']");
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
	var text = "<br /><input type=\"file\" class=\"text_nor\" style=\"width: 400px;\" name=\"file.files\" onchange=\"testFileName(this)\" />";
	$(obj).before(text);
}

function removeFile(guId)
{
	var text = "<input type=\"hidden\" name=\"file.removeAttachementIds\" value=\"" + guId +"\">";
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
	var files = document.getElementsByName("file.files");
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

function initSelect(name,value){
	$("select[name='"+name+"'] > option").each(function(){
		if($(this).val()==value){
			$(this).attr("selected","selected");
		}
	});
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
							<span id="title">文件下载<font color="#0f5dc2" style="font-weight:normal;"></font></span>
							<input type="hidden" name="file.guid" value="${file.guid }"/>
							<input type="hidden" name="file.status" value="${file.status }"/>
						</th>
					</tr>
				</thead>
				<tbody>
				   <tr>
				   		<th width="30%"><span class="red">*</span>文件类型</th>
				   		<td width="70%">
				   			<select name="file.type" id="fileType">
								<c:forEach items="${types }" var="item">
									<option value=${item.guid }>${item.description }</option>
								</c:forEach>				   				
				   			</select>
				   		</td>
				   </tr>
				   <tr>
						<th width="30%"><span class="red">*</span>标题</th>
						<td width="70%">
							<input type="text" id="fileName" name="file.name" class="text_nor" style="width:280px" value="${file.name }" maxlength="32"/>
						</td>
					</tr>
					<tr>
						<th><span class="red"></span>置顶</th>
						<td>
							<input type="radio" name="file.top" value="0" checked/>否
							<input type="radio" name="file.top" value="1"/>是
						</td>
					</tr>
					<tr>
						<th>附件</th>
						<td >
							<c:if test="${file.fileId!=null}">
								<a style="color: blue;" href="javascript:downloadFile('${file.fileId }');" >下载<span class="close-icon"></span></a>
							</c:if>
							<c:if test="${file.fileId == null}">
							<input type="file" style="width: 200px;" name="file.files" onchange="testFileName(this)"/>
								<br/><span>说明：不能上传.exe和.dll文件,附件大小不要超过20M</span>
							</c:if>
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
