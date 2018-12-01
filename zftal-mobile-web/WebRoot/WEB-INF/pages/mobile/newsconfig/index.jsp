<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
  <head>
    <%@ include file="/commons/hrm/head_v5.ini" %>

  </head>
  
  <body>
   <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    	<div class="panel panel-primary" style="border:none">
		  <div class="panel-body">
		  	<%--<div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" data-toggle="modal"  id="btn_zj">增加</button>
             </div><br>
		    --%>
		    <table class="table table-hover" style="margin-top:10px">
		    	<tr><th>Key</th><th>描述</th><th>Value</th><th>操作</th></tr>
		    	<c:forEach items="${list }" var="config" varStatus="st">
		    		<tr>
		    			<td>${config.key }</td>
		    			<!-- <td><input class="form-control" type="text" name="comment" value="${config.comment }"></td> -->
		    			<td>${config.comment }</td>
			    		<td>
			    			<input maxlength="30" placeholder="不要超过30个字哦(^_^)" class="form-control" type="hidden" name="${config.key }" value="${config.value }">
			    		<c:if test="${config.key != 'max_news_recommend' and config.key != 'max_news_headline'}">
				    		<input maxlength="30" placeholder="不要超过30个字哦(^_^)" class="form-control" type="text" name="value" value="${config.value }">
			    		</c:if>
			    		<c:if test="${config.key == 'max_news_headline'}">
				    		<input maxlength="1" placeholder="只能填写一个数字哦(^_^)" class="form-control" type="text" name="value" value="${config.value }">
			    		</c:if>
			    		<c:if test="${config.key == 'max_news_recommend'}">
			    			  <select name="value"  value="${config.value }"  class="form-control">
                              <option value="1" <c:if test="${config.value == '1' }"> selected="selected"</c:if>>1</option>
                              <option value="2" <c:if test="${config.value == '2' }"> selected="selected"</c:if>>2</option>
                              <option value="3" <c:if test="${config.value == '3' }"> selected="selected"</c:if>>3</option>
                              <option value="4" <c:if test="${config.value == '4' }"> selected="selected"</c:if>>4</option>
                              <option value="5" <c:if test="${config.value == '5' }"> selected="selected"</c:if>>5</option>
                             </select>
                         </c:if>
			    		</td>
			    		
			    		<td>
			    		<button type="button" op="update" class="btn btn-info" onclick="doOperate(this,'${config.key}','<%=request.getContextPath()%>/newsconfig/config_update.html')">保存</button>&nbsp;&nbsp;
			    		<%--
			    		<button type="button" op="delete" class="btn btn-info" onclick="doOperate(this,'${config.key}','<%=request.getContextPath()%>/newsconfig/config_delete.html')">删除</button>
			    		--%></td>
		    		</tr>
		    	</c:forEach>
		    </table>
		  </div>
	  		<div class="panel-footer"></div>
		</div>
		<div id="add-modal" class="modal fade modal-insert" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-lg">
			    <div class="modal-content">
			      	<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="modal-title">新增</h4>
				      </div>
		<div class="modal-body">
        <form name="add-form" id="add-form" action="<%=request.getContextPath() %>/newsconfig/config_add.html" meyhod="post">
        <div style="color:red" id="errorMsg"></div>
          <div class="form-group">
            <label for="key-name" class="control-label"><span style="color:red">*</span>Key:</label>
            <input type="text" maxlength="15" placeholder="不要超过30个字符哦(^_^)" name="query.key" class="form-control" id="key-name">
          </div>
          <div class="form-group">
            <label for="value-text" class="control-label">Value:</label>
            <input type="text" maxlength="30" placeholder="不要超过30个字符哦(^_^)" name="query.value" class="form-control" id="value-text">
          </div>
          <div class="form-group">
            <label for="comment-name" class="control-label">描述:</label>
            <input type="text" maxlength="30" placeholder="不要超过30个字哦(^_^)" name="query.comment" class="form-control" id="comment-name">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" onclick="doSubmit()" class="btn btn-primary">提交</button>
      </div>
			    </div>
  			</div>
		</div>
		<script type="text/javascript">
		var max_news_topVal = $("input[name='max_news_top']").val();
		var max_news_headlineVal = $("input[name='max_news_headline']").val();
		var max_news_recommendVal = $("input[name='max_news_recommend']").val();
		$(function(){
			$("#btn_zj").click(function(){
				$("input[name='query.key']").val("");
				$("input[name='query.value']").val("");
				$("input[name='query.comment']").val("");
				$("#errorMsg").empty();
				$('#add-modal').modal('show');
			});
		});
			function doOperate(obj,key,url) {
				if(key != 'max_news_recommend'){
					var value = $(obj).parent().parent().find('input[name="value"]').val();
				}else{
					var value = $(obj).parent().parent().find('select[name="value"]').val();
				}
				var comment = $(obj).parent().parent().find('input[name="comment"]').val();
				//console.log("key: " + key + ",value: " + value + ",op: " + $(obj).attr('op'));
				var op = $(obj).attr('op');
				if (op == 'delete') {
					openAlert('确定要删除吗？','w');
					$('#why_sure').click(function(){
						$.post(url,{'query.key':key,'query.value':value,'query.comment':comment},function(data){
							//console.log(data);
							processCall(data,function(){
								location.reload();
							});
						});
					})
				} else if (op == 'update') {
					$.post(url,{'query.key':key,'query.value':value,'query.comment':comment},function(data){
							if(data.success){
								processCall(data,function(){
									location.reload();
								});
							}else{
								openAlert(data.text, "e");
								if(key == 'max_news_top'){
									$("input[name='max_news_top']").closest("td").find("input[name='value']").val(max_news_topVal);
								}else if(key == 'max_news_headline'){
									$("input[name='max_news_headline']").closest("td").find("input[name='value']").val(max_news_headlineVal);
								}else if(key == 'max_news_recommend'){
									$("input[name='max_news_recommend']").closest("td").find("select[name='value'] option[value='"+max_news_recommendVal+"']").attr('selected','selected');
								}
							}
						});
				}
			}
			
			function doSubmit() {
				var comment = $('#comment-name').val();
				var key = $('#key-name').val();
				var value = $('#value-text').val();
				var url = $('#add-form').attr('action');
				if (key == null || key == '') {
					//openAlert("Key不能为空", "e");
					$('#errorMsg').html('Key不能为空');
					return;
				}
				$.post(url,{'query.key':key,'query.value':value,'query.comment':comment},function(data){
					if (data.success) {
						$('#add-modal').modal('hide');
						location.reload();
					} else {
						$('#errorMsg').html(data.text);
					}
				});
			}
		</script>
  </body>
</html>
