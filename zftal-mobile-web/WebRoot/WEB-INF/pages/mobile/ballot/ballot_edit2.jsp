<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/laydate/laydate.js"></script>
	<style>
		.ui-autocomplete{
			z-index:12001;
			width: 500px
		}
		<c:if test="${view=='view'}">
		#beanview td{
		   width:240px;
		}
		body{margin:0 0 0 0;}
		</c:if>
		
		.file {
		    position: relative;
		    display: inline-block;
		    background: #D0EEFF;
		    border: 1px solid #99D3F5;
		    border-radius: 4px;
		    padding: 4px 12px;
		    overflow: hidden;
		    color: #1E88C7;
		    text-decoration: none;
		    text-indent: 0;
		    line-height: 20px;
		}
		.file input {
		    position: absolute;
		    font-size: 100px;
		    right: 0;
		    top: 0;
		    opacity: 0;
		}
		.file:hover {
		    background: #AADFFD;
		    border-color: #78C3F3;
		    color: #004974;
		    text-decoration: none;
		}
		
	</style>
	<script type="text/javascript">
		$(function(){
			
			//执行一个laydate实例
       	laydate.render({
         	elem: '#startTime', //指定元素
         	type: 'datetime',
         	format: 'yyyy-MM-dd HH:mm:ss'
       	});
       	
       	laydate.render({
         	elem: '#endTime', //指定元素
         	type: 'datetime',
         	format: 'yyyy-MM-dd HH:mm:ss'
       	});
			
			$("#cancel").click(function(){
				modalClose();
				return false;
			});
			
			$(".close").click(function(){
                modalClose();
                return false;
            });
		
			var showType = $('#sshowType').val();
			if (showType == 'TELETEXT_SHOW') {
				$('#listtype_tr').css('display','');
			} else {
				$('#listtype_tr').css('display','none');
			}
			
			$('#sshowType').bind('change',function(){
				var type = $(this).val();
				if (type == 'TELETEXT_SHOW') {
				$('#listtype_tr').css('display','');
			} else {
				$('#listtype_tr').css('display','none');
			}
			});
		});		
		
		function add(){
			//initErrorAlert();
				var name=$("#form_edit").find("input[name='ballot.name']").val();
				var ballotNum=$("#form_edit").find("input[name='ballot.ballotNum']").val();
				if(name==''){
					errorAlert("投票的标题为必填项");
					return false;
				}
				
				if(ballotNum==''){
					//showWarning("类别编码为必填项");
					errorAlert("每人可投票数为必填项");
					return false;
				}
				var reg = /^[0-9]+$/ ;
       		   	if(!reg.test(ballotNum)){
	       		   	//alert("类别编码必须只包含字母或数字！");
	       		   	errorAlert("每人可投票数必须是正整数！");
	       		   	return false; 
       		   	}
				
				var formData = new FormData();
				formData.append("name",$("#form_edit").find("input[name='ballot.name']").val());
				/*formData.append("ballot.startTime",$("#form_edit").find("input[name='ballot.startTime']").val());
				formData.append("ballot.endTime",$("#form_edit").find("input[name='ballot.endTime']").val());
				formData.append("ballot.ruleType",$("#form_edit").find("input[name='ballot.ruleType']").val());
				formData.append("ballot.ballotNum",$("#form_edit").find("input[name='ballot.ballotNum']").val());*/
				formData.append("baseImg",$("#baseImg")[0].files[0]);
				console.log(formData.get('baseImg'));
				$.ajax({
					type:"post",
					url:"<%=request.getContextPath()%>/htballot/htballot_addBallot.html",
					data:formData,
					processData: false,
    				contentType: false,
					success:function(data){
						
						if(data.success){
						    modalClose();
						    callback();
						}else{
							errorAlert(data.text);
						}
					}
				});
			};
		
		
		var callback = function(){
			top.xg_rightFrame.$('#form_list').submit();
		};
		
	</script>
</head>

<body>

    <form id="form_edit">
        <!--<input type="hidden" name="op" value="${op }"/>-->
        <input type="hidden" name="ballot.id" value="${ballot.id}"/>
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">文艺投票信息维护</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
                
                <tbody>
                    <tr>
                        <th width="30%">
                            <span class="red">*</span>投票的标题
                        </th>
                        <td><input id="name" type="text" name="ballot.name" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                        	class="form-control" value="${ballot.name }" /></td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>开始时间
                        </th>
                        <td><!--<input id="startTime" type="text" name="ballot.startTime" maxlength="10" placeholder="如2018-01-01 12：00：00"  
                        	class="form-control" value="${ballot.startTime}" />-->
                        	<input type="text" id="startTime" name="ballot.startTime" class="form-control" value="${ballot.startTime}"   placeholder="请选择日期" readonly="readonly"/>  
                        </td>
                        	
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>结束时间
                        </th>
                        <td>
                        	<input type="text" id="endTime" name="ballot.endTime" class="form-control" value="${ballot.endTime}"   placeholder="请选择日期" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>投票规则
                        </th>
                        <td>
                       		<select id="ruleType" name="ballot.ruleType">
                       			<option value="1">每天可投票</option>
                       			<option value="2">每人固定票数</option>>
                       		</select>
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>每人可投票数
                        </th>
                        <td>
                        <input id="ballotNum" type="text" name="ballot.ballotNum" maxlength="10" placeholder="请输入数字"  
                        	class="form-control" value="${ballot.ballotNum}" /></td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red"></span>投票介绍
                        </th>
                        <td>
                        	<textarea name="ballot.content" maxlength="250" placeholder="不要超过250个字符哦(^_^)" 
                        		class="form-control" rows="3" cols="10">${ballot.content}</textarea>
                        </td>
                    </tr>
                    
                    <tr style="max-height: 300px;">
                    <th>
                      	<span class="red"></span>缩略图片<br />
                    </th>
                   <!-- <td >
							<div  style="height:300px; " id="myselfimage">
								${imageHtml }
							</div>
							
                    </td>-->
                    
                    <td >
                    <input type="hidden" name="imageName" value="${imageName }"/>
						<div  style="height:300px; " id="myselfimage">
                     	 	${imageHtml }
                      	</div>
                    </td>
                    
             </tr>
                    
                </tbody>
            </table>
            </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn" onclick="add()">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>
</body>
</html>
