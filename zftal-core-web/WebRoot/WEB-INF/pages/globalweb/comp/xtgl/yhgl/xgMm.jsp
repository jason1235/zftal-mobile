<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>密码修改</title>
		
		<%@ include file="/commons/hrm/head_v5.ini" %>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript"
            src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
    <script type="text/javascript"
        src="<%=systemPath %>/js/globalweb/password.js"></script>
        <style type="text/css">
        .is0{margin-top:3px;}
        .is0>span{float:left; font-size:12px;}
        .is0 .strengthResult_pic{float:left; border:1px solid #ddd; margin-top:4px;}
        .is0 .strengthResult_pic span{background:#ddd; margin:1px 2px; float:left; width:50px; height:5px;}
        .is10 .strengthResult_pic .strengthResult_pic1{background:#71c624;}
        .is50 .strengthResult_pic .strengthResult_pic1,.is50 .strengthResult_pic .strengthResult_pic2{background:#71c624;}
        .is80 .strengthResult_pic span{background:#71c624;}
        .is0 .strengthResult_tip{float:left; font-size:12px;}
        .is0 .strengthResult_tip span{display:none;}
        .is10 .strengthResult_tip .strengthResult_1{display:block;}
        .is50 .strengthResult_tip .strengthResult_2{display:block;}
        .is80 .strengthResult_tip .strengthResult_3{display:block;}
   </style>
	<script type="text/javascript">		
	$(function(){
		$("#mm").passwordStrength();
        $("#mm").keyup(function(e){
        	$("#save_btn").removeAttr("disabled");
            pwStrength(this.value);
            if(e.keyCode==13){
               dl();
            }
        });
        $("#nmm").keyup(function(e){
        	$("#save_btn").removeAttr("disabled");
            pwStrength(this.value);
            if(e.keyCode==13){
               dl();
            }
        });
        $("#ymm").keyup(function(e){
        	$("#save_btn").removeAttr("disabled");
            if(e.keyCode==13){
               dl();
            }
        });
        $("#save_btn").click(function(e){
            dl();
        });
		
		
		
		$("#cancel").click(function(){
			window.parent.$('#myModal_toji').modal('hide');
		});
		
		$(".close").click(function(){
			window.parent.$('#myModal_toji').modal('hide');
		});
		
		//$("#mm").keyup(function(e){
        //    pwStrength(this.value);
        //});
	});
	
	function dl(){
		if(isEmpty('ymm')){
            $(".errors").html("<p class='sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>原密码不能为空！");
            $("#save_btn").attr("disabled","disabled");
            return false;
        }
        if(isEmpty('mm')){
            $(".errors").html("<p class='sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>新密码不能为空！");
            $("#save_btn").attr("disabled","disabled");
            return false;
        }
        if(isEmpty('nmm')){
            $(".errors").html("<p class='sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>重复新密码不能为空！");
            $("#save_btn").attr("disabled","disabled");
            return false;
        }
        var ymm = $('#ymm').val();
		var mm = $('#mm').val();
		var nmm = $('#nmm').val();
		var nmm = $('#nmm').val();
		 if(mm!=nmm){
			 $(".errors").html("<p class='sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>两次密码输入不一致！");
			 $("#save_btn").attr("disabled","disabled");
			return false;
			//showDownError(obj,'两次密码输入不一致！');
		}
		 if(ymm == mm){
			 $(".errors").html("<p class='sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>新密码不能与原密码一致！");
			 $("#save_btn").attr("disabled","disabled");
			return false;
		 }
		 if(mm.indexOf(" ") >=0){
			 $(".errors").html("<p class='sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>新密码不能包含空格！");
			 $("#save_btn").attr("disabled","disabled");
			return false;
		 }
		 if(strong){
			 var param = $("#form_edit").serialize();
	         $.post(_path+'/xtgl/yhgl_xgMm.html',param,function(data){
					if(data.success){
						window.parent.$('#myModal_toji').modal('hide');
			            return false;
					}else{
						$(".errors").html("<p class='sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>"+data.html);
						$("#save_btn").attr("disabled","disabled");
					}
				},"json");
		 }else{
			 $("#save_btn").removeAttr("disabled");
		 }
	}
	function isEmpty(dataid){
        if($('#' + dataid).val() != null && $('#' + dataid).val() != ''){
            return false;
        }else{
            return true;
        }
    }
	
	function showErrMsg(message){
        $(".errors").html("<p class='sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>" + message);
        $("#save_btn").attr("disabled","disabled");
    } 
    function hideMMshow(){
        $(".errors").html("");
        $("#save_btn").removeAttr("disabled");
    } 

		
	

		
		
	</script>
  </head>
  
  <body>
    <form action="<%=request.getContextPath()%>/xtgl/yhgl_xgMm.html" name="search" id="form_edit" method="post">
     <input type="hidden" name="doType" value="save"/>
	  <input type="hidden" id="myName" name="myName" value="${user.yhm}" />
        <div style="background: #FFFFFF">
        	<div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">密码修改</h4>
            </div>
             <div class="modal-body">
                <div>
			  <p class="sl_warning">
			  <span class="glyphicon"></span>
			  <font color="red"> 温馨提示：</font>
			 新密码长度为6~16位，至少包含数字、字母、特殊符号中的两类，密码不能包含空格，强度至少为中级，字母区分大小写。
					请妥善保管密码，如遇忘记或者丢失，请及时与系统管理员联系！
			  </p>
             </div>
             <br />
             <jsp:include page="../../../../globalweb/alert.jsp" flush="true"></jsp:include>
              <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            	<tbody>
	            	<tr>
	                        <th width="30%" >
	                            <span class="red">*</span>原密码
	                        </th>
	                         <td  >
	                                <input name="ymm" id="ymm" maxlength="16"   type="password"  class="form-control"/>
	                        </td>
		              </tr>
		              <tr>
	                        <th width="30%" >
	                            <span class="red">*</span>新密码
	                        </th>
	                         <td  >
	                           <input name="mm" id="mm" maxlength="16"   type="password"  class="form-control"   onkeyup="CreateRatePasswdReq(this);"/>
	                        </td>
	              	</tr>
	              	<tr>
	                        <th width="30%" >
	                            <span class="red">*</span>重复新密码
	                        </th>
	                         <td  >
	                           <input name="nmm" id="nmm" maxlength="16"   type="password"  class="form-control"   />
	                        </td>
	              	</tr>
	              	<tr>
	              			<th width="30%" >
	                            密码强度:<font id="passwdRating"   size="2"></font>
	                        </th>
	                        <td  >
	                           <table id="passwdBar" cellspacing="0" cellpadding="0" width="135" bgcolor="#ffffff" border="0">
											<tbody>
												<tr>
													<td id="posBar" width="0%" bgcolor="#e0e0e0" height="1" style="width: 0px; height:10px;background: rgb(255, 204, 51);"></td>
													<td id="negBar" width="100%;" bgcolor="#e0e0e0" height="1" style="width: 135px;height:10px;"></td>
												</tr>
											</tbody>
										</table>
	                        </td>
	                       
	              	</tr>
            	</tbody>
            </table>
            </div>
            
            <br/>
            <div class="errors" style="color: red">
            </div>
            <br/>
            
			</div>
           <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">修 改</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>
    </body>
</html>
