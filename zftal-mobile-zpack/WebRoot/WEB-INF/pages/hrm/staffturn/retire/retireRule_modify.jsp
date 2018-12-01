<%@page import="com.zfsoft.hrm.config.IConstants"%>
<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<script type="text/javascript">
$(function(){
	$("#modifyBtn").click(function(){
		modifyEntity();
		return false;
	});
});
function modifyEntity(){
	if(!validateRule()){
		return;
	}
	var inputs= $("#form2 input,#form2 select").serialize();
	$.post('<%=request.getContextPath()%>/retire/retireScan_modify.html',inputs,function(data){
        var callback = function(){
            refreshList();
        };
        if(data.success){
            processDataCall(data, callback);
        }else{
            showWarning(data.text);
        }
	},"json");
}

function validateRule(){
	if("${model.cond_col}" != "" && $("#form2 input[name='model.cond_val']").val()==""){
		alert("请选择规则值！");
		return false;
	}
	
	if($("#form2 input[name='model.sex']").val()==""){
		alert("性别不能为空！");
		return false;
	}
	var age=$("#form2 input[name='model.retire_age']").val();
	if(age==""){
		alert("退休年龄不能为空！");
		return false;
	}else if(!validateAge(age)){
		return false;
	}
	var seq=$("#form2 input[name='model.seq']").val();
	if(seq==""){
		alert("权重不能为空！");
		return false;
	}else{
		var regexp=/^[1-9][0-9]*$/;
		if(!regexp.test(seq)){
			alert("权重请输入正整数！");
			return false;
		}
		if(parseInt(seq)>999999){
			alert("权重不能大于999999");
			return false;
		}
	}
	return true;
}

function validateAge(age){
	var regexp=/^[1-9][0-9]*$/;
	if(!regexp.test(age)){
		alert("退休年龄请输入整数！");
		return false;
	}
	if(parseInt(age)>100){
		alert("退休年龄不能大于100！");
		return false;
	}
	return true;
}
// 20140423 add start
/**
 * 选择人员（多选）
 * @param type 人员类型
 * <pre>
 * 示例：
 * &lt;input type="text" onclick="selectPersonMultiple('teacher')" />
 * </pre>
 * @return 人员信息数组，array[ 值, 姓名 ]，如：['001;002;003','张三、李四、王五']
 */
function spMultiple(type) {
    var dept = "";
    var identity = document.getElementById("model.identity").value;
    var duty_level = document.getElementById("model.duty_level").value;
    
    // 工人
    if (identity == '<%= IConstants.WORKER %>') {
      dept = '<%= IConstants.SEND_LEVER_3%>';
    // 干部
    } else if (identity == '<%= IConstants.CADRE%>') {
        // 副科以上
        if (dutyLevel(duty_level)) {
          dept = '<%= IConstants.SEND_LEVER_1%>';
        } else {
          dept = '<%= IConstants.SEND_LEVER_2%>';
        }
    } else {
      dept = "";
    }
    return showTopWin("<%=request.getContextPath()%>/retire/retireSp_multiple.html?type=" + type + "&dept=" + dept, 840, 520, null );
}

/**
 * 选择人员（多选）
 * @param type 人员类型
 * <pre>
 * 示例：&lt;code:selectPerson name="model.staffid" id="staffid" />
 * </pre>
 */
function spMultipleX( obj, type ,callBack) {
    
    var result = spMultiple( type );
    if(!result){
        result=window.returnValue;
    }
    if( result == null ) return;
    if(obj!=null){
        $(obj).prev().val( result[0] );
        $(obj).val( result[1] );
    }
    if(callBack!=null){
        callBack(result[0]);
    }
}

function dutyLevel (val) {
  var duty = '<%= IConstants.DUTY_LEVER%>';
  var durys = duty.split(",");
  
  for(var i = 0; i < durys.length; i++) {
    if (val == durys[i]) {
      return true;
    }
  }
  return false;
}
// 20140423 add end
</script>
<div id="testID">    
  <div class="tab">
		<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="4">
					<span>修改退休规则<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form2">
			<input type="hidden" name="model.id" value="${model.id }"/>
			<tr>
				<th>规则名称</th>
				<td>
					${empty model.cond_col ? "默认规则" : model.cond_col_name}
				</td>
			</tr>
			<tr>
				<th>规则值</th>
				<td>
					<c:if test="${!empty model.cond_col }">
						<ct:codePicker name="model.cond_val" code="${model.cond_val }" catalog="${model.cond_col_code }" width="300" />
					</c:if>
				</td>
			</tr>
			<tr>
				<th><span class="red">*</span>性别</th>
				<td>
					<ct:codePicker name="model.sex" code="${model.sex }" catalog="<%=ICodeConstants.SEX %>" width="80" />
				</td>
			</tr>
			<tr>
				<th><span class="red">*</span>退休年龄</th>
				<td>
					<input type="text" name="model.retire_age" style="width:80px;" value="${model.retire_age }"/>
				</td>
			</tr>
			<!-- 20140423 add start -->
            <tr>
                <th>人员身份</th>
                <td>
                    <ct:codePicker name="model.identity" code="${model.identity}" catalog="<%=ICodeConstants.DM_GB_RYSFDMB %>" width="80" />
                </td>
            </tr>
            <tr>
                <th>行政职务级别</th>
                <td>
                    <ct:codePicker name="model.duty_level" code="${model.duty_level}" catalog="<%=ICodeConstants.ADMIN_DUTY_LEVEL %>" width="200" />
                </td>
            </tr>
            <tr>
                <th>通知处理人</th>
                <td>
                    <input type="hidden" id="receiver" name="model.receiver" value="${model.receiver}" />
                    <input type="text" id="receiver_info" class="text_nor text_sel" readonly="readonly" style="width: 200px;" onclick="spMultipleX(this, '<%=IConstants.INFO_CATALOG_TYPE_DEFAULT %>')" value="${model.receiverNm}" />
                </td>
            </tr>
            <!-- 20140423 add end -->
			<tr>
				<th><span class="red">*</span>权重</th>
				<td>
					<input type="text" name="model.seq" style="width:80px;" value="${model.seq }"/>&nbsp;<span class="red">说明：</span>数字大的先执行
				</td>
			</tr>
		</tbody>
	    <tfoot>
			<tr>
			  <td colspan="4">
			      <div class="bz">"<span class="red">*</span>"为必填项</div>
			    <div class="btn">
			      <button id="modifyBtn">确 定</button>
			    </div>
			  </td>
			</tr>
		</tfoot>
	</table>
  </div>
</div>
