<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	var id = $("#form1 input[name='guid']").val();//判断增加还是修改操作
	if(id==""){
		$("#action").click(function(){
			addEntity();
			});
		$("select[name='source'] option").filter(":eq(0)").remove();//新增时屏蔽数据中心来源
	}else{
		$("input[name='guid']").attr("readonly","readonly").removeClass().addClass("text_nobor");
		$("input[name='guid']").focus(function(){
			$(this).blur();
			});
		$("select[name='type']").attr("disabled","disabled");
		$("select[name='source']").attr("disabled","disabled");
		$("#action").click(function(){
			updateEntity();
			});
	}
	initRadio("includeParentNode","${model.includeParentNode}");
	initSelect("type","${model.type}");
	initSelect("source","${model.source}");
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('<%=request.getContextPath()%>/code/codeCatalog_insert.html',$("#form1 input,#form1 select").serialize(),function(data){
		var callback = function(){
			window.location.reload();
		};
		processDataCall(data, callback);
	},"json");
}
function updateEntity(){//修改
	var disabled = $("#form1 input,#form1 select").filter(":disabled");
	disabled.attr("disabled","");
	var param = $("#form1 input,#form1 select").serialize();
	disabled.attr("disabled","disabled");
	var name = $("input[name='name']").val();
	$.post('<%=request.getContextPath()%>/code/codeCatalog_update.html',param,function(data){
		var callback = function(){
			$(_tr).find("td[name='name']").text(name);
		};
		processDataCall(data, callback);
		},"json");
}

function validate(){
	var v = $("#form1 input[name='guid']").val();
	if(v.length==0){
		alert("代码编目不能为空");
		return false;
	}
	v = $("#form1 input[name='name']").val();
	if(v.length==0){
		alert("代码编目名称不能为空");
		return false;
	}
	return true;
}
</script>
<div id="testID">    
  <div class="tab">
		<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="4">
					<span>编目维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th width="15%"><span class="red">*</span>代码编目</th>
				<td width="35%">
					<input type="text" name="guid" class="text_nor" style="width:180px" value="${model.guid }" />
				</td>
				<th width="15%"><span class="red">*</span>代码编目名称</th>
				<td width="35%"><input type="text" name="name" class="text_nor" style="width:180px" value="${model.name }"/></td>
				
			</tr>
			<tr>
				<th><span class="red">*</span>代码类型</th>
				<td><select name="type" style="width:186px">
					<option value="0">国标</option>
					<option value="1">校标</option>
					<option value="2">系统</option>
				</select></td>
				<th><span class="red">*</span>代码来源</th>
				<td><select name="source" style="width:186px" >
					<option value="0">数据中心</option>
					<option value="1">自己新增</option>
 				</select></td>
			</tr>
			<tr>
				<th><span class="red">*</span>是否包含父节点</th>
				<td><input type="radio" name="includeParentNode" value="1" checked="checked">是</input><input type="radio" name="includeParentNode" value="0">否</input></td>
 				<th><span class="red">*</span>可选中表达式</th>
				<td><input type="text" name="chooseExpr" class="text_nor" style="width:180px" value="${model.chooseExpr }"/></td>
			</tr>
			<tr>
				<th><span class="red">*</span>分级显示间隔符</th>
				<td><input type="text" name="delimiter" class="text_nor" style="width:180px" value="${model.delimiter }"/></td>
				<th><span class="red">*</span>分级码</th>
				<td><input type="text" name="classCode" class="text_nor" style="width:180px" value="${model.classCode }"/></td>
			</tr>
			<tr>
				<th><span class="red">*</span>备注</th>
				<td><input type="text" name="remark" class="text_nor" style="width:180px" value="${model.remark }"/></td>
				<th></th>
				<td></td>
			</tr>
		</tbody>
    <tfoot>
      <tr>
        <td colspan="4">
            <div class="bz">"<span class="red">*</span>"为必填项</div>
          <div class="btn">
            <button id="action" name="action" >保 存</button>
            <button name="cancel" onclick='divClose();'>取 消</button>
          </div></td>
      </tr>
		</tfoot>
	</table>
  </div>
</div>
