<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
	
	<script type="text/javascript">
	$(function(){
		$("#action").click(function(){
			saveRequest();
		});
	});
	
	function saveRequest(){
		if(!check()){
			return false;
		}
		var param = $("#form1").serialize();
		var zydm = $("input[name='model.specialty']").val();
		$.post(_path+"/expertvote/expertgroup_random.html?zydm="+zydm,param,function(data){
			var callback = function(){
				refreshList();
			};
			processDataCall(data,callback);
		},"json");
	}
	
	function check(){
		var v = $("#count").val();
		var p = new RegExp("^([1-9][0-9]*|0)$");
		var res = p.test(v);
		if(!res){
			alert("抽取条数请输入非负整数！");
			$("#count").focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<form id="form1" name="form1" method="post">
	<input type="hidden" name="groupMember.zjz_id" value="${zid }"/>
		<div id="testID">    
		  <div class="tab">
			<table align="center" class="formlist">
				<thead>
					<tr>
						<th colspan="2">
							<span id="title">随机抽取条件<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th width="25%"><span class="red">*</span>抽取范围</th>
						<td>
							<input type="radio" name="zy" value="single" checked/>同类型抽取
							<input type="radio" name="zy" value="all"/>全部
						</td>
					</tr>
					<tr>
						<th width="25%"><span class="red">*</span>抽取条数</th>
						<td id="zydm">
							<input type="text" id="count" name="count" value="${count}"/>
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