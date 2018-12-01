<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
    <%@ include file="/commons/hrm/head_v5.ini" %>
        
        <script type="text/javascript">
        var modelID="${model.id}"; 
        </script>
        <script type="text/javascript">
            //为select option 增加title属性提示
            $(function(){

            	//$("#btn_zj").click(function(){
                //    var name = $("input[name='model.name']");
                //    if(name.val()==null||trim(name.val()).length==0){
                //        //alert("群组名称不能为空");
                //        $("#myAlert").find("#alertContent").html("群组名称不能为空");
                //        $("#myAlert").find("#why_sure").hide();
                //        $("#myAlert").find("#why_cancel").show();
                //        $("#myAlert").find("#alertBody").removeClass();
                //        $("#myAlert").find("#img").removeClass();
                //        $("#myAlert").find("#alertBody").addClass("row alertError");
                //        $("#myAlert").find("#img").addClass("glyphicon glyphicon-remove");
                //        $("#myAlert").modal("show");
                //        return;}
                //    $.post("<%=request.getContextPath()%>/pushMsg/group_save.html",
                //    		$("#groupModelForm").serialize(),function(data){
                //        if(data.success){
                //            location.href="<%=request.getContextPath()%>/pushMsg/group_edit.html?model.id="+data.model.id;
                //        }else{
                //        	openAlert(data.text, "e");
                //        }
                                
                //	},"json");
                //});
                
                $("#add_btn").click(function(){
                	var userId = $('#userId').val();
                	var userName = $('#userName').val();
                	var academy = $('#academy').val();
                	var classgrade = $('#classgrade').val();
                	var grade = $('#grade').val();
                	var sex = $('#sex').val();
                	var jsdm = $("#jsdm").val();
                	$.post("<%=request.getContextPath()%>/pushMsg/group_addAll.html",$("#groupModelForm").serialize(),function(data){
                        if(data.success){
                        	var callback = function(){
                        		$("#groupModelForm").submit();
                        		return false;
        					};
                        	openAlert("操作成功", "s","auto", callback);
                        }else{
                        	openAlert(data.text, "e");
                        }
                                
                	},"json");
                });
                
                $("#sc_btn").click(function(){
                	var groupId = $('input[name="model.id"]').val();
                	$.post("<%=request.getContextPath()%>/pushMsg/group_removeFp.html",
                			$("#groupModelForm").serialize(),function(data){
                        if(data.success){
                        	var callback = function(){
                        		$("#groupModelForm").submit();
                        		return false;
        					};
                        	openAlert("操作成功", "s","auto", callback);
                        }else{
                        	openAlert(data.text, "e");
                        }
                                
                	},"json");
                });
                
                $("#search_go").click(function(){
                	$("#groupModelForm").submit();
                });
                
                $('.body tr').find('input').bind('click',function(e){
                 	var status = $(this).prop('checked');
                 	if(status){
            	   		$(this).removeAttr('checked');
                 	}else{
                 		$(this).prop('checked',true);
                 	}
            	 });
            				
            	 $('.body tr').bind('click',function(e){
            	 var status = $(this).find('input').prop('checked');
            	 if (status) {
            		$(this).find('input').removeAttr('checked');
            	 } else {
            		$(this).find('input').prop('checked',true);
            	 }
            	 	
            	});
            	 
                $("select[name='model.status']").val("${model.status}");
                
                fillRows("10", "", "", false);//填充空行
                fillRows("10", "yi_head", "yi_body", false);//填充空行
            });
            
            function selectAllOrCancel(obj,name){//全选狂操作
        		var checks = document.getElementsByName(name);
    			if(obj.checked){
    				for ( var i = 0; i < checks.length; i++) {
    					checks[i].checked = true;
    				}
    			}else{
    				for ( var i = 0; i < checks.length; i++) {
    					checks[i].checked = false;
    				}
    			}
        	
       	}
            
            function doRight(){
           		if($("input[name='weiuserId']:checked").length==0){
                           //alert("请先选中操作行！");
                           openAlert("请先选中未分配用户选项！", "e");
                            return false;
                        };
                  $.post(_path+"/pushMsg/group_addUser.html",$("#groupModelForm").serialize(),function(data){
                        if(data.success){
                        	$("#groupModelForm").submit();
                        }else{
                        	openAlert(data.text, "e");
                            //alert(data.text);
                        }
                    },"json");
           	}
           	
           	function doLeft(){
           		if($("input[name='yiuserId']:checked").length==0){
                           //alert("请先选中操作行！");
                           openAlert("请先选中已分配用户选项！", "e");
                            return false;
                        };
                $.post(_path+"/pushMsg/group_removeUser.html",$("#groupModelForm").serialize(),function(data){
                        if(data.success){
                        	$("#groupModelForm").submit();
                        }else{
                        	openAlert(data.text, "e");
                            //alert(data.text);
                        }
                    },"json");
           	}
        </script>
</head>

    <body >
<jsp:include page="/WEB-INF/pages/globalweb/modal.jsp" flush="true"></jsp:include>
    <form action="<%=request.getContextPath() %>/pushMsg/group_reuser.html" id="groupModelForm" method="post">
    <div class="container_ydht sl_all_bg" style="height:100%;">
        <%--<div class="toolbox">
            <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-primary" id="btn_fh" onclick="window.location.href='group_list.html';return false;">返回</button>
              <button type="button" class="btn btn-primary" id="btn_zj">保存</button>
            </div>
        </div>--%>
        <div class="tab" style="display: none;">
            <table width="100%" class="formlist" >
		        <tbody>
		          <tr>
		            <th width="15%">群组名称</th>
		            <td width="85%">
		                <input maxlength="30" placeholder="不要超过30个字符哦(^_^)" name="model.name" class="form-control" value="${model.name}"/>
		                <input type="hidden" name="model.id" value="${model.id }"/>   
		                <input type="hidden" name="model.type" value="user_add"/>   
		            </td>
		          </tr>
		          <tr>
		            <th width="15%">状态</th>
		            <td width="85%">
		                <select name="model.status" class="form-control">
		                    <option value="1">启用</option>
		                    <option value="0">不启用</option>
		                </select>
		            </td>
		          </tr>
		          <tr>
		            <th>群组说明</th>
		            <td style="word-wrap:break-word">
		                <textarea maxlength="300" placeholder="不要超过300个字符哦(^_^)" rows="3" cols="50" name="model.detail">${model.detail }</textarea>
		            </td>
		          </tr>
		        </tbody>
            </table>
        </div>
    
    <s:if test="model.id==null">
    <div style="height: 30px;padding-top: 10px;font-size: 14px;color: red;">新增群组在保存后才可以进行分配用户</div>
    </s:if>
    <div style="height: 30px;padding-top: 10px;font-size: 14px;">群组名称：${model.name}</div>
    <s:if test="model.id!=null">
        <div class="form-horizontal sl_all_form">
        <input type="hidden" name="jsyhStr" id="jsyhStr" value="${jsyhStr }"/>
            <div class="row">
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">用户名</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" name="member.userId" id="userId" value="${member.userId }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">姓名</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" name="member.userName" id="userName" value="${member.userName }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                	<div class="form-group">
                        <label for="" class="col-xs-5 control-label">学院</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" name="member.academy" id="academy" value="${member.academy }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">班级</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" name="member.classgrade" id="classgrade" value="${member.classgrade }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">年级</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" name="member.grade" id="grade" value="${member.grade }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                	<div class="form-group">
                        <label for="" class="col-xs-5 control-label">性别</label>
                        <div class="col-xs-7">
                        	<select id="sex" name="member.sex" class="form-control" value="${member.sex }" >
                        			<option value="" ></option>
                                    <option value="男" <c:if test="${member.sex eq '男'}">selected="selected" </c:if>>男</option>
                                    <option value="女" <c:if test="${member.sex eq '女'}">selected="selected" </c:if>>女</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                	<div class="form-group">
                        <label for="" class="col-xs-5 control-label">角色</label>
                        <div class="col-xs-7">
                        	<select id="jsdm" name="member.jsdm" class="form-control" value="${member.jsdm }" >
                                <option value="" ></option>
                                <c:forEach var="menu" items="${jsList}" varStatus="st">
                                    <option value="${menu.jsdm}" <c:if test = "${member.jsdm eq menu.jsdm}"> selected="selected"</c:if>>${menu.jsmc}</option>
                                 </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button type="button" class="btn btn-primary btn-sm" id="search_go" >查询</button>
                <button type="button" class="btn btn-primary btn-sm" id="add_btn"  >批量增加</button>
                <button type="button" class="btn btn-primary btn-sm" id="sc_btn">全部删除</button>
                <button type="button" class="btn btn-primary" id="btn_fh" onclick="window.location.href='group_list.html';return false;">返回</button>
            </div>
        </div>
    
    <div style="height: 30px;padding-top: 10px;font-size: 14px;color: red;">说明：可双击"未分配用户列表"中的记录至"已分配用户列表"</div>
  </s:if>  
  
  <!-- Modal end -->
    <Div class="row">
        <div class="col-sm-5" style="float: left" id="wei">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="">
                    <th colspan="7" style="text-align: center">
                        <font style="font-weight:bold;">未分配用户列表</font>
                    </th>
                </tr>
                </thead>
                <thead id="list_head">
                <tr class="active">
                    <th></th>
                    <th>用户名</th>
                    <th>姓名</th>
                    <th>学院</th>
                    <th>班级</th>
                    <th>年级</th>
                    <th>性别</th>
                </tr>
                </thead>
                <tbody id="list_body" class="body">
				<s:iterator value="wfpMemberPageList" var="menu" status="st">
	                <tr>
	                    <td><input id="weiuserId" name="weiuserId" type="checkbox" value="${menu.userId }"/></td>
	                    <td>${menu.userId }</td>
	                    <td>${menu.userName }</td>
	                    <td>${menu.academy }</td>
	                    <td>${menu.classgrade }</td>
	                    <td>${menu.grade }</td>
	                    <td>${menu.sex }</td>
	                </tr>
				</s:iterator>
                </tbody>
				<tfoot>
                <tr>
                    <td colspan="7">
					<ct:page pageList="${wfpMemberPageList }" query="${member }" queryName="member"/>
                    </td>
                </tr>
				</tfoot>
            </table>
        </div>
        <div class="col-sm-1" style="text-align: center;" id="middle">
            <div class="toolbox" style="margin-top: 150%;margin-left: 70%">
                <div class="btn-group" id="but_ancd" style="vertical-align: middle;">
                    <button type="button" class="btn btn-default" id="btn_zj" onclick="doRight()">右移</button>
                    <br><br><br><br>
                    <button type="button" class="btn btn-default" id="btn_save_index" onclick="doLeft()">左移</button>
                </div>
            </div>
        </div>
        <div class="col-sm-5" style="float: right;height: 480px;margin-bottom: 5px;color: #799bb5;overflow-x: hidden;overflow-y: scroll;margin-right: 15px;padding-right: 0px;border-color: #000;">
            <table class="table table-bordered table-hover">
				<thead>
                <tr class="">
                    <th colspan="7" style="text-align: center">
                        <font style="font-weight:bold;">已分配用户列表</font>
                    </th>
                </tr>
				</thead>
                <thead id="yi_head">
                <tr class="active">
                    <th></th>
                    <th>用户名</th>
                    <th>姓名</th>
                    <th>学院</th>
                    <th>班级</th>
                    <th>年级</th>
                    <th>性别</th>
                </tr>
                </thead>
                <tbody id="yi_body" class="body">
                <s:iterator value="yfpMemberPageList" var="menu" status="st">
	                <tr>
	                    <td><input id="yiuserId" name="yiuserId" type="checkbox" value="${menu.userId }"/></td>
	                    <td>${menu.userId }</td>
	                    <td>${menu.userName }</td>
	                    <td>${menu.academy }</td>
	                    <td>${menu.classgrade }</td>
	                    <td>${menu.grade }</td>
	                    <td>${menu.sex }</td>
	                </tr>
				</s:iterator>
                </tbody>
            </table>
        	</div>
    	</Div>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
    <script>
        refreshParent($('#result').val());
    </script>
  </s:if>
  </div> 
    </div>
  </form>
</body>
</html>