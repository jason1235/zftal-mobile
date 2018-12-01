<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
        
        <script type="text/javascript">
        var modelID="${model.id}"; 
        </script>
        <script type="text/javascript" 
            src="<%=systemPath %>/js/mobile/fpqz.js"></script>
        <script type="text/javascript">
            //为select option 增加title属性提示
            jQuery(function(){
                //if(""!="${model.id}"){
	            //    wfpGrid= new WfpGrid();
	            //    loadJqGrid("#tabGrid","#pager",wfpGrid);
	
	            //    yfpGrid=new YfpGrid();
	            //    loadJqGrid("#yfpTabGrid","#pagerYfp",yfpGrid);
                //}
                
                

                jQuery("#btn_zj").click(function(){
                    var name = jQuery("input[name='model.name']");
                    if(name.val()==null||trim(name.val()).length==0){
                        //alert("群组名称不能为空");
                        jQuery("#myAlert").find("#alertContent").html("群组名称不能为空");
                        jQuery("#myAlert").find("#why_sure").hide();
                        jQuery("#myAlert").find("#why_cancel").show();
                        jQuery("#myAlert").find("#alertBody").removeClass();
                        jQuery("#myAlert").find("#img").removeClass();
                        jQuery("#myAlert").find("#alertBody").addClass("row alertError");
                        jQuery("#myAlert").find("#img").addClass("glyphicon glyphicon-remove");
                        jQuery("#myAlert").modal("show");
                        return;}
                    jQuery.post("<%=request.getContextPath()%>/pushMsg/group_save.html",
                    		jQuery("#groupModelForm").serialize(),function(data){
                        if(data.success){
                            location.href="<%=request.getContextPath()%>/pushMsg/group_list.html";
                        }else{
                        	alert(data.text);
                        }
                                
                	},"json");
                });
                
                jQuery("#add_btn").click(function(){
                	var userId = jQuery('#userId').val();
                	var userName = jQuery('#userName').val();
                	var academy = jQuery('#academy').val();
                	var classgrade = jQuery('#classgrade').val();
                	var grade = jQuery('#grade').val();
                	var sex = jQuery('#sex').val();
                	jQuery.post("<%=request.getContextPath()%>/pushMsg/group_addAll.html?member.userId="+userId+"&member.userName="+userName+
                				"&member.academy="+academy+"&member.classgrade="+classgrade+"&member.grade="+grade+"&member.sex="+sex+
                				"&member.groupId="+modelID,
                    		jQuery("#groupModelForm").serialize(),function(data){
                        if(data.success){
                        	jQuery("#tabGrid").jqGrid().trigger('reloadGrid');
                        	jQuery("#yfpTabGrid").jqGrid().trigger('reloadGrid');
                        }else{
                        	alert(data.text);
                        }
                                
                	},"json");
                });
                
                jQuery("#sc_btn").click(function(){
                	var groupId = jQuery('input[name="model.id"]').val();
                	jQuery.post("<%=request.getContextPath()%>/pushMsg/group_removeFp.html?member.groupId="+modelID,
                    		"",function(data){
                        if(data.success){
                        	jQuery("#tabGrid").jqGrid().trigger('reloadGrid');
                        	jQuery("#yfpTabGrid").jqGrid().trigger('reloadGrid');
                        }else{
                        	alert(data.text);
                        }
                                
                	},"json");
                });
                

                //jQuery("select[name='model.status']").val("${model.status}");
            });
        </script>
</head>

    <body >
    <div class="container_ydht sl_all_bg" style="height:100%;">
    <form action="pushMsg/group_save.html" id="groupModelForm">
        <div class="toolbox">
            <div class="btn-group" id="but_ancd">
              <%--<button type="button" class="btn btn-primary" id="btn_fh" onclick="window.location.href='group_list.html';return false;">返回</button>
              --%><button type="button" class="btn btn-primary" id="btn_zj">保存</button>
            </div>
        </div>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
        <div class="tab">
            <table width="100%" class=" formlist" >
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
		                <select name="model.status" class="form-control" value="${model.status }">
		                    <option value="1" <c:if test="${model.status == '1' }"> selected="selected"</c:if>>启用</option>
		                    <option value="0" <c:if test="${model.status == '0' }"> selected="selected"</c:if>>不启用</option>
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
    </form>
    <%--<s:if test="model.id==null">
    <div style="height: 30px;padding-top: 10px;font-size: 14px;color: red;">新增群组在保存后才可以进行分配用户</div>
    </s:if>
    <s:if test="model.id!=null">
    <s:form name="form" method="post" action="/pushMsg/group_edit.html" theme="simple">
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
                                    <option value="男" >男</option>
                                    <option value="女" >女</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button class="btn btn-primary btn-sm" id="search_go" onclick="searchResult();return false;">查询</button>
                <button class="btn btn-primary btn-sm" id="add_btn"  onclick="return false;">批量增加</button>
                <button class="btn btn-primary btn-sm" id="sc_btn" onclick="return false;">全部删除</button>
            </div>
        </div>
    </s:form>
   <div style="height: 30px;padding-top: 10px;font-size: 14px;color: red;">说明：可双击"未分配用户列表"中的记录至"已分配用户列表"</div>
  </s:if>  
  <div style="width:1000px;height:300px;">
      <div  style="width:450px;float:left;">
                <table id="tabGrid"></table>
                <div id="pager"></div>
            </div>
         <div style="width:10px;float:left;">&nbsp;&nbsp;&nbsp;</div>
        <div  style="width:450px;float:left;">
                <table id="yfpTabGrid"></table>
                <div id="pagerYfp"></div>
        </div>
    </div>
    --%>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
    <script>
        refreshParent($('#result').val());
    </script>
  </s:if>
  </div> 
    </div>
    <%--
     <div class="tab">
     <form action="pushMsg/group_save.html" id="groupModelForm">
      <table width="100%" class=" formlist" >
            <tr>
             <div class="toolbox">
                <div class="buttonbox"> 
                    <ul>
                    <li><a href="#" class="btn_zj" id="btn_zj">保存</a></li>
                    <li><a href="#" class="btn_fh" id="btn_fh" onclick="window.location.href='group_list.html';return false;">返回</a></li>
                    </ul>
                </div> 
            </div>  
          </tr>
        <tbody>
          <tr>
            <th width="15%">群组名称</th>
            <td width="85%">
                <input name="model.name" value="${model.name}"/>
                <input type="hidden" name="model.id" value="${model.id }"/>   
                <input type="hidden" name="model.type" value="user_add"/>   
            </td>
          </tr>
          <tr>
            <th width="15%">状态</th>
            <td width="85%">
                <select name="model.status">
                    <option value="1">启用</option>
                    <option value="0">不启用</option>
                </select>
            </td>
          </tr>
          <tr>
            <th>群组说明</th>
            <td style="word-wrap:break-word">
                <textarea rows="3" cols="50" name="model.detail">${model.detail }</textarea>
            </td>
          </tr>
        </tbody>
      </table>
      </form>
  </div>
  <s:if test="model.id==null">
    <div style="height: 30px;padding-top: 10px;font-size: 14px;color: red;">新增群组在保存后才可以进行分配用户</div>
  </s:if>
  <s:if test="model.id!=null">
    <tr>
                <div class="searchtab">
                <s:form name="form" method="post" action="/pushMsg/group_edit.html" theme="simple">
                 <input type="hidden" name="jsyhStr" id="jsyhStr" value="${jsyhStr }"/>
                <table width="100%" border="0" id="searchTab">
                    <tbody>

                            <tr>
                            <th>用户名</th>
                            <td>
                                <input type="text" class="form-control" name="member.userId" id="userId"/>
                            </td>
                            <th>姓名</th>
                            <td>
                                <input type="text" class="form-control" name="member.userName" id="userName"/>
                            </td>
                            <td>
                                <div class="btn">
                                    <button id="search_go" class="btn btn-primary"
                                        onclick="searchResult();return false;">
                                        查 询
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                </s:form>
            </div>
    <div style="height: 30px;padding-top: 10px;font-size: 14px;color: red;">说明：可双击"未分配用户列表"中的记录至"已分配用户列表"</div>
  </s:if>  
  <div style="width:800px">
      <div  style="width:450px;float:left;">
                <table id="tabGrid"></table>
                <div id="pager"></div>
            </div>
            <div width="10px;float:left;"></div>
        <div  style="width:300px;float:right;padding-right: 15px;">
                <table id="yfpTabGrid"></table>
                <div id="pagerYfp"></div>
        </div>
    </div>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
    <script>
        refreshParent($('#result').val());
    </script>
  </s:if>
  </div> --%>
</body>
</html>